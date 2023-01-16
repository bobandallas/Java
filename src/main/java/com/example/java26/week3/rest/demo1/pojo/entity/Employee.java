package com.example.java26.week3.rest.demo1.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.lang.annotation.Target;
import java.util.Date;


//          使用spring中，为了实现类的注入和属性值注入，一般会在定义的类里声明set,get等方法，需要对类里的每个属性值都需声明造成比较冗余，
//        使用了@Data注解后，就可以在直接定义的类添加即可，就会自动实现类里所有属性的set,get等方法，极大的简化了代码
//        该类提供了get、set、equals、hashCode、canEqual、toString方法

@Data
@Entity
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String name;
    @Column
    @JsonIgnore
    private Date lastAccessDate;
    @Column
    @JsonIgnore  //不会把这个colum转换成为json返回， 第二个方法是创建dto Data Transfer Object
    private boolean isActive;
}
