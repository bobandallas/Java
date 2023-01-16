package com.example.java26.week3.rest.demo1.service;

import static com.example.java26.week3.rest.demo1.pojo.dto.EmployeeResponseDTO.*;

import com.example.java26.week3.rest.demo1.pojo.dto.EmployeeResponseDTO;
import com.example.java26.week3.rest.demo1.pojo.entity.Employee;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    EmployeeResponseDTO getAllEmp();
    EmployeeDTO getEmpById(String id);
}
