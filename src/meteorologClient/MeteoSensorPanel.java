package meteorologClient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MeteoSensorPanel extends JPanel {
    JPanel sensorPanel;
    private String[] sensorData;
    private JLabel errorLabel;
    private JLabel windLabel;
    private JLabel temperatureLabel;
    private JLabel snowLabel;
    private JLabel pressureLabel;
    private String host;
    private String name;
    private boolean error = true;

    public MeteoSensorPanel(String nameIn, String hostIn) {
        super();
        name = nameIn;
        host = hostIn;

        sensorPanel = new JPanel();
        sensorPanel.setBackground(Color.CYAN);

        setSensorName();
        setErrorLabel();
        setWindLabel();
        setTemperatureLabel();
        setSnowLabel();
        setPressureLabel();

        BoxLayout layout = new BoxLayout(sensorPanel, BoxLayout.Y_AXIS);
        sensorPanel.setLayout(layout);

        add(sensorPanel);
    }

    private void setSensorName() {
        JLabel sensorName = new JLabel(name, SwingConstants.RIGHT);
        sensorName.setFont(new Font("", Font.PLAIN, 22));
        sensorName.setOpaque(true);
        sensorName.setBackground(Color.GREEN);

        sensorPanel.add(sensorName);
    }

    private void setErrorLabel() {
        errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font("", Font.PLAIN, 14));
        sensorPanel.add(errorLabel);
    }

    private void setWindLabel() {
        JLabel windLabelName = new JLabel("Wind velocity", SwingConstants.LEFT);
        windLabelName.setFont(new Font("", Font.PLAIN, 14));
        windLabel = new JLabel(" ", SwingConstants.LEFT);
        sensorPanel.add(windLabelName);
        sensorPanel.add(windLabel);
    }

    private void setTemperatureLabel() {
        JLabel temperatureLabelName = new JLabel("Temperature", SwingConstants.LEFT);
        temperatureLabelName.setFont(new Font("", Font.PLAIN, 14));
        temperatureLabel = new JLabel(" ", SwingConstants.LEFT);
        sensorPanel.add(temperatureLabelName);
        sensorPanel.add(temperatureLabel);
    }

    private void setSnowLabel() {
        JLabel snowLabelName = new JLabel("Snow depth", SwingConstants.LEFT);
        snowLabelName.setFont(new Font("", Font.PLAIN, 14));
        snowLabel = new JLabel(" ", SwingConstants.LEFT);
        sensorPanel.add(snowLabelName);
        sensorPanel.add(snowLabel);
    }

    private void setPressureLabel() {
        JLabel pressureLabelName = new JLabel("Pressure", SwingConstants.LEFT);
        pressureLabelName.setFont(new Font("", Font.PLAIN, 14));
        pressureLabel = new JLabel(" ", SwingConstants.LEFT);
        sensorPanel.add(pressureLabelName);
        sensorPanel.add(pressureLabel);
    }

    public void downloadDataFromSensor() {
        try {
            sensorData = SensorDataDownloader.downloadDataFromSensor(host);
            windLabel.setText(sensorData[0]);
            temperatureLabel.setText(sensorData[1]);
            snowLabel.setText(sensorData[2]);
            pressureLabel.setText(sensorData[3]);
            errorLabel.setBackground(Color.CYAN);
            errorLabel.setOpaque(true);
            errorLabel.setText(" ");
            error = false;
//            return null;
        } catch (IOException e) {
            windLabel.setText(" ");
            temperatureLabel.setText(" ");
            snowLabel.setText(" ");
            pressureLabel.setText(" ");
            errorLabel.setBackground(Color.RED);
            errorLabel.setOpaque(true);
            errorLabel.setText("Connection error!!!");
            error = true;
//            return "dupa";
        }
    }

    public boolean checkError() {
        return this.error;
    }

    public String getSensorName() {
        return this.name;
    }

    public String[] getSensorData() {
        if(!checkError()){
            return this.sensorData;
        }
        else {
            return null;
        }
    }
}
