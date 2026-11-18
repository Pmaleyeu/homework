package homework;

import java.math.BigInteger;

public final class Factorial {

    private Factorial() {
    }

    public static BigInteger calculate(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Число не должно быть отрицательным");
        }

        BigInteger result = BigInteger.ONE;

        for (int i = 2; i <= number; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }

        return result;
    }

    public static void main(String[] args) {
        int number = 5;
        System.out.println(number + "! = " + calculate(number));
    }
}