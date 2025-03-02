import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AccessControl {
    protected String cardId;
    protected String roomName;
    protected String floorName;
    private String loginPassword;
    protected LocalDateTime timestamp;
    protected static List<String> accessLog = new ArrayList<>();


    public AccessControl(String cardId, String loginPassword, String floorName, String roomName) {
        this.cardId = cardId;
        this.roomName = roomName;
        this.floorName = floorName;
        this.loginPassword = loginPassword;
        this.timestamp = LocalDateTime.now();
    }

    // ตรวจสอบสิทธิ์การเข้าใช้งาน
    public boolean verifyAccess() {
        // เพิ่มเงื่อนไขการตรวจสอบสิทธิ์ที่นี่
        // ในกรณีนี้อนุญาตให้ผ่านทุกกรณี
        return true;
    }

    // บันทึกการเข้าถึง
    public void logAccessAttempt(boolean isSuccess) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String logMessage = "Card ID: " + cardId + " - Floor: " + floorName + " - Room: " + roomName
                + " at " + timestamp.format(formatter) + " - " + (isSuccess ? "Access Granted" : "Access Denied");
        accessLog.add(logMessage);
    }

    public static void showAccessLogDialog(JFrame parentFrame) {
        if (accessLog.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No access logs available.", "Audit Log", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder logText = new StringBuilder("Access Logs:\n\n");
            for (String log : accessLog) {
                logText.append(log).append("\n");
            }

            JTextArea logArea = new JTextArea(logText.toString());
            logArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(logArea);
            JOptionPane.showMessageDialog(parentFrame, scrollPane, "Audit Logs", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}