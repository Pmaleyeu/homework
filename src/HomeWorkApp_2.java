import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.out.println(isSumInRange(5, 10));

        printPositiveOrNegative(0); 

        System.out.println(isNegative(-5)); 

        printStringMultiple("Привет!", 3);

        System.out.println(isLeapYear(2024)); 

        int[] binaryArray = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        invertZeroAndOne(binaryArray);
        System.out.println(Arrays.toString(binaryArray));

        int[] sequence = createSequenceArray();
        System.out.println(Arrays.toString(sequence));

        int[] numbers = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        multiplyNumbersLessThanSix(numbers);
        System.out.println(Arrays.toString(numbers));

        int[][] matrix = createDiagonalMatrix(5);
        System.out.println(Arrays.deepToString(matrix));

        int[] filledArray = createArray(5, 7);
        System.out.println(Arrays.toString(filledArray));
    }

  
    public static boolean isSumInRange(int firstNumber, int secondNumber) {
        long sum = (long) firstNumber + secondNumber;
        return sum >= 10 && sum <= 20;
    }

    
    public static void printPositiveOrNegative(int number) {
        if (number >= 0) {
            System.out.println("Положительное число");
        } else {
            System.out.println("Отрицательное число");
        }
    }

  
    public static boolean isNegative(int number) {
        return number < 0;
    }

  
    public static void printStringMultiple(String text, int count) {
        for (int i = 0; i < count; i++) {
            System.out.println(text);
        }
    }

    
    public static boolean isLeapYear(int year) {
        return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
    }

    
    public static void invertZeroAndOne(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                array[i] = 1;
            } else if (array[i] == 1) {
                array[i] = 0;
            }
        }
    }

    
    public static int[] createSequenceArray() {
        int[] array = new int[100];

        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        return array;
    }

   
    public static void multiplyNumbersLessThanSix(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 6) {
                array[i] *= 2;
            }
        }
    }

    
    public static int[][] createDiagonalMatrix(int size) {
        int[][] matrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            matrix[i][i] = 1;
        }

        return matrix;
    }

    
    public static int[] createArray(int len, int initialValue) {
        int[] array = new int[len];

        for (int i = 0; i < array.length; i++) {
            array[i] = initialValue;
        }

        return array;
    }
}