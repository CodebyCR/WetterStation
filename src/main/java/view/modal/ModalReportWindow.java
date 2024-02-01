package view.modal;


import environment.UI;
import model.Station;
import model.TemperatureModel;
import model.template.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class ModalReportWindow extends JFrame {
    /// Constants

    private final static int width = 520;
    private final static int height = 500;
    private final static double diagramScale = 0.75;
    private final static int titleSize = 20;
    private final static int fontSize = 14;
    private final static int lineHeight = 24;
    private final static int lineSpace = 5;

    /**
     * Represent -7°C as Start of the y-axis
     */
    private final static double yTempOrigen = -7.0;

    /**
     * Represent 0' o clock of the current day as Start of the x-axis
     */
    private final static long xDateOrigin = System.currentTimeMillis() - (System.currentTimeMillis() % 86400000);

    /// Attributes
    private final Station station;
    private final TemperatureModel tempModel;

    public ModalReportWindow(final Station station) {
        super();
        this.station = station;
        this.tempModel = station.getTemperatureModel();
        this.setTitle("Tagesbericht");
        this.setFont(new Font("Arial", Font.PLAIN, 16));
        this.setSize(width, height);
        this.setVisible(true);
        this.setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());
        this.setResizable(false);
        init();
    }

    private void init() {
        final var createReportPanel = new JPanel();
        createReportPanel.setLayout(null);
        createReportPanel.setVisible(true);
        this.add(createReportPanel);


        final var stationNameLabel = new JLabel();
        stationNameLabel.setText(station.getName() + " — " + station.getDate());
        stationNameLabel.setBounds(0, 0, width, lineHeight + lineSpace);
        stationNameLabel.setHorizontalAlignment(JLabel.CENTER);
        stationNameLabel.setVerticalAlignment(JLabel.CENTER);
        stationNameLabel.setFont(new Font(UI.FONT, Font.PLAIN, titleSize));
        stationNameLabel.setForeground(Color.black);
        createReportPanel.add(stationNameLabel);

        final int indent = 10;
        // Min, Max and Average Temperature
        final var minTempLabel = new JLabel();
        minTempLabel.setText("Min.  Temperature:          " + station.getTemperatureModel().getMin() + "°C");
        minTempLabel.setFont(new Font(UI.FONT, Font.PLAIN, fontSize));
        minTempLabel.setForeground(Color.black);
        minTempLabel.setBounds(indent, lineHeight + lineSpace, width, lineHeight);
        createReportPanel.add(minTempLabel);

        final var maxTempLabel = new JLabel();
        maxTempLabel.setText("Max. Temperature:          " + station.getTemperatureModel().getMax() + "°C");
        maxTempLabel.setBounds(indent, lineHeight * 2 + lineSpace, width, lineHeight);
        maxTempLabel.setForeground(Color.black);
        maxTempLabel.setFont(new Font(UI.FONT, Font.PLAIN, fontSize));
        createReportPanel.add(maxTempLabel);

        final var avgTempLabel = new JLabel();
        avgTempLabel.setText("Ø      Temperature:          " + station.getTemperatureModel().getAverage() + "°C");
        avgTempLabel.setBounds(indent, lineHeight * 3 + lineSpace, width, lineHeight);
        avgTempLabel.setForeground(Color.black);
        avgTempLabel.setFont(new Font(UI.FONT, Font.PLAIN, fontSize));
        createReportPanel.add(avgTempLabel);


        // Fix Graph

        final var graphPanel = new CoordinateSystem(
                getGraphPoints(),
                "Temperatur",
                "Zeit",
                yTempOrigen,
                30,
                xDateOrigin,
                xDateOrigin + 86400000,
                width, //(int) (width * diagrammScale), // 500 * 0.75 = 375
                374, //(int) (height * diagrammScale), // 500 * 0.75 = 375
                true
        );
        graphPanel.setBounds(0, lineHeight * 4 + lineSpace, width, (int) (height * diagramScale) ); // Adjust size here

        // add border
//        graphPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        createReportPanel.add(graphPanel);

        // Add vertical temperature label
//        VerticalLabel temperatureLabel = new VerticalLabel("Temperature");
//        temperatureLabel.setBounds(20, (int)(height * diagrammScale) / 4, 30, (int)(height * diagrammScale) / 2); // Adjust size and position here
//        createReportPanel.add(temperatureLabel);


    }

    private long getXDateScale() {
        return width / tempModel.stream().max(Comparator.comparingLong(Pair::first)).get().first();
    }

    private double getYTempScale() {
        return height / tempModel.stream().max(Comparator.comparingDouble(Pair::second)).get().second();
    }

    private ArrayList<Point> getGraphPoints(){
        final var graphPoints = new ArrayList<Point>();

        final int diagrammHeight = (int) (height * diagramScale);
//        final int diagrammWidth = (int) (width * diagrammScale);

        final double yScale = getYTempScale();
        System.out.println("yScale: " + yScale);

        tempModel.enumerate((index, pair) -> {
            final long xDate = pair.first();
            final double yTemp = pair.second();
//            System.out.println("xDate: " + xDate);
            System.out.println("yTemp: " + yTemp);

            // xDate to hour of the day
            final long xDateHours = (xDate - xDateOrigin) / 3600000;
            System.out.println("xDateHours: " + xDateHours);


            final var currentPoint = new Point((int) xDateHours, (int) yTemp);


            graphPoints.add(currentPoint);
        });

        return graphPoints;
    }

    private JPanel getGraphPanel() {
        final var graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics diagrammGraphic) {
                super.paintComponent(diagrammGraphic);

                final int diagrammHeight = (int) (height * diagramScale);
                final int diagrammWidth = (int) (width * diagramScale);

                // Draw x and y axes
                diagrammGraphic.setColor(Color.BLACK);
                diagrammGraphic.drawLine(50, diagrammHeight - 50, diagrammWidth - 20, diagrammHeight - 50); // x-axis
                diagrammGraphic.drawLine(50, diagrammHeight - 50, 50, 50); // y-axis

                // Draw labels for axes
                diagrammGraphic.setFont(diagrammGraphic.getFont().deriveFont(fontSize));
                diagrammGraphic.drawString("0", 40, diagrammHeight - 40); // y-axis
//                diagrammGraphic.drawString("0", 40, diagrammHeight - 40); // x-axis

                // Draw grid
                diagrammGraphic.setColor(Color.LIGHT_GRAY);
                for (int i = 0; i < diagrammHeight - 50; i += 50) {
                    diagrammGraphic.drawLine(50, diagrammHeight - 50 - i, diagrammWidth - 20, diagrammHeight - 50 - i); // x-axis
                }
                for (int i = 0; i < diagrammWidth - 50; i += 50) {
                    diagrammGraphic.drawLine(50 + i, diagrammHeight - 50, 50 + i, 50); // y-axis
                }

                // Draw graph
                final var graphPoints = getGraphPoints();
                for (int i = 0; i < graphPoints.size() - 1; i++) {
                    final var currentPoint = graphPoints.get(i);
                    final var nextPoint = graphPoints.get(i + 1);
                    diagrammGraphic.drawLine(currentPoint.x, currentPoint.y, nextPoint.x, nextPoint.y);
                }


                final long xDateScale = getXDateScale();
                final double yScale = getYTempScale();


                // Draw markers for axes
                diagrammGraphic.setColor(Color.DARK_GRAY);
                diagrammGraphic.drawLine(50, diagrammHeight - 50, 50, diagrammHeight - 60); // y-axis
                diagrammGraphic.drawLine(50, diagrammHeight - 50, 60, diagrammHeight - 50); // x-axis


                // Draw x and y axis labels
                diagrammGraphic.drawString("Zeit", diagrammWidth - 30, diagrammHeight - 30); // x-axis
                diagrammGraphic.drawString("Temperatur", 20, 30); // y-axis

                // draw points
                diagrammGraphic.setColor(Color.RED);
                tempModel.enumerate((index, pair) -> {
                    final long xDate = pair.first();
                    final double yTemp = pair.second();

                    // Use origin of coordinate system as default value if xDateScale or yScale is zero
                    final int x = xDateScale != 0 ? (int) ((xDate - xDateOrigin) / xDateScale) + 50 : 50;
                    final int y = yScale != 0 ? (int) (diagrammHeight - ((yTemp - yTempOrigen) / yScale)) - 50 : diagrammHeight - 50;
                    diagrammGraphic.fillOval(x, y, 5, 5);

                });

            }
        };
        graphPanel.setBounds(0, lineHeight * 4 + lineSpace, (int) (width * diagramScale), (int) (height * diagramScale)); // Adjust size here
        return graphPanel;
    }

}