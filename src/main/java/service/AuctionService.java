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
            throw new Exception("Titlul licitației este obligatoriu!");
        }

        if (startingPrice <= 0) {
            throw new Exception("Pretul trebuie să fie pozitiv!");
        }

        if (endTime.isBefore(LocalDateTime.now())) {
            throw new Exception("Data de expirare trebuie să fie în viitor!");
        }

        Auction auction = new Auction(title, description, startingPrice, endTime, sellerId);
        auctionDAO.createAuction(auction);
    }

    public void placeBid(int auctionId, int userId, double amount) throws Exception {
        Auction auction = auctionDAO.findById(auctionId);

        if (auction == null) {
            throw new Exception("Licitația nu există!");
        }

        if (auction.getStatus().equals("CLOSED")) {
            throw new Exception("Licitația s-a închis!");
        }

        if (LocalDateTime.now().isAfter(auction.getEndTime())) {
            throw new Exception("Licitația a expirat!");
        }

        if (amount <= auction.getCurrentPrice()) {
            throw new Exception("Oferta trebuie să fie mai mare decât " + auction.getCurrentPrice());
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
            throw new Exception("Licitația nu există!");
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