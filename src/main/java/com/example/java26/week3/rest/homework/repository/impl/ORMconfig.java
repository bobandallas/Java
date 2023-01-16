package com.example.java26.week3.rest.homework.repository.impl;


import com.example.java26.week3.orm.Student;
import com.example.java26.week3.rest.homework.pojo.entity.Teacher;
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

public class ORMconfig {
    private static final ORMconfig instance = new ORMconfig();

    private EntityManager em;

    private ORMconfig(){
        DataSource dataSource = getDataSource();
        Properties properties = getProperties();
        EntityManagerFactory entityManagerFactory = entityManagerFactory(dataSource, properties);
        EntityManager em = entityManagerFactory.createEntityManager();
        PersistenceUnitUtil unitUtil = entityManagerFactory.getPersistenceUnitUtil();
        this.em = em;
    }
    public static ORMconfig getInstance(){
        return instance;
    }

    private DataSource getDataSource() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
//        dataSource.setDatabaseName("OrmDemo");
        dataSource.setUser("postgres");
        dataSource.setPassword("2222");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/antra");
        return dataSource;
    }

    private Properties getProperties() {
        final Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.put("hibernate.show_sql", "true");
        return properties;
    }


    private EntityManagerFactory entityManagerFactory(DataSource dataSource, Properties hibernateProperties) {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.java26.week3.rest.homework.pojo.entity"); //扫描对应目录，告诉对应class object是哪个
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(hibernateProperties);
        em.setPersistenceUnitName("demo-unit");
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.afterPropertiesSet();
        return em.getObject();
    }


    //CRUD get function
    public Teacher getById( int id) {
        Query query = em.createQuery("select s from Teacher s where s.id = ?1");
        query.setParameter(1, id);
        Teacher s = (Teacher)query.getSingleResult();
        System.out.println("test result" + s.getName());
        return s;
    }

    //CRUD get all function
    public List<Teacher> getAll() {
        Query query = em.createQuery("select t from Teacher t");
        List<Teacher> s = (List<Teacher>)query.getResultList();
        return s;
    }

    public String addOne(Teacher teacher) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Teacher t = new Teacher();
        t.setName(teacher.getName());
        t.setAge(teacher.getAge());
        t.setGender(teacher.getGender());
        em.merge(t);
        tx.commit();
        return "success";
    }

    public String updateOne(int id, Teacher teacher) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        int query = em.createQuery("update Teacher t set t.name = :newName, t.age = :newAge, t.gender = : newGender where t.id = :oldId")
                        .setParameter("newName", teacher.getName())
                        .setParameter("newAge", teacher.getAge())
                        .setParameter("newGender", teacher.getGender())
                        .setParameter("oldId", id)
                        .executeUpdate();

        tx.commit();
        return "success";
    }

    public String deleteOne(int id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createQuery("select s from Teacher s where s.id = ?1");
        query.setParameter(1, id);
        Teacher t = (Teacher)query.getSingleResult();
        em.remove(t);
        tx.commit();
        return "success";
    }
}
