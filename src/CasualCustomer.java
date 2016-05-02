/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
import java.util.*;

public class CasualCustomer extends Customer {

    private int creditNumber;
    private int PIN;

    public CasualCustomer(String id, String name, int creditNumber, int PIN) {
        super(id, name);
        this.creditNumber = creditNumber;
        this.PIN = PIN;
    }

    public int getCreditNumber() {
        return creditNumber;
    }
    
    public int getPIN(){
        return PIN;
    }
    
    @Override
    public void completeSale(double price) {
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter your PIN: ");
        int num = reader.nextInt();
        if (num == PIN) {
            System.out.println(price + " has been charged to your credit card(" + creditNumber + ").");
        } else {
            System.out.println("The item has been withheld pending confirmation of the customer’s identity.");
        }
    }

    @Override
    public void print() {
        super.print();
        System.out.println("Credit Card Number: " + creditNumber);
        System.out.println("PIN: " + PIN);
    }
}
