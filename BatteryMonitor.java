import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.util.Timer;
import java.util.TimerTask;

public class BatteryMonitor extends Application {

    private static final int BATTERY_CHECK_INTERVAL = 60000; // Check battery every 60 seconds

    private TrayIcon trayIcon;
    private Timer timer;

    @Override
    public void start(Stage primaryStage) {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported.");
            return;
        }

        SystemTray tray = SystemTray.getSystemTray();
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");

        PopupMenu popup = new PopupMenu();
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> {
            tray.remove(trayIcon);
            System.exit(0);
        });
        popup.add(exitItem);

        trayIcon = new TrayIcon(icon, "Battery Monitor", popup);
        trayIcon.setImageAutoSize(true);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkBattery();
            }
        }, 0, BATTERY_CHECK_INTERVAL);
    }

    private void checkBattery() {
        try {
            double batteryLevel = getBatteryLevel();
            boolean isCharging = isCharging();
            if (isCharging && batteryLevel < 0.3) {
                Platform.runLater(() -> displayNotification("Battery Alert", "Your laptop battery is below 30% and charging!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double getBatteryLevel() throws Exception {
        // Your code to get the battery level (e.g., using third-party libraries or system APIs)
        return 0.3; // Dummy value for demonstration
    }

    private boolean isCharging() {
        // Your code to check if the laptop is charging
        return true; // Dummy value for demonstration
    }

    private void displayNotification(String title, String message) {
        if (SystemTray.isSupported() && trayIcon != null) {
            trayIcon.displayMessage(title, message, MessageType.INFO);
        } else {
            System.out.println(message);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
