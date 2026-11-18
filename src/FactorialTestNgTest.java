package homework;

import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

public class FactorialTestNgTest {

    @Test
    public void factorialOfZeroIsOne() {
        assertEquals(Factorial.calculate(0), BigInteger.ONE);
    }

    @Test
    public void factorialOfOneIsOne() {
        assertEquals(Factorial.calculate(1), BigInteger.ONE);
    }

    @Test
    public void factorialOfFiveIsOneHundredTwenty() {
        assertEquals(Factorial.calculate(5), BigInteger.valueOf(120));
    }

    @Test
    public void factorialSupportsLargeNumbers() {
        assertEquals(Factorial.calculate(20),
                new BigInteger("2432902008176640000"));
    }

    @Test
    public void negativeNumberThrowsException() {
        expectThrows(IllegalArgumentException.class,
                () -> Factorial.calculate(-1));
    }
}