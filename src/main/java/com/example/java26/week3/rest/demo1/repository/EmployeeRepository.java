package com.example.java26.week3.rest.demo1.repository;

import com.example.java26.week3.rest.demo1.pojo.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EmployeeRepository {
    Employee findById(String id);
    Collection<Employee> findAll();
}
