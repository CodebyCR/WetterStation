package view;

import controller.CreateReportController;
import controller.ViewDataController;
import environment.CRColor;
import environment.Column;
import environment.UI;
import model.Station;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public final class MainWindow extends JFrame{

    public MainWindow(){
        this.setTitle("Wetterstation");
        final ImageIcon icon = new ImageIcon("src/main/resources/icon.png");
        this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(UI.FULL_WIDTH, UI.FULL_HEIGHT);
        this.setResizable(false);
//        this.setLayout(null); // Set layout to null
        this.setVisible(true);
        this.init();
    }

    public void init(){
        this.add(addHeader(), BorderLayout.NORTH);
        this.add(addStationTable(), BorderLayout.CENTER);

//        this.setSize(WIDTH, HEIGHT);
    }



    private Box addHeader(){

        final var box = new Box(BoxLayout.X_AXIS);
        box.setBounds(0, 0, UI.FULL_WIDTH, UI.HEADER_HEIGHT);
        box.setBackground(Color.white);
        box.setOpaque(true);
        box.setVisible(true);
        // X_AXIS layout


        final var headerLabel = new JLabel("Wetterstation");
        headerLabel.setBounds(-40 , 0, UI.FULL_WIDTH / 5 * 2 , 50);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setVerticalAlignment(JLabel.CENTER);
        headerLabel.setFont(headerLabel.getFont().deriveFont(32.0f));
        headerLabel.setForeground(Color.black);

        box.setSize(UI.FULL_WIDTH, UI.HEADER_HEIGHT);
//        box.add(headerLabel, BorderLayout.WEST);
//        box.add(getSearchField(), BorderLayout.CENTER);
//        box.add(getCsvUploadButton(), BorderLayout.EAST);
        box.add(headerLabel);
        box.add(getSearchField());
        box.add(getCsvUploadButton());
        return box;
    }

    private JTextField getSearchField() {
        final var searchField = new JTextField();
//        searchField.setBounds(245 , 15, UI.FULL_WIDTH / 5 , UI.HEADER_HEIGHT / 2);
//        searchField.setBounds(UI.FULL_WIDTH / 5 * 2, 15, UI.FULL_WIDTH / 5 * 2, UI.HEADER_HEIGHT / 2);
        searchField.setBounds(UI.FULL_WIDTH / 5 * 2, 15, UI.FULL_WIDTH / 5 * 2, UI.HEADER_HEIGHT / 2);
//        searchField.setHorizontalAlignment(JTextField.CENTER);
        searchField.setFont(searchField.getFont().deriveFont(16.0f));
        searchField.setForeground(Color.black);
        searchField.setBackground(Color.white);
        searchField.setText("Search..");
//        searchField.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
//        searchField.setOpaque(true);
        searchField.addActionListener(on_search -> {
            final var search = searchField.getText();
            System.out.println("Search: " + search);
        });

        return searchField;
    }

    private JButton getCsvUploadButton() {
        final var csvUploadButton = new JButton();
        csvUploadButton.setText(".csv Upload");
        csvUploadButton.setBounds(UI.FULL_WIDTH / 5 * 4, 15, UI.FULL_WIDTH / 5, UI.HEADER_HEIGHT / 2);
        csvUploadButton.setBackground(CRColor.lightPurple);
        csvUploadButton.setForeground(Color.white);
        csvUploadButton.setBorderPainted(false);
        csvUploadButton.setOpaque(true);
        csvUploadButton.setVisible(true);
//        csvUploadButton.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
//        csvUploadButton.setVisible(true);
        csvUploadButton.addActionListener(on_upload -> {
            final var fileChooser = new JFileChooser(); //TODO: outsource
            final var result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                final var file = fileChooser.getSelectedFile();
                System.out.println("Selected file: " + file.getAbsolutePath());
            }
        });
        return csvUploadButton;
    }

    private StationTable addStationTable() {
        final Object[][] demoModel = Station.getDemoModel();
        final var header = new String[]{"Name", "Datum", "Daten einsehen", "Bericht erstellen"};
        final DefaultTableModel model = new DefaultTableModel(demoModel, header);


        final StationTable stationTable = new StationTable(model);
        stationTable.setDefaultEditor(Object.class, null);
        stationTable.setRowSelectionAllowed(true);
        stationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        stationTable.setToolTipText("This is a tip.");

        // set ToolTip for row
        stationTable.setRowHeight(50);
        stationTable.setRowMargin(10);
        stationTable.setFillsViewportHeight(true);
        stationTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        stationTable.setShowHorizontalLines(true);
        stationTable.setShowVerticalLines(false);

        // set color of selceted row
        stationTable.setSelectionBackground(CRColor.purple);
        stationTable.setSelectionForeground(Color.white);

//        ButtonColumn buttonColumn = new ButtonColumn(stationTable, delete, 2);
//        buttonColumn.setMnemonic(KeyEvent.VK_D);

        final ViewDataController viewDataController = new ViewDataController();
        final ButtonColumn viewDataColumn = new ButtonColumn(stationTable, viewDataController, Column.VIEW_DATA);
        viewDataColumn.setMnemonic(KeyEvent.VK_D);

        final CreateReportController createReportController = new CreateReportController();
        final ButtonColumn createReportColumn = new ButtonColumn(stationTable, createReportController, Column.STATION_REPORT);
        createReportColumn.setMnemonic(KeyEvent.VK_D);


//        final TableColumnModel columnModel = stationTable.getColumnModel();
//        columnModel.setColumnMargin(2);
//        columnModel
//                .getColumns()
//                .asIterator()
//                .forEachRemaining(dataColumn -> dataColumn.setWidth(UI.COLUMN_WIDTH));


        final var scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 60, UI.FULL_WIDTH, UI.FULL_HEIGHT - UI.HEADER_HEIGHT);
//        scrollPane.setVisible(true);
        scrollPane.setOpaque(true);
        scrollPane.setBackground(Color.white);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setAutoscrolls(true);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.add(stationTable);
        scrollPane.setViewportView(stationTable);


        return stationTable;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(MainWindow::new);
    }

}
