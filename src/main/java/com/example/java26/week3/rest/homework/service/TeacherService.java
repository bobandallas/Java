package com.example.java26.week3.rest.homework.service;

import com.example.java26.week3.rest.homework.pojo.dto.TeacherResponseDTO.*;
import com.example.java26.week3.rest.homework.pojo.entity.Student;
import com.example.java26.week3.rest.homework.pojo.entity.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherService {
    TeacherDTO getById(int id);

    List<TeacherDTO> getAll();

    String addOne(Teacher teacher);

    String deleteOne(int id);

    String updateOne(int id, Teacher teacher);
}
