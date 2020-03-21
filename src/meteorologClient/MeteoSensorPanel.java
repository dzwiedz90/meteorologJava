package meteorologClient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MeteoSensorPanel extends JPanel {
    JLabel windLabel;
    JLabel temperatureLabel;
    JLabel snowLabel;
    JLabel pressureLabel;

    public MeteoSensorPanel(String name) {
        super();

        JPanel sensorPanel = new JPanel();
        sensorPanel.setBackground(Color.CYAN);

        JLabel sensorName = new JLabel(name, SwingConstants.RIGHT);
        sensorName.setFont(new Font("", Font.PLAIN, 22));
//        sensorName.setPreferredSize(new Dimension(150, 200));
        sensorName.setOpaque(true);
        sensorName.setBackground(Color.GREEN);


        JLabel windLabelName = new JLabel("Wind velocity", SwingConstants.LEFT);
        windLabelName.setFont(new Font("", Font.PLAIN, 14));
        windLabel = new JLabel(" ", SwingConstants.LEFT);

        JLabel temperatureLabelName = new JLabel("Temperature", SwingConstants.LEFT);
        temperatureLabelName.setFont(new Font("", Font.PLAIN, 14));
        temperatureLabel = new JLabel(" ", SwingConstants.LEFT);

        JLabel snowLabelName = new JLabel("Snow depth", SwingConstants.LEFT);
        snowLabelName.setFont(new Font("", Font.PLAIN, 14));
        snowLabel = new JLabel(" ", SwingConstants.LEFT);

        JLabel pressureLabelName = new JLabel("Pressure", SwingConstants.LEFT);
        pressureLabelName.setFont(new Font("", Font.PLAIN, 14));
        pressureLabel = new JLabel(" ", SwingConstants.LEFT);

        BoxLayout layout = new BoxLayout(sensorPanel, BoxLayout.Y_AXIS);
        sensorPanel.setLayout(layout);

        sensorPanel.add(sensorName);
        sensorPanel.add(windLabelName);
        sensorPanel.add(windLabel);
        sensorPanel.add(temperatureLabelName);
        sensorPanel.add(temperatureLabel);
        sensorPanel.add(snowLabelName);
        sensorPanel.add(snowLabel);
        sensorPanel.add(pressureLabelName);
        sensorPanel.add(pressureLabel);


        add(sensorPanel);
    }

    public void downloadData() throws IOException {
        String[] sensorData = SensorDataDownloader.downloadDataFromSensor();
        windLabel.setText(sensorData[0]);
        temperatureLabel.setText(sensorData[1]);
        snowLabel.setText(sensorData[2]);
        pressureLabel.setText(sensorData[3]);
    }
}
