package meteorologClient;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SensorDataDownloader {
    public static String[] downloadDataFromSensor() throws IOException{
        String[] sensorData = new String[4];
        try (Socket socket = new Socket("192.168.1.67", 4415);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
            socket.setSoTimeout(5000);
            boolean eof = false;
            int i = 0;
            while (!eof) {
                String line = in.readLine();
                if (line != null) {
                    sensorData[i] = line;
                    i++;
                } else {
                    eof = true;
                }
            }
            socket.close();
        }
        return sensorData;
    }
}
