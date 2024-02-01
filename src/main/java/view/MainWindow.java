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
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.KeyEvent;


public final class MainWindow extends JFrame{

    private Station selectedStation;
    private JTable currentStationTable;
    private final  Object[][] currentModel;
    private StationTable stationTable;

    public MainWindow(final Object[][] demoModel){
        this.currentModel = demoModel;
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


        final var headerLabel = new JLabel(" Wetterstation ") {
            @Override
            protected void paintComponent(Graphics g) {
                final Graphics2D g2d = (Graphics2D) g;
                final var headerLabelGradient = new GradientPaint(0, 0, UI.primaryColor, 0, 50, UI.primaryColor.darker(), true);
                g2d.setPaint(headerLabelGradient);
                g2d.fillRect(0, 0, getWidth(), getHeight() + 10);
                super.paintComponent(g);
            }
            @Override
            public Insets getInsets() {
                return new Insets(10, 10, 10, 10);
            }
        };

        // add margin to text
//        final var insets = headerLabel.getInsets();
//        final var margin = new Insets(insets.top, insets.left + 20, insets.bottom, insets.right);
//        headerLabel.setInsets(margin);


        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setVerticalAlignment(JLabel.CENTER);
        headerLabel.setFont(new Font(UI.FONT, Font.PLAIN, 28));
        headerLabel.setBounds(-40 , 0, UI.FULL_WIDTH / 5 * 2 , 60);
        headerLabel.setForeground(Color.white);
        headerLabel.setOpaque(false);


        box.setSize(UI.FULL_WIDTH, UI.HEADER_HEIGHT);
        box.add(headerLabel);
        box.add(getSearchField());
        box.add(getCsvUploadButton());
        return box;
    }

    private JTextField getSearchField() {
        final var searchField = new JTextField(){
            // placehoder
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    final Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    final var font = getFont().deriveFont(Font.ITALIC);
                    g2d.setFont(font);
                    g2d.setColor(Color.gray);
                    final var text = "Search..";
                    final var fm = g2d.getFontMetrics();
                    final var x = getWidth() / 2 - fm.stringWidth(text) / 2;
                    final var y = getHeight() / 2 + fm.getAscent() / 2 - 2;
                    g2d.drawString(text, x, y);
                }
            }
        };

        searchField.setBounds(UI.FULL_WIDTH / 5 * 2, 20, UI.FULL_WIDTH / 5 * 2, UI.HEADER_HEIGHT / 2);
        searchField.setFont(searchField.getFont().deriveFont(16.0f));
        searchField.setForeground(Color.black);
        searchField.setBackground(Color.white);
        searchField.addActionListener(on_search -> {

            final String searchValue = on_search.getActionCommand();

            final Object[][] filteredModel;
            if(searchValue.isEmpty()){
                filteredModel = currentModel;
            }
            else{
                filteredModel = Station.filteredModel(currentModel, searchValue);
            }
            final DefaultTableModel model = new DefaultTableModel(filteredModel, new String[]{"Name", "Datum", "Bericht erstellen"});

            stationTable.setModel(model);
            stationTable.repaint();

            final CreateReportController createReportController = new CreateReportController(filteredModel);
            final ButtonColumn createReportColumn = new ButtonColumn(stationTable, createReportController, Column.STATION_REPORT);
            createReportColumn.setMnemonic(KeyEvent.VK_D);
        });

        return searchField;
    }



    private JButton getCsvUploadButton() {
        final var csvUploadButton = new JButton();
        csvUploadButton.setText(".csv Upload");
        csvUploadButton.setBounds(UI.FULL_WIDTH / 5 * 4, 15, UI.FULL_WIDTH / 5, UI.HEADER_HEIGHT / 2);
        csvUploadButton.setBackground(UI.secundaryColor);
        csvUploadButton.setForeground(Color.white);
        csvUploadButton.setOpaque(true);
        csvUploadButton.setBorderPainted(false);
        csvUploadButton.setFont(csvUploadButton.getFont().deriveFont(16.0f));
        csvUploadButton.setFocusPainted(false);
        // On focus change color to purple
        csvUploadButton.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                csvUploadButton.setBackground(UI.secundaryColor);
            }

            public void focusGained(java.awt.event.FocusEvent evt) {
                csvUploadButton.setBackground(UI.primaryColor);
            }
        });

        // on focus accept enter
        csvUploadButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER){
                    csvUploadButton.doClick();
                }
            }
        });

        csvUploadButton.addActionListener(on_upload -> {
            final var fileChooser = new JFileChooser(); //TODO: outsource
            final var result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                final var file = fileChooser.getSelectedFile();
                System.out.println("Selected file: " + file.getAbsolutePath());
            }
            csvUploadButton.setBackground(UI.secundaryColor);
        });
        return csvUploadButton;
    }

    private JScrollPane addStationTable() {
        final var header = new String[]{"Name", "Datum", "Bericht erstellen"};
        final DefaultTableModel model = new DefaultTableModel(currentModel, header);


        stationTable = new StationTable(model);
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


            final DefaultTableModel stationDataModel = new DefaultTableModel(stationReport.toHumanReadableObjectMatrix(), currentStationHeader);


            // set header font size
//            final JTableHeader tableHeader = new JTableHeader();
//            tableHeader.setFont(tableHeader.getFont().deriveFont(16.0f));
//            tableHeader.setForeground(Color.white);
//            tableHeader.setBackground(Color.BLUE);
//            tableHeader.setOpaque(true);
//            tableHeader.setVisible(true);
//
//
//            currentStationTable.setTableHeader(tableHeader);
            currentStationTable.setModel(stationDataModel);

            // align centered in cell
            final var centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            final var currentModel = currentStationTable.getColumnModel();

            for (int i = 0; i < currentModel.getColumnCount(); i++) {
                currentModel.getColumn(i).setCellRenderer(centerRenderer);
            }


        });


        final CreateReportController createReportController = new CreateReportController(currentModel);
        final ButtonColumn createReportColumn = new ButtonColumn(stationTable, createReportController, Column.STATION_REPORT);
        createReportColumn.setMnemonic(KeyEvent.VK_D);



        final var scrollPane = new JScrollPane(stationTable);
        scrollPane.setBounds(UI.TABLE_MARGIN, 60, UI.FULL_WIDTH - UI.TABLE_MARGIN *2, UI.TABLE_HEIGHT);
        scrollPane.setVisible(true);
        scrollPane.setOpaque(true);
        scrollPane.setBackground(Color.white);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setAutoscrolls(true);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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



        currentStationTable = new JTable(){
                public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                    final Component component = super.prepareRenderer(renderer, row, column);
                    final Color foreground;
                    final Color background;
                    if (row == this.getSelectedRow()) {
                        foreground = Color.white;
                        background = UI.secundaryColor;
                    } else {
                        if (row % 2 == 0) {
                            foreground = Color.black;
                            background = Color.white;
                        } else {
                            foreground = Color.black;
                            background = CRColor.setAlpha(Color.lightGray, 30);
                        }
                    }
                    component.setForeground(foreground);
                    component.setBackground(background);
                    return component;
                }
        };

        JTableHeader tableHeader = currentStationTable.getTableHeader();
        tableHeader.setBackground(UI.primaryColor.brighter());
        tableHeader.setForeground(Color.white);
        tableHeader.setFont(new Font(UI.FONT, Font.PLAIN, 14));
        tableHeader.setReorderingAllowed(false);
        tableHeader.setResizingAllowed(false);
        tableHeader.setPreferredSize(new Dimension(UI.FULL_WIDTH - UI.TABLE_MARGIN * 2, 24));

        currentStationTable.setDefaultEditor(Object.class, null);
        currentStationTable.setRowSelectionAllowed(true);
        currentStationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        currentStationTable.setTableHeader(tableHeader);

        // set evenOddRenderer





        // center coulumns


//        stationTable.setToolTipText("This is a tip.");

        scrollPane.setViewportView(currentStationTable);
        selectionPanel.add(scrollPane);



        return selectionPanel;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->new MainWindow(Station.getDemoModel()));
    }

}
