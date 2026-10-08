public class Main {

    public static void main(String[] args) {
        // Задача 1: животные и еда
        Dog dog = new Dog("Бобик");
        Cat cat = new Cat("Мурзик", 5);

        dog.run(150);
        dog.swim(8);
        cat.run(100);
        cat.swim(3);

        Plate plate = new Plate(10);
        Cat[] cats = {
                new Cat("Барсик", 5),
                new Cat("Пушок", 4),
                new Cat("Рыжик", 3)
        };

        System.out.println("\nКоты обедают:");
        for (Cat currentCat : cats) {
            currentCat.eat(plate);
        }

        System.out.println("\nСытость котов:");
        for (Cat currentCat : cats) {
            System.out.println(currentCat.getName() + ": "
                    + (currentCat.isFull() ? "сыт" : "голоден"));
        }

        System.out.println("Осталось еды: " + plate.getFood());

        System.out.println("\nСоздано животных: " + Animal.getCreatedAnimalsCount());
        System.out.println("Создано собак: " + Dog.getCreatedDogsCount());
        System.out.println("Создано котов: " + Cat.getCreatedCatsCount());

        // Задача 2: геометрические фигуры
        Shape[] shapes = {
                new Circle(5, "красный", "чёрный"),
                new Rectangle(4, 6, "синий", "белый"),
                new Triangle(3, 4, 5, "зелёный", "чёрный")
        };

        System.out.println("\nГеометрические фигуры:");
        for (Shape shape : shapes) {
            shape.printInfo();
        }
    }
}

// -------------------- Задача 1 --------------------

abstract class Animal {
    private static int createdAnimalsCount = 0;

    private final String name;

    protected Animal(String name) {
        this.name = name;
        createdAnimalsCount++;
    }

    public String getName() {
        return name;
    }

    public static int getCreatedAnimalsCount() {
        return createdAnimalsCount;
    }

    public abstract void run(int distance);

    public abstract void swim(int distance);
}

class Dog extends Animal {
    private static final int MAX_RUN_DISTANCE = 500;
    private static final int MAX_SWIM_DISTANCE = 10;

    private static int createdDogsCount = 0;

    public Dog(String name) {
        super(name);
        createdDogsCount++;
    }

    public static int getCreatedDogsCount() {
        return createdDogsCount;
    }

    @Override
    public void run(int distance) {
        if (distance < 0) {
            System.out.println(getName() + ": расстояние не может быть отрицательным.");
        } else if (distance <= MAX_RUN_DISTANCE) {
            System.out.println(getName() + " пробежал " + distance + " м.");
        } else {
            System.out.println(getName() + " не смог пробежать " + distance + " м.");
        }
    }

    @Override
    public void swim(int distance) {
        if (distance < 0) {
            System.out.println(getName() + ": расстояние не может быть отрицательным.");
        } else if (distance <= MAX_SWIM_DISTANCE) {
            System.out.println(getName() + " проплыл " + distance + " м.");
        } else {
            System.out.println(getName() + " не смог проплыть " + distance + " м.");
        }
    }
}

class Cat extends Animal {
    private static final int MAX_RUN_DISTANCE = 200;

    private static int createdCatsCount = 0;

    private final int appetite;
    private boolean full;

    public Cat(String name, int appetite) {
        super(name);

        if (appetite < 0) {
            throw new IllegalArgumentException("Аппетит не может быть отрицательным.");
        }

        this.appetite = appetite;
        this.full = false;
        createdCatsCount++;
    }

    public static int getCreatedCatsCount() {
        return createdCatsCount;
    }

    public boolean isFull() {
        return full;
    }

    @Override
    public void run(int distance) {
        if (distance < 0) {
            System.out.println(getName() + ": расстояние не может быть отрицательным.");
        } else if (distance <= MAX_RUN_DISTANCE) {
            System.out.println(getName() + " пробежал " + distance + " м.");
        } else {
            System.out.println(getName() + " не смог пробежать " + distance + " м.");
        }
    }

    @Override
    public void swim(int distance) {
        System.out.println(getName() + " не умеет плавать.");
    }

    public void eat(Plate plate) {
        if (full) {
            System.out.println(getName() + " уже сыт.");
        } else if (plate.decreaseFood(appetite)) {
            full = true;
            System.out.println(getName() + " поел.");
        } else {
            System.out.println(getName() + " не стал есть: в тарелке недостаточно еды.");
        }
    }
}

class Plate {
    private int food;

    public Plate(int food) {
        if (food < 0) {
            throw new IllegalArgumentException("Количество еды не может быть отрицательным.");
        }
        this.food = food;
    }

    public int getFood() {
        return food;
    }

    // Уменьшает количество еды только тогда, когда еды достаточно.
    public boolean decreaseFood(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Количество еды не может быть отрицательным.");
        }

        if (food < amount) {
            return false;
        }

        food -= amount;
        return true;
    }

    public void addFood(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Нельзя добавить отрицательное количество еды.");
        }

        food += amount;
    }
}

// -------------------- Задача 2 --------------------

interface Shape {
    String getFillColor();

    String getBorderColor();

    /*
     * Каждая фигура возвращает части своей границы.
     * Для круга это длина окружности, для прямоугольника —
     * длины его сторон, для треугольника — три стороны.
     */
    double[] getBoundaryParts();

    double getArea();

    // Общий алгоритм вычисления периметра для всех фигур.
    default double getPerimeter() {
        double perimeter = 0;

        for (double part : getBoundaryParts()) {
            perimeter += part;
        }

        return perimeter;
    }

    default void printInfo() {
        System.out.printf(
                "%s | Периметр: %.2f | Площадь: %.2f | Цвет заливки: %s | Цвет границы: %s%n",
                getClass().getSimpleName(),
                getPerimeter(),
                getArea(),
                getFillColor(),
                getBorderColor()
        );
    }
}

abstract class AbstractShape implements Shape {
    private final String fillColor;
    private final String borderColor;

    protected AbstractShape(String fillColor, String borderColor) {
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    @Override
    public String getFillColor() {
        return fillColor;
    }

    @Override
    public String getBorderColor() {
        return borderColor;
    }
}

class Circle extends AbstractShape {
    private final double radius;

    public Circle(double radius, String fillColor, String borderColor) {
        super(fillColor, borderColor);

        if (radius <= 0) {
            throw new IllegalArgumentException("Радиус должен быть больше нуля.");
        }

        this.radius = radius;
    }

    @Override
    public double[] getBoundaryParts() {
        return new double[]{2 * Math.PI * radius};
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends AbstractShape {
    private final double width;
    private final double height;

    public Rectangle(double width, double height, String fillColor, String borderColor) {
        super(fillColor, borderColor);

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Стороны прямоугольника должны быть больше нуля.");
        }

        this.width = width;
        this.height = height;
    }

    @Override
    public double[] getBoundaryParts() {
        return new double[]{width, height, width, height};
    }

    @Override
    public double getArea() {
        return width * height;
    }
}

class Triangle extends AbstractShape {
    private final double sideA;
    private final double sideB;
    private final double sideC;

    public Triangle(
            double sideA,
            double sideB,
            double sideC,
            String fillColor,
            String borderColor
    ) {
        super(fillColor, borderColor);

        if (sideA <= 0 || sideB <= 0 || sideC <= 0
                || sideA + sideB <= sideC
                || sideA + sideC <= sideB
                || sideB + sideC <= sideA) {
            throw new IllegalArgumentException("Указаны некорректные стороны треугольника.");
        }

        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    @Override
    public double[] getBoundaryParts() {
        return new double[]{sideA, sideB, sideC};
    }

    @Override
    public double getArea() {
        // Формула Герона
        double halfPerimeter = getPerimeter() / 2;
        return Math.sqrt(
                halfPerimeter
                        * (halfPerimeter - sideA)
                        * (halfPerimeter - sideB)
                        * (halfPerimeter - sideC)
        );
    }
}