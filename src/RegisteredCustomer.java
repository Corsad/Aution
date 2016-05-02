/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class RegisteredCustomer extends Customer {

    private double accBalance;

    public RegisteredCustomer(String id, String name, double accBalance) {
        super(id, name);
        if (accBalance < 0) {
            this.accBalance = 0;
        } else {
            this.accBalance = accBalance;
        }
    }

    public double getAccBalance() {
        return accBalance;
    }

    public void addFunds(double amount) {
        accBalance += amount;
    }

    public void deductFees(double fees) {
        accBalance -= fees;
    }

    @Override
    public void completeSale(double price) {
        accBalance -= price;
    }

    @Override
    public void print() {
        super.print();
        System.out.println("Account Balance: " + accBalance);
    }
}
