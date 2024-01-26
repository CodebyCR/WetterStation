package view;

import model.Station;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public final class StationRender extends JPanel implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        final Station currentStation = (Station) value;

        final var nameLabel = new JLabel();
        nameLabel.setText(currentStation.getName());
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setVerticalAlignment(JLabel.CENTER);
        nameLabel.setFont(nameLabel.getFont().deriveFont(16.0f));
        nameLabel.setForeground(Color.black);
        nameLabel.setBackground(isSelected ? Color.cyan : Color.white);
        nameLabel.setOpaque(true);
        this.add(nameLabel);

        final var dateLabel = new JLabel();
        dateLabel.setText(currentStation.getDate());
        dateLabel.setHorizontalAlignment(JLabel.CENTER);
        dateLabel.setVerticalAlignment(JLabel.CENTER);
        dateLabel.setFont(dateLabel.getFont().deriveFont(16.0f));
        dateLabel.setForeground(Color.black);
        dateLabel.setBackground(isSelected ? Color.cyan : Color.white);
        dateLabel.setOpaque(true);
        this.add(dateLabel);

        final var viewDataButton = new JButton();
        viewDataButton.setText("View Data");
        viewDataButton.setHorizontalAlignment(JLabel.CENTER);
        viewDataButton.setVerticalAlignment(JLabel.CENTER);
        viewDataButton.setFont(viewDataButton.getFont().deriveFont(16.0f));
        viewDataButton.setForeground(Color.black);
        viewDataButton.setBackground(isSelected ? Color.cyan : Color.white);
        viewDataButton.setOpaque(true);
        this.add(viewDataButton);

        final var createReportButton = new JButton();
        createReportButton.setText("Create Report");
        createReportButton.setHorizontalAlignment(JLabel.CENTER);
        createReportButton.setVerticalAlignment(JLabel.CENTER);
        createReportButton.setFont(createReportButton.getFont().deriveFont(16.0f));
        createReportButton.setForeground(Color.black);
        createReportButton.setBackground(isSelected ? Color.cyan : Color.white);
        createReportButton.setOpaque(true);
        this.add(createReportButton);

        return this;
    }
}
