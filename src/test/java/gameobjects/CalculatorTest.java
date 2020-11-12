package gameobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    private transient Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void calculateTimeBonus1Positive1() {
        assertEquals(100, calculator.calculateTimeBonus(1,100));
    }

    @Test
    void calculateTimeBonus1Positive2() {
        assertEquals(50, calculator.calculateTimeBonus(1,200));
    }

    @Test
    void calculateTimeBonus1ZeroTime() {
        assertEquals(150, calculator.calculateTimeBonus(1,0));
    }

    @Test
    void calculateTimeBonus1LongTime1() {
        assertEquals(0, calculator.calculateTimeBonus(1,300));
    }

    @Test
    void calculateTimeBonus1LongTime2() {
        assertEquals(0, calculator.calculateTimeBonus(1,1500));
    }

    @Test
    void calculateTimeBonus2Positive1() {
        assertEquals(250, calculator.calculateTimeBonus(2,100));
    }

    @Test
    void calculateTimeBonus2Positive2() {
        assertEquals(50, calculator.calculateTimeBonus(2,500));
    }

    @Test
    void calculateTimeBonus2ZeroTime() {
        assertEquals(300, calculator.calculateTimeBonus(2,0));
    }

    @Test
    void calculateTimeBonus2LongTime1() {
        assertEquals(0, calculator.calculateTimeBonus(2,600));
    }

    @Test
    void calculateTimeBonus2LongTime2() {
        assertEquals(0, calculator.calculateTimeBonus(2,1500));
    }
}