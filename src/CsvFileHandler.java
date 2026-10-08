import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvFileHandler {

    public static void save(AppData appData, String fileName) throws IOException {
        if (appData == null) {
            throw new IllegalArgumentException("Данные не должны быть null.");
        }

        String[] header = appData.getHeader();
        int[][] data = appData.getData();

        List<String> lines = new ArrayList<>();

        // Первая строка — заголовок.
        lines.add(String.join(";", header));

        // Остальные строки — целочисленные данные.
        for (int[] row : data) {
            StringBuilder line = new StringBuilder();

            for (int i = 0; i < row.length; i++) {
                if (i > 0) {
                    line.append(';');
                }
                line.append(row[i]);
            }

            lines.add(line.toString());
        }

        Path path = Paths.get(fileName);

        // CREATE создаёт файл, если его ещё нет.
        // TRUNCATE_EXISTING полностью перезаписывает существующий файл.
        Files.write(
                path,
                lines,
                StandardCharsets.UTF_8
        );
    }

    public static AppData load(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        // Файл читается целиком.
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        if (lines.isEmpty()) {
            throw new IOException("Файл пустой: отсутствует строка заголовка.");
        }

        String[] header = lines.get(0).split(";", -1);

        if (header.length == 0 || (header.length == 1 && header[0].isEmpty())) {
            throw new IOException("В файле отсутствует заголовок.");
        }

        int[][] data = new int[lines.size() - 1][header.length];

        for (int lineIndex = 1; lineIndex < lines.size(); lineIndex++) {
            String[] values = lines.get(lineIndex).split(";", -1);

            if (values.length != header.length) {
                throw new IOException(
                        "В строке " + (lineIndex + 1)
                                + " количество значений не совпадает с количеством столбцов."
                );
            }

            for (int columnIndex = 0; columnIndex < values.length; columnIndex++) {
                try {
                    data[lineIndex - 1][columnIndex] =
                            Integer.parseInt(values[columnIndex].trim());
                } catch (NumberFormatException e) {
                    throw new IOException(
                            "Некорректное целое число в строке " + (lineIndex + 1)
                                    + ", столбце " + (columnIndex + 1)
                                    + ": \"" + values[columnIndex] + "\"",
                            e
                    );
                }
            }
        }

        return new AppData(header, data);
    }
}