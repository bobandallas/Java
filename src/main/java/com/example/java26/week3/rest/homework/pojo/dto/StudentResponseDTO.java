package com.example.java26.week3.rest.homework.pojo.dto;

import com.example.java26.week3.rest.homework.pojo.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class StudentResponseDTO {
    private List<StudentDTO> students;


    @Data
    @Builder
    @AllArgsConstructor
    public static class StudentDTO{
        private int id;
        private String name;

        private int age;

        public StudentDTO(Student s){
            this.id = s.getId();
            this.name = s.getName();
            this.age = s.getAge();
        }
    }

}
