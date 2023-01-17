package com.example.java26.week4.homework.controller;

import com.example.java26.week3.rest.homework.pojo.dto.StudentResponseDTO;
import com.example.java26.week3.rest.homework.pojo.entity.Student;
import com.example.java26.week4.homework.service.HomeWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class HomeworkController {
    private final HomeWorkService homeWorkService;

    @Autowired
    public HomeworkController(HomeWorkService homeWorkService){
        this.homeWorkService = homeWorkService;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<StudentResponseDTO.StudentDTO> getEmpById(@PathVariable int id) {
//        return new ResponseEntity<>(studentService.getById(id), HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<Map> getAll(){
        return new ResponseEntity<>(homeWorkService.findAllGroupByAge(), HttpStatus.OK);
    }

    @GetMapping(params = "age")
    public ResponseEntity<Map> getCondition(@RequestParam int age){
        return new ResponseEntity<>(homeWorkService.getCondition(age), HttpStatus.OK);
    }


//    CustomGlobalExceptionHandler
}
