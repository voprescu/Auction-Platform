package ui;

import service.AuctionService;
import model.Auction;
import model.User;
import model.Bid;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AuctionDetailsWindow extends JFrame {
    private AuctionService auctionService = new AuctionService();
    private Auction auction;
    private User currentUser;

    public AuctionDetailsWindow(Auction auction, User user) {
        this.auction = auction;
        this.currentUser = user;

        setTitle("Detalii Licitație: " + auction.getTitle());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Titlu
        JLabel titleLabel = new JLabel(auction.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));

        // Info
        panel.add(new JLabel("Descriere: " + auction.getDescription()));
        panel.add(new JLabel("Preț curent: " + auction.getCurrentPrice() + " lei"));
        panel.add(new JLabel("Preț de start: " + auction.getStartingPrice() + " lei"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        panel.add(new JLabel("Expirare: " + auction.getEndTime().format(formatter)));
        panel.add(Box.createVerticalStrut(20));

        // Istoric oferte
        panel.add(new JLabel("Oferte:"));
        DefaultListModel<String> bidsModel = new DefaultListModel<>();
        JList<String> bidsList = new JList<>(bidsModel);

        try {
            List<Bid> bids = auctionService.getAuctionBids(auction.getId());
            for (Bid bid : bids) {
                bidsModel.addElement("User " + bid.getUserId() + ": " + bid.getAmount() + " lei");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Eroare: " + e.getMessage());
        }

        panel.add(new JScrollPane(bidsList));
        panel.add(Box.createVerticalStrut(20));

        // Plasare oferta
        panel.add(new JLabel("Plaseaza oferta:"));
        JTextField ofertaField = new JTextField();
        ofertaField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(ofertaField);
        panel.add(Box.createVerticalStrut(10));

        JButton bidBtn = new JButton("Plaseaza Oferta");
        bidBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        bidBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(ofertaField.getText());
                auctionService.placeBid(auction.getId(), currentUser.getId(), amount);
                JOptionPane.showMessageDialog(this, "Oferta plasată cu succes!");
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preț invalid!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
        panel.add(bidBtn);

        add(new JScrollPane(panel));
    }
}