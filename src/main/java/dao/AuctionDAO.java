package dao;

import model.Auction;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuctionDAO {

    public void createAuction(Auction auction) throws SQLException {
        String sql = "INSERT INTO auctions (title, description, starting_price, current_price, end_time, seller_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, auction.getTitle());
            ps.setString(2, auction.getDescription());
            ps.setDouble(3, auction.getStartingPrice());
            ps.setDouble(4, auction.getCurrentPrice());
            ps.setObject(5, auction.getEndTime());
            ps.setInt(6, auction.getSellerId());
            ps.executeUpdate();
        }
    }

    public Auction findById(int id) throws SQLException {
        String sql = "SELECT * FROM auctions WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Auction(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDouble("starting_price"),
                        rs.getDouble("current_price"),
                        rs.getObject("end_time", LocalDateTime.class),
                        rs.getInt("seller_id"),
                        rs.getString("status")
                );
            }
        }
        return null;
    }

    public List<Auction> getAllActive() throws SQLException {
        String sql = "SELECT * FROM auctions WHERE status = 'ACTIVE'";
        List<Auction> auctions = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                auctions.add(new Auction(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDouble("starting_price"),
                        rs.getDouble("current_price"),
                        rs.getObject("end_time", LocalDateTime.class),
                        rs.getInt("seller_id"),
                        rs.getString("status")
                ));
            }
        }
        return auctions;
    }

    public void updateCurrentPrice(int auctionId, double newPrice) throws SQLException {
        String sql = "UPDATE auctions SET current_price = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, newPrice);
            ps.setInt(2, auctionId);
            ps.executeUpdate();
        }
    }
}