package view;

import environment.CRColor;
import environment.UI;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public final class StationTable extends JTable {

    public StationTable(final DefaultTableModel model) {
        super(model);
        // style header
        final JTableHeader header = this.getTableHeader();
        header.setBackground(UI.primaryColor.brighter());
        header.setForeground(Color.white);
        header.setFont(new Font(UI.FONT, Font.PLAIN, 16));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
        header.setPreferredSize(new Dimension(UI.FULL_WIDTH - UI.TABLE_MARGIN * 2, 30));

//        this.setBounds(UI.TABLE_MARGIN, 60, (UI.FULL_WIDTH - UI.TABLE_MARGIN * 2) , UI.FULL_HEIGHT - 100);
        this.setOpaque(true);
        this.setGridColor(Color.lightGray);
        this.setShowGrid(true);
        this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.setShowHorizontalLines(true);
        this.setDefaultEditor(Object.class, null);
        this.setRowSelectionAllowed(true);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setRowHeight(50);
        this.setRowMargin(10);
        this.setFillsViewportHeight(true);
        this.setShowVerticalLines(false);
        this.setSelectionBackground(UI.secundaryColor);
        this.setSelectionForeground(Color.white);
        this.setBounds(UI.TABLE_MARGIN, 60, (UI.FULL_WIDTH - UI.TABLE_MARGIN * 2) , UI.TABLE_HEIGHT);
        this.setFont(new Font(UI.FONT, Font.PLAIN, 16));
    }

//    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
//        final Component component = super.prepareRenderer(renderer, row, column);
//        final Color foreground;
//        final Color background;
////        if (row == this.getSelectedRow()) {
////            foreground = Color.white;
////            background = CRColor.purple;
////        } else {
//            if (row % 2 == 0) {
//                foreground = Color.black;
//                background = Color.white;
//            } else {
//                foreground = Color.black;
//                background = CRColor.lightBlue;
//            }
////        }
//        component.setForeground(foreground);
//        component.setBackground(background);
//        return component;
//    }

}
