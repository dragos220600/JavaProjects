package view;

import Controller.UserController;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.regex.Pattern;

public class Register extends JFrame {

    private final JTextField username;
    private final JPasswordField passw;
    private final JPasswordField passwConf;
    private final JTextField email;

    public Register() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 200);
        setTitle("Register Page");
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel lowerPanel = getLowerPanel();
        JPanel upperPanel = getUpperPanel();

        JLabel userLabel = getUserLabel();
        upperPanel.add(userLabel);

        username = new JTextField();
        upperPanel.add(username);

        JLabel passwLabel = getPasswordLabel();
        upperPanel.add(passwLabel);

        passw = new JPasswordField();
        upperPanel.add(passw);

        JLabel passwConfLabel = getConfPasswordLabel();
        upperPanel.add(passwConfLabel);

        passwConf = new JPasswordField();
        upperPanel.add(passwConf);

        JLabel emailLabel = getEmailLabel();
        upperPanel.add(emailLabel);

        email = new JTextField();
        upperPanel.add(email);

        JButton resetButton = getResetButton();
        JButton registerButton = getRegisterButton();
        JButton backButton = getBackButton();

        lowerPanel.add(resetButton);
        lowerPanel.add(registerButton);

        JMenuBar menuBar = getMenuBar(backButton);

        add(menuBar, BorderLayout.NORTH);
        add(lowerPanel, BorderLayout.SOUTH);
        add(upperPanel, BorderLayout.CENTER);
    }

    private JMenuBar getMenuBar(JButton backButton) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(backButton, BorderLayout.WEST);
        menuBar.setBackground(Color.BLACK);
        return menuBar;
    }

    private JButton getBackButton() {
        Image img = new ImageIcon("./3.png").getImage().getScaledInstance(25,15, 20);
        ImageIcon backIcon = new ImageIcon(img);
        JButton backButton = new JButton(backIcon);
        backButton.setBackground(Color.BLACK);
        backButton.addActionListener(e -> {
            PanelController.components.pop();
            Component c = PanelController.components.pop();
            if (c instanceof Start) {
                Start s = new Start();
                PanelController.components.push(s);
            }
            if (c instanceof Login) {
                Login log = new Login();
                PanelController.components.push(log);
            }
            dispose();
        });
        return backButton;
    }

    private JButton getRegisterButton() {
        JButton registerButton = new JButton("Register");
        registerButton.setForeground(Color.CYAN);
        registerButton.setBackground(Color.darkGray);
        registerButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        registerButton.addActionListener(e -> {
            boolean verificare = true;
            Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            String s = username.getText();
            if (s.length() == 0) {
                verificare = false;
                JOptionPane.showMessageDialog(null, "All fields must be completed!");
            }
            if (specialCharPatten.matcher(s).find() || s.contains(" ")) {
                verificare = false;
                JOptionPane.showMessageDialog(null, "Invalid characters!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            char[] litere = passw.getPassword();
            String s1 = new String(litere);
            Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
            Pattern lowerCasePatten = Pattern.compile("[a-z ]");
            Pattern digitCasePatten = Pattern.compile("[0-9 ]");
            if (s1.length() == 0) {
                verificare = false;
                JOptionPane.showMessageDialog(null, "All fields must be completed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (s1.length() < 6) {
                verificare = false;
                JOptionPane.showMessageDialog(null, "Password must contain 6 characters minimum!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (specialCharPatten.matcher(s1).find()) {
                verificare = false;
                JOptionPane.showMessageDialog(null, "Invalid characters!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (!UpperCasePatten.matcher(s1).find() || !lowerCasePatten.matcher(s1).find()
                    || !digitCasePatten.matcher(s1).find()) {
                verificare = false;
                JOptionPane.showMessageDialog(null, "Password must contain one lowercase character, one uppercase character and a number!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            String s2 = String.valueOf(passwConf.getPassword());
            String s3 = String.valueOf(passw.getPassword());
            if (!s2.equals(s3)) {
                verificare = false;
                JOptionPane.showMessageDialog(null, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            verificare = MyAccount.isVerificare(verificare, email);
            if (verificare) {
                User user = new User(0, username.getText(), email.getText(), s1);
                if (UserController.getInstance().create(user)) {
                    Timestamp t = Timestamp.from(Instant.now());
                    HistoryActions.actions.put(t, username.getText() + " has been registered");
                    Login log = new Login();
                    PanelController.components.push(log);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Username or e-mail has been already used!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return registerButton;
    }

    private JButton getResetButton() {
        JButton resetButton = new JButton("Reset");
        resetButton.setForeground(Color.CYAN);
        resetButton.setBackground(Color.darkGray);
        resetButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        resetButton.addActionListener(e -> {
            username.setText("");
            passw.setText("");
            passwConf.setText("");
            email.setText("");
        });
        return resetButton;
    }

    private JLabel getEmailLabel() {
        JLabel emailLabel = new JLabel("E-mail: ");
        emailLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        emailLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        emailLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        return emailLabel;
    }

    private JLabel getConfPasswordLabel() {
        JLabel passwConfLabel = new JLabel("Confirm password: ");
        passwConfLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        passwConfLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        passwConfLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        return passwConfLabel;
    }

    private JLabel getPasswordLabel() {
        JLabel passwLabel = new JLabel("Password: ");
        passwLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        passwLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        passwLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        return passwLabel;
    }

    private JLabel getUserLabel() {
        JLabel userLabel = new JLabel("Username: ");
        userLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        userLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        return userLabel;
    }

    private JPanel getUpperPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new GridLayout(4, 1));
        upperPanel.setBackground(new Color(46, 189, 189, 255));
        return upperPanel;
    }

    private JPanel getLowerPanel() {
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new FlowLayout());
        lowerPanel.setBackground(new Color(0, 0, 0, 255));
        return lowerPanel;
    }
}
