/*
public class LoggingManager {
    private static LoggingManager instance;

    private LoggingManager() {
        // คอนสตรัคเตอร์แบบส่วนตัว
    }

    public static LoggingManager getInstance() {
        if (instance == null) {
            synchronized (LoggingManager.class) {
                if (instance == null) {
                    instance = new LoggingManager();
                }
            }
        }
        return instance;
    }

    public void logAccessAttempt(String cardId, boolean accessGranted) {
        String status = accessGranted ? "granted" : "denied";
        System.out.println("Access " + status + " for card ID: " + cardId);
    }
}*/
