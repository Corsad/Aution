/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class ReserveItem extends AuctionItem {

    private double reservePrice;

    public ReserveItem(int id, String description, String sellerId, double startPrice, double reservePrice) {
        super(id, description, sellerId, startPrice);
        this.reservePrice = reservePrice;
    }

    public double getReservePrice(){
        return reservePrice;
    }
    
    @Override
    public String getStatus() {
        if ((super.getStatus()).equals("Close") && super.getHighestBill() < reservePrice) {
            return "Passed In";
        } else {
            return super.getStatus();
        }
    }

    @Override
    public double listingFee() {
        if (reservePrice <= 1) {
            return 0;
        } else if (reservePrice <= 100) {
            return (reservePrice / 100.0 * 4);
        } else if (reservePrice <= 1000) {
            return (4 + (reservePrice - 100) / 100 * 2);
        } else {
            return (22 + (reservePrice - 1000) / 100 * 3);
        }
    }

    @Override
    public double saleFee() {
        if (getStatus().equals("Passed In")) {
            return (reservePrice / 100 * 2);
        } else {
            return super.saleFee();
        }
    }

    public boolean lowerReserve(double newReservePrice) throws Exception {
        if ((super.getStatus()).equals("Open") && newReservePrice < reservePrice) {
            reservePrice = newReservePrice;
            return true;
        } else {
            throw new Exception("The new Reserve Price is higher the the current one.");
        }
    }

    @Override
    public void print() {
        super.print();
        System.out.println("Reserve Price: " + reservePrice);
    }
}