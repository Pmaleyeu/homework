import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Box<Apple> appleBox1 = new Box<>();
        appleBox1.addFruit(new Apple());
        appleBox1.addFruit(new Apple());

        Box<Apple> appleBox2 = new Box<>();
        appleBox2.addFruit(new Apple());
        appleBox2.addFruit(new Apple());

        Box<Orange> orangeBox = new Box<>();
        orangeBox.addFruit(new Orange());

        System.out.println("Вес первой коробки с яблоками: " + appleBox1.getWeight());
        System.out.println("Вес коробок с яблоками одинаков: "
                + appleBox1.compare(appleBox2));

       
        System.out.println("Вес коробки с яблоками равен весу коробки с апельсинами: "
                + appleBox1.compare(orangeBox));

        appleBox1.transferTo(appleBox2);

        System.out.println("После пересыпания:");
        System.out.println("Вес первой коробки: " + appleBox1.getWeight());
        System.out.println("Вес второй коробки: " + appleBox2.getWeight());
    }
}

abstract class Fruit {
    public abstract float getWeight();
}

class Apple extends Fruit {
    @Override
    public float getWeight() {
        return 1.0f;
    }
}

class Orange extends Fruit {
    @Override
    public float getWeight() {
        return 1.5f;
    }
}

class Box<T extends Fruit> {
    private final List<T> fruits = new ArrayList<>();

    public void addFruit(T fruit) {
        if (fruit == null) {
            throw new IllegalArgumentException("Нельзя добавить null в коробку.");
        }

        fruits.add(fruit);
    }

    public float getWeight() {
        float totalWeight = 0.0f;

        for (T fruit : fruits) {
            totalWeight += fruit.getWeight();
        }

        return totalWeight;
    }

    public boolean compare(Box<?> anotherBox) {
        if (anotherBox == null) {
            return false;
        }

        return Float.compare(getWeight(), anotherBox.getWeight()) == 0;
    }

    public void transferTo(Box<T> destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Коробка назначения не должна быть null.");
        }

       
        if (this == destination) {
            return;
        }

        destination.fruits.addAll(fruits);
        fruits.clear();
    }

    public int getFruitCount() {
        return fruits.size();
    }
}