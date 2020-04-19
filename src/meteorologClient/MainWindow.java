package meteorologClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class MainWindow extends JFrame implements ActionListener {
    public JFrame mainFrame;
    JButton downloadDataButton;
    JButton exportToFileButton;
    JButton addNewSensorButton;
    JButton deleteSensorsButton;
    ArrayList<MeteoSensorPanel> sensorList = new ArrayList<>();
    BorderLayout layout;
    SaveDataToFile savePanel;
    JPanel addSensorsPanel;
    JPanel mainWindowButtonPanel;
    int sensorCounter = 0;


    public MainWindow() {
        super();
        mainFrame = new JFrame("MeteoSensor");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLookAndFeel();
        mainFrame.setSize(500, 395);
        layout = new BorderLayout();
        mainFrame.setLayout(layout);


        // Menu
        Menu menuBar = new Menu();
        mainFrame.setJMenuBar(Menu.menuBar);

        addButtons();
        addSensorsPanel = new JPanel();
        mainFrame.add(addSensorsPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
        centerWindowOnScreen();
    }

    private void centerWindowOnScreen() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);
    }

    private void addButtons() {
        mainWindowButtonPanel = new JPanel();
        addNewSensorButton = new JButton("Add new sensor");
        deleteSensorsButton = new JButton("Delete sensors");
        downloadDataButton = new JButton("Download Data");
        exportToFileButton = new JButton("Export data to file");

        addNewSensorButton.addActionListener(this);
        deleteSensorsButton.addActionListener(this);
        downloadDataButton.addActionListener(this);
        exportToFileButton.addActionListener(this);

        JPanel addingDeletingButtonsPanel = new JPanel();
        JPanel downloadExportButtonsPanel = new JPanel();

        addingDeletingButtonsPanel.add(addNewSensorButton);
        addingDeletingButtonsPanel.add(deleteSensorsButton);
        downloadExportButtonsPanel.add(downloadDataButton);
        downloadExportButtonsPanel.add(exportToFileButton);

        GridLayout layout2 = new GridLayout(2, 1);
        mainWindowButtonPanel.setLayout(layout2);
        mainWindowButtonPanel.add(addingDeletingButtonsPanel);
        mainWindowButtonPanel.add(downloadExportButtonsPanel);

        mainFrame.add(mainWindowButtonPanel, BorderLayout.NORTH);
    }

    private void addSensors() {
        String name = getSensorName();
        String ip = getIpAddress();
        MeteoSensorPanel sensor = new MeteoSensorPanel(name, ip);
        addSensorsPanel.add(sensor);
        addSensorsPanel.validate();
        addSensorsPanel.repaint();
        sensorList.add(sensor);
        sensorCounter += 1;
        resizeMainWindow();
    }

    private void removeSensor() {
        ArrayList<MeteoSensorPanel> sensorsToRemoveArray = new ArrayList<>();
        for (MeteoSensorPanel sensor : sensorList) {
            if (sensor.isDeleteSet()) {
                sensorCounter -= 1;
                sensor.removeSensor();
                resizeMainWindow();
                sensorsToRemoveArray.add(sensor);
                mainFrame.repaint();
            }
        }
        removeSensorsFromSensorList(sensorsToRemoveArray);
    }

    private void removeSensorsFromSensorList(ArrayList<MeteoSensorPanel> sensorsToRemoveArray) {
        for (MeteoSensorPanel sensor : sensorsToRemoveArray) {
            sensorList.remove(sensor);
        }
    }

    private void resizeMainWindow() {
        switch (sensorCounter) {
            case 4:
                resizedMainWindowSetSize(395);
                break;
            case 5:
                resizedMainWindowSetSize(620);
                break;
            case 8:
                resizedMainWindowSetSize(620);
                break;
            case 9:
                resizedMainWindowSetSize(850);
                break;
            case 12:
                resizedMainWindowSetSize(850);
                break;
            case 13:
                resizedMainWindowSetSize(1080);
                break;
        }
    }

    private void resizedMainWindowSetSize(int size) {
        mainFrame.setSize(500, size);
        mainFrame.repaint();
    }

    private String getIpAddress() {
        return JOptionPane.showInputDialog(null, "Enter sensor's IP address:");
    }

    private String getSensorName() {
        return JOptionPane.showInputDialog(null, "Enter sensor's name:");
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == downloadDataButton) {
            for (MeteoSensorPanel panel : sensorList) {
                panel.downloadDataFromSensor();
            }
            repaint();
        }
        if (source == exportToFileButton) {
            savePanel = new SaveDataToFile(sensorList);
        }
        if (source == addNewSensorButton) {
            addSensors();
        }
        if (source == deleteSensorsButton) {
            removeSensor();
        }
    }

    private void setLookAndFeel() {
        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.err.println("Nie moge wczytac systemowego wygladu " + e);
        }
    }
}
