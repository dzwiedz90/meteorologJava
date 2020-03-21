package meteorologClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class MainWindow extends JFrame implements ActionListener {
    public JFrame mainFrame;
    JButton downloadData;
    MeteoSensorPanel sensor1;
    MeteoSensorPanel sensor2;
    MeteoSensorPanel sensor3;
    MeteoSensorPanel sensor4;
    ArrayList<MeteoSensorPanel> sensorList;
    BorderLayout layout;

    public MainWindow() {
        super();
        mainFrame = new JFrame("MeteoSensor");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLookAndFeel();
        mainFrame.setSize(750, 325);
        layout = new BorderLayout();
        mainFrame.setLayout(layout);
        sensorList = new ArrayList<>();

        // Menu
        Menu menuBar = new Menu();
        mainFrame.setJMenuBar(Menu.menuBar);

        this.addDownloadDataButton();
        this.addSensors();


        mainFrame.setVisible(true);
    }

    JPanel downloadDataButtonPanel;
    private void addDownloadDataButton() {
        downloadDataButtonPanel = new JPanel();
        downloadData = new JButton("Download Data");
        downloadData.addActionListener(this);
        downloadDataButtonPanel.add(downloadData);
        mainFrame.add(downloadDataButtonPanel, BorderLayout.NORTH);
    }

    JPanel addSensorsPanel;
    private void addSensors() {
        addSensorsPanel = new JPanel();
        sensor1 = new MeteoSensorPanel("Sensor DUPA 1");
        addSensorsPanel.add(sensor1);
        sensor2 = new MeteoSensorPanel("Sensor DUPA 2");
        addSensorsPanel.add(sensor2);
        sensor3 = new MeteoSensorPanel("Sensor DUPA 3");
        addSensorsPanel.add(sensor3);
        sensor4 = new MeteoSensorPanel("Sensor DUPA 4");
        addSensorsPanel.add(sensor4);
        mainFrame.add(addSensorsPanel, BorderLayout.CENTER);
        sensorList.add(sensor1);
        sensorList.add(sensor2);
        sensorList.add(sensor3);
        sensorList.add(sensor4);
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == downloadData) {
            try {
                for (MeteoSensorPanel panel : sensorList) {
                    panel.downloadData();
                }
                repaint();
            } catch (IOException e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Connection refused!");
            }
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
