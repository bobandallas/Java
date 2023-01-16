package com.example.java26.week3.rest.homework.service.impl;

import static com.example.java26.week3.rest.homework.pojo.dto.StudentResponseDTO.*;

import com.example.java26.week3.rest.homework.pojo.entity.Student;
import com.example.java26.week3.rest.homework.repository.StudentRepository;
import com.example.java26.week3.rest.homework.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;


    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentDTO getById(int id) {
        Student student = studentRepository.getById(id);
        return new StudentDTO(student);
    }
    @Override
    public List<StudentDTO> findAll(){
        List<Student> temp = studentRepository.findAll();
        List<StudentDTO> res = temp.stream()
                                    .map(e -> new StudentDTO(e))
                                    .collect(Collectors.toList());
        return res;
    }

    @Override
    public String addOne(Student student){
        studentRepository.addOne(student);
        return "success";
    }

    @Override
    public String deleteOne(int id){
        studentRepository.deleteOne(id);
        return "success";
    }

    @Override
    public String updateOne(int id, Student student){
        studentRepository.updateOne(id, student);
        return "success";
    }
}
