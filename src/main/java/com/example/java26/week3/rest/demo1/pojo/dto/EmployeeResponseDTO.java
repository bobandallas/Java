package com.example.java26.week3.rest.demo1.pojo.dto;

import com.example.java26.week3.rest.demo1.pojo.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeResponseDTO {
    private List<EmployeeDTO> employeeList;

    @Data
    @AllArgsConstructor
    @Builder
    public static class EmployeeDTO {
        private String id;
        private String name;

        public EmployeeDTO(Employee e) {
            this.id = e.getId();
            this.name = e.getName();
        }
    }
}
