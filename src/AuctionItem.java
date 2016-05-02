/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author S3372771
 */
public class AuctionItem {

    private int id;
    private String description;
    private String sellerId;
    private double startPrice;
    private String status;
    private double highestBill;
    private String bidderId;

    // Constructor
    public AuctionItem(int nId, String nDescription, String nSId, double nSPrice) {
        id = nId;
        description = nDescription;
        sellerId = nSId;
        startPrice = nSPrice;
        status = "Pending";
        highestBill = 0;
    }

    //Accessors
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getSellerId() {
        return sellerId;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public String getStatus() {
        return status;
    }

    public double getHighestBill() {
        return highestBill;
    }

    public String getBidderId() {
        return bidderId;
    }

    public boolean hasBids(double bidAmount) {
        if (highestBill == 0) {
            if (bidAmount >= startPrice) {
                return true;
            } else {
                return false;
            }
        } else {
            if (bidAmount > highestBill) {
                return true;
            } else {
                return false;
            }
        }
    }

    public double open() throws Exception {
        if (status.equals("Pending")) {
            status = "Open";
            return listingFee();
        } else {
            throw new Exception("The auction is not in the correct state to be opened.");
        }
    }

    public double listingFee() {
        if (startPrice <= 5) {
            return 0.2;
        } else if (startPrice <= 20) {
            return 0.5;
        } else if (startPrice <= 100) {
            return 1;
        } else if (startPrice <= 250) {
            return 2.5;
        } else {
            return 5;
        }
    }

    public double close() throws Exception{
        if (status.equals("Open")) {
            status = "Close";
            return saleFee();
        } else {
            throw new Exception("The auction is not in the correct state to be closed.");
        }
    }

    public double saleFee() {
        if (highestBill < 1) {
            return 0;
        } else if (highestBill <= 100) {
            return (highestBill * 5.0 / 100.0);
        } else if (highestBill <= 1000) {
            return (5 + 3.0 / 100.0 * (highestBill - 100));
        } else {
            return (32 + 1.0 / 100.0 * (highestBill - 100));
        }
    }

    public int placeBid(double bidAmount, String bidder) throws Exception {
        if (status.equals("Open")) {
            if (bidAmount >= startPrice && bidAmount > highestBill) {
                highestBill = bidAmount;
                bidderId = bidder;
                return 0;
            } else {
                throw new IllegalArgumentException("Bid amount is invalid.");
            }
        } else {
                throw new Exception("The auction is not in the correct state to be bidded on.");
        }
    }

    public void print() {
        System.out.println("Item ID : " + id);
        System.out.println("Description : \"" + description + "\"");
        System.out.println("Seller ID : " + sellerId);
        System.out.println("Starting price : " + startPrice);
        System.out.println("Auction Status : " + status);
        System.out.println("Highest Bid : " + highestBill);
        System.out.println("Highest Bidder: " + bidderId);
    }
}