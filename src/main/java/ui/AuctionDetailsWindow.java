package ui;

import service.AuctionService;
import model.Auction;
import model.User;
import model.Bid;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AuctionDetailsWindow extends JFrame {
    private AuctionService auctionService = new AuctionService();
    private Auction auction;
    private User currentUser;

    public AuctionDetailsWindow(Auction auction, User user) {
        this.auction = auction;
        this.currentUser = user;

        setTitle("Detalii Licitatie: " + auction.getTitle());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Titlu: " + auction.getTitle());
        titleLabel.setBounds(20, 20, 660, 25);
        panel.add(titleLabel);

        JLabel descLabel = new JLabel("Descriere: " + auction.getDescription());
        descLabel.setBounds(20, 50, 660, 25);
        panel.add(descLabel);

        JLabel currentPriceLabel = new JLabel("Pret curent: " + auction.getCurrentPrice() + " lei");
        currentPriceLabel.setBounds(20, 80, 660, 25);
        panel.add(currentPriceLabel);

        JLabel startPriceLabel = new JLabel("Preț de start: " + auction.getStartingPrice() + " lei");
        startPriceLabel.setBounds(20, 110, 660, 25);
        panel.add(startPriceLabel);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        JLabel endTimeLabel = new JLabel("Expirare: " + auction.getEndTime().format(formatter));
        endTimeLabel.setBounds(20, 140, 660, 25);
        panel.add(endTimeLabel);

        JLabel bidsLabel = new JLabel("Oferte:");
        bidsLabel.setBounds(20, 170, 660, 25);
        panel.add(bidsLabel);

        DefaultListModel<String> bidsModel = new DefaultListModel<>();
        JList<String> bidsList = new JList<>(bidsModel);
        JScrollPane bidsScroll = new JScrollPane(bidsList);
        bidsScroll.setBounds(20, 195, 660, 200);
        panel.add(bidsScroll);

        try {
            List<Bid> bids = auctionService.getAuctionBids(auction.getId());
            if (bids.isEmpty()) {
                bidsModel.addElement("Nu sunt oferte incă");
            } else {
                for (Bid bid : bids) {
                    bidsModel.addElement("User " + bid.getUserId() + ": " + bid.getAmount() + " lei");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Eroare: " + e.getMessage());
        }

        JLabel ofertaLabel = new JLabel("Plaseaza oferta (lei):");
        ofertaLabel.setBounds(20, 410, 200, 25);
        panel.add(ofertaLabel);

        JTextField ofertaField = new JTextField();
        ofertaField.setBounds(20, 435, 300, 35);
        panel.add(ofertaField);

        JButton bidBtn = new JButton("Plaseaza Oferta");
        bidBtn.setBounds(350, 435, 150, 35);
        bidBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(ofertaField.getText());
                auctionService.placeBid(auction.getId(), currentUser.getId(), amount);
                JOptionPane.showMessageDialog(this, "Oferta plasata cu succes!");
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Pret invalid!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
        panel.add(bidBtn);

        add(panel);
    }
}