package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class Station{
    // Attributes
    final String id;
    final String name;
    final long date;

    final TemperatureModel temperatureModel;

    // Constructors
    public Station(final String name,
                   final long date,
                   final TemperatureModel temperatureModel){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.date = date;
        this.temperatureModel = temperatureModel;
    }

    /**
     * for database loading
     */
    private Station(final String id,
                   final String name,
                   final long date){
        this.id = id;
        this.name = name;
        this.date = date;
        this.temperatureModel = TemperatureModel.loadById(id);
    }

    /////////////////////////////
    /////       Public     /////
    ///////////////////////////

    public static Station loadById(final String id){
        return new Station(id, "Hamburg", System.currentTimeMillis());
    }

    public static ArrayList<Station> loadAll(){

        return new ArrayList<Station>();
    }

    public String getDate(){
        final String dateFormat = "dd. MMM yyyy";
        final Date time = new Date(date);
        final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(time);
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }


    public static ArrayList<Station> getDemoList(){
        final var stationDemoList = new ArrayList<Station>();
        stationDemoList.add(new Station("Hamburg", System.currentTimeMillis(), TemperatureModel.getDemoModel()));
        stationDemoList.add(new Station("Berlin", System.currentTimeMillis(), TemperatureModel.getDemoModel()));
        stationDemoList.add(new Station("München", System.currentTimeMillis(), TemperatureModel.getDemoModel()));
        stationDemoList.add(new Station("Köln", System.currentTimeMillis(), TemperatureModel.getDemoModel()));
        stationDemoList.add(new Station("Frankfurt", System.currentTimeMillis(), TemperatureModel.getDemoModel()));
        stationDemoList.add(new Station("Stuttgart", System.currentTimeMillis(), TemperatureModel.getDemoModel()));
        stationDemoList.add(new Station("Düsseldorf", System.currentTimeMillis(), TemperatureModel.getDemoModel()));
        stationDemoList.add(new Station("Dortmund", System.currentTimeMillis(), TemperatureModel.getDemoModel()));
        stationDemoList.add(new Station("Essen", System.currentTimeMillis(), TemperatureModel.getDemoModel()));
        stationDemoList.add(new Station("Leipzig", System.currentTimeMillis(), TemperatureModel.getDemoModel()));
        stationDemoList.add(new Station("Bremen", System.currentTimeMillis(), TemperatureModel.getDemoModel()));
        stationDemoList.add(new Station("Dresden", System.currentTimeMillis(), TemperatureModel.getDemoModel()));
        stationDemoList.add(new Station("Hannover", System.currentTimeMillis(), TemperatureModel.getDemoModel()));
        stationDemoList.add(new Station("Nürnberg", System.currentTimeMillis(), TemperatureModel.getDemoModel()));

        return stationDemoList;
    }

    public static Object[][] getDemoModel(){
        final var stationDemoList = Station.getDemoList();

        final Object[][] demoModel = new Object[stationDemoList.size()][4];

        for (int i = 0; i < stationDemoList.size(); i++) {
            demoModel[i][0] = stationDemoList.get(i).getName();
            demoModel[i][1] = stationDemoList.get(i).getDate();
            demoModel[i][2] = "Bericht erstellen";
        }

        return demoModel;
    }

    public TemperatureModel getTemperatureModel(){
//        final var temperatureModel = TemperatureModel.loadById(id);
//        return temperatureModel;

        final long oneHour = 3600000;
        final long midnight = System.currentTimeMillis() - (System.currentTimeMillis() % 86400000) - 3600000;
        TemperatureModel temperatureModel = new TemperatureModel();
        temperatureModel.addEntry(midnight, 15.0);
        temperatureModel.addEntry(midnight + oneHour, 16.0);
        temperatureModel.addEntry(midnight + oneHour*2, 17.0);
        temperatureModel.addEntry(midnight + oneHour*3, 18.0);
        temperatureModel.addEntry(midnight + oneHour*4, 17.0);
        temperatureModel.addEntry(midnight + oneHour*5, 15.0);
        temperatureModel.addEntry(midnight + oneHour*6, 16.0);
        temperatureModel.addEntry(midnight + oneHour*7, 17.0);
        temperatureModel.addEntry(midnight + oneHour*8, 18.0);
        temperatureModel.addEntry(midnight + oneHour*9, 16.0);
        temperatureModel.addEntry(midnight + oneHour*10, 15.0);
        temperatureModel.addEntry(midnight + oneHour*11, 16.0);
        temperatureModel.addEntry(midnight + oneHour*12, 17.0);
        temperatureModel.addEntry(midnight + oneHour*13, 18.0);
        temperatureModel.addEntry(midnight + oneHour*14, 16.0);
        temperatureModel.addEntry(midnight + oneHour*15, 15.0);
        temperatureModel.addEntry(midnight + oneHour*16, 10.0);
        temperatureModel.addEntry(midnight + oneHour*17, 17.0);
        temperatureModel.addEntry(midnight + oneHour*18, 18.0);
        temperatureModel.addEntry(midnight + oneHour*19, 20.0);
        temperatureModel.addEntry(midnight + oneHour*20, -5.0);
        temperatureModel.addEntry(midnight + oneHour*21, 16.0);
        temperatureModel.addEntry(midnight + oneHour*22, 15.0);
        temperatureModel.addEntry(midnight + oneHour*23, 15.0);
        temperatureModel.addEntry(midnight + oneHour*24, 14.0);

        return temperatureModel;
    }

    public String toString(){
        return """
                "Station": {
                    "id": "%s",
                    "name": "%s",
                    "date": "%d",
                }
                """.formatted(id, name, date);
    }

    // bei filterung object mit original model vergleichen und dessen index zurückgeben

//    public Station fromString(final String stationString){
//        final String[] stationStringArray = stationString.split("\n");
//        final String id = stationStringArray[1].split(": ")[1].replace("\"", "");
//        final String name = stationStringArray[2].split(": ")[1].replace("\"", "");
//        final long date = Long.parseLong(stationStringArray[3].split(": ")[1].replace("\"", ""));
//
//        return new Station(name, date);
//    }

}