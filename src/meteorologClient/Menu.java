package meteorologClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;

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

        }
    }

    private void saveConfigurationToFile() {
        try {
            PrintWriter out = new PrintWriter("configuration.txt");
            for (MeteoSensorPanel sensor : mainFrame.sensorList) {
                out.println(sensor.getSensorName() + " " + sensor.getSensorIp());
            }
            out.close();
            Desktop.getDesktop().open(new File(System.getProperty("user.dir")));
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void importDataFromFile() {

    }
}
