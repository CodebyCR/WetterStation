package model;

import java.nio.file.Path;

public class CSVProcessor {
    private CSVProcessor(){
        throw new IllegalStateException("Utility class");
    }

    public static void process(final String path) {
        final CSVParser parser = new CSVParser(Path.of(path), ";");
        final Matrix<String> csvValueMatrix = parser.parseIntoMatrix();

        printMatrix(csvValueMatrix);

        final String stationName = csvValueMatrix.getValueAt(1, 1);
        System.out.println("Station: " + stationName);

        final TemperatureModel currentTemperatureModel = parseIntoTemperatureModel(csvValueMatrix);
        final Station station = new Station(stationName, currentTemperatureModel);
        station.save();
    }

    private static TemperatureModel parseIntoTemperatureModel(Matrix<String> csvValueMatrix) {
        final TemperatureModel temperatureModel = new TemperatureModel();
        csvValueMatrix.enumerate( (index, row) -> {
            if(index < 4 || index > 51){
                // Skip header
                return;
            }
            final String fullDateAsString = row.get(1) + " " + row.get(2);
            final String fullDatePattern = "dd.MM.yyyy HH:mm";
            final java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(fullDatePattern);
            try {
                final Long date = formatter.parse(fullDateAsString).getTime();
                final String rawTemperature = row.get(3).replace(",", ".");
                final Double temperature = Double.parseDouble(rawTemperature);
                temperatureModel.addEntry(date, temperature);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        });

        System.out.println("TemperatureModel: ");
        temperatureModel.enumerate( (index, pair) -> {
            System.out.println("Date: " + pair.first() + " Temperature: " + pair.second());
        });
        return temperatureModel;
    }

    private static void printMatrix(Matrix<String> csvValueMatrix) {
        csvValueMatrix.enumerate( (index, row) -> {
            System.out.print("Row  " + index + ": " );
            row.forEach( (value) -> System.out.print("\t" + value + "\t"));
            System.out.println();
        });
    }

    public static void main(String[] args) {
        CSVProcessor.process("Documentation/2022-08-CunoI.CSV");
    }
}
