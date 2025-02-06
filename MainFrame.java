import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private JTextField cardIdField = new JTextField();
    private JTextArea auditTrailTextArea = new JTextArea();
    private AccessCardManager cardManager = new AccessCardManager();

    public MainFrame() {
        setTitle("Access Control System");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel.add(createPanel("Welcome", new String[]{"Admin", "Customer"}, e -> cardLayout.show(mainPanel, "IDInput")), "Welcome");
        mainPanel.add(createPanel("Enter ID Card", new String[]{"Submit"}, e -> cardManager.accessAttempt(cardIdField.getText(), auditTrailTextArea)), "IDInput");
        mainPanel.add(createPanel("Card ID", new String[]{"Add Card", "Modify Card", "Revoke Card", "Access Attempt"}, this::handleCardActions), "Admin");

        add(mainPanel);
        cardLayout.show(mainPanel, "Welcome");
    }

    private JPanel createPanel(String labelText, String[] buttons, ActionListener action) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.add(new JLabel(labelText, SwingConstants.CENTER));
        if (labelText.equals("Enter ID Card")) panel.add(cardIdField);
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(action);
            panel.add(button);
        }
        return panel;
    }

    private void handleCardActions(java.awt.event.ActionEvent e) {
        String action = ((JButton) e.getSource()).getText();
        String cardId = cardIdField.getText();
        if (cardId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Card ID first!");
            return;
        }

        switch (action) {
            case "Add Card":
                cardManager.addCard(cardId);
                break;
            case "Modify Card":
                cardManager.modifyCard(cardId);
                break;
            case "Revoke Card":
                cardManager.revokeCard(cardId);
                break;
            case "Access Attempt":
                cardManager.accessAttempt(cardId, auditTrailTextArea);
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}