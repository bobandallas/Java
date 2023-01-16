package com.example.java26.week1;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 *  primitive type (int, boolean...), object（默认继承Object）
 *  ****  **** **** **** **** **** **** **** **** ****
 *  OOP
 *      polymorphism （多态 run time进行调用）: overriding（重写） + overloading（方法名相同，方法参数不同）
 *          Car car = new BMW();
 *          polymorphism definition: subclass present different form from parent class
 *          overriding ： overriding：方法的重写，发生在子父类中，方法名相同，
 *          参数列表相同，返回值相同，子类的访问修饰符要大于或等于父类的访问修饰符，
 *          子类的异常声明必须要小于等于父类的异常声明，
 *          如果父类的、方法被private、static、final修饰，那么方法不能被重写
 *      encapsulation: access modifier
 *              private : not let other program direct access some field or method; 仍旧可以被 reflection 访问
 *              default ： 同package可以访问， 其他packge不行
 *              protected ： 其他package可以
 *              public ： 任意
 *      abstraction:
 *          抽象类总结规定
 *          1. 抽象类不能被实例化(初学者很容易犯的错)，如果被实例化，就会报错，编译无法通过。只有抽象类的非抽象子类可以创建对象。
 *
 *          2. 抽象类中不一定包含抽象方法，但是有抽象方法的类必定是抽象类。
 *
 *          3. 抽象类中的抽象方法只是声明，不包含方法体，就是不给出方法的具体实现也就是方法的具体功能。
 *
 *          4. 构造方法，类方法（用 static 修饰的方法）不能声明为抽象方法。
 *
 *          5. 抽象类的子类必须给出抽象类中的抽象方法的具体实现，除非该子类也是抽象类。
 *              abstract class： 单一继承；可变的
 *              interface ： extends multiple interfaces
 *                  int a = 5; 默认 public static final
 *                  default method() {}
 *                  private method()
 *      inheritance
 *              class A extends B implements interface1, 2, 3 {
 *
 *              }
 */
interface AbstractionDemo {
    int a = 6;
}
class EncapsulationExample {
    protected String name;

    public String getName() {
        return name;
    }

}
/**
 * 	SOLID Principal
 * 	Single Responsibility
 * 	        class StudentService {
 *
 * 	        }
 * 	Open Close
 * 	        extends
 * 	Liskov Substitution
 * 	        class HawaiiPizza extends Pizza {}
 * 	        Pizza p = new HawaiiPizza();
 * 	Interface Segregation
 * 	        interface Car {
 * 	            //1000 methods
 * 	        }
 * 	Dependency Inversion
 * 	        class A {
 * 	            private Pizza p;
 * 	            public A (..) {
 *
 * 	            }
 * 	        }
 *
 *  class A {
 *      public void xx() {
 *          for() {
 *              if() {
 *                  for() {
 *
 *                  }
 *              }
 *          }
 *          int x = 5;
 *          int y = 5;
 *      }
 *  }
 */

/**
 * 	pass by value
 *
 */
class PassByValueExample {
    public static void func1(int a) {
        a = 5;
    }
    //list 0x888[0x000] -> Arrays.asList()
    public static void func2(List<Integer> list) {
        //list 0x888[0x555] -> empty list
        list = new ArrayList<>();
    }

    public static void main(String[] args) {
//        int a = 10;
//        func1(a);
//        System.out.println(a); //5 or 10

        // 0x000 -> Arrays.asList(1, 2, 3);
        //list 0x777[0x000] -> -> Arrays.asList(1, 2, 3)
        List<Integer> list = Arrays.asList(1, 2, 3);
        //func2(0x000)
        func2(list);
        //print 0x777[0x000]
        System.out.println(list);
    }
}
/**
 * generic
 */
class Day1Node<T> {
    private T val;

    public Day1Node(T val) {
        this.val = val;
    }
}

/**
 * 	static -> class obj
 *
 * 	    Class<?> clazz = Day1Node.class;  class Object就是一个instance。它会在第一次调用".class"时候加载
 * 	    Field f = clazz.getDeclaredFields()[0]    classObject中所有的东西都可以理解为Instance Object
 * 	    静态方法不能访问non-static
 * 	    Java 所有东西都属于Object除了primitive type
 * 	    static 属于 Class Object， JVM会创建Class Object 存储到Heap里面
 *
 * 	non-static -> this obj
 */


class Day1ClassObjDemo {
    public static int val1 = 5;
    public int val2 = 5;

    public Day1ClassObjDemo(int val2) {
        this.val2 = val2;
    }

    public static void main(String[] args) {
//        Day1ClassObjDemo d1 = new Day1ClassObjDemo();
//        d1.val2 = 3;
//        Day1ClassObjDemo d2 = new Day1ClassObjDemo();
//        d2.val2 = 4;
        Day1ClassObjDemo d2 = new Day1ClassObjDemo(10);
        System.out.println(d2.val2); //5 if not this in line 152.
    }
}

/**
 * 	immutable / string / how to create immutable
 *
 * 	String : constant string pool
 */
class StringImmutableDemo {
    /**
     * constant string pool :
     *      "abc"
     *      "abcd"
     */
    public static void main(String[] args) {
//        String s1 = "abc"; //Constant string pool
//        String s2 = "abc"; //Constant string pool
//        String s3 = new String("abc"); //instance1 in heap
//        String s4 = new String("abc"); //instance2 in heap
//        System.out.println(s1 == s2); T
//        System.out.println(s2 == s3); F
//        System.out.println(s3 == s4); F
//        System.out.println(s3.equals(s4)); T


        Integer v1 = 1;
        Integer v2 = 1;
        Integer v3 = 300;
        Integer v4 = 300;

        int v5 = 300;
        System.out.println(v5 == v4);
    }

    //str = null;
    public static String reverse(String str) {

        // 节省空间写法，如果for loop不断 += =》 等于每次循环创建一个stringBuilder来生成新的String 然后 放到constant string pool中
        // 本质上 String 用的是 private final byte
        // StringBuilder 用的是 类似于char ArrayList
        if(str == null) {
            //log
            throw new IllegalArgumentException("..");
        }
        StringBuilder sb = new StringBuilder();
        for(int i = str.length() - 1; i >= 0; i--) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
        //return new StringBuilder(str).reverse().toString();

        // 如果是Integer之间比较在 -128 -- 127之间应该 == 是没问题的，但是超过了会有问题。
        // 除非（int）v1 == v2 会自动 unboxing
    }
}


/** 深拷贝和浅拷贝
 * shallow copy vs deep copy
 * class Emp {
 *     private String name;
 *     private Address ads;
 * }
 *
 * class Address {
 *     private String street;
 * }
 * Address ad1 = new Address();
 * Emp e1 = new Emp(ad1);
 * //shallow copy e1 to e2,  e1 + e2 use same ad1
 * Emp e2 = e1.clone();
 * //deep copy e1 to e2, e1, e2 use diff address instance
 * Emp e2 = e1.clone();
 * serialize可以实现Deep Copy
 */

//        1、不可变类
//        （1）不可变类是指这个类的实例一旦创建完成后，就不能改变其成员变量值，也就是不能改变对象的状态。
//        （2）Java 中八个基本类型的包装类和 String 类都属于不可变类，而其他的大多数类都属于可变类。
//        （3）不可变对象是线程安全的。
//        2、如何设计不可变类？
//        （1）类声明为final，不可以被继承。
//        （2）所有成员变量定义为私有和final（private final）。
//        （3）不提供改变成员变量的方法。
//        （4）通过构造器初始化成员，若构造器传入引用数据类型需要进行深拷贝。

final class Day1MyImmutableInstance {
    private final String name;
    private final List<Object> list;

    public Day1MyImmutableInstance(String name, List<Object> x) {
        this.name = name;

        this.list = new ArrayList<>();

        // 初始化时候对list 中的 Oject 要深拷贝
        for(Object obj: x) {
            //list.add(deep copy obj)
        }
    }
    // 返回时候对list 中的 Oject 做同样的深拷贝

    public List<Object> getList() {
        List<Object> ans = new ArrayList<>();
        for(Object obj: this.list) {
            //ans.add(deep copy obj)
        }
        return ans;
//        return this.list;
    }

    public String getName() {
        return name;
    }
}
/**
 * instance1 = new Day1MyImmutableInstance()
 * List<Object> l1 = instance1.getList();
 * List<Object> l2 = instance1.getList();
 * Object obj = l1.get(0);
 */


/**
 * exception
 *
 *          Throwable class
 *          /           \
 *       Error          Exception (compile)
 *                        \
 *                        RuntimeException
 *                         \
 *
 *
 */
class MyRuntimeException extends RuntimeException {
    public MyRuntimeException(String message) {
        super(message);
    }
}
class MyCompileTimeException extends Exception {
    public MyCompileTimeException(String message) {
        super(message);
    }
}

// try catch 会处理掉low level的异常，所以平时写代码只在最底层抛出异常即可
// 多个catch 要从小的到大的，不然永远无法到达大的exception
// compile exception 要立即处理
class Day1ExceptionDemo {
    public static void main(String[] args) {
        func3();
    }
    private static void func3() {
        func2();
    }

    private static void func2() {
        try {
            func1();
        } catch (Exception ex) {
            throw new IllegalArgumentException("ccc");
        }
    }

    private static void func1() throws Exception {
//        throw new MyCompileTimeException("aaa");

//        try {
            throw new MyCompileTimeException("aaa");
//        } catch (Exception ex) {
//
//        } finally {
//            //release lock / resources / connection
//        }
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface MyAnnotation {
    String name() default "Tom";
}

@MyAnnotation(name = "Jerry")
class Day1Student {
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Day1Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
class Day1ReflectionDemo {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Day1Student.class;
        Day1Student stu = (Day1Student) clazz.getDeclaredConstructors()[0].newInstance();
        Field f = clazz.getDeclaredField("name");
        f.setAccessible(true);
        f.set(stu, "Tom");
        System.out.println(stu);
    }
}

/**
 * 	final / finally
 *
 * 	final List<Integer> list = new ArrayList<>();
 * 	list.
 */

/**
 *  Array
 *  List vs Map vs Set
 *  ArrayList, LinkedList
 *  Hashtable, HashMap, TreeMap
 *  HashSet, TreeSet
 *  PriorityQueue
 *  Stack, Queue, Deque
 */


/**
 *  Summary ：
 *  Runtime Exception为什么会向上传递
 *  Compile Exception 为什么不会向上传递 lost trace ： 会向上传递。只是不要逐层check。
 *                  用Complie类似于给程序员的警告或check
 *  Finally 遇上 Compile exception会怎么样
 */