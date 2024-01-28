package model;

import interfaces.Enumerate;
import model.template.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.BiConsumer;


public class TemperatureModel extends ArrayList<Pair<Long, Double>>
                                implements Enumerate<BiConsumer<Integer, Pair<Long, Double>>>{

    // Attributes
    private final String id;

    // Constructors
    public TemperatureModel(){
        this.id = java.util.UUID.randomUUID().toString();
    }

    public static TemperatureModel loadById(String id) {
        return new TemperatureModel();
    }

    public static TemperatureModel getDemoModel() {
        TemperatureModel temperatureModel = new TemperatureModel();
        temperatureModel.addEntry(System.currentTimeMillis(), 20.0);
        temperatureModel.addEntry(System.currentTimeMillis(), 21.0);
        temperatureModel.addEntry(System.currentTimeMillis(), 22.0);
        temperatureModel.addEntry(System.currentTimeMillis(), 23.0);
        return temperatureModel;
    }

    /////////////////////////////
    /////       Public     /////
    ///////////////////////////

    public String getId(){
        return id;
    }

    public void addEntry(final Long date,
                         final Double temperature){
        this.add(new Pair<>(date, temperature));
    }

    @Override
    public void enumerate(final BiConsumer<Integer, Pair<Long, Double>> consumer){
        for (int index = 0; index < this.size(); index++) {
            consumer.accept(index, this.get(index));
        }
    }

    public Object[][] toObjectMatrix(){
        final Object[][] data = new Object[this.size()][2];
        this.enumerate((index, pair) -> {
            data[index][0] = pair.first();
            data[index][1] = pair.second();
        });
        return data;
    }

    public Object[][] toHumanReadableObjectMatrix(){
        this.sort(Comparator.comparing(Pair::first));
        final Object[][] data = new Object[this.size()][2];
        final String datePattern = "dd. MMM yyyy | HH:mm:ss";
        final java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(datePattern);

        this.enumerate((index, pair) -> {
            data[index][0] = formatter.format(new java.util.Date(pair.first()));
            data[index][1] = pair.second() + " °C";
        });
        return data;
    }

    public static void main(String[] args) {
        TemperatureModel temperatureModel = new TemperatureModel();
        temperatureModel.addEntry(System.currentTimeMillis(), 20.0);
        temperatureModel.addEntry(System.currentTimeMillis(), 21.0);
        temperatureModel.addEntry(System.currentTimeMillis(), 22.0);
        temperatureModel.addEntry(System.currentTimeMillis(), 23.0);

        temperatureModel.enumerate((index, pair) -> {
            System.out.println("--------------------");
            System.out.println("Index: " + index);
            System.out.println("Date: " + pair.first());
            System.out.println("Temperature: " + pair.second());
        });

    }


    public Double getMin() {
        return this
                .stream()
                .mapToDouble(Pair::second)
                .min()
                .orElse(Double.NaN);
    }

    public Double getMax() {
        return this
                .stream()
                .mapToDouble(Pair::second)
                .max()
                .orElse(Double.NaN);
    }

    public Double getAverage() {
        return this
                .stream()
                .mapToDouble(Pair::second)
                .average()
                .orElse(Double.NaN);
    }
}
