package model;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public final class CSVParser {
    private final File csvFile;
    private final String separator;
    private final Matrix<String> matrix = new Matrix<>();

    public CSVParser(final Path path) {
        this.csvFile = path.toFile();
        this.separator = ",";
    }

    public CSVParser(final Path path, final String separator) {
        this.csvFile = path.toFile();
        this.separator = separator;
    }

    public Matrix<String> parseIntoMatrix() {
        if(!csvFile.exists()){
            throw new IllegalArgumentException("File does not exist");
        }

        try {
            final java.util.Scanner scanner = new java.util.Scanner(csvFile);
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                final String[] values = line.split(this.separator);
                final ArrayList<String> row = new ArrayList<>();
                Collections.addAll(row, values);
                matrix.add(row);
            }
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }

        return matrix;
    }

}
