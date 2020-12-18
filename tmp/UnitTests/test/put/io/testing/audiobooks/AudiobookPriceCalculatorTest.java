package put.io.testing.audiobooks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudiobookPriceCalculatorTest {
    private AudiobookPriceCalculator calc;
    private Audiobook audiobook;

    @BeforeEach
    void setUp(){
        calc = new AudiobookPriceCalculator();
        audiobook = new Audiobook("The Hunger Games", 20.0);
    }

    @Test
    void test1(){
        Customer customer = new Customer("Michal", Customer.LoyaltyLevel.STANDARD, true);
        assertEquals(0.0, calc.calculate(customer, audiobook));
    }

    @Test
    void test2(){
        Customer customer = new Customer("Michal", Customer.LoyaltyLevel.STANDARD, false);
        assertEquals(20.0, calc.calculate(customer, audiobook));
    }

    @Test
    void test3(){
        Customer customer = new Customer("Michal", Customer.LoyaltyLevel.SILVER, false);
        assertEquals(20.0 * 0.9, calc.calculate(customer, audiobook));
    }

    @Test
    void test4(){
        Customer customer = new Customer("Michal", Customer.LoyaltyLevel.GOLD, false);
        assertEquals(20.0 * 0.8, calc.calculate(customer, audiobook));
    }

}