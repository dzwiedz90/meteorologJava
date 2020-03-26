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
    MeteoSensorPanel sensor1;
    MeteoSensorPanel sensor2;
    ArrayList<MeteoSensorPanel> sensorList = new ArrayList<>();
    BorderLayout layout;
    SaveDataToFile savePanel;

    public MainWindow() {
        super();
        mainFrame = new JFrame("MeteoSensor");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLookAndFeel();
        mainFrame.setSize(750, 350);
        layout = new BorderLayout();
        mainFrame.setLayout(layout);


        // Menu
        Menu menuBar = new Menu();
        mainFrame.setJMenuBar(Menu.menuBar);

        addButtons();
        addSensors();


        mainFrame.setVisible(true);
    }

    JPanel mainWindowButtonPanel;

    private void addButtons() {
        mainWindowButtonPanel = new JPanel();
        addNewSensorButton = new JButton("Add new sensor");
        downloadDataButton = new JButton("Download Data");
        exportToFileButton = new JButton("Export data to file");

        addNewSensorButton.addActionListener(this);
        downloadDataButton.addActionListener(this);
        exportToFileButton.addActionListener(this);

        mainWindowButtonPanel.add(addNewSensorButton);
        mainWindowButtonPanel.add(downloadDataButton);
        mainWindowButtonPanel.add(exportToFileButton);

        mainFrame.add(mainWindowButtonPanel, BorderLayout.NORTH);
    }

    JPanel addSensorsPanel;

    private void addSensors() {
        addSensorsPanel = new JPanel();
        sensor1 = new MeteoSensorPanel("Sensor 1", "192.168.56.240");
        addSensorsPanel.add(sensor1);
        sensor2 = new MeteoSensorPanel("Sensor 2", "192.168.56.241");
        addSensorsPanel.add(sensor2);
        mainFrame.add(addSensorsPanel, BorderLayout.CENTER);
        sensorList.add(sensor1);
        sensorList.add(sensor2);
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
