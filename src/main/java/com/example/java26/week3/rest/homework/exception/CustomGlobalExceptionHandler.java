package com.example.java26.week3.rest.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<String> manageException1(Exception ex) {
        StackTraceElement[] ste = ex.getStackTrace();
        String className=ste[ste.length - 1].getClassName();
        System.out.println(className);

        return new ResponseEntity<>("Error occur: " + className , HttpStatus.NOT_FOUND);
    }

}