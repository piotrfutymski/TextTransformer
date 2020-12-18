package put.io.testing.junit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private Calculator calc;

    @BeforeEach
    public void setUp(){
        calc = new Calculator();
    }

    @Test
    void testAdd() {
        assertEquals(1, calc.add(0, 1));
        assertEquals(0, calc.add(-1, 1));
    }

    @Test
    void testMultiply() {
        assertEquals(0, calc.multiply(0, 5));
        assertEquals(-20, calc.multiply(-2, 10));
    }

    @Test
    void testAddPositiveNumbers(){
        assertThrows(IllegalArgumentException.class, ()->{calc.addPositiveNumbers(-5, 5);});
    }
}