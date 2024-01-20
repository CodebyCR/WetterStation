import model.Station;

public class Main {

    public static void main(final String[] args){

        final var station = new Station("Hamburg", System.currentTimeMillis());
        System.out.println(station);

    }

}
