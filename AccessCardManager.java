import javax.swing.*;
import java.util.*;

public class AccessCardManager extends AccessControl {
    private Map<String, String> accessCards = new HashMap<>();

    // เพิ่มบัตร
    @Override
    public void addCard(String cardId) {
        if (!accessCards.containsKey(cardId)) {
            accessCards.put(cardId, "Granted");
            logAccessAttempt(cardId, true);
            showMessage("Card added successfully!");
        } else {
            showMessage("Card ID already exists!");
        }
    }

    // แก้ไขบัตร
    @Override
    public void modifyCard(String cardId) {
        if (accessCards.containsKey(cardId)) {
            accessCards.put(cardId, "Modified");
            logAccessAttempt(cardId, true);
            showMessage("Card modified successfully!");
        } else {
            showMessage("Card not found!");
        }
    }

    // ยกเลิกบัตร
    @Override
    public void revokeCard(String cardId) {
        if (accessCards.containsKey(cardId)) {
            accessCards.remove(cardId);
            logAccessAttempt(cardId, false);
            showMessage("Card revoked successfully!");
        } else {
            showMessage("Card not found!");
        }
    }

    // พยายามเข้าถึง
    public void accessAttempt(String cardId, JTextArea auditTrailTextArea) {
        boolean granted = accessCards.containsKey(cardId);
        logAccessAttempt(cardId, granted);
        showMessage(granted ? "Access granted!" : "Access denied!");
        auditTrailTextArea.setText(getAuditTrail());
    }

    // แสดงข้อความแจ้งเตือน
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}