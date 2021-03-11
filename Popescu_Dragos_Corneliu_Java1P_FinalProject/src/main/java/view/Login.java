package view;

import Controller.UserController;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

public class Login extends JFrame {

    private final JTextField username;
    private final JPasswordField passw;

    public Login() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 130);
        setTitle("Login Page");
        setLocationRelativeTo(null);
        setVisible(true);

        JLabel userLabel = getUserLabel();
        username = new JTextField();

        JLabel passwLabel = getPasswLabel();
        passw = new JPasswordField();

        JPanel upperPanel = getUpperPanel(userLabel, passwLabel);

        JButton resetButton = getResetButton();
        JButton loginButton = getLoginButton();
        JButton registerButton = getRegisterButton();

        JPanel lowerPanel = getLowerPanel(resetButton, loginButton, registerButton);

        add(upperPanel, BorderLayout.CENTER);
        add(lowerPanel, BorderLayout.SOUTH);
    }

    private JPanel getLowerPanel(JButton resetButton, JButton loginButton, JButton registerButton) {
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new FlowLayout());
        lowerPanel.setBackground(new Color(46, 189, 189, 255));
        lowerPanel.add(resetButton);
        lowerPanel.add(loginButton);
        lowerPanel.add(registerButton);
        return lowerPanel;
    }

    private JPanel getUpperPanel(JLabel userLabel, JLabel passwLabel) {
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new GridLayout(2, 1));
        upperPanel.setBackground(new Color(46, 189, 189, 255));
        upperPanel.add(userLabel);
        upperPanel.add(username);
        upperPanel.add(passwLabel);
        upperPanel.add(passw);
        return upperPanel;
    }

    private JButton getRegisterButton() {
        JButton registerButton = new JButton("Register");
        registerButton.setForeground(Color.CYAN);
        registerButton.setBackground(Color.darkGray);
        registerButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        registerButton.addActionListener(e -> {
            Register r = new Register();
            PanelController.components.push(r);
            dispose();
        });
        return registerButton;
    }

    private JButton getLoginButton() {
        JButton loginButton = new JButton("Login");
        loginButton.setForeground(Color.CYAN);
        loginButton.setBackground(Color.darkGray);
        loginButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        loginButton.addActionListener(e -> {
            UserController userController = UserController.getInstance();
            Optional<User> user = userController.findUsername(username.getText());
            Optional<User> mail = userController.findMail(username.getText());

            if (user.isEmpty()) {
                if (mail.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Account does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    isPassEqualsWithUserPass(mail);
                }
            } else {
                isPassEqualsWithUserPass(user);
            }
        });
        return loginButton;
    }

    private void isPassEqualsWithUserPass(Optional<User> mail) {
        if (mail.isPresent()) {
            if (new String(passw.getPassword()).equals(mail.get().getPassw())) {
                Timestamp t = Timestamp.from(Instant.now());
                HistoryActions.actions.put(t, mail.get().getUsername() + " has been logged in");
                CurrentlyUser.currentlyId = mail.get().getId();
                Home home = new Home();
                PanelController.components.push(home);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Account does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JButton getResetButton() {
        JButton resetButton = new JButton("Reset");
        resetButton.setForeground(Color.CYAN);
        resetButton.setBackground(Color.darkGray);
        resetButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        resetButton.addActionListener(e -> {
            username.setText("");
            passw.setText("");
        });
        return resetButton;
    }

    private JLabel getPasswLabel() {
        JLabel passwLabel = new JLabel("Password: ");
        passwLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        passwLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        passwLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        return passwLabel;
    }

    private JLabel getUserLabel() {
        JLabel userLabel = new JLabel("Username/E-mail: ");
        userLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        userLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        return userLabel;
    }
}
