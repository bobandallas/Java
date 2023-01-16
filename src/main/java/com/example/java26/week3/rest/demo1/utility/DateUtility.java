package com.example.java26.week3.rest.demo1.utility;

import org.springframework.stereotype.Component;

import java.util.Date;

public class DateUtility {

    private DateUtility() {}

    public static Date getCurrentDate() {
        return new Date();
    }
}
