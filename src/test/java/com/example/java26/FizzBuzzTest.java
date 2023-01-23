package com.example.java26;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FizzBuzzTest {

    private B b;
    private A a;

    @BeforeEach
    public void init() {
        b = mock(B.class);
        a = new A(b);
    }

    @Test
    public void mockTest() {
        FizzBuzz fizzBuzz = mock(FizzBuzz.class);
        when(fizzBuzz.fizzBuzzHelper(5)).thenReturn("5");
        assertEquals(fizzBuzz.fizzBuzzHelper(5), "5");
        verify(fizzBuzz, times(1)).fizzBuzzHelper(5);
        //检测调用了多少次
    }

    @Test
    public void testA() {
        when(b.get(5)).thenReturn(5);
        assertEquals(5, a.print(5));
    }
}

class A {
    private B b;

    public A(B b) {
        this.b = b;
    }

    public int print(int x) {
        return b.get(x);
    }
}
class B {
    public int get(int x) {
        return x;
    }
}

/**
 *  print fizz buzz 1 ~ 100
 *  1. public List<String> FizzBuzz(int n)
 *  2. public String fizzBuzzHelper(int number)
 *     public void printFizzBuzz(int from, int to)
 *  3. public List<String> FizzBuzz(int from, int to)
 *  4. public void FizzBuzz(int from, int to)
 *
 *  solution
 *      1. cache
 */
class FizzBuzz {

    public void printFizzBuzz(int from, int to) {
        if(from > to) {
            throw new IllegalArgumentException("..");
        }
        for(; from <= to; from++) {
            System.out.println(fizzBuzzHelper(from));
        }
    }

    public String fizzBuzzHelper(int number) {
        if(number <= 0) {
            throw new IllegalArgumentException("..");
        }
        String res;
        if(number % 3 == 0 && number % 5 == 0) {
            res = "fizzbuzz";
        } else if(number % 3 == 0) {
            res = "fizz";
        } else if(number % 5 == 0) {
            res = "buzz";
        } else {
            res = String.valueOf(number);
        }
        return res;
    }
}
