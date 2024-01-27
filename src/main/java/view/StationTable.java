package view;

import environment.UI;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public final class StationTable extends JTable {

    public StationTable(final DefaultTableModel model) {
        super(model);

        this.setBounds(UI.TABLE_MARGIN, 60, (UI.FULL_WIDTH - UI.TABLE_MARGIN * 2) , UI.FULL_HEIGHT - 100);
        this.setOpaque(true);
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1, true));
        this.setGridColor(Color.lightGray);
        this.setRowHeight(30);
        this.setShowGrid(true);
        this.setRowSelectionAllowed(true);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setRowHeight(50);
        this.setRowMargin(10);
        this.setFillsViewportHeight(true);
        this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.setShowHorizontalLines(true);
        this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//        this.setShowVerticalLines(true);
        init();
    }

    private void init() {
        final JTableHeader tableHeader = new JTableHeader();
        tableHeader.setFont(tableHeader.getFont().deriveFont(16.0f));
        tableHeader.setForeground(Color.black);
        tableHeader.setBackground(Color.white);
        tableHeader.setOpaque(true);
        tableHeader.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
        tableHeader.setVisible(true);

        this.setTableHeader(tableHeader);
    }
}
