package com.example.java26.week3;

import com.example.java26.week3.orm.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *  Spring (以前版本打包war file部署到tomcat， 现在spring已经包含了tomcat)
 *          spring 的优点 ：IOC + AOP
 *          用spring来管理library，这些都运行在spring 平台上
 *      IOC(Inversion of Control):
 *          IOC container
 *          1 . IOC不是一种技术而是一种设计思想，Ioc意味着将你设计好的对象交给容器控制，而不是传统的在你的对象内部直接控制。
 *          2 . 在传统Java SE程序设计，我们直接在对象内部通过new进行创建对象，IoC是有专门一个容器来创建这些对象，即由Ioc容器来控制对 象的创建；
 *          3. 何为控制反转？
 *          传统应用程序是由我们自己在对象中主动控制去直接获取依赖对象，也就是正转；而反转则是由容器来帮忙创建及注入依赖对象；
 *          因为由容器帮我们查找及注入依赖对象，对象只是被动的接受依赖对象，所以是反转；哪些方面反转了？依赖对象的获取被反转了。
 *          4. IOC 是一个概念，Dependency Injection是一个实现
 * ————————————————
 *      Dependency Injection:
 *          Bean是个昵称，被Spring管理的instance叫bean
 *          1. IOC container实现: ApplicationContext(BeanFactory(map + factory pattern)) eager loading在启动时自动加载
 *          2. class - level @Component  / @Service / @Repository / @Controller
 *              (parent annotation, 是所有annotation的parent，其他都是继承于Component)
 *             method - level @Bean
 *             手动添加 getApplicationContext().addBean()
 *          3. @Autowired + @Qualifier / @Primary
 *              (Bean的使用)
 *              field injection
 *              setter injection
 *              constructor injection
 *
 *              注入时候会有两种方式, 扫描时候如果扫描到注入 一定会停下来注入完成继续再扫描
 *                  setter Injection发生在 加载完constructor完成之后 程序开始执行之前，任意时间被reflection调用然后执行。
 *                  很可能造成constructor 中某些instance还是null
 *              by type：找到这个type的implementation，如果有多个会很困惑建议使用by name 或者 @Primary 或者
 *                       使用bean name ： (StudentService studentServiceImpl1)
 *              by name : @Qualifier(Bean name)
 *          4. bean scope :
 *              singleton (default) 这是Spring默认的scope，表示Spring容器只创建唯一一个bean的实例，所有该对象的引用都共享这个实例，并且Spring在创建第一次后，会在Spring的IoC容器中缓存起来，之后不再创建，就是设计模式中的单例模式的形式。
 *
 *                                  并且对该bean的所有后续请求和引用都将返回该缓存中的对象实例。一般情况下，无状态的bean使用该scope。
 *
 *              prototype 代表线程每次调用或请求这个bean都会创建一个新的实例。一般情况下，有状态的bean使用该scope。
 *
 *              request 每次http请求将会有各自的bean实例，类似于prototype，也就是说每个request作用域内的请求只创建一个实例。
 *
 *              session 在一个http session中，一个bean定义对应一个bean实例。也就是说每个session作用域内的请求只创建一个实例。
 *
 *              global session
 *               * 在一个全局的http session中，一个bean定义对应一个bean实例。
 *  *
 *  *           但是，这个scope只在porlet的web应用程序中才有意义，它映射到porlet的global范围的session，如果普通的web应用使用了这个scope，容器会把它作为普通的session作用域的scope创建。
 *  *
 *  *           注： 再次说明spring的默认scope（bean作用域）是singleton
 *
 *          5。 SpringBootApplication 里面有几个annotation值得注意
 *              1. @ComponentScan 会扫描对应当前路径下的classes
 *              2. @EnableAutoConfiguration
 *                  因为在xml 包含了spring-boot-starter-data-jpa 它会去找 Spring.factories 加载各种Hibernate对应的内容
 *                  默认从application.properties读
 *
 *
 *          6. 以前的版本注入Entity Manager 要 加       @PersistenceContext
 *              spring注解@PersistenceContext详细使用说明 ：
 *              这个是JPA中的注解，PersistenceContext，称为持久化上下文，它一般包含有当前事务范围内的，被管理的实体对象(Entity)的数据。
 *              每个EntityManager，都会跟一个PersistenceContext相关联。PersistenceContext中存储的是实体对象的数据，而关系数据库中存储的是记录。
 *
 *              @PersistenceContext与@Resource区别
 *              @PersistenceContext
 *              注入的是实体管理器，执行持久化操作的，需要配置文件persistence.xml。
 *              注入一堆保存实体类状态的数据结构，针对实体类的不同状态(四种,managedh或detached等)可以做出不同的反应(merge,persist等等)，其实就是把数据从数据库里提出，然后在内存里处理的，再返回数据库的法则。
 *
 *              @Resource
 *              是注入容器提供的资源对象，比如SessionContext MessageDrivenContext。或者你那个name指定的JNDI对象，可以理解为资源->数据源->也就是数据连接，基本上就是告诉程序数据库在哪里


 */

@SpringBootApplication
class SpringBasic {
    private static StudentService s1;
    private static StudentService s2;
    private static EntityManager em;

    @Autowired
    //Constructor injection
    public SpringBasic(
            @Qualifier("aaa") StudentService xx1,
            @Qualifier("aaa") StudentService xx2,
            EntityManager em         //是signleton，共享的
    ) {
        s1 = xx1;
        s2 = xx2;
        SpringBasic.em = em;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBasic.class, args);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s1 == s2);
        System.out.println(em.find(Student.class, "15"));
    }
}
@Service
interface StudentService {}
@Service("aaa") //可以更改bean的名字同时
@Scope("prototype")
class StudentServiceImpl1 implements StudentService {
    @Override
    public String toString() {
        return "StudentServiceImpl1{}";
    }
}
@Service
//@Primary
class StudentServiceImpl2 implements StudentService {
    @Override
    public String toString() {
        return "StudentServiceImpl2{}";
    }
}

/**
 *  AOP(dynamic proxy)(Aspect-Oriented Programming)
 *
 * @AspectJ
 * class PublicMethodAspect {
 *      //before after必须返回void，加了其他的返回类型也没用， around不一定
 *      @Before
 *      @PointCut(public * *) //提供method location，class location，...
 *      public void xx() {
 *
 *      }
 *
 *      @Around
 *      public Object xx(JoinPoint mi) {
 *          //before
 *          Object obj = mi.proceed();
 *          //after
 *          return obj;
 *      }
 *
 *
 *      @After
 *      @PointCut(public * *)
 *      public void xx() {
 *
 *      }
 * }
 *
 *
 *      mi: List: [after1, before1, after2]      其实就是recursion的方式来执行mi List
 *                                              i
 *      after1: {
 *          mi.proceed() {
 *              before1: {
 *                  execute before1 logic
 *                  return mi.proceed() {
 *                      after2: {
     *                      mi.proceed() {
     *                          execute target logic
     *                      }
     *
     *                      execute after2 logic
     *                      return res
 *                      }
 *                  }
 *              }
 *          }
 *
 *          execute after1 logic
 *          return result
 *      }
 *      before1, execute target logic, after2, after1
 *
 * Tomorrow :
 *      Network + server
 *
 * * * * * * * * * * * * *
 * question: create 2 threads, and print 1a2b3c4d..26z
 *
 * * * * * * * * * * * * *
 * filter -> dispatcher servlet -> handler mapping -> controller
 *
 * filter /endpoint -> servlet (method) -> service
 *
 * filter是用户最先接触到的一层，在servlet之前。 AOP在servlet之中
 *
 */