package view.modal;

import model.Station;

import javax.swing.*;

public class ModalReportWindow extends JFrame {
    private final Station station;
    public ModalReportWindow(final Station station) {
        super();
        this.station = station;
        this.setTitle("Create Report");
        this.setSize(500, 500);
        this.setVisible(true);

    }

    public void ini(){
        final var createReportPanel = new JPanel();
        createReportPanel.setLayout(null);
        createReportPanel.setVisible(true);
        this.add(createReportPanel);

        final var stationNameLabel = new JLabel();
        stationNameLabel.setText(station.getName());
        stationNameLabel.setBounds(0, 0, 500, 50);
        stationNameLabel.setHorizontalAlignment(JLabel.CENTER);
        stationNameLabel.setVerticalAlignment(JLabel.CENTER);
        stationNameLabel.setFont(stationNameLabel.getFont().deriveFont(32.0f));
        stationNameLabel.setForeground(java.awt.Color.black);
        createReportPanel.add(stationNameLabel);

        final var stationDateLabel = new JLabel();
        stationDateLabel.setText(station.getDate());
        stationDateLabel.setBounds(0, 50, 500, 50);
        stationDateLabel.setHorizontalAlignment(JLabel.CENTER);
        stationDateLabel.setVerticalAlignment(JLabel.CENTER);
        stationDateLabel.setFont(stationDateLabel.getFont().deriveFont(32.0f));
        stationDateLabel.setForeground(java.awt.Color.black);
        createReportPanel.add(stationDateLabel);
    }
}
