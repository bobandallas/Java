package com.example.java26.week3.rest.demo1.repository.impl;

import com.example.java26.week3.rest.demo1.pojo.entity.Employee;
import com.example.java26.week3.rest.demo1.repository.EmployeeRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final Map<String, Employee> map = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        map.put("1", new Employee("1", "Tom", new Date(), true));
        map.put("2", new Employee("2", "Jerry", new Date(), false));
    }

    @Override
    public Employee findById(String id) {
        return map.get(id);
    }

    @Override
    public Collection<Employee> findAll() {
        return map.values();
    }
}
