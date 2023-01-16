package com.example.java26.week2;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *  java 8 new features
 *      1. perm gen -> meta space
 *      2. hashmap -> red black tree
 *      3. concurrent hashmap ->
 *
 *      4. functional interface
 *      5. lambda expression
 *      6. stream api
 *          相当于创建了新的object（stream）而不是在原有的list上操作，最后通过terminal operation来传出
 *          res = func1(x -> x * 2).func2(x -> x - 3).func3(x -> x / 2).func4(get collections);(chain operation）)
 *          functional programming
 *
 *        运行流程
 *        a. stream() / Arrays.stream()
 *        b. intermediate operation
 *           map(Function interface) : one input, one output
 *           sorted(Comparator)
 *           filter(Predicate)
 *           ..
 *
 *           terminal operation  只有提交了terminal operation才会执行intermediate的方法，有点像lazy loading
 *           reduce()
 *           collect(..)
 *           forEach(Consumer interface) : one input, no output
 *
 *        c. Function Interface: one input, one output
 *           Consumer Interface: one input, no output
 *           Supplier Interface: no input, one output
 *           Predicate Interface: one input, boolean output
 *          这几种functionalinterface都是需要api实现才有意义，单独拿出来没有任何逻辑
 *
 *
 *        运行逻辑：1。生成ReferencePipleLine 2.然后当我们生成了terminate operation时候再反向构建Sink Class最后构成了Chain Operation
 *        list.stream().map().map().map()
 *        referencePipeLine像linkedlist一样连在一起
 *        ReferencePipeline1(() -> iterator) <-> ReferencePipeline2 <-> ReferencePipeline3..
 *        list.stream().map().map().map().collect()
 *
 *        根据reference pipeLine生成Sink，Sink才是执行的过程，ReferncePipeLine类似于metadata
 *        Sink(iterator) -> Sink(map) -> Sink(map) -> Sink(map) -> Sink(sorted)
 */



/**
 * 4。 FunctionalInterface    只有一个abstract方法的Interface
 */

@FunctionalInterface
interface MyRunnable {
    void run();
}

/**
 * 5. lambda expression
 *      主要目的还是用在 Stream API当中
 */

class MyTest {
    public static void main(String[] args) {
        Comparator<Integer> cpt1 = new Comparator<Integer>() {
            @Override // 加不加anotation都行，主要是给别人看的友好。
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        };
        Comparator<Integer> cpt2 = (v1, v2) -> v2 - v1;

    }
}

/**
 *  6。 stream api 和 parallelStream
 *
 */


class StreamTest1 {

    //基本逻辑
    List<Integer> list = Arrays.asList(1,2,3,4);
    List<Integer> res = list.stream() //相当于创建了iterator但是没有初始化。
                            .map(x -> x * 2)
                            .map(x -> x - 3)
                            .collect(Collectors.toList());


    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 5, 4, 3, 2, 124, 124, 35, 325, 23234);
//        Supplier<Iterator> s = new Supplier<Iterator>() {
//            @Override
//            public Iterator get() {
//                return list.iterator();
//            }
//        };
//
//        s.get(); 只有调用get时候list.iteratorc才会运行。lambda表达式一个道理


        //cpu core
        // parallelStream 没法构建 改变自己的线程池 她会调用ForkJoinPool --Common Pool 类似于cached pool 跟 cpu core有关。 如果
        // 被bock可能创建新的thread。
        // 不建议用户自己创建ForkJoinPool 或 Common Pool。 这些pool都是用于大量计算。若自己创建，可能不会很多thread，系统不允许
        // 使用ForkJoinPool中最好不要写Sleep或Lock之类的阻塞系统性能
        list.parallelStream() //() -> list.iterator()
                                .map(x -> x * 2) // 1 -> 2
                                .map(x -> x - 3) // 2 -> -1
                                .map(x -> x / 2) //-1 -> 0
                                .filter(x -> x > 0)
                                .sorted((x1, x2) -> x2 - x1)
                                .map(x -> x + 3);
//                                .forEach(x -> System.out.println(x)); //new list() -> add
//        System.out.println(res);

        Map<Integer, Long> cnt1 = list.stream()
                                        .collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        Map<Integer, Integer> cnt2 = list.stream()
                                        .collect(
                                                () -> new HashMap<>(),
                                                (map, ele) -> map.put(ele, map.getOrDefault(ele, 0) + 1),
                                                (m1, m2) -> m1.putAll(m2)
                                        );
//        System.out.println(cnt1);
//        System.out.println(cnt2);

        System.out.println(list.stream().reduce(0, (x1, x2) -> x1 + x2)); //累加


        //convert integer to string and collect / return string starts with '2'
        List<String> res = list.stream()
                .map(x -> String.valueOf(x))
                .distinct() //调用了hashset去重
                .filter(x -> x.startsWith("2"))
                .collect(Collectors.toList()); //如果在collect之后可以继续添加stream，但是是属于新的，尽量用一个stream来解决
        System.out.println(res);
    }
}

//
//3.通配符的使用
//
//        通配符：传入的类型有一个指定的范围，从而可以进行一些特定的操作
//
//        ? 和 Object 不一样，List<?> 表示未知类型的列表，而 List<Object> 表示任意类型的列表。
//
//        泛型中有三种通配符形式：
//        （1）<?>无限制通配符
//        （2）<? extends E>上界通配符：表示传入数据值需要是E类型或E的子类，
//        （3）<? super E>    下界通配符：表示传入数据值需要是E类型或E的父类。
//
//< ? super E> 用于灵活写入或比较，使得对象可以写入父类型的容器，使得父类型的比较方法可以应用于子类对象。
//< ? extends E> 用于灵活读取，使得方法可以读取 E 或 E 的任意子类型的容器对象。

/**
 *      7. default method
 *      8. optional 避免了一些null pointer的问题
 */

class OptionalTest {
    public static void main(String[] args) {
        Optional res = Optional.ofNullable(null);
        System.out.println(res.orElse(5));
    }
}
/**
 *      9. completable future
 *
 *      5 tasks
 *      t1 10s -> step2
 *      t2 5s  -> step2
 *      t3 3s  -> step3     -> allof    -> join()
 *      t4 7s  -> step5
 *      t5 6s  -> step3
 */
class CFTest1 {
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    public static void main(String[] args) {
        List<CompletableFuture<Integer>> list = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            final int val = i;
            CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return val; //估计是线程安全的问题，lamda只能返回final
                            //  lambda 表达式只能引用标记了 final 的外层局部变量，这就是说不能在 lambda 内部修改定义在域外的局部变量，否则会编译错误。

            }, pool).thenApply(x -> x * 2).orTimeout(1000, TimeUnit.SECONDS);
            list.add(cf);
        }
        //solution1
//        int sum = 0;
//        for(CompletableFuture<Integer> cf: list) {
//            sum += cf.join();
//        }
//        System.out.println(sum);

        //solution2
        CompletableFuture<Integer> res = CompletableFuture.allOf(list.toArray(new CompletableFuture[0])) //Refection 创建 Array，不用定义Size
                .thenApply(Void -> list.stream().map(cf -> cf.join()).reduce(0, (v1, v2) -> v1 + v2));
//        res.isDone() //看看当前线程是否完成，没完成可以干一些其他的事情
        System.out.println(res.join()); // 只有调用join的时候才会阻塞
//        System.out.println("aaa");

    }

    //Feature不好做chainOperation，只是CF写代码逻辑更容易
    private static void func1() throws Exception {
        List<Future<Integer>> list = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            final int val = i;
            Future<Integer> future = pool.submit(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return val;
            });
            list.add(future);
        }
        int sum = 0;
        for(Future<Integer> f: list) {
            sum += f.get() * 2;
        }
    }
}



/**
 *      10. method reference
 */

/**
 *     练习LC时候forLoop用streamAPI来做
 */


/**
 *  tomorrow: Design pattern + reflection
 *      Singleton
 *      Factory
 *      builder
 *      composition
 *      template
 *      prototype
 *      bridge
 *      strategy
 *      facade
 *      observer
 *      adaptor
 *      proxy / decorator
 *      dynamic proxy
 *
 */