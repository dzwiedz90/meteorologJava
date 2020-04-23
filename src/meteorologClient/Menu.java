package meteorologClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Menu extends JFrame implements ActionListener {
    static JMenuBar menuBar;
    JMenuItem exportConfiguration;
    JMenuItem importConfiguration;
    MainWindow mainFrame;

    public Menu(MainWindow mainFrameIn) {
        this.mainFrame = mainFrameIn;
        JMenuItem file = new JMenuItem("File");
        exportConfiguration = new JMenuItem("Export configuration");
        exportConfiguration.addActionListener(this);
        importConfiguration = new JMenuItem("Import configuration");
        importConfiguration.addActionListener(this);
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem about = new JMenuItem("About");

        menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu aboutMenu = new JMenu("About");

        fileMenu.add(file);
        fileMenu.addSeparator();
        fileMenu.add(exportConfiguration);
        fileMenu.add(importConfiguration);
        fileMenu.addSeparator();
        fileMenu.add(exit);
        menuBar.add(fileMenu);

        aboutMenu.add(about);
        menuBar.add(aboutMenu);
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == exportConfiguration) {
            saveConfigurationToFile();
        }
        if (source == importConfiguration) {
            importDataFromFile();
        }
    }

    private void saveConfigurationToFile() {
        try {
            PrintWriter out = new PrintWriter("configuration.txt");
            for (MeteoSensorPanel sensor : mainFrame.sensorList) {
                out.println(sensor.getSensorName());
                out.println(sensor.getSensorIp());
            }
            out.close();
            Desktop.getDesktop().open(new File(System.getProperty("user.dir")));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void importDataFromFile() {
        ArrayList<String> sensorsFromFile = new ArrayList<String>();
        try {
            File sensorData = new File("configuration.txt");
            Scanner myReader = new Scanner(sensorData);
            while (myReader.hasNextLine()) {
                sensorsFromFile.add(myReader.nextLine());
            }
            myReader.close();
            createSensorsFromFile(sensorsFromFile);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void createSensorsFromFile(ArrayList<String> sensorsFromFile) {
        for (int i = 0; i < sensorsFromFile.size(); i += 2) {
            mainFrame.addSensors(sensorsFromFile.get(i), sensorsFromFile.get(i+1));
        }
    }
}
