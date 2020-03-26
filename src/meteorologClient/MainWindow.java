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
    ArrayList<MeteoSensorPanel> sensorList = new ArrayList<>();
    BorderLayout layout;
    SaveDataToFile savePanel;
    JPanel addSensorsPanel;

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
        addSensorsPanel = new JPanel();
        mainFrame.add(addSensorsPanel, BorderLayout.CENTER);

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

    private void addSensors() {
        String name = getSensorName();
        String ip = getIpAddress();
        MeteoSensorPanel sensor = new MeteoSensorPanel(name, ip);
        addSensorsPanel.add(sensor);
        addSensorsPanel.validate();
        addSensorsPanel.repaint();
        sensorList.add(sensor);
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
            System.out.println("Dupa");
            addSensors();
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
