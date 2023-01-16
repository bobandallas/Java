package com.example.java26.week2;

import java.io.Serializable;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  tomorrow: Design pattern + reflection
 *  Singleton
 */
//eager loading
class EagerLoadingSingleton {
    private static final EagerLoadingSingleton instance = new EagerLoadingSingleton();

    private EagerLoadingSingleton() {}

    public static EagerLoadingSingleton getInstance() {
        return instance;
    }
}

class LazyLoadingSingleton {
    private static volatile LazyLoadingSingleton instance;
    // 在初始化的时候JVM分配一块空间，其他人可以直接读取在初始化完成之前
    // 如果没有加volatile，则不保证先后顺序

    private final Map<Integer, Integer> map;

    private LazyLoadingSingleton() {
        map = new ConcurrentHashMap<>();
    }

    public static LazyLoadingSingleton getInstance() {
        if(instance == null) {
            //T1, T2, T3 都在等待
            synchronized (LazyLoadingSingleton.class) {
                if(instance == null) { //在T1完成的时候，T2进入不检查instance会覆盖
                    instance = new LazyLoadingSingleton();
                }
            }
        }
        return instance;
    }

    public void put(int key, int val) {
        map.put(key, val);
    }
}

enum EnumSingleton {
    INSTANCE1;
}

//refection依旧可以访问Singleton，但是Enum没问题，类似于在constructor里面check instance然后throw exception
// 第二个问题是比如自定义类extends 实现了Clone的父类。这时候要重写clone方法抛出异常
// Serializable 也要重写一些方法
class TestSingleton {
    public static void main(String[] args) throws Exception {
        Class clazz = LazyLoadingSingleton.class;
        Constructor constructor = clazz.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        LazyLoadingSingleton instance = (LazyLoadingSingleton) constructor.newInstance();
        System.out.println(instance);

        instance.put(5, 5);
    }
}

/**
 *      1000 x new Car(...)
 *
 *      Factory
 *          1. loose coupling
 *          centralize 初始化过程，当有1000个Car new instance, car变化的时候我们不得不改变1000次。然而有factory，只改变factory就可以了
 *
 *
 *
 *          2. hide implementation
 */
class Car {
    private String name;
    private String type;
    private int year;
    private String id;


    public Car(String name, String type, int year, String id) {
        this.name = name;
        this.type = type;
        this.year = year;
        this.id = id;
    }
    //getter setter

    //factory method
    public static Car getCar(String name, String type) {
        return new Car(name, type, new Date().getYear(), null);
    }
}
class CarFactory {
    public static Car getCar(String name, String type) {
        return new Car(name, type, new Date().getYear(), null);
    }
}

// abstract factory , BMW， Benz 可以定义自己的
abstract class CarFactory2 {
    public static Car getCar(String name, String type) {
        return null;
    }
}

/**
 *      builder
 *      dynamic initialization
 *      当参数特别多，factory太繁琐就可以用builder,而不是多次调用不同constructor 或 列举全部的attribute组合
 *
 */
class Student {
    @MyAnnotation(name = "aaaaaabbbcc")
    private String name;
    private int age;
    // 第二种 做Builder的方法。每次都传入上一个的实例
    public Student(Student xxx) {
        this.name = xxx.getName();
        this.age = xxx.getAge();
    }
    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Student setAge(int age) {
        this.age = age;
        return this;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

// 第三种builder的实现
class StudentBuilder {
    private String name;
    private int age;

    public StudentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StudentBuilder setAge(int age) {
        this.age = age;
        return this;
    }

    public Student build() {
        return new Student(name, age);
    }
}

class BuilderTest {
    public static void main(String[] args) {
        Student s1 = new Student().setAge(5).setName("aaa");
        Student s2 = new StudentBuilder().setAge(5).setName("aaa").build();
    }
}


/**
 *      composition(Has-A) vs aggregation(Is-A)
 *
 *      class LinkedList {
 *          public static xx func1() {
 *              new Node()
 *          }
 *          private class Node {
 *              Node prev;
 *              Node next;
 *          }
 *      }
 *
 *      class TreeNode {
 *          TreeNode left;
 *          TreeNode right;
 *      }
 */

/**
 *      template
 *          abstract class Car {
 *              private String type;
 *              private int year;
 *
 *              public void start() {
 *                  //..
 *              }
 *
 *              abstract void stop();
 *          }
 *
 *          class BMWCar extends Car {}
 *          class HondaCar extends Car {}
 */


/**
 *      prototype == clone
 */

/**
 *      bridge
 *
 *      class A {
 *          private B b;
 *
 *          public A(B b) {
 *              this.b = b;
 *          }
 *
 *          public void func1() {
 *              int x = b.get();
 *          }
 *      }
 *
 *      interface B {}
 *      class BImpl1 implements B {}
 *      class BImpl2 implements B {}
 *      传入的implements是不一样的，但都是parent B，实现loose coupling
 *      当需要传入implementation2的时候直接传入即可，不需要改变class A的代码
 */


/**
 *      strategy
 *
 *      runtime job
 *
 */
interface Calculate {
    int execute(int v1, int v2);
}
class Calculator {
//    private Calculate calculate;
//
//    public Calculator(Calculate calculate) {
//        this.calculate = calculate;
//    }

    public int run(int v1, int v2, Calculate calculate) {
        return calculate.execute(v1, v2);
    }
}

class CalculatorTest {
    public static void main(String[] args) {
        int res = new Calculator().run(0, 1, (s1, s2) -> s1 * s2);
        System.out.println(res);
    }
}

/**
 *       facade == api gateway
 */

/**
 *      observer == publisher + subscriber
 */
class Topic {
    private final List<Observer> observers = new ArrayList<>();

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void publish(String msg) {
        for(Observer observer: observers) {
            observer.receive(msg);
        }
    }
}

class Observer {
    public void receive(String msg) {
        System.out.println(msg);
    }
}

/**
 *      adaptor
 */
//3rd party library
class A {}
class B {}
interface Mapper {
    B mapping (A a);
}
class MapperImpl implements Mapper {
    @Override
    public B mapping(A a) {
        return null;
    }
}
//-------------------------------
interface MyMapper {
    B mapping (A a);
}
class ProductService {
    private MyMapper mapper;

    public ProductService(MyMapper mapper) {
        this.mapper = mapper;
    }

    public void func1(A a) {
        B b = mapper.mapping(a);
    }

    public static void main(String[] args) {
        new ProductService(new MapperAdaptor(new MapperImpl()));
    }
}

//将不同class进行换了个壳
class MapperAdaptor implements MyMapper {
    private Mapper mapper;

    public MapperAdaptor(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public B mapping(A a) {
        return mapper.mapping(a);
    }
}

/**
 *      static proxy （其实就是extends和implement）/ decorator
 */

//class level proxy (inheritance)

class ProxyClassMapper extends MapperImpl {
    @Override
    public B mapping(A a) {

        //一切都是一样的只是加了自己的东西进去
        System.out.println("before");
        B b = super.mapping(a);
        System.out.println("after");
        return b;
    }
}

//interface implementation

class ProxyInterfaceMapper implements Mapper {
    private Mapper mapper;

    public ProxyInterfaceMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public B mapping(A a) {
        System.out.println("before");
        B b = mapper.mapping(a);
        System.out.println("after");
        return b;
    }
}
/**
 *      dynamic proxy
 *
 *      static proxy的缺点在于如果Override的方法很多，难道要一个个重写吗？
 */

//class level dynamic proxy : cglib
//interface level dynamic proxy: java proxy 自带的

class MyInvocationHandler implements InvocationHandler {
    private Calculate obj;

    public MyInvocationHandler(Calculate obj) {
        this.obj = obj;
    }

    //所有的Calculate方法逻辑处理都会进入到invode，实现所有的方法都改写
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        System.out.println(method);
        //method.invoke(obj, args) == obj.method(args)
        Object res = method.invoke(obj, args);
        System.out.println(res);
        System.out.println("after");
        return res;
    }
}

class ProxyTest {
    public static void main(String[] args) {
        Calculate proxy = (Calculate) Proxy.newProxyInstance(
                ProxyTest.class.getClassLoader(),
                new Class[]{Calculate.class},
                new MyInvocationHandler((v1, v2) -> v1 + v2)
        );
        int res = proxy.execute(1, 2);
//        System.out.println(res);
    }
}

/**
 *  reflection
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
    String name() default "Tom";
}

class ReflectionDemo {
    public static void main(String[] args) throws Exception {
        Class clazz = Student.class;
        Student s1 = (Student) clazz.getDeclaredConstructors()[0].newInstance();
        Field f = clazz.getDeclaredField("name");

        System.out.println(s1);
        f.setAccessible(true);

        Annotation annotation = f.getDeclaredAnnotation(MyAnnotation.class);
        MyAnnotation m = (MyAnnotation) annotation;
        f.set(s1, m.name());
        System.out.println(s1);
    }
}

/**
 *  oracle live sql
 *  oracle sql
 */