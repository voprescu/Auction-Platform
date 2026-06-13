package dao;

import model.Bid;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BidDAO {

    // Salveaza o oferta noua
    public void saveBid(int auctionId, int userId, double amount) throws SQLException {
        String sql = "INSERT INTO bids (auction_id, user_id, amount) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, auctionId);
            ps.setInt(2, userId);
            ps.setDouble(3, amount);
            ps.executeUpdate();
        }
    }

    // Preia toate ofertele pentru o licitatie
    public List<Bid> getBidsByAuction(int auctionId) throws SQLException {
        String sql = "SELECT * FROM bids WHERE auction_id = ? ORDER BY amount DESC";
        List<Bid> bids = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, auctionId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                bids.add(new Bid(
                        rs.getInt("id"),
                        rs.getInt("auction_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getObject("bid_time", LocalDateTime.class)
                ));
            }
        }
        return bids;
    }

    public Bid getHighestBid(int auctionId) throws SQLException {
        String sql = "SELECT * FROM bids WHERE auction_id = ? ORDER BY amount DESC LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, auctionId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Bid(
                        rs.getInt("id"),
                        rs.getInt("auction_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getObject("bid_time", LocalDateTime.class)
                );
            }
        }
        return null;
    }
}