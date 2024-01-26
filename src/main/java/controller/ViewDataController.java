package controller;

import environment.Column;
import model.Station;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ViewDataController extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent e) {
        final JTable table = (JTable)e.getSource();
        final int modelRowIndex = Integer.parseInt( e.getActionCommand() );
        final var station = (Station) table.getModel().getValueAt(modelRowIndex, Column.VIEW_DATA);
        System.out.println("View data of station: " + station.getName());
    }
}
