package controller;

import model.Station;
import view.modal.ModalReportWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CreateReportController extends AbstractAction {
    private final Object[][] model;
    public CreateReportController(Object[][] model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final JTable table = (JTable)e.getSource();
        final int modelRowIndex = Integer.parseInt( e.getActionCommand() );

        String stationName = "";
        for(int i = 0; i < model.length; i++) {
            if (i == modelRowIndex) {
                stationName = (String) model[i][0];
                break;
            }
        }

        final String finalStationName = stationName;
        final var station = Station.getDemoList().stream()
                .filter(s -> s.getName().equals(finalStationName))
                .findFirst()
                .orElseThrow();
        System.out.println("Create report of station: " + station.getName());



        // open new window
        new ModalReportWindow(station);
//        createReportWindow.ini();



    }
}
