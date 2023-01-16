package com.example.java26.week3.rest.homework.service.impl;

import com.example.java26.week3.rest.homework.pojo.dto.StudentResponseDTO;
import com.example.java26.week3.rest.homework.pojo.dto.TeacherResponseDTO.*;
import com.example.java26.week3.rest.homework.pojo.entity.Student;
import com.example.java26.week3.rest.homework.pojo.entity.Teacher;
import com.example.java26.week3.rest.homework.repository.TeacherRepository;
import com.example.java26.week3.rest.homework.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository){
        this.teacherRepository = teacherRepository;
    }

    @Override
    public TeacherDTO getById(int id) {
        Teacher teacher = teacherRepository.getById(id);
        return new TeacherDTO(teacher);
    }


    @Override
    public List<TeacherDTO> getAll() {
        List<Teacher> temp = teacherRepository.getAll();;
        List<TeacherDTO> res = temp.stream()
                .map(e -> new TeacherDTO(e))
                .collect(Collectors.toList());
        return res;
    }
    @Override
    public String addOne( Teacher teacher){
        teacherRepository.addOne(teacher);
        return "success";
    }

    @Override
    public String deleteOne(int id){
        teacherRepository.deleteOne(id);
        return "success";
    }

    @Override
    public String updateOne(int id,  Teacher teacher){
        teacherRepository.updateOne(id, teacher);
        return "success";
    }

}
