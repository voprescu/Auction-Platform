package ui;

import service.UserService;
import model.User;

import javax.swing.*;

public class RegisterWindow extends JFrame {
    private UserService userService = new UserService();

    public RegisterWindow() {
        setTitle("Platforma Licitații - Înregistrare");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("Înregistrare");
        titleLabel.setBounds(130, 20, 150, 30);
        panel.add(titleLabel);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 70, 100, 20);
        panel.add(userLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(50, 90, 300, 30);
        panel.add(usernameField);


        // Password
        JLabel passLabel = new JLabel("Parola:");
        passLabel.setBounds(50, 190, 100, 20);
        panel.add(passLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(50, 210, 300, 30);
        panel.add(passwordField);

        // Register button
        JButton registerBtn = new JButton("Înregistrare");
        registerBtn.setBounds(50, 270, 140, 40);
        registerBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                User user = userService.register(username, password);
                JOptionPane.showMessageDialog(this, "Înregistrare reușită! Bun venit, " + user.getUsername());
                new LoginWindow().setVisible(true);
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(registerBtn);

        JButton backBtn = new JButton("Înapoi");
        backBtn.setBounds(210, 270, 140, 40);
        backBtn.addActionListener(e -> {
            new LoginWindow().setVisible(true);
            this.dispose();
        });
        panel.add(backBtn);

        add(panel);
    }
}