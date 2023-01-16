package com.example.java26.week1;

import java.util.*;

/**
 *  array
 */
class ArrayTest {
    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3};
        int[] arr2 = new int[]{1, 2, 3};
        Object[] arr3 = new Object[10];
        List<Integer>[] graph = new List[10];
        for(int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }

        String str = "";
        char[] chs = str.toCharArray();
    }

    public static String reverse(String str) {
        char[] chs = str.toCharArray(); // 会创建新的charArray 并不是在原string上进行操作，string是immutable
        for(int i = 0, j = chs.length - 1; i < j; i++, j--) {
            char ch = chs[i];
            chs[i] = chs[j];
            chs[j] = ch;
        }
        return new String(chs);
    }
}
/**
 *  ArrayList
 *      get by index 很多的时候用ArrayList
 *      add 到特定的index， 会把所有后面的东西往后移动一位
 *      delete 和 最后一位交换O（1）
 *  LinkedList（Double Linked List，要讲一下Node 之类的）
 *      add(index, element) 很多的时候用LinkedList
 */

/**
 *  HashMap<K, V>
 *      基于array，拓展的时候也会重新分配新的array
 *      [LinkedList][][][][][][][Red black tree]
 *
 *      put(key, value)
 *      get(key)
 *
 *      time complexity : O(1)
 *      不要用equals比较hash
 *  id -> hash(id)
 */
class Day2Student {
    private int id;

    public Day2Student(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day2Student that = (Day2Student) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Day2Student{" +
                "id=" + id +
                '}';
    }
}

/**
 *  在用custom object做为key的时候一定要记得重写equals 和 hashCode 方法。
 *  不然 两个完全一样的object因为地址不同而判断失败。
 *  看源码得出，第一步判定hashcode，第二部判定 key 是否相等 或者 调用k中的equals来判定。
 *  所以要重写 Object的hashCode 和 Object的equals
 *
 *
 *   custom Object Key 最好是immuteable。 不要改变导致重写之后的hashCode方法无效
 *
 *   Q&A：
 *   1. 为什么hashCode返回 Int？ 因为HashMap用的是Array，Array最大数组长度为Integer.MAX_VALUE
 *   2. 如果hashCode返回固定值？ K，V无法均匀的分布在bucket上，
 *      导致linkedlist很长，最终转换为RedBlackTree很大，时间复杂度也会很大。Resize也无法进行
 *
 */
class HashMapTest {
    public static void main(String[] args) {
        //s1.hashCode == s2.hashCode
        //s1.equals(s2) == true
        Day2Student s1 = new Day2Student(1);
//        Day2Student s2 = new Day2Student(1);
        Map<Day2Student, Integer> map = new HashMap<>();
        map.put(s1, 1);

        //在重新set ID之后，key， equals仍然相等，但是hashCode（基于ID生成）变了，所以会失败
        s1.setId(2); // null。 if hashcode返回固定值，则不会范围null
        System.out.println(map.get(s1));
    }
}

/**
 *  TreeMap
 *  key必须是comparable 如果不是
 *  1。可以 custom 一个
 *  class A implements Comparable{
 *      @overide
 *      public int compareTo(Object o){
 *          return 0;
 *      }
 *  }
 *  2。加一个Comparator匿名类（重写类中的function）， Override compare function
 *  TreeMap<Day2Student, Integer> map = new TreeMap<>(new Comparator<>{
 *      @Override{
 *          public int compare(Day2Student s1, Day2Student s2){
 *              return 0
 *          }
 *      }
 *  })
 *
 *  3。 lamda Expression
 */
class TreeMapTest {
    public static void main(String[] args) {
        Day2Student day2Student = new Day2Student(1);
        TreeMap<Day2Student, Integer> map = new TreeMap<>((s1, s2) -> s1.getId() - s2.getId());
        map.put(day2Student, 1);
        System.out.println(map.get(day2Student));
    }
}


class Test {
    public static void main(String[] args) {
        String a = "123";
        String b = "123";
        System.out.println(a == b);
    }
}
/**
 *  loop through map
 */
class ForLoopMap {
    public static void main(String[] args) {
        Map<Integer, Integer> m = new HashMap<>();
        m.put(1, 1);
        m.put(2, 2);
        m.put(3, 3);
//        for(Map.Entry<Integer, Integer> entry: m.entrySet()) {
//            System.out.println(entry);  //内部调用的就是iterator
//        }
//        for(int key: m.keySet()) {
//            System.out.println(key);
//        }
//        for(int val: m.values()) {
//            System.out.println(val);
//        }
//        Iterator<Map.Entry<Integer, Integer>> itr = m.entrySet().iterator();
//        while(itr.hasNext()) {
//            System.out.println(itr.next());
                // 如果此时使用 m.remove()会报错 //@Bo
//        }
//            java 8 之后
//        m.entrySet().forEach(System.out::println);
//        m.entrySet().stream().forEach(System.out::println);
    }
}
/**
 * Stack
 * Queue
 */
class DequeTest {
    public static void main(String[] args) {

        Deque<Integer> q = new LinkedList<>(); //或者 ArrayDeque
    }
}
/**
 *  heap.offer() / poll()
 */
class PriorityQueueTest {
    public static void main(String[] args) {
//        PriorityQueue<Integer> heap = new PriorityQueue<>((v1, v2) -> v2 - v1);
//        PriorityQueue<Day2Student> heap = new PriorityQueue<>((v1, v2) -> v2.getId() - v1.getId());
//        Day2Student s1 = new Day2Student(2);
//        Day2Student s2 = new Day2Student(3);
//        Day2Student s3 = new Day2Student(4);
//        heap.offer(s1);
//        heap.offer(s2);
//        heap.offer(s3);
//        s1.setId(10); // 不会更新 Heap， heap只会在Offer或者Poll的时候改变， 所以只能想办法拿出来再改变
//        PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue<>((e1, e2) -> {
//            Integer v1 = e1.getValue(); //getValue()返回的是Integer， 要么转换为int进行比较，要么就进行equals（）比较。
                                          // 如果直接 e1.getValue() == e2.getValue() 会出现constant pool的错误
//        });
    }
}

/**
 *  iterator + concurrent modification exception
 *      ArrayList 中 add, addByIndex, Remove... 会导致 modCount++
 *      interator创建后会copy modCount 到 expectModCount
 *      如果你想操作数据都需要通过iterator进行操作，不然都视为另一个线程对它进行了修改，抛出异常。因为每一步iterator操作都会checkFormodification
 *      都会比较数组当前的modCount和之前记录下来的expectModCount，如果不同就视为了线程错误，抛出异常。如果想要改变数组，要调用iterator中的remove
 *      或其他方法，这些方法会更新expectModCount
 *
 */
class IteratorTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(4);
        list.add(3);
        list.add(2);
        for(int i = 0, len = list.size(); i < len; i++) {
            list.add(5);
        }
        System.out.println(list);
        //会抛出异常， 因为for(int v: list) 就是一个iterator
//        for(int v: list) {
//            list.add(v + 2);
//        }
        //会抛出异常
//        Iterator<Integer> itr = list.iterator();
//        while(itr.hasNext()) {
//            System.out.println(itr.next());
//            list.add(5);
//        }
        //避免这种问题就是用index for loop
    }
}

/**
 *  thread vs process
 *  thread working unit
 *  process 包含多个thread还有一堆resource
 */
class ThreadExample {
    public static void main(String[] args) throws Exception {
        System.out.println(Thread.currentThread());
        Thread t1 = new MyThread();
        Thread t2 = new Thread(() -> System.out.println(Thread.currentThread()));
        t1.start();
        t2.start();
        // main thread 会先结束，所以要等一下用Jion
        t1.join();
        t2.join();
    }
}

/**
 *  CPU1            ---> TIME LINE
 *      T1  -----           ------
 *      T2       -----
 *      T3                        -------
 *      T4             -----
 *      T5                               ------
 *
 */
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread());
    }
}
/**
 *  Jvm
 *  Stack
 *      thread 1 to 1 stack
 *      存自己用的代码块等等，并不会与别人公用
 *
 *  Heap
 *      Object / instance
 *      [ eden area ][s0][s1]  young gen (minor gc) STW
 *      [                   ]  old gen   (major gc) CMS / mark and sweep / G1
 *      eden area 整个移到s0 s1避免碎片化
 *      STW停下来然后操作
 *      CMS造成碎片化，不会停
 *      full gc = minor + major gc
 *
 *  memory leak
 *  out of memory
 *  stack over flow
 *
 *  synchronized
 *  volatile
 *  CAS
 */


/**
 * Summary:
 * 1.  List<Integer> list = Array.asList(1,2,3,4); list.add(5);
 *     为什么Array.asList出来的List无法add，且Size是固定的？
 * 2.
 */