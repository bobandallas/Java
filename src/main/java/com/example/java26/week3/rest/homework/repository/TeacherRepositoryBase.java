package com.example.java26.week3.rest.homework.repository;

import com.example.java26.week3.rest.homework.pojo.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepositoryBase extends JpaRepository<Teacher, Integer> {

}
