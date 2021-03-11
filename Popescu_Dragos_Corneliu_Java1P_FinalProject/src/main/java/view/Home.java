package view;

import Controller.FlightController;
import Controller.UserController;
import main.ClockPane;
import model.Flight;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Home extends JFrame {

    JPanel panel;

    public Home() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        Optional<User> user = UserController.getInstance().findId(CurrentlyUser.currentlyId);
        if (user.isPresent()) {
            Timestamp t = Timestamp.from(Instant.now());
            HistoryActions.actions.put(t, user.get().getUsername() + " has accessed Home page");
        }

        JButton addButton = getAddButton();
        JButton clearAllButton = getClearAllButton();

        JPanel lowerPanel = getLowerPanel(addButton, clearAllButton);

        JButton homeButton = getHomeButton();
        JButton historyButton = getHistoryButton();
        JButton accountButton = getAccountButton();
        JButton logoutButton = getLogoutButton();
        JButton backButton = getBackButton();

        getJMenuBar(homeButton, historyButton, accountButton, logoutButton, backButton);

        JLabel newLabel = new JLabel();
        JLabel sourceLabel = getSourceLabel();
        JLabel destinationLabel = getDestinationLabel();
        JLabel departureLabel = getDepartureLabel();
        JLabel arriveLabel = getArriveLabel();
        JLabel daysLabel = getDaysLabel();
        JLabel priceLabel = getPriceLabel();

        getPanel(newLabel, sourceLabel, destinationLabel, departureLabel, arriveLabel, daysLabel, priceLabel);

        List<Flight> flights = FlightController.getInstance().select();
        for (Flight f : flights) {
            panel.add(f.getButton());
            JLabel label = new JLabel();
            label.setText(f.getSursa());
            panel.add(label);
            JLabel label1 = new JLabel();
            label1.setText(f.getDest());
            panel.add(label1);
            JLabel label2 = new JLabel();
            label2.setText(f.getOraPlecarii());
            panel.add(label2);
            JLabel label3 = new JLabel();
            label3.setText(getOra(f.getOraPlecarii(), f.getDurata()));
            panel.add(label3);
            JLabel label4 = new JLabel();
            label4.setText(f.getZile());
            panel.add(label4);
            JLabel label5 = new JLabel();
            label5.setText(String.valueOf(f.getPret()));
            panel.add(label5);
        }

        add(panel, BorderLayout.PAGE_START);
        add(lowerPanel, BorderLayout.SOUTH);
    }

    private void getPanel(JLabel newLabel, JLabel sourceLabel, JLabel destinationLabel, JLabel departureLabel, JLabel arriveLabel, JLabel daysLabel, JLabel priceLabel) {
        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 7));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(newLabel);
        panel.add(sourceLabel);
        panel.add(destinationLabel);
        panel.add(departureLabel);
        panel.add(arriveLabel);
        panel.add(daysLabel);
        panel.add(priceLabel);
    }

    private JLabel getPriceLabel() {
        JLabel priceLabel = new JLabel("Price");
        priceLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        priceLabel.setFont(new Font("Georgia", Font.BOLD, 16));
        return priceLabel;
    }

    private JLabel getDaysLabel() {
        JLabel daysLabel = new JLabel("Days");
        daysLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        daysLabel.setFont(new Font("Georgia", Font.BOLD, 16));
        return daysLabel;
    }

    private JLabel getArriveLabel() {
        JLabel arriveLabel = new JLabel("Arrival Time");
        arriveLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        arriveLabel.setFont(new Font("Georgia", Font.BOLD, 16));
        return arriveLabel;
    }

    private JLabel getDepartureLabel() {
        JLabel departureLabel = new JLabel("Departure Time");
        departureLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        departureLabel.setFont(new Font("Georgia", Font.BOLD, 16));
        return departureLabel;
    }

    private JLabel getDestinationLabel() {
        JLabel destinationLabel = new JLabel("Destination");
        destinationLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        destinationLabel.setFont(new Font("Georgia", Font.BOLD, 16));
        return destinationLabel;
    }

    private JLabel getSourceLabel() {
        JLabel sourceLabel = new JLabel("Source");
        sourceLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        sourceLabel.setFont(new Font("Georgia", Font.BOLD, 16));
        return sourceLabel;
    }

    private JPanel getLowerPanel(JButton addButton, JButton clearAllButton) {
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new FlowLayout());
        lowerPanel.add(addButton,BorderLayout.CENTER);
        lowerPanel.add(clearAllButton,BorderLayout.CENTER);
        return lowerPanel;
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
            if (c instanceof MyAccount) {
                new MyAccount();
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
        accountButton.addActionListener(e -> {
            MyAccount acc = new MyAccount();
            PanelController.components.push(acc);
            dispose();
        });
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
        return homeButton;
    }

    private JButton getClearAllButton() {
        JButton clearAllButton = new JButton("CLEAR ALL");
        clearAllButton.setForeground(Color.CYAN);
        clearAllButton.setBackground(Color.darkGray);
        clearAllButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        clearAllButton.addActionListener(e -> {
            int i;
            for (i = PanelController.components.size() - 1; i >= 0; i--) {
                if (PanelController.components.get(i) instanceof Home) {
                    ((Home) PanelController.components.get(i)).dispose();
                }
            }
            FlightController.getInstance().removeAll();
            Optional<User> user1 = UserController.getInstance().findId(CurrentlyUser.currentlyId);
            if (user1.isPresent()) {
                Timestamp t = Timestamp.from(Instant.now());
                HistoryActions.actions.put(t, user1.get().getUsername() + " has deleted all flights");
            }
            Home h = new Home();
            PanelController.components.push(h);
        });
        return clearAllButton;
    }

    private JButton getAddButton() {
        JButton addButton = new JButton("ADD FLIGHT");
        addButton.setForeground(Color.CYAN);
        addButton.setBackground(Color.darkGray);
        addButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        addButton.addActionListener(e -> {
            AddFlight x = new AddFlight();
            PanelController.components.push(x);
            dispose();
        });
        return addButton;
    }

    public String getOra(String oraP, String durata) {
        try {
            StringBuilder ora = new StringBuilder();
            StringBuilder minut = new StringBuilder();
            SimpleDateFormat df = new SimpleDateFormat("hh:mm");
            Date date = df.parse(oraP);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int index = durata.indexOf(":");
            for (int i = 0; i < index; i++) {
                ora.append(durata.charAt(i));
            }
            for (int i = index + 1; i < durata.length(); i++) {
                minut.append(durata.charAt(i));
            }
            cal.add(Calendar.HOUR, Integer.parseInt(ora.toString()));
            cal.add(Calendar.MINUTE, Integer.parseInt(minut.toString()));
            int h = cal.get(Calendar.HOUR_OF_DAY);
            int m = cal.get(Calendar.MINUTE);
            return h + ":" + m;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
