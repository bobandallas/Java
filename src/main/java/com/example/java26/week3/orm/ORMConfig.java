package com.example.java26.week3.orm;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.*;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


/**
 * why hibernate
 * Hibernate 框架会帮我们把result转换为object or instance
 *      1. Hql
 *      2. object mapping 从一堆结果map到一个个的instance
 *      3. cache           1st level cache(每一个用户都给一个单独的链接，为单独链接cache，其他人无法读到) & 2nd level cache(几乎没人用了)
 *      4. connection pool
 *          unused connection blocking queue
 *          used connection blocking queue
 *      5. criteria  query(dynamic query, 类似于builder pattern的实现，不断append新的组合。根据不同的input生成不同的query，而不是无限组合)
 *      ...
 *      在hibernate里面我们用的是 session
 *      datasource 1 - 1 session factory 1 - m session 1 - 1 user
 *      save or update
 * JPA              同一套内核的不同实现 JPA是一套规范，hibernate 是对JPA的实现存在对应关系 save or update -- persist merge；
 *      datasource 1 - 1 entity manager factory 1 - m entity manager 1 - 1 user (用户拿到了session来对数据库进行crud)
 *      persist
 *      merge
 *
 * Spring data jpa  更简化的实现方式
 *      developer -> create interface .. (dynamic proxy implementation) 解析你的方法名anotation dynamic proxy实现
 *
 *    *     *     *     *     *     *     *     *     *     *     *     *     *
 * how to use hibernate
 *    1. configure pom.xml (dependency)
 *    2. configure datasource
 *    3. create entity class
 *    4. create repository interface (repository layer, java 与数据库交互的代码 )
 *       impl interface
 *
 *    *     *     *     *     *     *     *     *     *     *     *     *     *
 *   Student s = (Student) em.createQuery(select s from Student s where id = )
 *   eager loading:
 *      s.id
 *      s.name
 *      s.getTeacher_Student() //会自动加载student的时候帮我们把teacher_student对应的数据也拿出来
 *   lazy loading:
 *      s.id
 *      s.name
 *      s.getTeacher_Student() => trigger another sql query   //再出发query拿出来
 *
 *    *     *     *     *     *     *     *     *     *     *     *     *     *
 *    问 ：以下代码是lazyloading 一共向数据库发送了多少个query(N个Student)
 *   List<Student> list = em.createQuery(select s from Student s)   1
 *   for(Student s: list) {                                         N
 *       s.getTeacher_Student().size();
 *   }
 *   答案：N + 1 如果想改写成eager loading，可以用join fetch 关键字 只对当前query 其实也是N+1
 *    *     *     *     *     *     *     *     *     *     *     *     *     *
 *
 *
 *    merge 如果没有会创建新的，如果找到对应的这一行会update
 *     *    *     *     *     *     *     *     *     *     *     *     *     *     *
 *   stage / status of instances    instance的状态
 *   persist - attach / proxy (persistence context) - detach / un-proxy
 *
 *   persist 刚new出来还没有被hibernate管理的instance
 *   attach / proxy 意味着被hibernate管理在persistent context space(1st level cache)区域里，会触发lazy loading，
 *   detach / un-proxy  如果不想触发就这个status，和persistent context藕断丝连，当session close时会触发lazy initialization exception，
 *                      意味着不再被hibernate管理，那么我们就要把它attach回去
 *    *     *     *     *     *     *     *     *     *     *     *     *     *
 *
 *    最好都设置成lazy loading, 因为lazy loading可以用fetch设置成eager loading。但反之不行。
 *    如果都设置成eager loading的话开销太大了
 *
 *     *    *     *     *     *     *     *     *     *     *     *     *     *     *
 *  Tomorrow:
 *      Spring IOC, AOP
 *      Spring Boot
 */

//hibernate.dialect -> centralize hibernate query language -> 再翻译成对应sql的语法
public class ORMConfig {

    private DataSource getDataSource() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
//        dataSource.setDatabaseName("OrmDemo");
        dataSource.setUser("postgres");
        dataSource.setPassword("password");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/university");
        return dataSource;
    }

    private Properties getProperties() {
        final Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.put("hibernate.show_sql", "true"); //用来debug
        return properties;
    }

    private EntityManagerFactory entityManagerFactory(DataSource dataSource, Properties hibernateProperties) {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com/example/java26/week3/orm"); //扫描对应目录，告诉对应class object是哪个
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(hibernateProperties);
        em.setPersistenceUnitName("demo-unit");
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.afterPropertiesSet();
        return em.getObject();
    }

    public static void main(String[] args) {
        ORMConfig ormConfig = new ORMConfig();
        DataSource dataSource = ormConfig.getDataSource();
        Properties properties = ormConfig.getProperties();
        EntityManagerFactory entityManagerFactory = ormConfig.entityManagerFactory(dataSource, properties);

        EntityManager em = entityManagerFactory.createEntityManager();
        PersistenceUnitUtil unitUtil = entityManagerFactory.getPersistenceUnitUtil();

        getStudentById(em);
    }



    private static void insertToStudent(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Student s = new Student();
        s.setName("JerryTest");
        s.setId("50");
        em.merge(s);
        tx.commit();
    }
/*
select student0_.id as id1_0_, student0_.name as name2_0_ from student student0_ left outer join teacher_student teacher_st1_ on student0_.id=teacher_st1_.s_id where student0_.id=?

 */
    private static void getStudentById(EntityManager em) {
        Query query = em.createQuery("select s from Student s left join  s.teacher_students ts where s.id = ?1");
        query.setParameter(1, "17");
        Student s = (Student)query.getSingleResult();
        System.out.println(s.getTeacher_students());
    }

    private static void addToJunctionTable1(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Student s = new Student();
        s.setName("30th stu");
        Teacher t = new Teacher();
        //persist t first to get new id
        em.persist(t);
        t.setName("30th teacher");
        //build connection between t and s
        Teacher_Student ts = new Teacher_Student();
        ts.setStu(s);
        ts.setTeacher(t);
        t.addTeacher_students(ts);
        em.persist(s);
        tx.commit();
    }

    private static void addToJunctionTable2(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createNativeQuery("INSERT INTO TEACHER_STUDENT (S_ID, T_ID) VALUES (?, ?)");
        query.setParameter(1, 4);
        query.setParameter(2, 4);
        query.executeUpdate();
        tx.commit();
    }

    private static void addToJunctionTable3(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Student s = em.find(Student.class, "14");
        Teacher t = em.find(Teacher.class, "9");
        Teacher_Student ts = new Teacher_Student();
        ts.setStu(s);
        ts.setTeacher(t);
        em.persist(ts);
        tx.commit();
    }

    private static void addToJunctionTable4(EntityManager em) {
        em.getTransaction().begin();
        Student s = new Student();
        s.setId("14");
        Teacher t = new Teacher();
        t.setId("12");
        Teacher_Student ts = new Teacher_Student();
        ts.setStu(s);
        ts.setTeacher(t);
        em.persist(ts);
        em.getTransaction().commit();
    }

    private static void notOrphanRemoveBiRelation(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createQuery("select s from Student s join fetch s.teacher_students ts where s.id = ?1");
        query.setParameter(1, "5");
        Student s = (Student) query.getSingleResult();
        Teacher t = em.find(Teacher.class, "3");
        List<Teacher_Student> teacher_students = new ArrayList<>();
        for(Teacher_Student ts: s.getTeacher_students()) {
            if(ts.getTeacher().getId().equals(t.getId())) {
                teacher_students.add(ts);
                em.remove(ts);
            }
        }
        s.getTeacher_students().removeAll(teacher_students);
        t.getTeacher_students().removeAll(teacher_students);
        tx.commit();
    }

    private static void notOrphanRemoveSingleRelation(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createQuery("select s from Student s join fetch s.teacher_students ts where s.id = ?1");
        query.setParameter(1, "5");
        Student s = (Student) query.getSingleResult();
        for(Teacher_Student ts: s.getTeacher_students()) {
            em.remove(ts);
        }
        s.setTeacher_students(new ArrayList<>());
        tx.commit();
    }

    private static void orphanRemove(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createQuery("select s from Student s join s.teacher_students ts where s.id = ?1");
        query.setParameter(1, "14");
        Student s = (Student) query.getSingleResult();
        Iterator<Teacher_Student> itr = s.getTeacher_students().iterator();
        while(itr.hasNext()) {
            Teacher_Student ts = itr.next();
            if(ts.getTeacher().getId().equals("9")) {
                itr.remove();
            }
        }
        tx.commit();
    }


    private static void withoutOrphanRemove(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createQuery("select s from Student s join fetch s.teacher_students ts where s.id = ?1");
        query.setParameter(1, "14");
        Student s = (Student) query.getSingleResult();
        Iterator<Teacher_Student> itr = s.getTeacher_students().iterator();
        while(itr.hasNext()) {
            Teacher_Student ts = itr.next();
            if(ts.getTeacher().getId().equals("9")) {
                itr.remove();
                em.remove(ts);
            }
        }
        tx.commit();
    }
}
