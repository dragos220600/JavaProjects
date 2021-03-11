package view;

import javax.swing.*;
import java.awt.*;

public class Start extends JFrame{

    public Start() {
        PanelController.components.push(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        JLabel label = getLabel();
        JButton loginButton = getLoginButton();
        JButton registerButton = getRegisterButton();
        JPanel initPanel = getInitPanel(loginButton, registerButton);
        JPanel startPanel = getStartPanel(label);

        add(startPanel);
        add(initPanel, BorderLayout.SOUTH);
    }

    private JPanel getStartPanel(JLabel label) {
        Image img = Toolkit.getDefaultToolkit().getImage("./poza.jpeg");
        JPanel startPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, this);
            }
        };
        startPanel.add(label, BorderLayout.CENTER);
        return startPanel;
    }

    private JLabel getLabel() {
        JLabel label = new JLabel();
        label.setText("Welcome to Airlines!");
        label.setFont(new Font("Great Vibes", Font.PLAIN, 50));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JPanel getInitPanel(JButton loginButton, JButton registerButton) {
        JPanel initPanel = new JPanel();
        initPanel.setLayout(new FlowLayout());
        initPanel.add(loginButton);
        initPanel.add(registerButton);
        initPanel.setBackground(new Color(0, 204, 173));
        return initPanel;
    }

    private JButton getRegisterButton() {
        JButton registerButton = new JButton("Register");
        registerButton.setForeground(Color.CYAN);
        registerButton.setBackground(Color.darkGray);
        registerButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        registerButton.addActionListener(e -> {
            Register reg = new Register();
            PanelController.components.push(reg);
            dispose();
        });
        return registerButton;
    }

    private JButton getLoginButton() {
        JButton loginButton = new JButton();
        loginButton.setText("Login");
        loginButton.setForeground(Color.CYAN);
        loginButton.setBackground(Color.darkGray);
        loginButton.setFont(new Font("MV Boli", Font.PLAIN, 12));
        loginButton.addActionListener(e -> {
            Login log = new Login();
            PanelController.components.push(log);
            dispose();
        });
        return loginButton;
    }
}
