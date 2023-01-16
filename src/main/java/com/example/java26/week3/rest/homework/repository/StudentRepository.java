package com.example.java26.week3.rest.homework.repository;


import com.example.java26.week3.rest.demo1.pojo.entity.Employee;
import com.example.java26.week3.rest.homework.pojo.dto.StudentResponseDTO;
import com.example.java26.week3.rest.homework.pojo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository{
    String deleteOne(int id);

    String addOne(Student student);

    String updateOne(int id, Student student);

    List<Student> findAll();

    Student getById(int id);

}


