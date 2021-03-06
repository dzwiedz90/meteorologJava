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

public class SaveDataToFile extends JFrame implements ActionListener {
    JFrame frame;
    JPanel checkBoxPanel;
    JButton saveToFileButton;
    JButton selectAllButton;
    JButton deselectAllButton;
    private ArrayList<MeteoSensorPanel> sensorList;
    private ArrayList<JCheckBox> checkBoxList;

    public SaveDataToFile(ArrayList<MeteoSensorPanel> sensorListIn) {
        sensorList = sensorListIn;
        frame = new JFrame("Export to file");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(320, 150);
        BorderLayout layout = new BorderLayout();
        frame.setLayout(layout);

        JLabel questionLabel = new JLabel("Which objects do you want to export?:");
        addCheckBoxes(sensorListIn);

        frame.add(questionLabel, BorderLayout.NORTH);
        frame.add(checkBoxPanel, BorderLayout.CENTER);
        addButtons();

        frame.setVisible(true);
        centerWindowOnScreen();
    }

    private void centerWindowOnScreen() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }

    private void addButtons() {
        saveToFileButton = new JButton("Save");
        saveToFileButton.addActionListener(this);
        selectAllButton = new JButton("Select all");
        selectAllButton.addActionListener(this);
        deselectAllButton = new JButton("Deselect all");
        deselectAllButton.addActionListener(this);
        frame.add(selectAllButton, BorderLayout.WEST);
        frame.add(deselectAllButton, BorderLayout.EAST);
        frame.add(saveToFileButton, BorderLayout.SOUTH);
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
        ArrayList<MeteoSensorPanel> doNothing = new ArrayList<>();
        if (source == saveToFileButton) {
            for (int i = 0; i < sensorList.size(); i++) {
                if ((sensorList.get(i).getSensorData() != null) && (checkBoxList.get(i).isSelected())) {
                    toPrint.add(sensorList.get(i));
                } else if (((sensorList.get(i).getSensorData() == null) && (checkBoxList.get(i).isSelected()))) {
                    withError.add(sensorList.get(i));
                } else if (((sensorList.get(i).getSensorData() == null) && (!checkBoxList.get(i).isSelected()))) {
                    doNothing.add(sensorList.get(i));
                }
            }

            saveToFile(toPrint, withError, doNothing);
        }
        if (source == selectAllButton) {
            for (JCheckBox box : checkBoxList) {
                box.setSelected(true);
            }
        }
        if (source == deselectAllButton) {
            for (JCheckBox box : checkBoxList) {
                box.setSelected(false);
            }
        }
    }

    private void saveToFile(ArrayList<MeteoSensorPanel> toPrint, ArrayList<MeteoSensorPanel> withError, ArrayList<MeteoSensorPanel> doNothing) {
        boolean checkWhatToDo = checkIfShouldSave(toPrint.isEmpty(), withError.isEmpty(), doNothing.isEmpty());

        if (checkWhatToDo) {
            try {
                Date now1 = new Date();
                PrintWriter out = new PrintWriter("sensor_data.txt");
                for (MeteoSensorPanel sensor : toPrint) {
                    Date now2 = new Date();
                    out.println(now2 + " " + sensor.getSensorName() + " " + Arrays.toString(sensor.getSensorData()));
                }
                out.close();
                frame.dispose();
                Desktop.getDesktop().open(new File(System.getProperty("user.dir")));
            } catch (FileNotFoundException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            }
        } else {
            JOptionPane.showMessageDialog(frame,
                    "There is no data to save or you didn't choose any sensor",
                    "Data error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private boolean checkIfShouldSave(boolean toPrint, boolean withError, boolean doNothing) {
        if ((!toPrint && withError && doNothing) || (!toPrint && withError && !doNothing)) {
            return true;
        } else {
            return false;
        }
    }
}
