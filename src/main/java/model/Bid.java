package model;

import java.time.LocalDateTime;

public class Bid {
    private int id;
    private int auctionId;
    private int userId;
    private double amount;
    private LocalDateTime bidTime;

    public Bid(int id, int auctionId, int userId, double amount, LocalDateTime bidTime) {
        this.id = id;
        this.auctionId = auctionId;
        this.userId = userId;
        this.amount = amount;
        this.bidTime = bidTime;
    }

    public int getId() {
        return id;
    }
    public int getAuctionId() {
        return auctionId;
    }
    public int getUserId() {
        return userId;
    }
    public double getAmount() {
        return amount;
    }
    public LocalDateTime getBidTime() {
        return bidTime;
    }
}