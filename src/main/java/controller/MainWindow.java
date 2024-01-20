package controller;

import model.Station;
import view.StationRender;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class MainWindow extends JFrame{

    final static int FULL_WIDTH = 800;

    final static int FULL_HEIGHT = 600;
    final static int HEADER_HEIGHT = 60;

    public MainWindow(){
        this.setTitle("Wetterstation");
//        final ImageIcon icon = new ImageIcon("src/main/resources/icon.png");
//        final List<String> icons = new ArrayList<>();
//        this.thisFrame.setIconImages();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FULL_WIDTH, FULL_HEIGHT);
        this.setResizable(false);
        this.setLayout(null); // Set layout to null
        this.setVisible(true);
    }

    public void init(){
        addHeader();
        addStationTable();



//        this.setSize(WIDTH, HEIGHT);
    }



    private void addHeader(){

        final var panel = new JPanel();
        panel.setBounds(0, 0, FULL_WIDTH, HEADER_HEIGHT);
        panel.setBackground(Color.white);
        panel.setOpaque(true);
        panel.setVisible(true);
        this.add(panel);

        final var headerLabel = new JLabel("Wetterstation");
        headerLabel.setBounds(-40 , 0, FULL_WIDTH / 5 * 2 , 50);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setVerticalAlignment(JLabel.CENTER);
        headerLabel.setFont(headerLabel.getFont().deriveFont(32.0f));
        headerLabel.setForeground(Color.black);

        panel.add(headerLabel);
        panel.add(getSearchField());
        panel.add(getCsvUploadButton());
        panel.setSize(FULL_WIDTH, HEADER_HEIGHT);
    }

    private JTextField getSearchField() {
        final var searchField = new JTextField();
        searchField.setBounds(245 , 15, FULL_WIDTH / 5 * 2, HEADER_HEIGHT / 2);
        searchField.setHorizontalAlignment(JTextField.CENTER);
        searchField.setFont(searchField.getFont().deriveFont(16.0f));
        searchField.setForeground(Color.black);
        searchField.setBackground(Color.white);
        searchField.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
//        searchField.setBorder(BorderFactory.createCompoundBorder(
//                searchField.getBorder(),
//                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        searchField.setOpaque(true);
        searchField.addActionListener(on_search -> {
            final var search = searchField.getText();
            System.out.println("Search: " + search);
        });

        return searchField;
    }

    private JButton getCsvUploadButton() {
        final var csvUploadButton = new JButton();
        csvUploadButton.setText(".csv Upload");
        csvUploadButton.setBounds(FULL_WIDTH / 5 * 4, 15, FULL_WIDTH / 5, HEADER_HEIGHT / 2);
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

    private void addStationTable() {
        final var scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 60, FULL_WIDTH, FULL_HEIGHT - HEADER_HEIGHT);
//        scrollPane.setVisible(true);
        scrollPane.setOpaque(true);
        scrollPane.setBackground(Color.white);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setAutoscrolls(true);
        this.add(scrollPane);

        final Object[][] demoModel = Station.getDemoModel();
        final int TABLE_MARGIN = 10;

        final var stationTable = new JTable(demoModel, new String[]{"Name", "Datum", "Daten einsehen", "Bericht erstellen"});
        stationTable.setDefaultRenderer(Station.class, new StationRender());
        stationTable.setBounds(TABLE_MARGIN, 60, FULL_WIDTH - TABLE_MARGIN * 2 , FULL_HEIGHT - 100);
        stationTable.setOpaque(true);
        stationTable.setBackground(Color.white);
        stationTable.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1, true));
        stationTable.setGridColor(Color.black);
        stationTable.setRowHeight(30);
        stationTable.setShowGrid(true);
        stationTable.setRowSelectionAllowed(true);
        stationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        stationTable.setRowHeight(50);
        stationTable.setRowMargin(10);
        stationTable.setFillsViewportHeight(true);
        stationTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        stationTable.setShowHorizontalLines(true);
        stationTable.setShowVerticalLines(true);

        final int COLUMN_WIDTH = 195;
        final TableColumnModel columnModel = stationTable.getColumnModel();
        columnModel
                .getColumns()
                .asIterator()
                .forEachRemaining(dataColumn -> dataColumn.setWidth(COLUMN_WIDTH));

        this.add(stationTable);
    }

    public static void main(String[] args) {
        final var mainWindow = new MainWindow();
        mainWindow.init();
    }

}
