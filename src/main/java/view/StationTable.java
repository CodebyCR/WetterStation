package view;

import environment.UI;
import model.Station;

import javax.swing.*;
import java.awt.*;

public final class StationTable extends JTable {

    public StationTable(final Object[][] model,
                        final String[] header) {
        super(model, header);
        this.setDefaultRenderer(Station.class, new StationRender());
        this.setBounds(UI.TABLE_MARGIN, 60, UI.FULL_WIDTH - UI.TABLE_MARGIN * 2 , UI.FULL_HEIGHT - 100);
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
        this.setShowVerticalLines(true);
    }
}
