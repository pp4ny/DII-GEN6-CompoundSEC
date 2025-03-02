import javax.swing.*;
import java.awt.*;

public class AccessFrame extends JFrame {
    private String cardId;
    private String password;

    public AccessFrame(String cardId, String password) {
        this.cardId = cardId;
        this.password = password;

        setTitle("P4NY Luxe");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Select Floor");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);

        JButton floor1Button = new JButton("High floor");
        JButton floor2Button = new JButton("Medium floor");
        JButton floor3Button = new JButton("Low floor");
        JButton backButton = new JButton("Back");

        floor1Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        floor2Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        floor3Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        floor1Button.addActionListener(e -> RoomAccessControl("High floor"));
        floor2Button.addActionListener(e -> showRoomsForFloor("Medium floor"));
        floor3Button.addActionListener(e -> showRoomsForFloor("Low floor"));
        backButton.addActionListener(e -> goBackToMain());

        add(floor1Button);
        add(Box.createRigidArea(new Dimension(10, 20)));
        add(floor2Button);
        add(Box.createRigidArea(new Dimension(10, 20)));
        add(floor3Button);
        add(Box.createRigidArea(new Dimension(10, 20)));
        add(backButton);
    }

    private void RoomAccessControl(String floor) {
        if (floor.equals("High floor")) {
            this.setVisible(false);
            new RoomAccessControl(this, cardId);
        } else {
            JOptionPane.showMessageDialog(this, "Available rooms on " + floor);
        }
    }

    private void showRoomsForFloor(String floor) {
        JOptionPane.showMessageDialog(this, "Available rooms on " + floor);
    }

    private void goBackToMain() {
        this.dispose();
        new MainFrame().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AccessFrame("User", "Password").setVisible(true));
    }
}