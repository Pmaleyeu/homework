public class AppData {
    private final String[] header;
    private final int[][] data;

    public AppData(String[] header, int[][] data) {
        if (header == null || data == null) {
            throw new IllegalArgumentException("Заголовок и данные не должны быть null.");
        }

        if (header.length == 0) {
            throw new IllegalArgumentException("Заголовок должен содержать хотя бы один столбец.");
        }

        this.header = header.clone();
        this.data = copyData(data);

        for (int rowIndex = 0; rowIndex < this.data.length; rowIndex++) {
            if (this.data[rowIndex].length != this.header.length) {
                throw new IllegalArgumentException(
                        "Количество значений в строке " + (rowIndex + 1)
                                + " не совпадает с количеством столбцов."
                );
            }
        }
    }

    public String[] getHeader() {
        return header.clone();
    }

    public int[][] getData() {
        return copyData(data);
    }

    private static int[][] copyData(int[][] source) {
        int[][] copy = new int[source.length][];

        for (int i = 0; i < source.length; i++) {
            if (source[i] == null) {
                throw new IllegalArgumentException("Строка данных не должна быть null.");
            }
            copy[i] = source[i].clone();
        }

        return copy;
    }
}