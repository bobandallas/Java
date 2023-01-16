package com.example.java26.week3.rest.homework.controller;


import com.example.java26.week3.rest.homework.pojo.dto.TeacherResponseDTO.*;
import com.example.java26.week3.rest.homework.pojo.entity.Student;
import com.example.java26.week3.rest.homework.pojo.entity.Teacher;
import com.example.java26.week3.rest.homework.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;
    @Autowired
    public TeacherController(TeacherService teacherService){
        this.teacherService = teacherService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable int id){
        return new ResponseEntity<>(teacherService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAll(){
        return new ResponseEntity<>(teacherService.getAll(), HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<String> addOne(@RequestBody Teacher teacher){
        return new ResponseEntity<>(teacherService.addOne(teacher), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOne(@PathVariable int id){
        return new ResponseEntity<>(teacherService.deleteOne(id), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> updateOne(@PathVariable int id,@RequestBody Teacher teacher){
        return new ResponseEntity<>(teacherService.updateOne(id, teacher), HttpStatus.OK);
    }
}
