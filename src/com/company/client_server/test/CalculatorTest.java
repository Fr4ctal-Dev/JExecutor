package com.company.client_server.test;

import com.company.client_server.commands.Calculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {


    @Test
    void testAddition() {
        assertEquals(8, Calculator.addition(5, 3));
        assertEquals(10, Calculator.addition(15, -5));
        assertEquals(-10, Calculator.addition(-5, -5));
        assertEquals(1, Calculator.addition(0.9, 0.1), 0.01);
        assertEquals(1, Calculator.addition(1, 0));
        assertEquals(0, Calculator.addition(0.2, -0.2), 0.01);
    }

    @Test
    void testSubtraction() {
        assertEquals(2, Calculator.subtraction(5, 3));
        assertEquals(20, Calculator.subtraction(15, -5));
        assertEquals(0, Calculator.subtraction(-5, -5));
        assertEquals(0.8, Calculator.subtraction(0.9, 0.1), 0.01);
        assertEquals(1, Calculator.subtraction(1, 0));
        assertEquals(0, Calculator.subtraction(-0.2, -0.2), 0.01);
    }

    @Test
    void testMultiplication() {
        assertEquals(15, Calculator.multiplication(5, 3));
        assertEquals(-20, Calculator.multiplication(4, -5));
        assertEquals(0, Calculator.multiplication(-5, 0));
        assertEquals(0.09, Calculator.multiplication(0.9, 0.1), 0.01);
        assertEquals(2000000000, Calculator.multiplication(1000000000, 2));
        assertEquals(0.4, Calculator.multiplication(-0.2, -2), 0.01);
    }

    @Test
    void testDivision() {
        assertEquals(8, Calculator.division(16, 2));
    }

}