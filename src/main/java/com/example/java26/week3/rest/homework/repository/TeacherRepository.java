package com.example.java26.week3.rest.homework.repository;

import com.example.java26.week3.rest.homework.pojo.entity.Student;
import com.example.java26.week3.rest.homework.pojo.entity.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository {
    Teacher getById(int id);
    List<Teacher> getAll();

    String deleteOne(int id);

    String addOne(Teacher teacher);

    String updateOne(int id, Teacher teacher);

}
