package controller;

import model.Station;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ViewDataController extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent e) {
        final int modelRowIndex = Integer.parseInt( e.getActionCommand() );
        final var station = Station.getStationList().get(modelRowIndex);

        System.out.println("View data of station: " + station.getName());
    }
}
