import java.time.LocalDate;
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
        return LocalDate.ofEpochDay(date).format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String toString(){
        return """
                "Station": {
                    "id": "%s",
                    "name": "%s",
                    "date": "%ld",
                }
                """.formatted(id, name, date);
    }


}