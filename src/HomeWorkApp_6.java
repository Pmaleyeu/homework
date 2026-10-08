public class Main {

    public static void main(String[] args) {
        String[][] array = {
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };

        try {
            int sum = sumArray(array);
            System.out.println("Сумма элементов массива: " + sum);
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static int sumArray(String[][] array)
            throws MyArraySizeException, MyArrayDataException {

        if (array == null || array.length != 4) {
            throw new MyArraySizeException("Ожидается двумерный массив размером 4x4.");
        }

        for (int row = 0; row < array.length; row++) {
            if (array[row] == null || array[row].length != 4) {
                throw new MyArraySizeException("Строка " + row + " должна содержать 4 элемента.");
            }
        }

        int sum = 0;

        for (int row = 0; row < array.length; row++) {
            for (int column = 0; column < array[row].length; column++) {
                try {
                    sum += Integer.parseInt(array[row][column].trim());
                } catch (NumberFormatException | NullPointerException e) {
                    throw new MyArrayDataException(
                            "Некорректные данные в ячейке [" + row + "][" + column
                                    + "]: \"" + array[row][column] + "\""
                    );
                }
            }
        }

        return sum;
    }
}

class MyArraySizeException extends Exception {
    public MyArraySizeException(String message) {
        super(message);
    }
}

class MyArrayDataException extends Exception {
    public MyArrayDataException(String message) {
        super(message);
    }
}