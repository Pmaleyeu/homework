import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        
        Employee[] employees = new Employee[5];

        employees[0] = new Employee(
                "Иванов Иван Иванович",
                "Инженер",
                "ivanov@example.com",
                "+7-900-111-22-33",
                60000,
                35
        );

        employees[1] = new Employee(
                "Петров Пётр Петрович",
                "Менеджер",
                "petrov@example.com",
                "+7-900-222-33-44",
                75000,
                45
        );

        employees[2] = new Employee(
                "Сидорова Анна Сергеевна",
                "Бухгалтер",
                "sidorova@example.com",
                "+7-900-333-44-55",
                68000,
                41
        );

        employees[3] = new Employee(
                "Козлов Алексей Викторович",
                "Разработчик",
                "kozlov@example.com",
                "+7-900-444-55-66",
                95000,
                29
        );

        employees[4] = new Employee(
                "Смирнова Елена Андреевна",
                "Директор",
                "smirnova@example.com",
                "+7-900-555-66-77",
                150000,
                52
        );

        
        System.out.println("Сотрудники старше 40 лет:");
        for (Employee employee : employees) {
            if (employee.getAge() > 40) {
                employee.printInfo();
            }
        }

        
        Park park = new Park();
        park.addAttraction("Колесо обозрения", "10:00–22:00", 500);
        park.addAttraction("Американские горки", "11:00–21:00", 700);
        park.addAttraction("Карусель", "10:00–20:00", 300);

        System.out.println("\nАттракционы парка:");
        park.printAttractions();
    }
}

class Employee {
    private final String fullName;
    private final String position;
    private final String email;
    private final String phone;
    private final double salary;
    private final int age;

    public Employee(
            String fullName,
            String position,
            String email,
            String phone,
            double salary,
            int age
    ) {
        this.fullName = fullName;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void printInfo() {
        System.out.println("ФИО: " + fullName);
        System.out.println("Должность: " + position);
        System.out.println("Email: " + email);
        System.out.println("Телефон: " + phone);
        System.out.println("Зарплата: " + salary);
        System.out.println("Возраст: " + age);
        System.out.println();
    }
}

class Park {
    private final List<Attraction> attractions = new ArrayList<>();

    
    public class Attraction {
        private final String name;
        private final String workingHours;
        private final double price;

        public Attraction(String name, String workingHours, double price) {
            this.name = name;
            this.workingHours = workingHours;
            this.price = price;
        }

        public void printInfo() {
            System.out.printf(
                    "Аттракцион: %s, время работы: %s, стоимость: %.2f руб.%n",
                    name,
                    workingHours,
                    price
            );
        }
    }

    public void addAttraction(String name, String workingHours, double price) {
        attractions.add(new Attraction(name, workingHours, price));
    }

    public void printAttractions() {
        for (Attraction attraction : attractions) {
            attraction.printInfo();
        }
    }
}