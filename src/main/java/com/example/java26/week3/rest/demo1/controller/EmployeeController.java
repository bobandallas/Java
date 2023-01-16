package com.example.java26.week3.rest.demo1.controller;

import com.example.java26.week3.rest.demo1.exception.ResourceNotFoundException;
import com.example.java26.week3.rest.demo1.pojo.dto.EmployeeResponseDTO;
import com.example.java26.week3.rest.demo1.pojo.entity.Employee;
import com.example.java26.week3.rest.demo1.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.java26.week3.rest.demo1.pojo.dto.EmployeeResponseDTO.*;
import java.util.Date;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

//    @responseBody注解的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，
//    写入到response对象的body区，通常用来返回JSON数据或者是XML数据。
//    注意：在使用此注解之后不会再走视图处理器，而是直接将数据写入到输入流中，
//    他的效果等同于通过response对象输出指定格式的数据。


    private final EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<EmployeeResponseDTO> getEmp(@RequestParam(required = false) String name) {
        return new ResponseEntity<>(service.getAllEmp(), HttpStatus.OK);
    }

    //ResponseEntity 可以改变 status code

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmpById(@PathVariable String id) {
        return new ResponseEntity<>(service.getEmpById(id), HttpStatus.OK);
    }


    //@RequestBody Employee employee 会把传入的body转换为json

    @PostMapping
    public ResponseEntity<String> getEmp(@RequestBody Employee employee) {
        return new ResponseEntity<>("1", HttpStatus.OK);
    }
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<?> handleException() {
//        return new ResponseEntity<>("xxx not found", HttpStatus.NOT_FOUND);
//    }
}
