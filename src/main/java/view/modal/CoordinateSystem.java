package view.modal;

import environment.CRColor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CoordinateSystem extends JPanel {
    private final static float fontSize = 14.0f;

    ///////////////////////////////////////////////
    private final ArrayList<Point> points;
    private final String labelY;
    private final String labelX;
    private final double startY;
    private final int endY;
    private final long startX;
    private final long endX;

    private final int width;
    private final int height;
    private final boolean showGrid;

    public CoordinateSystem(final ArrayList<Point> points, final String labelY, final String labelX, final double startY, final int endY, final long startX, final long endX, final int width, final int height, final boolean showGrid) {
        this.points = points;
        this.labelY = labelY;
        this.labelX = labelX;
        this.startY = startY;
        this.endY = endY;
        this.startX = startX;
        this.endX = endX;
        this.width = width;
        this.height = height;
        this.showGrid = showGrid;

        init();
    }

    private void init() {
        this.setLayout(null);
        this.setVisible(true);
        this.setSize(width, height);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        // Now you can use the graphics object to draw your axes
        drawAxes(graphics);
//        drawAxesStartLabel(graphics);
        drawYAxisLabels(graphics);
        drawXAxisLabels(graphics);
        drawGrid(graphics);
        drawPoints(graphics);

    }

    private void drawAxes(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.drawLine(50, height - 50, width - 20, height - 50); // x-axis
        graphics.drawLine(50, height - 50, 50, 50); // y-axis
    }

    private void drawGraph(Graphics graphics) {
        for (int i = 0; i < points.size() - 1; i++) {
            final var currentPoint = points.get(i);
            final var nextPoint = points.get(i + 1);
            graphics.drawLine(currentPoint.x, currentPoint.y, nextPoint.x, nextPoint.y);
        }
    }

    private void drawYAxisLabels(Graphics graphics) {
        final int xPosition = 10;
        // dark purple & monospace
        graphics.setFont(graphics.getFont().deriveFont( 15.0F));
        graphics.setColor(new Color(128, 0, 128));

        graphics.drawString("-10°C", xPosition, height - 45);
        // dark blue
        graphics.setColor(new Color(0, 0, 128));
        graphics.drawString(" 0°C", xPosition + 8, height - 95);
        // blue
        graphics.setColor(new Color(0, 0, 255));
        graphics.drawString(" 10°C", xPosition, height - 145);
        // dark yellow
        graphics.setColor(new Color(0, 128, 0));
        graphics.drawString(" 20°C", xPosition, height - 195);
        // orange
        graphics.setColor(new Color(255, 128, 0));
        graphics.drawString(" 30°C", xPosition, height - 245);
        // dark red
        graphics.setColor(new Color(128, 0, 0));
        graphics.drawString(" 40°C", xPosition, height - 295);

        graphics.setColor(Color.GRAY);
        graphics.drawString("Temperatur", xPosition, 40);
    }

    private void drawXAxisLabels(Graphics graphics) {
        graphics.setColor(Color.BLACK);

        graphics.drawString("0 Uhr", 40, height - 30);
        graphics.drawString("12 Uhr", width / 2 - 4, height - 30);
        graphics.drawString("24 Uhr", width - 44, height - 30);

        graphics.setColor(Color.GRAY);
        graphics.drawString("Zeit", width / 2 - 4, height - 15); // x-axis
    }




    private void drawGrid(Graphics graphics) {
        graphics.setColor(Color.LIGHT_GRAY);

        // draw 5 horizontal lines for x axis
        for (int i = 0; i < height - 50; i += 50) {
            final var lineHeight = height - 50 - i;

            if(lineHeight < 40){
                continue;
            }
            graphics.drawLine(50, height - 50 - i, width - 20, lineHeight); // x-axis
        }

        // draw 7 vertical lines for y axis
        for (int i = 0; i < width - 50; i += 50) {
            graphics.drawLine(50 + i, height - 50, 50 + i, 50); // y-axis
        }
    }

    private void drawPoints(Graphics graphics) {
        final int pointSize = 8;
        final int pointSizeHalf = pointSize / 2;
        final int yOffset = 100;
        final int xOffset = 62 - pointSizeHalf;
        final int coordinateSystemWidth = 301;
        final double hourCount = 24.0;

        final var pointList = new ArrayList<Point>();

        for (final Point nextPoint : points) {
            final var rawTemp2 = nextPoint.y;
            final var temp2 = (rawTemp2 *5) + yOffset + pointSizeHalf;
            final var rawHour2 = nextPoint.x;
            final var faktor2 = coordinateSystemWidth / hourCount ;
            final var hour2 = (int) (rawHour2 * faktor2) + xOffset ;

            pointList.add(new Point(hour2, height - temp2));
        }

        // draw as line in light blue
        graphics.setColor(CRColor.lightBlue);
        for (int i = 0; i < pointList.size() - 1; i++) {
            final var currentPoint = pointList.get(i);
            final var nextPoint = pointList.get(i + 1);
            graphics.drawLine(currentPoint.x + pointSizeHalf, currentPoint.y + pointSizeHalf, nextPoint.x +pointSizeHalf, nextPoint.y +pointSizeHalf);
        }

        // draw points in dark blue
        graphics.setColor(new Color(0, 0, 255, 100));
        for (final Point nextPoint : pointList) {
            graphics.fillOval(nextPoint.x, nextPoint.y, pointSize, pointSize);
        }
    }
}