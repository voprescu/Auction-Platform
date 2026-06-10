package ui;

import service.AuctionService;
import model.User;
import model.Auction;

import javax.swing.*;
import java.util.List;

public class MainWindow extends JFrame {
    private User currentUser;
    private AuctionService auctionService = new AuctionService();

    public MainWindow(User user) {
        this.currentUser = user;

        setTitle("Platforma Licitații - Bun venit, " + user.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Butoane top
        JButton createAuctionBtn = new JButton("Creează Licitație");
        createAuctionBtn.setBounds(20, 20, 150, 40);
        createAuctionBtn.addActionListener(e -> {
            new CreateAuctionWindow(currentUser).setVisible(true);
            this.dispose();
        });
        panel.add(createAuctionBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(700, 20, 80, 40);
        logoutBtn.addActionListener(e -> {
            new LoginWindow().setVisible(true);
            this.dispose();
        });
        panel.add(logoutBtn);

        // Lista licitații
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> auctionList = new JList<>(listModel);

        try {
            List<Auction> auctions = auctionService.getAllActiveAuctions();
            for (Auction auction : auctions) {
                listModel.addElement(auction.getTitle() + " - " + auction.getCurrentPrice() + " lei");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Eroare: " + e.getMessage());
        }

        JScrollPane scrollPane = new JScrollPane(auctionList);
        scrollPane.setBounds(20, 70, 760, 450);
        panel.add(scrollPane);

        // Button detalii
        JButton viewDetailsBtn = new JButton("Vezi Detalii");
        viewDetailsBtn.setBounds(320, 530, 150, 40);
        viewDetailsBtn.addActionListener(e -> {
            int index = auctionList.getSelectedIndex();
            if (index >= 0) {
                try {
                    List<Auction> auctions = auctionService.getAllActiveAuctions();
                    new AuctionDetailsWindow(auctions.get(index), currentUser).setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Eroare: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selectează o licitație!");
            }
        });
        panel.add(viewDetailsBtn);

        add(panel);
    }
}