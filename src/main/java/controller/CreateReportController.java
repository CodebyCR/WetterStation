package controller;

import environment.Column;
import model.Station;
import view.modal.ModalReportWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CreateReportController extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent e) {
        final JTable table = (JTable)e.getSource();
        final int modelRowIndex = Integer.parseInt( e.getActionCommand() );
        final var station = (Station) table.getModel().getValueAt(modelRowIndex, Column.STATION_REPORT);
        System.out.println("Create report of station: " + station.getName());

        // open new window
        final var createReportWindow = new ModalReportWindow(station);
        createReportWindow.ini();



    }
}
