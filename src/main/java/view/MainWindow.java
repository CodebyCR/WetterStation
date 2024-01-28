package view;

import controller.CreateReportController;
import environment.CRColor;
import environment.Column;
import environment.UI;
import model.Station;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyEvent;


public final class MainWindow extends JFrame{

    private Station selectedStation;
    private JTable currentStationTable;

    public MainWindow(){
        this.setTitle("Wetterstation");
        final ImageIcon icon = new ImageIcon("src/main/resources/icon.png");
        this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(UI.FULL_WIDTH, UI.FULL_HEIGHT);
        this.setResizable(false);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
//        this.setLayout(null); // Set layout to null
        this.init();
        this.setVisible(true);
    }

    public void init(){
        this.add(addHeader(), BorderLayout.NORTH);
        this.add(addStationTable(), BorderLayout.CENTER);
        this.add(getSelectionPanel(), BorderLayout.SOUTH);
//        this.setSize(WIDTH, HEIGHT);
    }



    private Box addHeader(){

        final var box = new Box(BoxLayout.X_AXIS);
        box.setBounds(0, 0, UI.FULL_WIDTH, UI.HEADER_HEIGHT);
        box.setBackground(Color.white);
        box.setOpaque(true);
        box.setVisible(true);
        // X_AXIS layout


        final var headerLabel = new JLabel("Wetterstation") {
            @Override
            protected void paintComponent(Graphics g) {
                final Graphics2D g2d = (Graphics2D) g;
                final var headerLabelGradient = new GradientPaint(0, 0, CRColor.lightBlue, 0, 50, CRColor.lightPurple, true);
                g2d.setPaint(headerLabelGradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        headerLabel.setBounds(-40 , 0, UI.FULL_WIDTH / 5 * 2 , 50);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setVerticalAlignment(JLabel.CENTER);
        headerLabel.setFont(headerLabel.getFont().deriveFont(28.0f));
        headerLabel.setForeground(Color.white);
        headerLabel.setOpaque(false);


        box.setSize(UI.FULL_WIDTH, UI.HEADER_HEIGHT);
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

    private JScrollPane addStationTable() {
        final Object[][] demoModel = Station.getDemoModel();
        final var header = new String[]{"Name", "Datum", "Bericht erstellen"};
        final DefaultTableModel model = new DefaultTableModel(demoModel, header);
        // set header
        model.setColumnIdentifiers(header);



        final StationTable stationTable = new StationTable(model);

        stationTable.setDefaultEditor(Object.class, null);
        stationTable.setRowSelectionAllowed(true);
        stationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        stationTable.setToolTipText("This is a tip.");

        // set ToolTip for row
        stationTable.setRowHeight(50);
        stationTable.setRowMargin(10);
        stationTable.setFillsViewportHeight(true);
        stationTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        stationTable.setShowHorizontalLines(true);
        stationTable.setShowVerticalLines(false);
        stationTable.setBackground(CRColor.lightBlue);
        stationTable.setSelectionBackground(CRColor.purple);
        stationTable.setSelectionForeground(Color.white);
        stationTable.setBounds(UI.TABLE_MARGIN, 60, (UI.FULL_WIDTH - UI.TABLE_MARGIN * 2) , UI.TABLE_HEIGHT);
        stationTable.getSelectionModel().addListSelectionListener(on_select -> {
            if (on_select.getValueIsAdjusting()) {
                return;
            }
            final int selectedRowIndex = stationTable.getSelectedRow();
            selectedStation = Station.getDemoList().get(selectedRowIndex);
            System.out.println("Selected: " + selectedStation.getName());
            final var stationReport = selectedStation.getTemperatureModel();
            stationReport.addEntry(System.currentTimeMillis(), 20.0);


            final var currentStationHeader = new String[]{"Datum", "Temperatur"};
            // set header color


            final DefaultTableModel stationDataModel = new DefaultTableModel(stationReport.toHumanReadableObjectMatrix(), currentStationHeader);
            // set header
            stationDataModel.setColumnIdentifiers(currentStationHeader);
            currentStationTable.setModel(stationDataModel);

            // set header font size
            final JTableHeader tableHeader = new JTableHeader();
            tableHeader.setFont(tableHeader.getFont().deriveFont(16.0f));
            tableHeader.setForeground(Color.white);
            tableHeader.setBackground(Color.BLUE);
            tableHeader.setOpaque(true);
            tableHeader.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1, true));
            tableHeader.setVisible(true);

            currentStationTable.setTableHeader(tableHeader);


            // align centered in cell
            final var centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            final var currentModel = currentStationTable.getColumnModel();
            if(currentModel.getColumnCount() > 0){
                for (int i = 0; i < currentModel.getColumnCount(); i++) {
                    currentModel.getColumn(i).setCellRenderer(centerRenderer);

                }
            }

        });

        final JTableHeader tableHeader = new JTableHeader();
        tableHeader.setFont(tableHeader.getFont().deriveFont(16.0f));
        tableHeader.setForeground(Color.black);
        tableHeader.setBackground(Color.white);
        tableHeader.setOpaque(true);
        tableHeader.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
        tableHeader.setVisible(true);

        stationTable.setTableHeader(tableHeader);

        // align centered in cell

        final var centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        stationTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        stationTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        stationTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        stationTable.setShowVerticalLines(false);
        stationTable.setModel(model);


        final CreateReportController createReportController = new CreateReportController();
        final ButtonColumn createReportColumn = new ButtonColumn(stationTable, createReportController, Column.STATION_REPORT);
        createReportColumn.setMnemonic(KeyEvent.VK_D);


        final var scrollPane = new JScrollPane(stationTable);
        scrollPane.setBounds(UI.TABLE_MARGIN, 60, UI.FULL_WIDTH - UI.TABLE_MARGIN *2, UI.TABLE_HEIGHT);
        scrollPane.setVisible(true);
        scrollPane.setOpaque(true);
        scrollPane.setBackground(Color.white);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setAutoscrolls(true);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

//        scrollPane.setViewportView();
        // fix scrollable
        scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        scrollPane.getViewport().setOpaque(true);
        scrollPane.getViewport().setBackground(Color.white);

        return scrollPane;
    }

    public JPanel getSelectionPanel(){
        final var selectionPanel = new JPanel();
        selectionPanel.setBounds(0, 0, UI.FULL_WIDTH, UI.SELECTION_FOOTER_HEIGHT);
        selectionPanel.setBackground(Color.white);
        selectionPanel.setOpaque(true);
        selectionPanel.setVisible(true);
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.X_AXIS));

        final JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, UI.FULL_WIDTH, UI.SELECTION_FOOTER_HEIGHT);
        scrollPane.setBackground(Color.white);
        scrollPane.setOpaque(true);
        scrollPane.setVisible(true);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setAutoscrolls(true);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        currentStationTable = new JTable();
        currentStationTable.setDefaultEditor(Object.class, null);
        currentStationTable.setRowSelectionAllowed(true);
        currentStationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        // center coulumns


//        stationTable.setToolTipText("This is a tip.");

        scrollPane.setViewportView(currentStationTable);
        selectionPanel.add(scrollPane);



        return selectionPanel;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(MainWindow::new);
    }

}
