/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public abstract class Customer {

    private String id;
    private String name;

    public Customer(String nId, String nName) {
        id = nId;
        name = nName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract void completeSale(double price);

    public void print() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
    }
}
