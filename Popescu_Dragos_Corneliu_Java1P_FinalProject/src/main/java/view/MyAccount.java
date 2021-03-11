package view;

import Controller.UserController;
import main.ClockPane;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.regex.Pattern;

public class MyAccount extends JFrame {

    private final JTextField newUsername;
    private final JTextField newEmail;

    public MyAccount() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 300);
        setTitle("Account");
        setLocationRelativeTo(null);
        setVisible(true);

        Optional<User> user = UserController.getInstance().findId(CurrentlyUser.currentlyId);
        if (user.isPresent()) {
            Timestamp t = Timestamp.from(Instant.now());
            HistoryActions.actions.put(t, user.get().getUsername() + " has accessed MyAccount page");
        }

        JButton homeButton = getHomeButton();
        JButton historyButton = getHistoryButton();
        JButton accountButton = getAccountButton();
        JButton logoutButton = getLogoutButton();
        JButton backButton = getBackButton();

        getJMenuBar(homeButton, historyButton, accountButton, logoutButton, backButton);

        JLabel datesLabel = getDatesLabel();

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new GridLayout(2, 2));

        JLabel newUserLabel = getNewUserLabel();
        upperPanel.add(newUserLabel);

        newUsername = new JTextField();
        upperPanel.add(newUsername);

        JButton changeUserButton = getChangeUserButton();
        upperPanel.add(changeUserButton);

        JLabel newMailLabel = getNewMailLabel();
        upperPanel.add(newMailLabel);

        newEmail = new JTextField();
        upperPanel.add(newEmail);

        JButton changeEmailButton = getChangeEmailButton();
        upperPanel.add(changeEmailButton);

        JButton changePasswButton = getChangePasswButton();
        JPanel lowerPanel = new JPanel();
        lowerPanel.add(changePasswButton, BorderLayout.CENTER);

        add(datesLabel, BorderLayout.NORTH);
        add(upperPanel, BorderLayout.CENTER);
        add(lowerPanel, BorderLayout.SOUTH);
    }

    private JButton getChangePasswButton() {
        JButton changePasswButton = new JButton("Change password");
        changePasswButton.setForeground(Color.CYAN);
        changePasswButton.setBackground(Color.darkGray);
        changePasswButton.setFont(new Font("MV Boli", Font.PLAIN, 15));
        changePasswButton.addActionListener(e -> {
            new ChangePassw();
            dispose();
        });
        return changePasswButton;
    }

    private JButton getChangeEmailButton() {
        JButton changeEmailButton = new JButton("Change e-mail");
        changeEmailButton.setForeground(Color.CYAN);
        changeEmailButton.setBackground(Color.darkGray);
        changeEmailButton.setFont(new Font("MV Boli", Font.PLAIN, 15));
        changeEmailButton.addActionListener(e -> {
            boolean verificare;
            verificare = isVerificare(true, newEmail);
            if (verificare) {
               if (UserController.getInstance().findMail(newEmail.getText()).isPresent()) {
                   JOptionPane.showMessageDialog(null, "E-mail has already used!", "Error", JOptionPane.ERROR_MESSAGE);
               } else {
                   Optional<User> usr = UserController.getInstance().findId(CurrentlyUser.currentlyId);
                   if (usr.isPresent()) {
                       boolean b = UserController.getInstance().updateMail(usr.get().getEmail(), newEmail.getText());
                       if (b) {
                           Optional<User> user3 = UserController.getInstance().findId(CurrentlyUser.currentlyId);
                           if (user3.isPresent()) {
                               Timestamp t = Timestamp.from(Instant.now());
                               HistoryActions.actions.put(t, user3.get().getUsername() + " has changed the e-mail");
                           }
                           JOptionPane.showMessageDialog(null, "Change complete!", "Information", JOptionPane.INFORMATION_MESSAGE);
                           Login log = new Login();
                           PanelController.components.push(log);
                           dispose();
                       }
                   }
               }
            }
        });
        return changeEmailButton;
    }

    static boolean isVerificare(boolean verificare, JTextField newEmail) {
        String mail = newEmail.getText();
        int i, poz = 0;
        if (mail.length() == 0) {
            verificare = false;
            JOptionPane.showMessageDialog(null, "All fields must be completed!");
        }
        if (!mail.contains("@")) {
            verificare = false;
            JOptionPane.showMessageDialog(null, "Invalid e-mail!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        StringBuilder verif = new StringBuilder();
        for (i = 0; i < mail.length(); i++) {
            if (mail.charAt(i) == '@') {
                poz = i;
                break;
            }
            verif.append(mail.charAt(i));
        }
        if (verif.toString().equals("")) {
            verificare = false;
            JOptionPane.showMessageDialog(null, "Invalid e-mail!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        verif = new StringBuilder();
        for (i = poz + 1; i < mail.length(); i++) {
            if (mail.charAt(i) == '.') {
                break;
            }
            verif.append(mail.charAt(i));
        }
        if (verif.toString().equals("")) {
            verificare = false;
            JOptionPane.showMessageDialog(null, "Invalid e-mail!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return verificare;
    }

    private JLabel getNewMailLabel() {
        JLabel newMailLabel = new JLabel("New e-mail: ");
        newMailLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        newMailLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        return newMailLabel;
    }

    private JButton getChangeUserButton() {
        JButton changeUserButton = new JButton("Change username");
        changeUserButton.setForeground(Color.CYAN);
        changeUserButton.setBackground(Color.darkGray);
        changeUserButton.setFont(new Font("MV Boli", Font.PLAIN, 15));
        changeUserButton.addActionListener(e -> {
            int verificare = 1;
            Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            String text = newUsername.getText();
            if (text.length() == 0) {
                verificare = 0;
                JOptionPane.showMessageDialog(null, "All fields must be completed!");
            }
            if (specialCharPatten.matcher(text).find() || text.contains(" ")) {
                verificare = 0;
                JOptionPane.showMessageDialog(null, "Invalid characters!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            if (verificare == 1) {
                Optional<User> usr = UserController.getInstance().findId(CurrentlyUser.currentlyId);
                if (usr.isPresent()) {
                    boolean b = UserController.getInstance().updateUser(usr.get().getUsername(), text);
                    if (b) {
                        Optional<User> user2 = UserController.getInstance().findId(CurrentlyUser.currentlyId);
                        if (user2.isPresent()) {
                            Timestamp t = Timestamp.from(Instant.now());
                            HistoryActions.actions.put(t, user2.get().getUsername() + " has changed the username");
                        }
                        JOptionPane.showMessageDialog(null, "Change complete!", "Information", JOptionPane.INFORMATION_MESSAGE);
                        Login log = new Login();
                        PanelController.components.push(log);
                        dispose();
                    }
                }
            }
        });
        return changeUserButton;
    }

    private JLabel getNewUserLabel() {
        JLabel newUserLabel = new JLabel("New username: ");
        newUserLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        newUserLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        return newUserLabel;
    }

    private JLabel getDatesLabel() {
        JLabel datesLabel = new JLabel();
        String s = "";
        Optional<User> u = UserController.getInstance().findId(CurrentlyUser.currentlyId);
        if (u.isPresent()) {
            s = s + "Hello, " + u.get().getUsername() + "! <" + u.get().getEmail() + ">";
        }
        datesLabel.setText(s);
        return datesLabel;
    }

    private void getJMenuBar(JButton homeButton, JButton historyButton, JButton accountButton, JButton logoutButton, JButton backButton) {
        JMenuBar menuBar = new JMenuBar();
        add(menuBar, BorderLayout.BEFORE_FIRST_LINE);
        setJMenuBar(menuBar);
        menuBar.add(backButton);
        menuBar.add(homeButton);
        menuBar.add(historyButton);
        menuBar.add(accountButton);
        menuBar.add(logoutButton);
        menuBar.add(new ClockPane(), BorderLayout.EAST);
    }

    private JButton getBackButton() {
        Image img = new ImageIcon("./3.png").getImage().getScaledInstance(25,15, 20);
        JButton backButton = new JButton(new ImageIcon(img));
        backButton.setBackground(Color.BLACK);
        backButton.addActionListener(e -> {
            PanelController.components.remove(PanelController.components.size() - 1);
            Component c = PanelController.components.peek();
            if (c instanceof History) {
                new History();
                dispose();
            }
            if (c instanceof Home) {
                new Home();
                dispose();
            }
        });
        return backButton;
    }

    private JButton getLogoutButton() {
        JButton logoutButton = new JButton("LOGOUT");
        logoutButton.setForeground(Color.CYAN);
        logoutButton.setBackground(Color.darkGray);
        logoutButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        logoutButton.addActionListener(e -> {
            Optional<User> user1 = UserController.getInstance().findId(CurrentlyUser.currentlyId);
            if (user1.isPresent()) {
                System.out.println("logout");
                Timestamp t = Timestamp.from(Instant.now());
                HistoryActions.actions.put(t, user1.get().getUsername() + " has been logged out");
            }
            Login log = new Login();
            PanelController.components.push(log);
            dispose();
        });
        return logoutButton;
    }

    private JButton getAccountButton() {
        JButton accountButton = new JButton("MY ACCOUNT");
        accountButton.setForeground(Color.CYAN);
        accountButton.setBackground(Color.darkGray);
        accountButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        return accountButton;
    }

    private JButton getHistoryButton() {
        JButton historyButton = new JButton("HISTORY");
        historyButton.setForeground(Color.CYAN);
        historyButton.setBackground(Color.darkGray);
        historyButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        historyButton.addActionListener(e -> {
            History h = new History();
            PanelController.components.push(h);
            dispose();
        });
        return historyButton;
    }

    private JButton getHomeButton() {
        JButton homeButton = new JButton("HOME");
        homeButton.setForeground(Color.CYAN);
        homeButton.setBackground(Color.darkGray);
        homeButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        homeButton.addActionListener(e -> {
            Home h = new Home();
            PanelController.components.push(h);
            dispose();
        });
        return homeButton;
    }
}
