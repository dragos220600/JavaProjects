package view;

import Controller.UserController;
import main.ClockPane;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

public class History extends JFrame {

    public History() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        Optional<User> user = UserController.getInstance().findId(CurrentlyUser.currentlyId);
        if (user.isPresent()) {
            Timestamp t = Timestamp.from(Instant.now());
            HistoryActions.actions.put(t, user.get().getUsername() + " has accessed History page!");
        }

        JButton homeButton = getHomeButton();
        JButton historyButton = getHistoryButton();
        JButton logoutButton = getLogoutButton();
        JButton accountButton = getAccountButton();
        JButton backButton = getBackButton();

        getJMenuBar(homeButton, historyButton, logoutButton, accountButton, backButton);

        JLabel dateLabel = getDateLabel();
        JLabel actionLabel = getActionLabel();

        JPanel panel = getPanel(dateLabel, actionLabel);

        ArrayList<Timestamp> keys = new ArrayList<>(HistoryActions.actions.keySet());
        for (int i = keys.size() - 1; i >= 0; i--) {
            JLabel label = new JLabel();
            label.setText(keys.get(i).toString());
            label.setFont(new Font("Arial", Font.BOLD, 16));
            panel.add(label);
            JLabel label1 = new JLabel();
            label1.setText(HistoryActions.actions.get(keys.get(i)));
            label1.setFont(new Font("Arial", Font.BOLD, 16));
            panel.add(label1);
        }

        add(panel, BorderLayout.PAGE_START);
    }

    private JPanel getPanel(JLabel dateLabel, JLabel actionLabel) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(dateLabel);
        panel.add(actionLabel);
        return panel;
    }

    private JLabel getActionLabel() {
        JLabel actionLabel = new JLabel();
        actionLabel.setText("Action");
        actionLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        actionLabel.setFont(new Font("Georgia", Font.BOLD, 16));
        return actionLabel;
    }

    private JLabel getDateLabel() {
        JLabel dateLabel = new JLabel();
        dateLabel.setText("Time");
        dateLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        dateLabel.setFont(new Font("Georgia", Font.BOLD, 16));
        return dateLabel;
    }

    private void getJMenuBar(JButton homeButton, JButton historyButton, JButton logoutButton, JButton accountButton, JButton backButton) {
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
            if (c instanceof Home) {
                new Home();
                dispose();
            }
            if (c instanceof MyAccount) {
                new MyAccount();
                dispose();
            }
        });
        return backButton;
    }

    private JButton getAccountButton() {
        JButton accountButton = new JButton("MY ACCOUNT");
        accountButton.setForeground(Color.CYAN);
        accountButton.setBackground(Color.darkGray);
        accountButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        accountButton.addActionListener(e -> {
            MyAccount acc = new MyAccount();
            PanelController.components.push(acc);
            dispose();
        });
        return accountButton;
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
                HistoryActions.actions.put(t, user1.get().getUsername() + " has been logged out!");
            }
            Login log = new Login();
            PanelController.components.push(log);
            dispose();
        });
        return logoutButton;
    }

    private JButton getHistoryButton() {
        JButton historyButton = new JButton("HISTORY");
        historyButton.setForeground(Color.CYAN);
        historyButton.setBackground(Color.darkGray);
        historyButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
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
