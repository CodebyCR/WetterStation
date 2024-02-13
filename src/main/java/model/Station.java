package model;

import annotations.UniqueIdentifier;
import environment.utilities.DatabaseHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class Station{
    // Statics
    private final static ArrayList<Station> stationList = new ArrayList<>();

    // Attributes
    final String id;
    @UniqueIdentifier
    final String name;

    final TemperatureModel temperatureModel;

    // Constructors
    public Station(final String name,
                   final TemperatureModel temperatureModel){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.temperatureModel = temperatureModel;
        stationList.add(this);
    }

    /**
     * for database loading
     */
    public Station(final String id,
                   final String name){
        this.id = id;
        this.name = name;
        this.temperatureModel = TemperatureModel.loadById(id);
        stationList.add(this);
    }

    /////////////////////////////
    /////       Public     /////
    ///////////////////////////

    public static Station loadById(final String id){
        return new Station(id, "Hamburg");
    }


    public String getDate(){
        final String dateFormat = "dd. MMM yyyy";
        final Long lastDate = this.temperatureModel.getLastDate();
        final Date time = new Date(lastDate);
        final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(time);
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public static ArrayList<Station> getStationList() {
        return stationList;
    }

    public static Object[][] filteredModel(final Object[][] model,
                                           final String search) {

        final ArrayList<Object[]> filteredModel = new ArrayList<>();
        for (Object[] currentStation : model) {
            final String currentName = (String) currentStation[0];

            if (currentName.toLowerCase().contains(search.toLowerCase())) {
                filteredModel.add(currentStation);
            }

            final String currentDate = (String) currentStation[1];

            if (currentDate.toLowerCase().contains(search.toLowerCase())) {
                filteredModel.add(currentStation);
            }
        }
        return filteredModel.toArray(new Object[0][0]);
    }

    public static Object[][] getDemoModel(){
        final Object[][] demoModel = new Object[stationList.size()][4];

        for (int i = 0; i < stationList.size(); i++) {
            demoModel[i][0] = stationList.get(i).getName();
            demoModel[i][1] = stationList.get(i).getDate();
            demoModel[i][2] = "Bericht erstellen";
        }

        return demoModel;
    }

    public TemperatureModel getTemperatureModel(){
        System.out.println("TemperatureModel empty: " + temperatureModel.isEmpty());

        return this.temperatureModel;
    }


    public String toString(){
        final Long lastDate = this.temperatureModel.getLastDate();
        return """
                "Station": {
                    "id": "%s",
                    "name": "%s",
                    "date": "%d",
                }
                """.formatted(id, name, lastDate);
    }

    public void save(){
        DatabaseHandler.saveData(this);
    }

}