package homework;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FactorialJunitTest {

    @Test
    void factorialOfZeroIsOne() {
        assertEquals(BigInteger.ONE, Factorial.calculate(0));
    }

    @Test
    void factorialOfOneIsOne() {
        assertEquals(BigInteger.ONE, Factorial.calculate(1));
    }

    @Test
    void factorialOfFiveIsOneHundredTwenty() {
        assertEquals(BigInteger.valueOf(120), Factorial.calculate(5));
    }

    @Test
    void factorialSupportsLargeNumbers() {
        assertEquals(new BigInteger("2432902008176640000"),
                Factorial.calculate(20));
    }

    @Test
    void negativeNumberThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> Factorial.calculate(-1));
    }
}