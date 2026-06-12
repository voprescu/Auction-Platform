package ui;

import service.UserService;
import model.User;

import javax.swing.*;

public class RegisterWindow extends JFrame {
    private UserService userService = new UserService();

    public RegisterWindow() {
        setTitle("Auction Platform - Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Register");
        titleLabel.setBounds(130, 20, 150, 30);
        panel.add(titleLabel);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 70, 100, 20);
        panel.add(userLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(50, 90, 300, 30);
        panel.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 190, 100, 20);
        panel.add(passLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(50, 210, 300, 30);
        panel.add(passwordField);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(50, 270, 140, 40);
        registerBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                User user = userService.register(username, password);
                JOptionPane.showMessageDialog(this, "Registration successful! Welcome, " + user.getUsername());
                new LoginWindow().setVisible(true);
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(registerBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(210, 270, 140, 40);
        backBtn.addActionListener(e -> {
            new LoginWindow().setVisible(true);
            this.dispose();
        });
        panel.add(backBtn);

        add(panel);
    }
}