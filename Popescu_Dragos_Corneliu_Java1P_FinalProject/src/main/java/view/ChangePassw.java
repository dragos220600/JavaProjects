package view;

import Controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.regex.Pattern;

public class ChangePassw extends JFrame {

    private final JPasswordField passw;
    private final JPasswordField confPassw;

    public ChangePassw() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 300);
        setTitle("Account");
        setLocationRelativeTo(null);
        setVisible(true);

        JLabel passwLabel = new JLabel("New password: ");
        passw = new JPasswordField();

        JLabel confPasswLabel = new JLabel("Confirm password: ");
        confPassw = new JPasswordField();

        JButton changeButton = getChangeButton();

        JPanel lowerPanel = new JPanel();
        lowerPanel.add(changeButton, BorderLayout.CENTER);

        JPanel panel = getPanel(passwLabel, confPasswLabel);

        add(panel, BorderLayout.CENTER);
        add(lowerPanel, BorderLayout.SOUTH);
    }

    private JPanel getPanel(JLabel passwLabel, JLabel confPasswLabel) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.add(passwLabel);
        panel.add(passw);
        panel.add(confPasswLabel);
        panel.add(confPassw);
        return panel;
    }

    private JButton getChangeButton() {
        JButton changeButton = new JButton("Change");
        changeButton.setForeground(Color.CYAN);
        changeButton.setBackground(Color.darkGray);
        changeButton.setFont(new Font("MV Boli", Font.PLAIN, 15));
        changeButton.addActionListener(e -> {
            boolean verificare = true;
            char[] litere = passw.getPassword();
            String parola = new String(litere);
            Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
            Pattern lowerCasePatten = Pattern.compile("[a-z ]");
            Pattern digitCasePatten = Pattern.compile("[0-9 ]");

            if (parola.length() == 0) {
                verificare = false;
                JOptionPane.showMessageDialog(null, "All fields must be completed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (parola.length() < 6) {
                verificare = false;
                JOptionPane.showMessageDialog(null, "Password must contain 6 characters minimum!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (specialCharPatten.matcher(parola).find()) {
                verificare = false;
                JOptionPane.showMessageDialog(null, "Invalid characters!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (!UpperCasePatten.matcher(parola).find() || !lowerCasePatten.matcher(parola).find()
                    || !digitCasePatten.matcher(parola).find()) {
                verificare = false;
                JOptionPane.showMessageDialog(null, "Password must contain one lowercase character, one uppercase character and a number!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            char[] litere2 = confPassw.getPassword();
            String confParola = new String(litere2);
            if (!parola.equals(confParola)) {
                verificare = false;
                JOptionPane.showMessageDialog(null, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            if (verificare) {
                Optional<User> usr = UserController.getInstance().findId(CurrentlyUser.currentlyId);
                if (usr.isPresent()) {
                    boolean b = UserController.getInstance().updatePassw(usr.get().getPassw(), parola);
                    if (b) {
                        Optional<User> user = UserController.getInstance().findId(CurrentlyUser.currentlyId);
                        if (user.isPresent()) {
                            Timestamp t = Timestamp.from(Instant.now());
                            HistoryActions.actions.put(t, user.get().getUsername() + " has changed the password");
                        }
                        JOptionPane.showMessageDialog(null, "Change complete!", "Information", JOptionPane.INFORMATION_MESSAGE);
                        Login log = new Login();
                        PanelController.components.push(log);
                        dispose();
                    }
                }
            }
        });
        return changeButton;
    }
}
