package com.example.java26.week1;

/**
 *  Stack
 *      thread 1 to 1 stack
 *
 *  Heap
 *      Object / instance
 *      可以在JVM中指定回收策略，大小，GC设置
 *      [ eden area ][s0][s1]  young gen (minor gc) STW（gc root扫描，类似于标签）
 *      [                   ]  old gen   (major gc) CMS / mark and sweep / G1
 *
 *      CMS concuerent thread 来回收， 减少回收间隔时间。会造成碎片化所以需要mark and sweep来进行压缩，如果仍然满了就是out of memory
 *      1. initial mark (STW)   找到GC Root
 *      2. concurrent mark      通过GC Root 做 Concurent Mark
 *      3. final mark (STW)     不能保证前面两步完全准确，类似于前面两步标记为回收，后面又重新引用。
 *                              为了操作，扫描 保证准确性 所以有final mark
 *      4. concurrent remove
 *      Java 8 之后是G1
 *      G1  ： 在 CMS 上减少了更多的STW， 把yong 和 old gen 分成了很多小的区块， 在 OLD和Young上一起工作。
 *             在每一次回收中不会回收所有的block，评估策略选取几个block进行回收，把集合起来的可用Object放到另一个空的Block里面，
 *             然后把回收的block down掉，同时还会评估一下会不会带着OLD一起进行。
 *      [][][Y][Y][][O][][]
 *      [][][][][O][][][H]
 *      [][][][O][][O][][]
 *      [][][][][][][][]
 *      [][][][][][][][]
 *
 *
 *      full gc = minor + major gc
 *
 *      out of memory
 *          1. restart application
 *          2. use diff reference type
 *              StrongReference JVM 不会回收
 *              SoftReference   JVM 准备 out of memory时候会优先回收所有SoftReference
 *              WeakReference   更像是不确定的Reference. 如果不是很在意intance是否存在你的JVM里面就用它
 *              PhantomReference + Reference Queue 不想同时加载Instance就可以用queue查看
 *          3. change jvm parameters
 *          4. heap dump + analyze memory leak （snapshot 记录一下）
 *              JProfiler / Memory Analyzer / Java Mission Control 这些软件来查看JVM
 *              static ConcurrentHashMap
 *              connection 没人close会一直存
 *
 *      stack over flow
 *     *     *     *     *     *     *     *     *     *     *     *     *     *
 *  Thread             插队unfair lock
 *                      Blocked (enter / exit consume CPU resource)（手动无法操作进入Block状态）//Bo
 *                      |
 *    sleep()     -    [runnable  ->  running]  - wait() / await() / LockSupport - Wait
 *     (并不会释放锁)                     |           会释放锁 //Bo.
 *                                    terminate
 *                      有一定几率线程自动醒来，所以尽量塞到while loop中。很莫名奇妙
 *                   int num = 1
 *                T1 -> get lock -> if(num is odd)  -> print / i++
 *
 *                T2 -> get lock ->  if(num is even) -> print
 *
 *                blocking queue
 *        producer -> [][][][][] -> consumer //如果consumer不断check盒子会不断加锁解锁，这时候consumer wait，生产者来notify就非常合适
 *
 *  synchronized + wait() / notify()  ： target是instance Object
 *
 *  属于不同的waiting list
 *  T1: Obj1 -> wait() -> T1
 *  T2: Obj2 -> wait() -> T2
 *
 *  属于相同的waiting list
 *  T1: Obj1 -> wait() -> T1
 *  T2: Obj1 -> wait() -> T1 -> T2
 *  当Thread释放Synchronized之后，之后的线程的顺序是由CPU来决定的，是unfair lock
 */

// wait 只能放在synchronized block 里面
class SynchronizedTest {
    public synchronized void func1() {
//        synchronized (this) {  // 第二种synchronized blocked只会让方法体内部分代码sync
        try {
            Thread.sleep(3000);
            System.out.println(Thread.currentThread());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        }
    }


    //对于静态方法 即使s1.func2() 和 s2.func2()同时加入thread， 也会被锁住，因为是相同的class object
    public synchronized static void func2() {
        try {
            Thread.sleep(3000);
            System.out.println(Thread.currentThread());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception{
        SynchronizedTest s1 = new SynchronizedTest();
        SynchronizedTest s2 = new SynchronizedTest();
        Thread t1 = new Thread(() -> s1.func1());
        Thread t2 = new Thread(() -> func2());
        // t1，t2调用的不是同一个instance，所以是不同的锁
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

/**
 *
 *      CPU1            CPU2           CPU3
 *      L1              L1              L1
 *      L2              L2              L2
 *      L3              L3              L3
 *
 *
 *          Main memory  x = 12
 *          Volatile是JVM提供的一个轻量级的同步机制。有两个作用：
 *          ①：保证可见性（缓存一致性协议MESI）
 *          ②：保证有序性（禁止指令重排优化）
 *      线程不安全的两种解决方法
 *    1。volatile int x = 10;  会有barrier阻碍 x回到main memory期间不被读取 比如 x++ -> barrier -> print(x)
 *      但是synchronized在写回memory期间会被读取， 它也会有happen before的原则，但是是对整个代码块而言，在整个代码块执行完后才会write
 *    2。synchronized void increment() {
 *        /
 *        . x = 10
 *        .
 *        .  x *= 10
 *        .
 *        .  x -= 10
 *        /
 *    }
 *
 *
 *    void print() {
 *        System.out.println(x);
 *    }
 *
 *    main() {
 *      T1 : increment()
 *      T2 : increment()
 *      T3 : print()
 *    }
 *
 *    T1 increment() {
 *        x++;
 *    }
 *
 *    T2 increment() {
 *
 *    }
 *
 *
 */

/**
 *   int x = 10;
 *   T1 -> x++
 *   T2 -> x++
 *   T3 -> x = 12
 *   *    *    *   *    * *   *    * *   *    *
 *  volatile
        1. happen before rule
 *              --------- --------- --------- > timeline
 *              get  write(x = 10)   get    get   可以保证write之后的的get都是最新的数据，在某个时间点上是最新的
 *                                                  synchronized开始之后会有happen b4的原则。不用担心
 *      2. read from main memory (visibility)
 *      3. 防止reordering 或者其他优化打乱顺序等，
 *         并不能保证多个操作时候的线程安全，比如多个线程同时write，这时候他们会同时read到cpu中，最终结果只会执行一次或少次
 */
class VolatileTest1 {
    private static volatile int x = 10;

    public static void inc() {
        //线程不安全，多个操作不一定从哪个节点read get
        /**
         * x = 10
         */
        x = 20;
        /**
         * x = 20
         */
    }
}

class VolatileTest2 {
    private static int a = 0;
    private static boolean b = true;

    public static void update() {
        //hoist b = false; reorder
        a = 10;
        b = false;

        /**
         *   if(b == false)
         *      100% a==10
         */
    }

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> update());
        Thread t2 = new Thread(() -> {
            if(b == false) {
                System.out.println(a); //a = 10 / a = 0// reorder 如果在同一个thread不会出现reorder的问题
                                        // 如果a和b任意一个变量加上 volatile就没问题了，会在a和b之间存在barrier
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}


class VolatileTest3 {
    private volatile static boolean flag = true; //如果不加volatile t1中的while（flag）
                                                // 会直接被优化(reorder， pre-compile)替换为while(true)


    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {
            while(flag) {}
            System.out.println("print t1 out of while loop: " + flag);
        });
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {}
            flag = false;
            System.out.println("t2 flag: " + flag);
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

/**
 *  ConcurrentHashMap
 *  public void update() {
 *      put(k1, v1);
 *                               -> get(k1) = v1 , get(k2) = null
 *      put(k2, v2);
 *  }
 *
 *  ConcurrentHashMap
 *          get会得到当前最新的，如果要一起返回，加synchronized
 *  public void update() {
 *      for(List<Integer> id: stus) { //10 students
 *          map.put(id, v);
 *      }
 *      t1: map.put(id1, v);
 *      t2: map.put(id2, v);
 *      t3: map.put(id3, v);
 *      ..
 *      t10: map.put(id10, v);
 *  }
 *
 *  public void get() {
 *      map.iterator() => getAll() -> 5 entries
 *  }
 *
 *  HashMap
 *  public synchronized void update() {
 *      put(k1, v1);
 *      put(k2, v2);
 *      put(k1, v2);
 *      put(k1, v3);
 *  }
 *  public synchronized get() {
 *
 *  }
 *                           -> get(k1) = v1 , get(k2) = v2
 *
 * concurrent hashmap
 * public void update() {
 *   cMap.compute(k, () -> {});
 * }
 *
 *
 *  * concurrent hashmap
 *  下面的代码不是线程安全，在两行代码之间如果别人对cMap进行操作，会导致依赖cnt的接下来的代码出现错误
 *
 *  * public void update() {
 *  *   int cnt = cMap.getOrDefault(k, v);
 *      cMap.put(k, cnt + 1)
 *  * }
 */

/**
 *  synchronized / volatile / CAS
 *                          volatile / CAS
 *                            /
 *          AbstractQueuedSynchronized
 *          /                   \
 *  ReentrantLock       CountDownLatch ..
 *        +
 *   Condition
 *       /
 *  blocking queue
 *      /+thread
 *   Thread Pool (diff thread pools + how to decide thread number /count)
 *
 *                        synchronized / volatile / CAS
 *                           |                  \
 *                     concurrent hashmap       copy on write list
 *
 *
 *
 *                                  synchronized
 *                         /             \               \
 *                       hashtable   string buffer       vector
 *
 *
 *                              CAS  / volatile
 *                                  |
 *                               Atomic Library
 *
 *
 */