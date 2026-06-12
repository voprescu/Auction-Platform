package service;

import dao.AuctionDAO;
import dao.BidDAO;
import model.Auction;
import model.Bid;

import java.time.LocalDateTime;
import java.util.List;

public class AuctionService {
    private AuctionDAO auctionDAO = new AuctionDAO();
    private BidDAO bidDAO = new BidDAO();

    public void createAuction(String title, String description, double startingPrice,
                              LocalDateTime endTime, int sellerId) throws Exception {
        // Validari
        if (title.isEmpty()) {
            throw new Exception("Auction title is mandatory!");
        }

        if (startingPrice <= 0) {
            throw new Exception("Price needs to be a positive number!");
        }

        if (endTime.isBefore(LocalDateTime.now())) {
            throw new Exception("Expiration date needs to be in the future!");
        }

        Auction auction = new Auction(title, description, startingPrice, endTime, sellerId);
        auctionDAO.createAuction(auction);
    }

    public void placeBid(int auctionId, int userId, double amount) throws Exception {
        Auction auction = auctionDAO.findById(auctionId);

        if (auction == null) {
            throw new Exception("The auction doesn't exist!");
        }

        if (auction.getStatus().equals("CLOSED")) {
            throw new Exception("The auction is closed!");
        }

        if (LocalDateTime.now().isAfter(auction.getEndTime())) {
            throw new Exception("The auction is closed!");
        }

        if (amount <= auction.getCurrentPrice()) {
            throw new Exception("Offer must be bigger than " + auction.getCurrentPrice());
        }

        bidDAO.saveBid(auctionId, userId, amount);
        auctionDAO.updateCurrentPrice(auctionId, amount);
    }

    public List<Auction> getAllActiveAuctions() throws Exception {
        return auctionDAO.getAllActive();
    }

    public Auction getAuctionDetails(int auctionId) throws Exception {
        Auction auction = auctionDAO.findById(auctionId);
        if (auction == null) {
            throw new Exception("The auction doesn't exist!");
        }
        return auction;
    }

    public List<Bid> getAuctionBids(int auctionId) throws Exception {
        return bidDAO.getBidsByAuction(auctionId);
    }

    public Bid getHighestBid(int auctionId) throws Exception {
        return bidDAO.getHighestBid(auctionId);
    }
}