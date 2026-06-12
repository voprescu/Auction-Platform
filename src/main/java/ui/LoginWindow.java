package ui;

import service.UserService;
import model.User;

import javax.swing.*;

public class LoginWindow extends JFrame {
    private UserService userService = new UserService();

    public LoginWindow() {
        setTitle("Platforma Licitatii - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null); // layout manual

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setBounds(150, 20, 100, 30);
        panel.add(titleLabel);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 70, 100, 20);
        panel.add(userLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(50, 90, 300, 30);
        panel.add(usernameField);

        JLabel passLabel = new JLabel("Parola:");
        passLabel.setBounds(50, 130, 100, 20);
        panel.add(passLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(50, 150, 300, 30);
        panel.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(50, 200, 140, 40);
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                User user = userService.login(username, password);
                new MainWindow(user).setVisible(true);
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(loginBtn);

        JButton registerBtn = new JButton("Inregistrare");
        registerBtn.setBounds(210, 200, 140, 40);
        registerBtn.addActionListener(e -> {
            new RegisterWindow().setVisible(true);
            this.dispose();
        });
        panel.add(registerBtn);

        add(panel);
    }
}