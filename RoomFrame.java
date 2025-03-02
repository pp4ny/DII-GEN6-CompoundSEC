import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RoomFrame extends JFrame {
    private static final Map<String, String[]> accessRestrictions = new HashMap<>();

    static {
        accessRestrictions.put("Pany", new String[]{"VIP2", "Staff Area", "Medium Floor"});
        accessRestrictions.put("Ggnamy", new String[]{"VIP1", "Staff Area", "Medium Floor"});
        accessRestrictions.put("Dobby", new String[]{"Room2", "Room3", "Room4", "Staff Area", "High Floor"});
        accessRestrictions.put("Kitty", new String[]{"Room1", "Room3", "Room4", "Staff Area", "High Floor"});
    }

    public RoomFrame(String username, String floorName) {
        setTitle(floorName);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel(floorName, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel roomPanel = new JPanel(new GridLayout(0, 2, 10, 10));

        String[] rooms;
        switch (floorName) {
            case "High Floor":
                rooms = new String[]{"VIP1", "VIP2", "Suite", "Fitness Center", "Spa Exclusive", "Sky Bar", "Infinity Pool", "Private Meeting Rooms"};
                break;
            case "Medium Floor":
                rooms = new String[]{"Room1", "Room2", "Room3", "Room4", "Fitness", "Spa", "Common Area"};
                break;
            case "Low Floor":
                rooms = new String[]{"Meeting Room1", "Meeting Room2", "Lobby", "Restaurant", "Fitness", "Swimming Pool", "Staff Area"};
                break;
            default:
                rooms = new String[]{};
        }

        for (String room : rooms) {
            JButton roomButton = new JButton(room);
            roomButton.addActionListener(e -> checkAccess(username, room, floorName));
            roomPanel.add(roomButton);
        }
        add(roomPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setMargin(new Insets(2, 10, 2, 10));
        backButton.addActionListener(e -> {
            new RoomAccessControl(username, MainFrame.userPasswords.get(username));
            dispose();
        });

        Panel backPanel = new Panel();
        backPanel.add(backButton);
        add(backPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void checkAccess(String username, String room, String floorName) {
        boolean isAccessGranted = true;  // กำหนดค่าเริ่มต้นเป็นอนุญาต

        // ตรวจสอบสิทธิ์การเข้าถึงห้องหรือชั้น
        if (accessRestrictions.containsKey(username)) {
            String[] restrictedFloors = accessRestrictions.get(username);
            if (contains(restrictedFloors, floorName)) {
                isAccessGranted = false;  // ปฏิเสธการเข้าถึงชั้น
                JOptionPane.showMessageDialog(this, "Access Denied to " + floorName, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // ตรวจสอบห้องที่จำกัด
        String[] restrictions = accessRestrictions.getOrDefault(username, new String[]{});
        for (String restrictedRoom : restrictions) {
            if (room.equals(restrictedRoom)) {
                isAccessGranted = false;  // ปฏิเสธการเข้าถึงห้อง
                JOptionPane.showMessageDialog(this, "Access Denied to " + room, "Error", JOptionPane.ERROR_MESSAGE);
                break;
            }
        }

        // บันทึกการเข้าถึง
        AccessControl accessControl = new AccessControl(username, "password", floorName, room);
        accessControl.logAccessAttempt(isAccessGranted);  // บันทึกการเข้าถึงไม่ว่าจะอนุญาตหรือไม่

        // แสดงข้อความการเข้าถึง
        if (isAccessGranted) {
            if ("Staff Area".equals(room)) {
                new AccessCardManager(username, this);  // เปิดหน้าจัดการบัตรเมื่อเข้าพื้นที่ Staff Area
            } else {
                JOptionPane.showMessageDialog(this, username + " accessed " + room);
            }
        }
    }
    private boolean contains(String[] array, String value) {
        for (String element : array) {
            if (element.equals(value)) {
                return true;
            }
        }
        return false;
    }
}