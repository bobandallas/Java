package com.example.java26;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyFirstJunitTest {

    //只运行一次
    @BeforeAll
    public static void beforeAllTestInit() {
        System.out.println("before all");
    }
    @AfterAll
    public static void afterAllTestInit() {
        System.out.println("after all");
    }

    //每次test运行前初始化
    @BeforeEach
    public void beforeEachInit() {
        System.out.println("before each test cases");
    }

    @AfterEach
    public void afterEachInit() {
        System.out.println("after each test cases");
    }

    @Test
    public void func1() {
        assertTrue(true == true);
    }

    @Test
    public void func2() {
        assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException();
        });
    }
}

/**
 *  question: find first unique character from input stream
 *  input: String
 *  output: char
 *
 *  corner cases:
 *      1. cannot find unique / null / "" empty input => throw IllegalArgumentException
 *      2. character ranges / special characters => yes
 *      3. upper case / lower case => "Aa" => 'A'
 *      4. "A aA" => ' '
 *      5. return index or char
 */
class Solution {
    public static void main(String[] args) {

    }

    public char firstUniqueCharacter(String str) {
        if(str == null || str.equals("")) {
            throw new IllegalArgumentException(str + ": input is not valid");
        }
        Map<Character, Integer> cnt = new LinkedHashMap<>();
        for(int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            cnt.put(ch, cnt.getOrDefault(ch, 0) + 1);
        }
//        for(int i = 0; i < str.length(); i++) {
//            char ch = str.charAt(i);
//            if(cnt.get(ch) == 1) {
//                return ch;
//            }
//        }
        for(Map.Entry<Character, Integer> entry: cnt.entrySet()) {
            if(entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException(str + ": input is not valid");
    }
}

class SolutionUnitTest {
    private static Solution solution;

    @BeforeAll
    public static void init() {
        solution = new Solution();
    }

    @Test
    public void invalidInput() {
        assertThrows(IllegalArgumentException.class, () -> solution.firstUniqueCharacter(""));
        assertThrows(IllegalArgumentException.class, () -> solution.firstUniqueCharacter(null));
        assertThrows(IllegalArgumentException.class, () -> solution.firstUniqueCharacter("AAAAA"));
        assertThrows(IllegalArgumentException.class, () -> solution.firstUniqueCharacter("A  A aa2112"));
    }

    @Test
    public void specialCharactersInput() {
        assertEquals(solution.firstUniqueCharacter("A A"), ' ');
        assertEquals(solution.firstUniqueCharacter("1 2"), '1');
        assertEquals(solution.firstUniqueCharacter(" 2"), ' ');
        assertEquals(solution.firstUniqueCharacter("&&*"), '*');
    }

    @Test
    public void uppercaseAndLowercase() {
        assertEquals(solution.firstUniqueCharacter("Aa"), 'A');
        assertEquals(solution.firstUniqueCharacter("abc"), 'a');
        assertEquals(solution.firstUniqueCharacter("1122AAa"), 'a');
    }
}
