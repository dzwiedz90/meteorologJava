package meteorologServer;

public class SensorDataGenerator {
    static int checkWind() {
        return randomInt(5, 30);
    }

    static double checkTemperature() {
        return Math.round(randomDouble(-60, -25));
    }

    static int checkSnow() {
        return randomInt(200, 600);
    }

    static int checkPressure() {
        return randomInt(980, 1050);
    }

    static private double randomDouble(int min, int max) {
        int range = max - min + 1;
        return ((Math.random() * range) + min);
    }

    static private int randomInt(int min, int max) {
        int range = max - min + 1;
        return (int) (Math.random() * range) + min;
    }
}
