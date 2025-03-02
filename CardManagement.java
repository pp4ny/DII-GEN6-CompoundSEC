import javax.swing.*;
import java.awt.*;

public class CardManagement extends JFrame {
    private JTextField cardIdField;
    private JTextField passwordField;
    private CardDatabase cardDatabase;
    private JFrame parentFrame;

    private static CardManagement instance;

    public CardManagement() {
        this.parentFrame = parentFrame;
        cardDatabase = CardDatabase.getInstance();
        setTitle("Manage Cards");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Manage Cards", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.add(new JLabel("Card Name:"));
        cardIdField = new JTextField();
        inputPanel.add(cardIdField);

        inputPanel.add(new JLabel("Password:"));
        passwordField = new JTextField();
        inputPanel.add(passwordField);

        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        JButton addCardButton = new JButton("Add Card");
        addCardButton.addActionListener(e -> handleAddCardButtonClick());
        buttonPanel.add(addCardButton);

        JButton modifyCardButton = new JButton("Modify Card");
        modifyCardButton.addActionListener(e -> handleModifyCardButtonClick());
        buttonPanel.add(modifyCardButton);

        JButton revokeCardButton = new JButton("Revoke Card");
        revokeCardButton.addActionListener(e -> handleRevokeCardButtonClick());
        buttonPanel.add(revokeCardButton);

        add(buttonPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose();  // ปิดหน้าต่างนี้
            if (parentFrame != null) {
                parentFrame.setVisible(true);  // กลับไปที่หน้าต่างหลัก
            }
        });
        add(backButton, BorderLayout.SOUTH);
    }

    public static synchronized CardManagement getInstance() {
        if (instance == null) {
            instance = new CardManagement();
        }
        return instance;
    }

    private void handleAddCardButtonClick() {
        String cardID = cardIdField.getText();
        String password = passwordField.getText();

        if (cardID.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Card ID and Password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = cardDatabase.addCard(cardID, password);
        if (success) {
            JOptionPane.showMessageDialog(this, "Card added successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Card ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleModifyCardButtonClick() {
        String cardID = cardIdField.getText();
        String newPassword = passwordField.getText();

        if (cardID.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Card ID and New Password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = cardDatabase.modifyCard(cardID, newPassword);
        if (success) {
            JOptionPane.showMessageDialog(this, "Card modified successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Card ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRevokeCardButtonClick() {
        String cardID = cardIdField.getText();

        if (cardID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Card ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = cardDatabase.revokeCard(cardID);
        if (success) {
            JOptionPane.showMessageDialog(this, "Card revoked successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Card ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void openCardManagement(JFrame parentFrame) {
        CardManagement cardManagement = CardManagement.getInstance(); // ใช้ Singleton
        cardManagement.setParentFrame(parentFrame);  // ส่ง parentFrame ไปที่ CardManagement
        cardManagement.setVisible(true);  // แสดงหน้าต่าง
    }

    public void setParentFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;  // รับ parentFrame จากภายนอก
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> CardManagement.getInstance().setVisible(true));
    }
}