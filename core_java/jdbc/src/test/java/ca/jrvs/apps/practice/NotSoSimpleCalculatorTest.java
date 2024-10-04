package ca.jrvs.apps.practice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class NotSoSimpleCalculatorTest {

    NotSoSimpleCalculator calc;

    @Mock
    SimpleCalculator mockSimpleCalc;

    @BeforeEach
    void init() {
        calc = new NotSoSimpleCalculatorImp(mockSimpleCalc);
    }

    @Test
    void PowerUnitTest() {
        int expected = 16;
        int actual = calc.power(4, 2);
        assertEquals(expected, actual);
    }

    @Test
    void AbsUnitTest() {
        //This test will currently fail
        //Consider if the provided logic in NotSoSimpleCalcualtorImpl is correct
        //Consider if you need to add anything to this test case (hint: you do)
        int expected = 10;
        int actual = calc.abs(10);
        assertEquals(expected, actual);
    }

    @Test
    void SqrtUnitTest() {
        double expected = 7;
        double actual = calc.sqrt(49);
        assertEquals(expected, actual);
    }

}
