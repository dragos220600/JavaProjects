package view;

import Controller.FlightController;
import Controller.UserController;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

public class DeleteConfirmation extends JFrame {

    public DeleteConfirmation() {
        setLocationRelativeTo(null);
        setSize(400, 150);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel textLabel = getTextLabel();

        JButton noButton = getNoButton();
        JButton yesButton = getYesButton();

        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new FlowLayout());
        lowerPanel.add(noButton);
        lowerPanel.add(yesButton);

        add(lowerPanel, BorderLayout.SOUTH);
        add(textLabel, BorderLayout.CENTER);
    }

    private JButton getYesButton() {
        JButton yesButton = new JButton("Yes");
        yesButton.setForeground(Color.CYAN);
        yesButton.setBackground(Color.darkGray);
        yesButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        yesButton.addActionListener(e -> {
            int i;
            for (i = PanelController.components.size() - 1; i >= 0; i--) {
                if (PanelController.components.get(i) instanceof Home) {
                    ((Home) PanelController.components.get(i)).dispose();
                }
            }
            FlightController.getInstance().remove(CurrentlyFlightId.id);
            Optional<User> user = UserController.getInstance().findId(CurrentlyUser.currentlyId);
            if (user.isPresent()) {
                Timestamp t = Timestamp.from(Instant.now());
                HistoryActions.actions.put(t, user.get().getUsername() + " has deleted a flight");
            }
            Home h = new Home();
            PanelController.components.push(h);
        });
        return yesButton;
    }

    private JButton getNoButton() {
        JButton noButton = new JButton("No");
        noButton.setForeground(Color.CYAN);
        noButton.setBackground(Color.darkGray);
        noButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        noButton.addActionListener(e -> dispose());
        return noButton;
    }

    private JLabel getTextLabel() {
        JLabel textLabel = new JLabel();
        textLabel.setText("Are you sure you want to delete the flight?");
        textLabel.setBorder(new EmptyBorder(0, 60, 0, 0));
        return textLabel;
    }
}
