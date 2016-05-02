/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author s3372771
 */
import java.io.*;
import java.util.*;

public class UsingAuction {

    public static void main(String[] args) throws IOException {
        AuctionItem itemNo[] = new AuctionItem[50];
        Customer guestNo[] = new Customer[50];

        int i = 0;
        int c = 0;


        //Read Item
        Scanner input = new Scanner(new File("Item.txt"));
        String line;

        while (input.hasNextLine()) {
            line = input.nextLine();
            StringTokenizer inReader = new StringTokenizer(line, ";");
            if (inReader.countTokens() == 4) {
                int id = Integer.parseInt(inReader.nextToken());
                String description = inReader.nextToken();
                String sellerId = inReader.nextToken();
                double startPrice = Double.parseDouble(inReader.nextToken());
                itemNo[i] = new AuctionItem(id, description, sellerId, startPrice);
            } else if (inReader.countTokens() == 5) {
                int id = Integer.parseInt(inReader.nextToken());
                String description = inReader.nextToken();
                String sellerId = inReader.nextToken();
                double startPrice = Double.parseDouble(inReader.nextToken());
                double reservePrice = Double.parseDouble(inReader.nextToken());
                itemNo[i] = new ReserveItem(id, description, sellerId, startPrice, reservePrice);
            } else {
                break;
            }
            i++;
        }
        input.close();


        //Read Customer
        Scanner inputCustomer = new Scanner(new File("Customer.txt"));
        while (inputCustomer.hasNextLine()) {
            line = inputCustomer.nextLine();
            StringTokenizer inReader = new StringTokenizer(line, ";");
            if (inReader.countTokens() == 4) {
                String id = inReader.nextToken();
                String name = inReader.nextToken();
                int creditNumber = Integer.parseInt(inReader.nextToken());
                int PIN = Integer.parseInt(inReader.nextToken());
                guestNo[c] = new CasualCustomer(id, name, creditNumber, PIN);
            } else if (inReader.countTokens() == 3) {
                String id = inReader.nextToken();
                String name = inReader.nextToken();
                double accBalance = Double.parseDouble(inReader.nextToken());
                guestNo[c] = new RegisteredCustomer(id, name, accBalance);
            } else {
                break;
            }
            c++;
        }
        inputCustomer.close();

        // Menu
        String select = "A";
        while (!select.equalsIgnoreCase("X")) {

            System.out.println("*** iBuy Auction Recording System *********\n"
                    + "\nA - Add Auction"
                    + "\nB - Display Auction Details"
                    + "\nC - Open Auction"
                    + "\nD - Place Bid"
                    + "\nE - Close Auction"
                    + "\nF – Add Customer"
                    + "\nG – Display Customer Details"
                    + "\nH – Add Funds to Account"
                    + "\nX - Exit the Program");
            System.out.print("Enter you selection: ");
            Scanner reader = new Scanner(System.in);
            select = reader.next();

            //Add Auction
            if (select.equalsIgnoreCase("A")) {
                System.out.println("");
                System.out.println("Adding a new item auction...");
                System.out.print("Enter item ID: ");
                int nId = reader.nextInt();
                int k = 0;
                for (int j = 0; j < itemNo.length; j++) {
                    if (itemNo[j] == null) {
                        break;
                    } else if (nId == itemNo[j].getId()) {
                        System.out.println("Error - duplicate item ID entered!");
                        System.out.println("");
                        k++;
                        break;
                    }
                }
                if (k == 0) {
                    Scanner itemInput = new Scanner(System.in);
                    System.out.print("Enter item description: ");
                    String nDescription = itemInput.nextLine();
                    System.out.print("Enter seller ID: ");
                    String nSId = itemInput.next();
                    for (int l = 0; l < guestNo.length; l++) {
                        if (guestNo[l] == null) {
                            break;
                        } else if (nSId.equals(guestNo[l].getId())) {
                            if (guestNo[l] instanceof RegisteredCustomer) {
                                if (((RegisteredCustomer) guestNo[l]).getAccBalance() < 0) {
                                    System.out.println("Account Balance is less than 0");
                                } else {
                                    System.out.print("Enter starting price: ");
                                    double nSPrice = itemInput.nextDouble();
                                    System.out.print("Create a new AuctionItem or a ReserveItem? (enter A or R): ");
                                    String selectKindOfItem = reader.next();
                                    if (selectKindOfItem.equalsIgnoreCase("A")) {
                                        itemNo[i] = new AuctionItem(nId, nDescription, nSId, nSPrice);
                                    } else {
                                        System.out.print("Enter reserve price: ");
                                        double nRPrice = reader.nextDouble();
                                        itemNo[i] = new ReserveItem(nId, nDescription, nSId, nSPrice, nRPrice);
                                    }
                                    System.out.println("Auction item \"" + nDescription + "\"successfully added to system!");
                                    System.out.println("");
                                    i++;
                                }
                            } else {
                                System.out.println("This account is not a Registered Account.");
                            }
                            k++;
                        }
                    }
                    if (k == 0) {
                        System.out.println("There is no customer that has ID \"" + nSId + "\".");
                    }
                }

                //Display Item
            } else if (select.equalsIgnoreCase("B")) {
                System.out.println("");
                System.out.println("Printing details of all auctions in the system: ");
                for (int j = 0; j < itemNo.length; j++) {
                    if (itemNo[j] == null) {
                        break;
                    } else {
                        itemNo[j].print();
                        System.out.println("");
                    }
                }

                //Open Auction
            } else if (select.equalsIgnoreCase("C")) {
                System.out.print("Enter the id of the auction to open: ");
                int nId = reader.nextInt();
                int k = 0;
                for (int j = 0; j < itemNo.length; j++) {
                    if (itemNo[j] == null) {
                        break;
                    } else if (itemNo[j].getId() == nId) {
                        try {
                            double listFee = itemNo[j].open();
                            System.out.println("Auction " + nId + " has started - listing fee: $" + listFee);
                            for (int l = 0; l < guestNo.length; l++) {
                                if (guestNo[l] == null) {
                                    break;
                                } else if ((guestNo[l].getId()).equals(itemNo[j].getSellerId())) {
                                    ((RegisteredCustomer) guestNo[l]).deductFees(listFee);
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("The auction is not in the correct state to be opened.");
                        } finally {
                            k++;
                        }
                    }
                }
                if (k == 0) {
                    System.out.println("The auction item with id " + nId + " is not found!");
                }

                //Place Bid
            } else if (select.equalsIgnoreCase("D")) {
                System.out.print("Enter the id of the auction to bid on: ");
                int nId = reader.nextInt();
                int k = 0;
                for (int j = 0; j < itemNo.length; j++) {
                    if (itemNo[j] == null) {
                        break;
                    } else if (itemNo[j].getId() == nId) {
                        try {
                            System.out.println("You are bidding in the auction for item \"" + itemNo[j].getDescription() + "\".");
                            System.out.print("Enter bid amount: ");
                            double bidAmount = reader.nextDouble();
                            System.out.print("Enter bidder id: ");
                            String bidder = reader.next();
                            int p = 0;
                            for (int l = 0; l < guestNo.length; l++) {
                                if (guestNo[l] == null) {
                                    break;
                                } else if ((guestNo[l].getId()).equals(bidder)) {
                                    itemNo[j].placeBid(bidAmount, bidder);
                                    System.out.println("Your bid was successful!");
                                    System.out.println("");
                                    itemNo[j].print();
                                    p++;
                                }
                            }
                            if (p == 0) {
                                System.out.println("There is no customer that has ID \"" + bidder + "\".");
                            }
                        } catch (IllegalArgumentException ex) {
                            System.out.println("Bid amount is invalid.");
                        } catch (Exception ex) {
                            System.out.println("The auction is not in the correct state to be bidded on.");
                        } finally {
                            k++;
                        }
                    }
                }
                if (k == 0) {
                    System.out.println("The auction item with id " + nId + " is not found!");
                }

                //Close Auction
            } else if (select.equalsIgnoreCase("E")) {
                System.out.print("Enter the id of the auction to close: ");
                int nId = reader.nextInt();
                int k = 0;
                for (int j = 0; j < itemNo.length; j++) {
                    if (itemNo[j] == null) {
                        break;
                    } else if (itemNo[j].getId() == nId) {
                        if (itemNo[j] instanceof ReserveItem) {
                            if ((itemNo[j].getStatus()).equals("Open") && itemNo[j].getHighestBill() < (((ReserveItem) itemNo[j]).getReservePrice())) {
                                System.out.print("Do you want to lower the Reserve Price? (Enter Y or N) ");
                                String selectLower = reader.next();
                                if (selectLower.equalsIgnoreCase("Y")) {
                                    System.out.println("The current Reserve Price is: " + ((ReserveItem) itemNo[j]).getReservePrice());
                                    System.out.print("Enter the new Reserve Price: ");
                                    double newReservePrice = reader.nextDouble();
                                    try {
                                        ((ReserveItem) itemNo[j]).lowerReserve(newReservePrice);
                                        System.out.println("You have lowed the Reserve Price.");
                                    } catch (Exception ex) {
                                        System.out.println("Error - The new Reserve Price is higher the the current one.");
                                    } finally {
                                        k++;
                                    }
                                } else {
                                    try {
                                        double listFee = itemNo[j].close();
                                        System.out.println("Auction " + nId + " has ended - listing fee: $" + listFee);
                                    } catch (Exception ex) {
                                        System.out.println("The auction is not in the correct state to be closed.");
                                    } finally {
                                        k++;
                                    }
                                }
                            }
                        } else {
                            try {
                                double listFee = itemNo[j].close();
                                System.out.println("Auction " + nId + " has ended - listing fee: $" + listFee);
                            } catch (Exception ex) {
                                System.out.println("The auction is not in the correct state to be closed.");
                            } finally {
                                k++;
                            }
                        }
                    }
                }
                if (k == 0) {
                    System.out.println("The auction item with id " + nId + " is not found!");
                }


                //Add Customer
            } else if (select.equalsIgnoreCase("F")) {
                System.out.println("");
                System.out.println("Adding a new customer...");
                System.out.print("Enter customer ID: ");
                String nId = reader.next();
                int k = 0;
                for (int j = 0; j < guestNo.length; j++) {
                    if (guestNo[j] == null) {
                        break;
                    } else if (nId.equals(guestNo[j].getId())) {
                        System.out.println("Error - duplicate customer ID entered!");
                        System.out.println("");
                        k++;
                        break;
                    }
                }
                if (k == 0) {
                    Scanner customerInput = new Scanner(System.in);
                    System.out.print("Enter your name: ");
                    String nName = customerInput.nextLine();
                    System.out.print("Create a new CasualCustomer or"
                            + " a RegisterdCustomer? (enter C or R) ");
                    String selectKindOfItem = customerInput.next();
                    if (selectKindOfItem.equalsIgnoreCase("C")) {
                        System.out.print("Enter your Credit Number: ");
                        int nCreditNumber = customerInput.nextInt();
                        System.out.print("Enter your PIN: ");
                        int nPIN = customerInput.nextInt();
                        guestNo[c] = new CasualCustomer(nId, nName, nCreditNumber, nPIN);
                    } else {
                        System.out.print("Enter your Account Balance: ");
                        double nAccBalance = customerInput.nextDouble();
                        guestNo[c] = new RegisteredCustomer(nId, nName, nAccBalance);
                    }
                    System.out.println("Customer no \"" + nId + "\"successfully added to system!");
                    System.out.println("");
                    c++;
                }

                //Display Customer
            } else if (select.equalsIgnoreCase("G")) {
                System.out.println("");
                System.out.println("Printing details of all customer in the system: ");
                for (int j = 0; j < guestNo.length; j++) {
                    if (guestNo[j] == null) {
                        break;
                    } else {
                        guestNo[j].print();
                        System.out.println("");
                    }
                }

                //Add fund
            } else if (select.equalsIgnoreCase("H")) {
                System.out.println("Adding fund to registered customer account...");
                System.out.print("Enter your ID: ");
                String nId = reader.next();
                int k = 0;
                for (int j = 0; j < guestNo.length; j++) {
                    if (guestNo[j] == null) {
                        break;
                    } else if ((guestNo[j].getId()).equals(nId)) {
                        if (guestNo[j] instanceof CasualCustomer) {
                            System.out.println("This is not a registered account");
                        } else {
                            System.out.print("Enter the amount you want to add: ");
                            double amount = reader.nextDouble();
                            ((RegisteredCustomer) guestNo[j]).addFunds(amount);
                            System.out.println("Adding successfull.");
                        }
                        k++;
                    }
                }
                if (k == 0) {
                    System.out.println("No customer have the same Id.");
                }
            }
            System.out.println("");
        }


        //Write Item
        PrintWriter output = null;
        output = new PrintWriter(new FileWriter("Item.txt", false));
        for (int j = 0; j < itemNo.length; j++) {
            if (itemNo[j] == null) {
                break;
            } else if (itemNo[j] instanceof ReserveItem) {
                output.println(itemNo[j].getId() + ";" + itemNo[j].getDescription()
                        + ";" + itemNo[j].getSellerId() + ";" + itemNo[j].getStartPrice()
                        + ";" + ((ReserveItem) itemNo[j]).getReservePrice());
            } else if (itemNo[j] instanceof AuctionItem) {
                output.println(itemNo[j].getId() + ";" + itemNo[j].getDescription()
                        + ";" + itemNo[j].getSellerId() + ";" + itemNo[j].getStartPrice());

            }
        }
        output.close();

        //Write Customer
        PrintWriter outputCustomer = null;
        outputCustomer = new PrintWriter(new FileWriter("Customer.txt", false));
        for (int j = 0; j < guestNo.length; j++) {
            if (guestNo[j] == null) {
                break;
            } else if (guestNo[j] instanceof CasualCustomer) {
                outputCustomer.println(guestNo[j].getId() + ";" + guestNo[j].getName()
                        + ";" + ((CasualCustomer) guestNo[j]).getCreditNumber() + ";" + ((CasualCustomer) guestNo[j]).getPIN());
            } else if (guestNo[j] instanceof RegisteredCustomer) {
                outputCustomer.println(guestNo[j].getId() + ";" + guestNo[j].getName()
                        + ";" + ((RegisteredCustomer) guestNo[j]).getAccBalance());
            }
        }
        outputCustomer.close();

    }
}
