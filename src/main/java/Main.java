import environment.utilities.DatabaseHandler;
import model.Station;
import view.MainWindow;

import java.awt.*;

public class Main {

    public static void main(final String[] args){
        DatabaseHandler dbc = DatabaseHandler.getInstance();
        dbc.initDB();


        EventQueue.invokeLater(() ->new MainWindow(Station.getDemoModel()));
    }

}
