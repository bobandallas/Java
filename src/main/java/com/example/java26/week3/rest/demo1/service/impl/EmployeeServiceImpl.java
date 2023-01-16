package com.example.java26.week3.rest.demo1.service.impl;

import com.example.java26.week3.rest.demo1.exception.ResourceNotFoundException;
import com.example.java26.week3.rest.demo1.pojo.dto.EmployeeResponseDTO;
import static com.example.java26.week3.rest.demo1.pojo.dto.EmployeeResponseDTO.*;

import com.example.java26.week3.rest.demo1.pojo.entity.Employee;
import com.example.java26.week3.rest.demo1.repository.EmployeeRepository;
import com.example.java26.week3.rest.demo1.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeResponseDTO getAllEmp() {
        Collection<Employee> employeeCollection = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOS = employeeCollection
                    .stream()
                    .map(e -> new EmployeeDTO(e))
                    .collect(Collectors.toList());
        return new EmployeeResponseDTO(employeeDTOS);
    }

    @Override
    public EmployeeDTO getEmpById(String id) {
        //log id
        Employee employee = employeeRepository.findById(id);
        //log employee info
        if(employee == null) {
            //log..
            throw new ResourceNotFoundException("..");
        }
        return new EmployeeDTO(employee);
    }

}
