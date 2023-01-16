package com.example.java26.week3.rest.homework.repository;

import com.example.java26.week3.rest.homework.pojo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepositoryBase extends JpaRepository<Student, Integer> {

}