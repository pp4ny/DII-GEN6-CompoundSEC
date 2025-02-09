import java.text.SimpleDateFormat;
import java.util.*;

abstract class AccessControl {
    protected List<String> auditTrail = new ArrayList<>();

    // เมธอดบังคับให้คลาสลูกต้องมี
    public abstract void addCard(String cardId);
    public abstract void modifyCard(String cardId);
    public abstract void revokeCard(String cardId);

    // เมธอดบันทึกการพยายามเข้าออก
    public void logAccessAttempt(String cardId, boolean granted) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String log = timestamp + " - Card ID: " + cardId + " " + (granted ? "Access Granted" : "Access Denied");
        auditTrail.add(log);
        System.out.println(log);
    }

    // คืนค่าประวัติการเข้าถึง
    public String getAuditTrail() {
        return String.join("\n", auditTrail);
    }
}
