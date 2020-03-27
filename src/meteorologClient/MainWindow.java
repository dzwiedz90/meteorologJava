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
    int sensorCounter = 0;

    public MainWindow() {
        super();
        mainFrame = new JFrame("MeteoSensor");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLookAndFeel();
        mainFrame.setSize(500, 350);
        layout = new BorderLayout();
        mainFrame.setLayout(layout);


        // Menu
        Menu menuBar = new Menu();
        mainFrame.setJMenuBar(Menu.menuBar);

        addButtons();
        addSensorsPanel = new JPanel();
        mainFrame.add(addSensorsPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    JPanel mainWindowButtonPanel;

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

        mainWindowButtonPanel.add(addNewSensorButton);
        mainWindowButtonPanel.add(deleteSensorsButton);
        mainWindowButtonPanel.add(downloadDataButton);
        mainWindowButtonPanel.add(exportToFileButton);

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
        for (MeteoSensorPanel sensor : sensorList) {
            if (sensor.isDeleteSet()) {
                sensorCounter -= 1;
                sensor.removeSensor();
                resizeMainWindow();
            }
        }
    }

    private void resizeMainWindow() {
        switch (sensorCounter) {
            case 4:
                resizedMainWindowSetSize(350);
                break;
            case 5:
                resizedMainWindowSetSize(580);
                break;
            case 8:
                resizedMainWindowSetSize(580);
                break;
            case 9:
                resizedMainWindowSetSize(810);
                break;
            case 12:
                resizedMainWindowSetSize(810);
                break;
            case 13:
                resizedMainWindowSetSize(1040);
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
