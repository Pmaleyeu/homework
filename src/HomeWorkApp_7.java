import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        printUniqueWordsAndCounts();

        System.out.println();

        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Иванов", "+375291111111");
        phoneBook.add("Петров", "+375292222222");
        phoneBook.add("Иванов", "+375293333333");

        System.out.println("Телефоны Иванова: " + phoneBook.get("Иванов"));
        System.out.println("Телефоны Петрова: " + phoneBook.get("Петров"));
        System.out.println("Телефоны Сидорова: " + phoneBook.get("Сидоров"));
    }

    private static void printUniqueWordsAndCounts() {
        String[] words = {
                "яблоко", "банан", "груша", "яблоко", "апельсин",
                "банан", "слива", "груша", "яблоко", "киви",
                "слива", "банан", "арбуз", "киви", "яблоко"
        };

        Map<String, Integer> wordCounts = new LinkedHashMap<>();

        for (String word : words) {
            wordCounts.merge(word, 1, Integer::sum);
        }

        System.out.println("Уникальные слова и количество повторений:");
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            System.out.println(entry.getKey() + " — " + entry.getValue());
        }
    }

    static class PhoneBook {
        private final Map<String, List<String>> records = new LinkedHashMap<>();

        public void add(String surname, String phoneNumber) {
            records.computeIfAbsent(surname, key -> new ArrayList<>())
                    .add(phoneNumber);
        }

        public List<String> get(String surname) {
            List<String> phoneNumbers = records.get(surname);

            if (phoneNumbers == null) {
                return new ArrayList<>();
            }

            return new ArrayList<>(phoneNumbers);
        }
    }
}