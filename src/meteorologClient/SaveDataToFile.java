package meteorologClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SaveDataToFile extends JFrame implements ActionListener {
    JFrame frame;
    JPanel checkBoxPanel;
    JButton saveToFile;
    private ArrayList<MeteoSensorPanel> sensorList;
    private ArrayList<JCheckBox> checkBoxList;

    public SaveDataToFile(ArrayList<MeteoSensorPanel> sensorListIn) {
        sensorList = sensorListIn;
        frame = new JFrame("Export to file");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(320, 120);
        BorderLayout layout = new BorderLayout();
        frame.setLayout(layout);
        saveToFile = new JButton("Save");
        saveToFile.addActionListener(this);

        JLabel questionLabel = new JLabel("Which objects do you want to export?:");
        addCheckBoxes(sensorListIn);

        frame.add(questionLabel, BorderLayout.NORTH);
        frame.add(checkBoxPanel, BorderLayout.WEST);
        frame.add(saveToFile, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void addCheckBoxes(ArrayList<MeteoSensorPanel> sensorList) {
        checkBoxPanel = new JPanel();
        checkBoxList = new ArrayList<>();
        for (int i = 0; i < sensorList.size(); i++) {
            JCheckBox box = new JCheckBox(sensorList.get(i).getSensorName());
            checkBoxList.add(box);
            checkBoxPanel.add(checkBoxList.get(i));
        }
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        ArrayList<MeteoSensorPanel> toPrint = new ArrayList<>();
        ArrayList<MeteoSensorPanel> withError = new ArrayList<>();
        if (source == saveToFile) {
            for (int i = 0; i < sensorList.size(); i++) {
                if ((sensorList.get(i).getSensorData() != null) && (checkBoxList.get(i).isSelected())) {
                    toPrint.add(sensorList.get(i));
                } else {
                    withError.add(sensorList.get(i));
                }
            }
            if (!toPrint.isEmpty()) {
                saveToFile(toPrint, withError);
                return;
            }
            if(!withError.isEmpty()){
                JOptionPane.showMessageDialog(frame,
                        "There is no data to save or you didn't choose any sensor",
                        "Data error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // TODO DOKONCZYC
    private void saveToFile(ArrayList<MeteoSensorPanel> toPrint, ArrayList<MeteoSensorPanel> withError) {

        try {
            Date now1 = new Date();
            PrintWriter out = new PrintWriter("filename.txt");
            for (MeteoSensorPanel sensor : toPrint) {
                Date now2 = new Date();
                out.println(now2 + " " + sensor.getSensorName() + " " + Arrays.toString(sensor.getSensorData()));
            }
            out.close();
            frame.dispose();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }
}
