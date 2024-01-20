package model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class Station{
    // Attributes
    final String id;
    final String name;
    final long date;

    // Constructors
    public Station(final String name,
                   final long date){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.date = date;
    }

    /////////////////////////////
    /////       Public     /////
    ///////////////////////////

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

    public static Object[][] getDemoModel(){
        final var stationDemoList = new ArrayList<Station>();
        stationDemoList.add(new Station("Hamburg", System.currentTimeMillis()));
        stationDemoList.add(new Station("Berlin", System.currentTimeMillis()));
        stationDemoList.add(new Station("München", System.currentTimeMillis()));

        final Object[][] demoModel = new Object[stationDemoList.size()][4];

        for (int i = 0; i < stationDemoList.size(); i++) {
            demoModel[i][0] = stationDemoList.get(i).getName();
            demoModel[i][1] = stationDemoList.get(i).getDate();
            demoModel[i][2] = "Daten einsehen";
            demoModel[i][3] = "Bericht erstellen";
        }

        return demoModel;
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


}