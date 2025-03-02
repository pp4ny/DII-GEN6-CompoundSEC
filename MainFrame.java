import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainFrame extends JFrame {
    private JTextField nameField;
    private JPasswordField passwordField;
    private JLabel lockLabel; // แสดงเวลาล็อก
    static final Map<String, String> userPasswords = new HashMap<>(); //Singleton Pattern
    private static final int MAX_ATTEMPTS = 3;
    private static final Map<String, Integer> failedAttempts = new HashMap<>();
    private static final Map<String, Long> lockedTime = new HashMap<>();
    private Timer timer; // ใช้สำหรับอัปเดตเวลาล็อกเรียลไทม์

    static {
        userPasswords.put("Pany", "1234");
        userPasswords.put("Ggnamy", "2345");
        userPasswords.put("Dobby", "4321");
        userPasswords.put("Kitty", "5432");
        userPasswords.put("Staff", "0000");
    }

    public MainFrame() {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Welcome to P4NY Luxe", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        add(new JLabel("Card ID :"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(15);
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Password :"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        JButton loginButton = new JButton("Submit");
        loginButton.addActionListener(e -> verifyLogin());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        // เพิ่ม label สำหรับแสดงเวลาล็อก
        lockLabel = new JLabel(" ", SwingConstants.CENTER);
        lockLabel.setForeground(Color.RED);
        gbc.gridy = 4;
        add(lockLabel, gbc);

        setLocationRelativeTo(null);
        setVisible(true);

        // เช็คว่ามีบัญชีที่ถูกล็อกอยู่ไหม
        startLockTimer();
    }

    private void startLockTimer() {
        if (timer != null) {
            timer.cancel();  // ยกเลิก timer เดิมถ้ามี
        }
        timer = new Timer();  // สร้าง timer ใหม่
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    boolean anyLocked = false;
                    for (String user : lockedTime.keySet()) {
                        long currentTime = System.currentTimeMillis();
                        long lockDuration = (currentTime - lockedTime.get(user)) / 1000;  // คำนวณเวลาที่ถูกล็อก

                        // ตรวจสอบว่าผ่านเวลาล็อกไปแล้วกี่วินาที
                        if (lockDuration < 10) {
                            lockLabel.setText("Card ID " + user + " Locked! Please wait " + (10 - lockDuration) + " seconds.");
                            anyLocked = true;
                            break;
                        }
                    }
                    if (!anyLocked) {
                        lockLabel.setText(" ");  // รีเซ็ตข้อความล็อกเมื่อหมดเวลา
                        timer.cancel();  // ยกเลิก timer เมื่อเวลาผ่านไปครบ
                    }
                });
            }
        }, 0, 1000);  // เริ่มนับตั้งแต่เวลา 0 และตรวจสอบทุกๆ 1 วินาที
    }

    //Strategy Pattern
    public boolean validateCard(String cardID, String password) {
        if (lockedTime.containsKey(cardID)) {
            long currentTime = System.currentTimeMillis();
            long lockDuration = (currentTime - lockedTime.get(cardID)) / 1000;

            if (lockDuration < 10) {
                return false;
            } else {
                lockedTime.remove(cardID);
                failedAttempts.put(cardID, 0);
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("cards.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] cardData = line.split("\\s+");
                if (cardData.length == 2 && cardData[0].equals(cardID) && cardData[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading card data: " + e.getMessage());
        }
        return false;
    }

    private void verifyLogin() {
        String name = nameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // เช็คว่าผู้ใช้ถูกล็อกหรือไม่
        if (lockedTime.containsKey(name)) {
            long currentTime = System.currentTimeMillis();
            long lockDuration = (currentTime - lockedTime.get(name)) / 1000;

            if (lockDuration < 10) {
                lockLabel.setText("Locked! Please wait " + (10 - lockDuration) + " seconds.");
                return; // ถ้ายังไม่ครบเวลาให้ไม่สามารถล็อกอินได้
            } else {
                // รีเซ็ตการล็อกเมื่อครบเวลา
                lockedTime.remove(name);
                failedAttempts.put(name, 0);
                lockLabel.setText(" ");  // ลบข้อความล็อก
            }
        }

        // ตรวจสอบการล็อกอิน
        if (validateCard(name, password)) {
            failedAttempts.put(name, 0); // รีเซ็ตจำนวนครั้งที่ใส่ผิด
            new RoomAccessControl(name, password);
            dispose();
        } else {
            // หากรหัสผิด เพิ่มจำนวนครั้งที่ใส่ผิด
            int attempts = failedAttempts.getOrDefault(name, 0) + 1;
            failedAttempts.put(name, attempts);

            JOptionPane.showMessageDialog(this, "Invalid Card ID or Password! (Attempt " + attempts + " of " + MAX_ATTEMPTS + ")", "Error", JOptionPane.ERROR_MESSAGE);

            if (attempts >= MAX_ATTEMPTS) {
                lockedTime.put(name, System.currentTimeMillis());  // ล็อกเมื่อใส่ผิด 3 ครั้ง
                lockLabel.setText("Locked! Please wait 10 seconds.");
                startLockTimer(); // เริ่มนับเวลา
            }
        }
    }

    // Singleton Pattern
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}