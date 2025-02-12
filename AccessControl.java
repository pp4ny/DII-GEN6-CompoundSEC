import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

abstract class AccessControl {
    protected List<String> auditTrail = new ArrayList<>();

    // เมธอดบังคับให้คลาสลูกต้องมี
    public abstract void addCard(String cardId);

    public abstract void modifyCard(String cardId);

    public abstract void revokeCard(String cardId);

    // เมธอดบันทึกการพยายามเข้าออก >> ซ่อนรายละเอียดการบันทึก log
    void logAccessAttempt(String cardId, boolean granted) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String log = timestamp + " - Card ID: " + cardId + " " + (granted ? "Access Granted" : "Access Denied");
        auditTrail.add(log);
    }

    // เข้าถึง auditTrail ผ่าน method นี้เท่านั้น (Data Hiding)
    public String getAuditTrail() {
        return String.join("\n", auditTrail);
    }
}

