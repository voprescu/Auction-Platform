package ui;

import service.AuctionService;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class CreateAuctionWindow extends JFrame {
    private AuctionService auctionService = new AuctionService();
    private User currentUser;

    public CreateAuctionWindow(User user) {
        this.currentUser = user;

        setTitle("Creează Licitație");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        panel.add(new JLabel("Titlu:"));
        JTextField titleField = new JTextField();
        titleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(titleField);
        panel.add(Box.createVerticalStrut(10));

        // Description
        panel.add(new JLabel("Descriere:"));
        JTextArea descField = new JTextArea(5, 30);
        descField.setLineWrap(true);
        JScrollPane descScroll = new JScrollPane(descField);
        panel.add(descScroll);
        panel.add(Box.createVerticalStrut(10));

        // Starting Price
        panel.add(new JLabel("Preț de start:"));
        JTextField priceField = new JTextField();
        priceField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(priceField);
        panel.add(Box.createVerticalStrut(10));

        // Days
        panel.add(new JLabel("Durată (zile):"));
        JSpinner daysSpinner = new JSpinner(new SpinnerNumberModel(7, 1, 365, 1));
        panel.add(daysSpinner);
        panel.add(Box.createVerticalStrut(20));

        // Butoane
        JPanel buttonPanel = new JPanel();
        JButton createBtn = new JButton("Creează");
        createBtn.addActionListener(e -> {
            try {
                String title = titleField.getText();
                String description = descField.getText();
                double price = Double.parseDouble(priceField.getText());
                int days = (Integer) daysSpinner.getValue();

                LocalDateTime endTime = LocalDateTime.now().plusDays(days);
                auctionService.createAuction(title, description, price, endTime, currentUser.getId());

                JOptionPane.showMessageDialog(this, "Licitație creată cu succes!");
                new MainWindow(currentUser).setVisible(true);
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preț invalid!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        JButton backBtn = new JButton("Înapoi");
        backBtn.addActionListener(e -> {
            new MainWindow(currentUser).setVisible(true);
            this.dispose();
        });

        buttonPanel.add(createBtn);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(backBtn);
        panel.add(buttonPanel);

        add(panel);
    }
}