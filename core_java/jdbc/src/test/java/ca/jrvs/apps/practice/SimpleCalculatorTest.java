package ca.jrvs.apps.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleCalculatorTest {

    SimpleCalculator calculator;

    @BeforeEach
    void init() {
        calculator = new SimpleCalculatorImp();
    }

    @Test
    void AddUnitTest() {
        int expected = 2;
        int actual = calculator.add(1, 1);
        assertEquals(expected, actual);
    }

    @Test
    void SubtractUnitTest() {
        int expected = 3;
        int actual = calculator.subtract(4, 1);
        assertEquals(expected, actual);
    }

    @Test
    void MultiplyUnitTest() {
        int expected = 15;
        int actual = calculator.multiply(5, 3);
        assertEquals(expected, actual);
    }

    @Test
    void DivideUnitTest() {
        double expected = 4;
        double actual = calculator.divide(12, 3);
        assertEquals(expected, actual);

    }

}