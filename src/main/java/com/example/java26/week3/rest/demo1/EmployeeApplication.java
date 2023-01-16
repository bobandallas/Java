package com.example.java26.week3.rest.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }
}

/**
 *  rest api : 10 - 20min
 *  easy LeetCode : 2~5 min
 *  common medium : 5 ~ 15 min
 *  sql query: < 3min
 *
 *
 *  homework:
 *      1. rest api design in readme
 *      2. impl Student + Teacher rest api : CRUD
 *      3. handle global exception @ControllerAdvice + @ExceptionHandler
 *  deadline: next Monday 10am cdt
 *
 *  CREATE TABLE Student(
 *    ID INT PRIMARY KEY     NOT NULL,
 *    NAME           TEXT    NOT NULL,
 *    AGE            INT,
 *    ISEXIST        BOOLEAN,
 * );
 */

// CREATE TABLE Student(
//         ID INT PRIMARY KEY     NOT NULL,
//         NAME           TEXT    NOT NULL,
//         AGE            INT,
//         ISEXIST        BOOLEAN
//         );
//
//    CREATE TABLE Teacher(
//        ID INT PRIMARY KEY     NOT NULL,
//        NAME           TEXT    NOT NULL,
//        AGE            INT,
//        ISEXIST        BOOLEAN
//);
//
//    CREATE TABLE Student_Teacher(
//            S_ID INT references Student(ID),
//            T_ID INT references Teacher(ID),
//            PRIMARY KEY(S_ID, T_ID)
//
//);