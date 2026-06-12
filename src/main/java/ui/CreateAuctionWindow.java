package ui;

import service.AuctionService;
import model.User;

import javax.swing.*;
import java.time.LocalDateTime;

public class CreateAuctionWindow extends JFrame {
    private AuctionService auctionService = new AuctionService();
    private User currentUser;

    public CreateAuctionWindow(User user) {
        this.currentUser = user;

        setTitle("Creează Licitatie");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLbl = new JLabel("Titlu:");
        titleLbl.setBounds(50, 30, 100, 20);
        panel.add(titleLbl);

        JTextField titleField = new JTextField();
        titleField.setBounds(50, 50, 400, 30);
        panel.add(titleField);

        JLabel descLbl = new JLabel("Descriere:");
        descLbl.setBounds(50, 90, 100, 20);
        panel.add(descLbl);

        JTextArea descField = new JTextArea();
        descField.setLineWrap(true);
        descField.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descField);
        descScroll.setBounds(50, 110, 400, 100);
        panel.add(descScroll);

        JLabel priceLbl = new JLabel("Pret de start:");
        priceLbl.setBounds(50, 220, 100, 20);
        panel.add(priceLbl);

        JTextField priceField = new JTextField();
        priceField.setBounds(50, 240, 400, 30);
        panel.add(priceField);

        JLabel daysLbl = new JLabel("Durata (zile):");
        daysLbl.setBounds(50, 280, 100, 20);
        panel.add(daysLbl);

        JSpinner daysSpinner = new JSpinner(new SpinnerNumberModel(7, 1, 365, 1));
        daysSpinner.setBounds(50, 300, 100, 30);
        panel.add(daysSpinner);

        JButton createBtn = new JButton("Creeaza");
        createBtn.setBounds(150, 360, 100, 40);
        createBtn.addActionListener(e -> {
            try {
                String title = titleField.getText();
                String description = descField.getText();
                double price = Double.parseDouble(priceField.getText());
                int days = (Integer) daysSpinner.getValue();

                LocalDateTime endTime = LocalDateTime.now().plusDays(days);
                auctionService.createAuction(title, description, price, endTime, currentUser.getId());

                JOptionPane.showMessageDialog(this, "Licitatie creată cu succes!");
                new MainWindow(currentUser).setVisible(true);
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Pret invalid!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
        panel.add(createBtn);

        JButton backBtn = new JButton("Inapoi");
        backBtn.setBounds(270, 360, 100, 40);
        backBtn.addActionListener(e -> {
            new MainWindow(currentUser).setVisible(true);
            this.dispose();
        });
        panel.add(backBtn);

        add(panel);
    }
}