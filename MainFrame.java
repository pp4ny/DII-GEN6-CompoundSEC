import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

    private AccessCardManager cardManager;
    private JTextField nameField;
    private JPasswordField passwordField;

        cardManager = new AccessCardManager();

        setTitle("Access Control System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("Card ID:"));
        cardIdField = new JTextField();
        panel.add(cardIdField);

        JButton addButton = new JButton("Add Card");
        JButton modifyButton = new JButton("Modify Card");
        JButton revokeButton = new JButton("Revoke Card");
        JButton accessButton = new JButton("Access Attempt");

        panel.add(addButton);
        panel.add(modifyButton);
        panel.add(revokeButton);
        panel.add(accessButton);
        nameField = new JTextField(15);

        auditTrailTextArea = new JTextArea();
        auditTrailTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(auditTrailTextArea);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        passwordField = new JPasswordField(15);

        // Event listeners
        addButton.addActionListener(e -> cardManager.addCard(cardIdField.getText()));
        modifyButton.addActionListener(e -> cardManager.modifyCard(cardIdField.getText()));
        revokeButton.addActionListener(e -> cardManager.revokeCard(cardIdField.getText()));
        accessButton.addActionListener(e -> cardManager.accessAttempt(cardIdField.getText(), auditTrailTextArea));
    }

    public boolean validateCard(String cardID, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("cards.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue; // ข้ามบรรทัดว่าง

                String[] cardData = line.split("\\s+"); // แยกข้อมูลด้วยช่องว่าง (รองรับหลายช่องว่าง)
                if (cardData.length == 2) {
                    if (cardData[0].equals(cardID) && cardData[1].equals(password)) {
                        return true; // ถ้าตรงกันให้ล็อกอินผ่าน
                    }
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading card data: " + e.getMessage());
        }
        return false; // ถ้าไม่มีข้อมูลตรงกันให้ล็อกอินไม่ผ่าน
    }
    private void verifyLogin() {
        String name = nameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        if (validateCard(name, password)) {
            System.out.println("Login Successful for: " + name);
            new RoomAccessControl(name, password); // เปิดหน้าจัดการการเข้าห้อง
            dispose(); // ปิดหน้าต่างล็อกอิน
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Card ID or Password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}