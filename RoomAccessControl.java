import javax.swing.*;
import java.awt.*;

class RoomAccessControl extends JFrame {
    private String username;
    private String userPassword;

    public RoomAccessControl(String username, String userPassword) {
        this.username = username;
        this.userPassword = userPassword;

        setTitle("Select Floor");
        setSize(400, 250);
        setLayout(new GridBagLayout());
        setResizable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        JLabel titleLabel = new JLabel("Select Floor", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 0;
        add(titleLabel, gbc);

        String[] floorNames = {"High Floor", "Medium Floor", "Low Floor"};
        for (int i = 0; i < floorNames.length; i++) {
            JButton floorButton = new JButton(floorNames[i]);
            int finalI = i;
            floorButton.addActionListener(e -> requestPassword(floorNames[finalI]));
            gbc.gridy = i + 1;
            add(floorButton, gbc);
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new MainFrame();
            dispose();
        });
        gbc.gridy = floorNames.length + 1;
        add(backButton, gbc);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public RoomAccessControl(AccessFrame accessFrame, String cardId) {
    }

    public RoomAccessControl(AccessControl accessControl, String cardId) {
    }

    private void requestPassword(String floorName) {
        String inputPassword = JOptionPane.showInputDialog(this, "Enter password for " + floorName);
        if (inputPassword != null && inputPassword.equals(userPassword)) {
            new RoomFrame(username, floorName);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}