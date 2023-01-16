package com.example.java26.week3.rest.homework.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "teacher")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Teacher {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private  int id;

    @Column
    private String name;

    @Column
    private int age;

    @Column
    private String gender;
}
