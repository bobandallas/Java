package com.example.java26.week3.rest.homework.controller;

import com.example.java26.week3.rest.demo1.pojo.dto.EmployeeResponseDTO;
import com.example.java26.week3.rest.homework.pojo.dto.StudentResponseDTO;
import com.example.java26.week3.rest.homework.pojo.entity.Student;
import com.example.java26.week3.rest.homework.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO.StudentDTO> getEmpById(@PathVariable int id) {
        return new ResponseEntity<>(studentService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO.StudentDTO>> getAll(){
        return new ResponseEntity<>(studentService.findAll(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> addOne(@RequestBody Student student){
        return new ResponseEntity<>(studentService.addOne(student), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOne(@PathVariable int id){
        return new ResponseEntity<>(studentService.deleteOne(id), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> updateOne(@PathVariable int id,@RequestBody Student student){
        return new ResponseEntity<>(studentService.updateOne(id, student), HttpStatus.OK);
    }

//    CustomGlobalExceptionHandler
}
