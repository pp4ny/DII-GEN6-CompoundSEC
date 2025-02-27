    private void requestPassword(String floorName) {
        String inputPassword = JOptionPane.showInputDialog(this, "Enter password for " + floorName);
        if (inputPassword != null && inputPassword.equals(userPassword)) {
            new RoomFrame(username, floorName);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect password", "Error", JOptionPane.ERROR_MESSAGE);
        }
