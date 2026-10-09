import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        countEvenRandomNumbers();


        workWithCollection();


        sortStrings();


        workWithStudents();


        readLogins();
    }

    private static void countEvenRandomNumbers() {
        Random random = new Random();
        int[] numbers = new int[20];
        int evenCount = 0;

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(100); // число от 0 до 99
            if (numbers[i] % 2 == 0) {
                evenCount++;
            }
        }

        System.out.println("1. Случайные числа: " + Arrays.toString(numbers));
        System.out.println("Количество чётных чисел: " + evenCount);
    }

    private static void workWithCollection() {
        List<String> values = new ArrayList<>(
                Arrays.asList("Highload", "High", "Load", "Highload")
        );

        System.out.println("\n2. Коллекция: " + values);
        System.out.println("Количество элементов \"High\": "
                + Collections.frequency(values, "High"));
        System.out.println("Первый элемент: " + firstOrZero(values));
        System.out.println("Последний элемент: " + lastOrZero(values));


        List<String> emptyList = new ArrayList<>();
        System.out.println("Первый элемент пустой коллекции: "
                + firstOrZero(emptyList));
        System.out.println("Последний элемент пустой коллекции: "
                + lastOrZero(emptyList));
    }

    
    private static Object firstOrZero(List<String> values) {
        return values.isEmpty() ? 0 : values.get(0);
    }

    private static Object lastOrZero(List<String> values) {
        return values.isEmpty() ? 0 : values.get(values.size() - 1);
    }

    private static void sortStrings() {
        List<String> values = new ArrayList<>(
                Arrays.asList("f10", "f15", "f2", "f4", "f4")
        );

        Collections.sort(values);
        String[] sortedArray = values.toArray(new String[0]);

        System.out.println("\n3. Отсортированный массив: "
                + Arrays.toString(sortedArray));
    }

    private static void workWithStudents() {
 
        List<Student> students = Arrays.asList(
                new Student("Иван", 20, true),
                new Student("Мария", 21, false),
                new Student("Пётр", 17, true),
                new Student("Алексей", 27, true),
                new Student("Ольга", 25, false)
        );

        double averageMaleAge = students.stream()
                .filter(Student::isMale)
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);

        System.out.printf("%n4. Средний возраст студентов мужского пола: %.2f%n",
                averageMaleAge);

        System.out.println("Студенты мужского пола призывного возраста:");
        students.stream()
                .filter(Student::isMale)
                .filter(student -> student.getAge() >= 18
                        && student.getAge() <= 27)
                .forEach(student -> System.out.println(student.getName()
                        + ", " + student.getAge() + " лет"));
    }

    private static void readLogins() {
        List<String> logins = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\n5. Вводите логины. Пустая строка завершает ввод:");

            while (true) {
                String login = scanner.nextLine();

                if (login.isEmpty()) {
                    break;
                }

                logins.add(login);
            }
        }

        System.out.println("Логины, начинающиеся на строчную букву f:");
        logins.stream()
                .filter(login -> login.startsWith("f"))
                .forEach(System.out::println);
    }

    static class Student {
        private final String name;
        private final int age;
        private final boolean male;

        Student(String name, int age, boolean male) {
            this.name = name;
            this.age = age;
            this.male = male;
        }

        String getName() {
            return name;
        }

        int getAge() {
            return age;
        }

        boolean isMale() {
            return male;
        }
    }
}