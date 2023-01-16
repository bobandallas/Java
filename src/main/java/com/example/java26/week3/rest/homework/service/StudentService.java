package com.example.java26.week3.rest.homework.service;
import static com.example.java26.week3.rest.homework.pojo.dto.StudentResponseDTO.*;
import com.example.java26.week3.rest.homework.pojo.dto.StudentResponseDTO;
import com.example.java26.week3.rest.homework.pojo.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
//    void add();
//    void delete();

    StudentDTO getById(int id);

    List<StudentDTO> findAll();

    String addOne(Student student);

    String deleteOne(int id);

    String updateOne(int id, Student student);

}
