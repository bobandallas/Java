package com.example.java26.week1;

/**
 *  CAS: compare and swap
 *
 *  CAS(obj reference address, field reference, expected value, new value)
 *
 *  if(obj.field == expected value) {
 *      obj.field = new value;
 *      return true
 *  } else {
 *      return false;
 *  }
 *
 *  Atomic Library
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Atomic Integer 非常快
 * 1. volatile int id + synchronized increment()
 * 2. synchronized increment() + synchronized get()
 * 3. atomic Integer
 */
class IncrementIdGenerator {
    private AtomicInteger id; //这里没必要加volatile。加了也只会限制reference得到最新的           // Bo.

    public IncrementIdGenerator() {
        this(0);
    }

    public IncrementIdGenerator(int val) {
        this.id = new AtomicInteger(val);
    }

    public void increment() {
        id.incrementAndGet();
    }

    public int get() {
        return id.get();
    }
}
/**
 *  synchronized
 *      1. unfair lock
 *      2. obj -> waiting 同一个object只有一个waiting list
 *         notifyAll()
 *         notify()
 *      3. synchronized(obj)
 *         一旦进入队列无法退出，无法设置退出时间，直到拿到锁为止
 *         但是reentrantLock可以做一个try lock，锁被人用了就做其他行为
 *      4. func1() {
 *              synchronized() {
 *                  不能夸function，在fun2里面release
 *              }
 *         }
 *
 *         func2() {
 *             release
 *         }
 *
 *
 *  ReentrantLock {
 *      state = 0 : unlock
 *      state >= 1 : locked
 *      current thread owner : thread reference
 *      condition : waiting list
 *
 *      lock() / unlock()
 *      tryLock(time)
 * }
 *
 * AbstractQueuedSynchronizer : Queue(LinkedList) 先进队列后拿锁
 *                                          <-  N5   CAS(tail, expected Node(T3), N5)
 *      Node(T1) <-> Node(T2) <-> Node(T3)  <-  N4   CAS(tail, expected Node(T3), N4)
 *      head                      tail      <-  N6   CAS(tail, expected Node(T3), N6)
 *    假如当N6拿到了tail，其他的等待加入队列的node会等待
 *
 * ReentrantLock lock = new ReentrantLock();
 * lock.newCondition();
 * lock.newCondition();
 *     *     *     *     *     *     *     *     *     *     *     *     *
 *  BlockingQueue
 *     producer -> [][][][][][][][][][] -> consumer
 *
 *    consumer poll() {
 *        lock();
 *        while(queue is empty) {
 *            current thread / consumer await();
 *        }
 *        take(e)
 *        notify producer
 *        unlock();
 *    }
 *
 *
 *    producer offer(e) {
 *        lock();
 *        while(queue is full) {
 *            current thread / producer await();
 *        }
 *        add(e) into queue
 *        notify consumer
 *        unlock();
 *    }
 */
class MyBlockingQueue<T> {

    private final Object[] queue;
    private int size;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition fullWaitingList = lock.newCondition();
    private final Condition emptyWaitingList = lock.newCondition();
    private int startIndex;
    private int endIndex;

    public MyBlockingQueue(int size) {
        this.queue = new Object[size];
    }

    public T poll() throws Exception {
        lock.lock();
        try {
            while(size == 0) {
                emptyWaitingList.await();
            }
            Object res = queue[startIndex++];
            if(startIndex == queue.length) {
                startIndex = 0;
            }
            size--;
            fullWaitingList.signal();
            return (T)res;
        } finally {
            lock.unlock();
        }
    }

    public void offer(T ele) throws Exception {
        lock.lock();
        try {
            while(size == queue.length) {
                fullWaitingList.await();
            }
            queue[endIndex++] = ele;
            if(endIndex == queue.length) {
                endIndex = 0;
            }
            emptyWaitingList.signal();
            size++;
        } finally {
            lock.unlock();
        }
    }
}


/**
 *  Thread Pool (diff thread pools + how to decide thread number /count)
 *  ThreadPoolExecutor(
 *      int corePoolSize, 最少size
 *      int maxPoolSize,  最大size
 *      int idleTime,     idleTime auto 维持pool size
 *      TimeUnit unit,
 *      BlockQueue queue,
 *      ThreadFactory factory
 *  )
 *
 *     producer -> ([][][][][][] blocking queue  ->  worker thread) Thread pool
 *
 *     1. fixedThreadPool : core == max
 *     2. cachedThreadPool: core != max
 *     3. scheduledThreadPool: delayedWorkQueue 最小堆对比timestamp
 *       thread1
 *       thread2(wait 3s) -> [task1(3s)][task3(5s)][task5(t3)]
 *       thread3
 *
 *       time wheel
 *       [0][1][2][..][59] minute
 *             |
 *            p1
 *
 *       [0][1][2][..][59] second
 *        |
 *       p2
 *
 *  ForkJoinPool (stealing algorithm)把大的Task分成很多unit来多线程，最后merge
 *                                      用的common pool默认是cpu + 1（backup instance）
 *     [][][][][][][][][][][] -> T1 [x1][x2][x3][x4]
 *                               T2 [X5][][][]
 *    *    *    *    *    *    *    *    *    *    *    *    *    *    *
 * thread pool thread number （fixed thread pool）
 *    CPU / service / calculator based task :  core number + 1
 *    (IO + service) task : n * ( 1 / (1 - IO percentage)) + 1 （一秒钟能完成一个线程需要多少个线程估计）
 *    50%   50%
 */
class ThreadPoolTest {
    private final static ExecutorService pool = Executors.newCachedThreadPool();

    public static void get() throws Exception {
        Future<Integer> f = pool.submit(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 5;
        });
        System.out.println(f.isDone()); //false
        int res = f.get(); // block方法，当任务完成了就会解除
        System.out.println(res);
    }

    public static void main(String[] args) throws Exception {
        get();
        get();
    }
}

/**
 *  deadlock
 *
 *      T1 : holding lock A {
 *          try to lock B {
 *
 *          }
 *      }
 *
 *      T2 : holding lock B {
 *          try to lock A {
 *
 *          }
 *      }
 *
 *   1. timeout
 *   2. lock in order
 *   3. look up table
 */

/**
 *
 *  semaphore (permit) 多个线程访问一个lock， permit个
 *      example
 *          permit = 4
 *          T1 acquire permit = 3
 *          T2 acquire permit = 2
 *          T2 release permit = 3
 *  countdownlatch
 *        类似于计数器，多少个线程完成之后开始执行latch.await的线程
 */
class CountDownLatchDemo {
    private static final CountDownLatch latch = new CountDownLatch(2);
    public synchronized static void func1() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
        System.out.println(Thread.currentThread() + ", count down");
    }
    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" this is t1");
        });
        Thread t2 = new Thread(() -> func1());
        Thread t3 = new Thread(() -> func1());
        t1.start();
        t2.start();
        t3.start();

        t1.join();
    }
}
/**
 *  cyclicbarrier
 *    *    *    *    *    *    *    *    *    *    *    *    *
 *
 *      read lock + write lock == share lock + exclusive lock
 *
 *      share lock only blocks ex lock
 *      ex lock blocks share lock + ex clock
 *
 *       *    *    *    *    *    *    *    *    *    *    *
 *
 *  1.为什么concurent HashMap是线程安全的
 *  2. CMS
 *   abstract queued synchronized
 *   how to impl reentrant lock
 *   x86 / hardware
 *   how does g1 work
 *   treemap source code
 *
 *    *    *    *    *    *    *    *    *    *
 *    Jan 2nd
 *     1. java 8 stream api / parallel stream / completable future
 *     2. sql / index...
 */
