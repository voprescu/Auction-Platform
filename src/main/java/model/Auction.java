package model;

import java.time.LocalDateTime;

public class Auction {
    private int id;
    private String title;
    private String description;
    private double startingPrice;
    private double currentPrice;
    private LocalDateTime endTime;
    private int sellerId;
    private String status;

    public Auction(int id, String title, String description, double startingPrice,
                   double currentPrice, LocalDateTime endTime, int sellerId, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startingPrice = startingPrice;
        this.currentPrice = currentPrice;
        this.endTime = endTime;
        this.sellerId = sellerId;
        this.status = status;
    }

    public Auction(String title, String description, double startingPrice,
                   LocalDateTime endTime, int sellerId) {             //Constructor fara ID
        this.title = title;
        this.description = description;
        this.startingPrice = startingPrice;
        this.currentPrice = startingPrice;
        this.endTime = endTime;
        this.sellerId = sellerId;
        this.status = "ACTIVE";
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public double getStartingPrice() {
        return startingPrice;
    }
    public double getCurrentPrice() {
        return currentPrice;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public int getSellerId() {
        return sellerId;
    }
    public String getStatus() {
        return status;
    }

}
