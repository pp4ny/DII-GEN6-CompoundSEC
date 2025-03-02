import javax.swing.*;
import java.awt.*;

public class AccessCardManager extends JFrame {
    private String username;

    public AccessCardManager(String username, JFrame parentFrame) {
        this.username = username;

        setTitle("Staff Area: " + username);
        setSize(400, 250);
        setLocationRelativeTo(parentFrame);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Staff Area: " + username, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        // ปุ่มจัดการบัตร
        JButton manageCardsButton = new JButton("Manage Cards");
        manageCardsButton.addActionListener(e -> {
            this.setVisible(false);
            CardManagement.openCardManagement(this);  // เปิด CardManagement และส่ง parentFrame
        });
        buttonPanel.add(manageCardsButton);

        // ปุ่มดู Audit Log
        JButton auditLoggingButton = new JButton("Audit Logging");
        auditLoggingButton.addActionListener(e -> {
            AccessControl.showAccessLogDialog(this);
        });
        buttonPanel.add(auditLoggingButton);

        add(buttonPanel, BorderLayout.CENTER);

        // ปุ่มกลับ
        JButton backButton = new JButton("Back");
        backButton.setMargin(new Insets(2, 10, 2, 10));
        backButton.addActionListener(e -> {
            dispose();
            if (parentFrame != null) {
                parentFrame.setVisible(true);
            }
        });
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Constructor สำหรับกรณีที่ไม่ต้องการ parentFrame
    public AccessCardManager(String username) {
        this(username, null);  // เรียก constructor หลัก
    }
}