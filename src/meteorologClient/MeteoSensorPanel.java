package meteorologClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MeteoSensorPanel extends JPanel implements ActionListener {
    JPanel sensorPanel;
    private String[] sensorData;
    JLabel sensorName;
    private JLabel errorLabel;
    private JLabel windLabel;
    private JLabel temperatureLabel;
    private JLabel snowLabel;
    private JLabel pressureLabel;
    private String host;
    private String name;
    private boolean error = true;
    private JCheckBox deleteSensorCheckbox;
    JButton editSensorPropertiesButton;

    public MeteoSensorPanel(String nameIn, String hostIn) {
        super();

        if (nameIn.isEmpty()){
            name = " ";
        }
        else{
            name = nameIn;
        }
        if (hostIn.isEmpty()){
            host = "null";
        }
        else{
            host = hostIn;
        }

        sensorPanel = new JPanel();
        sensorPanel.setBackground(Color.LIGHT_GRAY);

        setSensorName();
        deleteSensorCheckbox = new JCheckBox("Delete");
        sensorPanel.add(deleteSensorCheckbox);
        editSensorPropertiesButton = new JButton("Edit properties");
        editSensorPropertiesButton.addActionListener(this);
        sensorPanel.add(editSensorPropertiesButton);
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
        sensorName = new JLabel(name, SwingConstants.RIGHT);
        sensorName.setFont(new Font("", Font.PLAIN, 22));
        sensorName.setOpaque(true);
        sensorName.setBackground(Color.GRAY);

        sensorPanel.add(sensorName);
    }

    public boolean isDeleteSet() {
        return deleteSensorCheckbox.isSelected();
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

    public String getSensorIp() {
        return this.host;
    }

    public void removeSensor() {
        this.remove(sensorPanel);
    }

    public String[] getSensorData() {
        if (!checkError()) {
            return this.sensorData;
        } else {
            return null;
        }
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == editSensorPropertiesButton) {
            editSensorProperties();
        }
    }

    private void editSensorProperties() {
        String name = JOptionPane.showInputDialog(null, "Enter sensor's name:");
        String ip = JOptionPane.showInputDialog(null, "Enter sensor's IP address:");
        sensorName.setText(name);
        host = ip;
    }
}
