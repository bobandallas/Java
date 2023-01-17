package com.example.java26.week4.homework.service;

import com.example.java26.week3.rest.homework.pojo.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.example.java26.week3.rest.homework.pojo.dto.StudentResponseDTO.StudentDTO;

@Service
public interface HomeWorkService {


    Map findAllGroupByAge();
    Map getCondition(int age);


}
