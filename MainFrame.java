import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JTextField cardIdField;
    private JTextArea auditTrailTextArea;
    private AccessCardManager cardManager;

    public MainFrame() {
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

        auditTrailTextArea = new JTextArea();
        auditTrailTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(auditTrailTextArea);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Event listeners
        addButton.addActionListener(e -> cardManager.addCard(cardIdField.getText()));
        modifyButton.addActionListener(e -> cardManager.modifyCard(cardIdField.getText()));
        revokeButton.addActionListener(e -> cardManager.revokeCard(cardIdField.getText()));
        accessButton.addActionListener(e -> cardManager.accessAttempt(cardIdField.getText(), auditTrailTextArea));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}