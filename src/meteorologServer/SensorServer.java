package meteorologServer;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SensorServer extends Thread {
    private ServerSocket socket;

    public SensorServer() {
        super();
        try {
            socket = new ServerSocket(4415);
            System.out.println("Uruchomiono SensorServer...");
        } catch (IOException e) {
            System.out.println("Błąd: nie udaloa sie utworzyc gniazda");
            System.exit(1);
        }
    }

    public void run() {
        Socket client = null;

        while (true) {
            if (socket == null) {
                return;
            }

            try {
                int wind = SensorDataGenerator.checkWind();
                double temperature = SensorDataGenerator.checkTemperature();
                int snow = SensorDataGenerator.checkSnow();
                int pressure = SensorDataGenerator.checkPressure();

                client = socket.accept();
                BufferedOutputStream bb = new BufferedOutputStream(client.getOutputStream());
                PrintWriter os = new PrintWriter(bb, false);

                Date now = new Date();
                System.out.println(now + " | " + client + " | " + wind + ", " + temperature + ", " + snow + ", " + pressure);
                os.println(wind);
                os.println(temperature);
                os.println(snow);
                os.println(pressure);
                os.flush();
                os.close();
                client.close();
            } catch (IOException e) {
                System.out.println("Blad polaczenia");
                System.exit(1);
            }
        }
    }

    public static void main(String[] arguments) {
        SensorServer server = new SensorServer();
        server.start();
    }
}
