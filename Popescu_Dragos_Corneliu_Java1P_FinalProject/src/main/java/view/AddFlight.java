package view;

import Controller.FlightController;
import Controller.UserController;
import model.Flight;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.regex.Pattern;

public class AddFlight extends JFrame {

    private final JTextField source;
    private final JTextField dest;
    private final JTextField departureTime;
    private final JTextField duration;
    private final JTextField price;

    public AddFlight() {
         setDefaultCloseOperation(EXIT_ON_CLOSE);
         setSize(500, 500);
         setTitle("Add flight");
         setLocationRelativeTo(null);
         setVisible(true);

         JLabel sourceLabel = getSourceLabel();
         source = new JTextField();

         JLabel destLabel = getDestLabel();
         dest = new JTextField();

         JLabel depTimeLabel = getDepTimeLabel();
         departureTime = new JTextField();

         JLabel durationLabel = getDurationLabel();
         duration = new JTextField();

         JLabel priceLabel = getPriceLabel();
         price = new JTextField();

         JLabel daysLabel = getDaysLabel();

         JPanel daysPanel = new JPanel();
         daysPanel.setLayout(new FlowLayout());
         JCheckBox luni = new JCheckBox("Monday", false);
         daysPanel.add(luni);
         JCheckBox marti = new JCheckBox("Tuesday", false);
         daysPanel.add(marti);
         JCheckBox miercuri = new JCheckBox("Wednesday", false);
         daysPanel.add(miercuri);
         JCheckBox joi = new JCheckBox("Thursday", false);
         daysPanel.add(joi);
         JCheckBox vineri = new JCheckBox("Friday", false);
         daysPanel.add(vineri);
         JCheckBox sambata = new JCheckBox("Saturday", false);
         daysPanel.add(sambata);
         JCheckBox duminica = new JCheckBox("Sunday", false);
         daysPanel.add(duminica);

         JPanel upperPanel = getUpperPanel(sourceLabel, destLabel, depTimeLabel, durationLabel, priceLabel, daysLabel, daysPanel);

         JButton cancelButton = getCancelButton();
         JButton resetButton = getResetButton(luni, marti, miercuri, joi, vineri, sambata, duminica);
         JButton adaugareButton = getAdaugareButton(luni, marti, miercuri, joi, vineri, sambata, duminica);

         JPanel lowerPanel = getLowerPanel(cancelButton, resetButton, adaugareButton);

         add(lowerPanel, BorderLayout.SOUTH);
         add(upperPanel, BorderLayout.CENTER);
     }

    private JPanel getLowerPanel(JButton cancelButton, JButton resetButton, JButton adaugareButton) {
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new FlowLayout());
        lowerPanel.setBackground(new Color(0, 0, 0, 255));
        lowerPanel.add(cancelButton);
        lowerPanel.add(resetButton);
        lowerPanel.add(adaugareButton);
        return lowerPanel;
    }

    private JButton getAdaugareButton(JCheckBox luni, JCheckBox marti, JCheckBox miercuri, JCheckBox joi, JCheckBox vineri, JCheckBox sambata, JCheckBox duminica) {
        JButton adaugareButton = new JButton("Add flight");
        adaugareButton.setForeground(Color.CYAN);
        adaugareButton.setBackground(Color.darkGray);
        adaugareButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        adaugareButton.addActionListener(e -> {
            boolean verificare = true;
            Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Pattern digitCasePatten = Pattern.compile("[0-9 ]");
            if (source.getText().length() < 3) {
                JOptionPane.showMessageDialog(null, "Source must contain 3 characters minimum", "Error", JOptionPane.ERROR_MESSAGE);
                verificare = false;
            }
            if (specialCharPatten.matcher(source.getText()).find() || source.getText().contains(" ")) {
                verificare = false;
                JOptionPane.showMessageDialog(null, "Invalid characters!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (digitCasePatten.matcher(source.getText()).find()) {
                verificare = false;
                JOptionPane.showMessageDialog(null, "Invalid characters!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            if (dest.getText().length() < 3 || dest.getText().equals(source.getText())) {
                JOptionPane.showMessageDialog(null, "Destination must contain 3 characters minimum", "Error", JOptionPane.ERROR_MESSAGE);
                verificare = false;
            } else {
                Optional<Flight> f = FlightController.getInstance().findBySrcAndDest(source.getText(), dest.getText());
                if (f.isPresent()) {
                    JOptionPane.showMessageDialog(null, "Has already exists this flight", "Error", JOptionPane.ERROR_MESSAGE);
                    verificare = false;
                }
            }
            if (specialCharPatten.matcher(dest.getText()).find() || dest.getText().contains(" ")) {
                verificare = false;
                JOptionPane.showMessageDialog(null, "Invalid characters!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (digitCasePatten.matcher(dest.getText()).find()) {
                verificare = false;
                JOptionPane.showMessageDialog(null, "Invalid characters!", "Error", JOptionPane.ERROR_MESSAGE);
            }

           if (!Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]").matcher(departureTime.getText()).matches()) {
               JOptionPane.showMessageDialog(null, "Hour format is incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
               verificare = false;
           }

           if (!Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]").matcher(duration.getText()).matches()) {
               JOptionPane.showMessageDialog(null, "Hour format is incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
               verificare = false;
           }

           if (!luni.isSelected() && !marti.isSelected() && !miercuri.isSelected()
                   && !joi.isSelected() && !vineri.isSelected() && !sambata.isSelected() && !duminica.isSelected()) {
               JOptionPane.showMessageDialog(null, "Must be selected 1 day minimum!", "Error", JOptionPane.ERROR_MESSAGE);
               verificare = false;
           }

           try {
               if (Integer.parseInt(price.getText()) <= 0) {
                   JOptionPane.showMessageDialog(null, "Price must be greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
                   verificare = false;
               }
           } catch (NumberFormatException er) {
               JOptionPane.showMessageDialog(null, "Invalid characters!", "Error", JOptionPane.ERROR_MESSAGE);
           }

           if (verificare) {
               String s = "";
               if (luni.isSelected()) {
                   s = s + luni.getText() + " ";
               }
               if (marti.isSelected()) {
                   s = s + marti.getText() + " ";
               }
               if (miercuri.isSelected()) {
                   s = s + miercuri.getText() + " ";
               }
               if (joi.isSelected()) {
                   s = s + joi.getText() + " ";
               }
               if (vineri.isSelected()) {
                   s = s + vineri.getText() + " ";
               }
               if (sambata.isSelected()) {
                   s = s + sambata.getText() + " ";
               }
               if (duminica.isSelected()) {
                   s = s + duminica.getText() + " ";
               }

               Flight f = new Flight(0, source.getText(), dest.getText(), departureTime.getText(), duration.getText(), s, Integer.parseInt(price.getText()));
               CurrentlyFlight.currentlyFlight = f;
               FlightController.getInstance().create(f);
               Optional<User> user = UserController.getInstance().findId(CurrentlyUser.currentlyId);
               if (user.isPresent()) {
                   Timestamp t = Timestamp.from(Instant.now());
                   HistoryActions.actions.put(t, user.get().getUsername() + " has added a flight");
               }
               Home h = new Home();
               PanelController.components.push(h);
               dispose();
           }
        });
        return adaugareButton;
    }

    private JButton getResetButton(JCheckBox luni, JCheckBox marti, JCheckBox miercuri, JCheckBox joi, JCheckBox vineri, JCheckBox sambata, JCheckBox duminica) {
        JButton resetButton = new JButton("Reset");
        resetButton.setForeground(Color.CYAN);
        resetButton.setBackground(Color.darkGray);
        resetButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        resetButton.addActionListener(e -> {
            source.setText("");
            dest.setText("");
            departureTime.setText("");
            duration.setText("");
            price.setText("");
            luni.setSelected(false);
            marti.setSelected(false);
            miercuri.setSelected(false);
            joi.setSelected(false);
            vineri.setSelected(false);
            sambata.setSelected(false);
            duminica.setSelected(false);
        });
        return resetButton;
    }

    private JButton getCancelButton() {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setForeground(Color.CYAN);
        cancelButton.setBackground(Color.darkGray);
        cancelButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        cancelButton.addActionListener(e -> {
            PanelController.components.pop();
            Component c = PanelController.components.pop();
            if (c instanceof Home) {
                Home h = new Home();
                PanelController.components.push(h);
                dispose();
            }
        });
        return cancelButton;
    }

    private JPanel getUpperPanel(JLabel sourceLabel, JLabel destLabel, JLabel depTimeLabel, JLabel durationLabel, JLabel priceLabel, JLabel daysLabel, JPanel daysPanel) {
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new GridLayout(12, 1));
        upperPanel.setBackground(new Color(46, 189, 189, 255));
        upperPanel.add(sourceLabel);
        upperPanel.add(source);
        upperPanel.add(destLabel);
        upperPanel.add(dest);
        upperPanel.add(depTimeLabel);
        upperPanel.add(departureTime);
        upperPanel.add(durationLabel);
        upperPanel.add(duration);
        upperPanel.add(priceLabel);
        upperPanel.add(price);
        upperPanel.add(daysLabel);
        upperPanel.add(daysPanel, 11);
        return upperPanel;
    }

    private JLabel getDaysLabel() {
        JLabel daysLabel = new JLabel("Days: ");
        daysLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        daysLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        return daysLabel;
    }

    private JLabel getPriceLabel() {
        JLabel priceLabel = new JLabel("Price: ");
        priceLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        priceLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        return priceLabel;
    }

    private JLabel getDurationLabel() {
        JLabel durationLabel = new JLabel("Duration: ");
        durationLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        durationLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        return durationLabel;
    }

    private JLabel getDepTimeLabel() {
        JLabel depTimeLabel = new JLabel("Departure Time: ");
        depTimeLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        depTimeLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        return depTimeLabel;
    }

    private JLabel getDestLabel() {
        JLabel destLabel = new JLabel("Destination: ");
        destLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        destLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        return destLabel;
    }

    private JLabel getSourceLabel() {
        JLabel sourceLabel = new JLabel("Source: ");
        sourceLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        sourceLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        return sourceLabel;
    }
}
