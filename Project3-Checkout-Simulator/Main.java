//Alexander Shampton
//CS-1181L-07
//Due: 4/3/22

import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class Main {

    // Takes a file and returns an array list with customers inside of it
    public static ArrayList<Customer> readData(String filename) throws FileNotFoundException {
        Scanner fileReader = new Scanner(new File(filename));
        ArrayList<Customer> arrayList = new ArrayList<Customer>();
        int lineNum = 1;
        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            String[] result = line.split("\t");
            double timeOfArrival = Double.parseDouble(result[0]);
            int numOfItems = Integer.parseInt(result[1]);
            double value = Double.parseDouble(result[2]);
            Customer customer = new Customer(timeOfArrival, numOfItems, value, lineNum, 0, 0, 0, 0);
            arrayList.add(customer);
            lineNum++;
        }
        fileReader.close();
        return (arrayList);
    }

    public static void main(String[] args) throws Exception {
        String fileName = "arrival.txt";

        // Array list of customers
        ArrayList<Customer> customers = readData(fileName);
        // Sorts the customers by their arrival times
        Collections.sort(customers);

        // Array list of all the checkoutlines
        ArrayList<CheckoutLine> allCheckoutLines = new ArrayList<CheckoutLine>();

        int numOfLanes = 0;
        // creates and adds regular checkoutlanes to allcheckoutlanes
        for (int i = 0; i < 7; i++) {
            CheckoutLine checkoutLine1 = new RegularCheckoutLine(i + 1, 0, 0, 0);
            allCheckoutLines.add(checkoutLine1);
            numOfLanes = i + 1;
        }

        // creates and adds express checkoutlanes to allcheckoutlanes
        for (int i = 0; i < 3; i++) {
            numOfLanes += 1;
            CheckoutLine expressCheckoutLine = new ExpressCheckoutLine(numOfLanes, 0, 0, 0);
            allCheckoutLines.add(expressCheckoutLine);
        }

        // Array list of events
        ArrayList<Event> events = new ArrayList<Event>();

        // Goes through all the customers in the customer array and creates arrival and
        // picks line events
        for (int i = 0; i < customers.size(); i++) {
            CustomerArrival customerArrival = new CustomerArrival(customers.get(i), customers.get(i).getArrivalTime());
            CustomerPicksLine customerPicksLine = new CustomerPicksLine(customers.get(i),
                    customers.get(i).getShoppingTime());

            // Takes the index outputted from customerPicks line
            // Uses the index from customerpicks line and takes saves the line as a variable
            // adds the customer to the line
            allCheckoutLines.get(customerPicksLine.choosingLine(allCheckoutLines)).add(customers.get(i));

            // adds both of the events to the events array lists
            events.add(customerArrival);
            events.add(customerPicksLine);
        }

        for (CheckoutLine checkoutLine : allCheckoutLines) {
            if (checkoutLine.size() >= 2) {
                while (checkoutLine.size() != 0) {
                    if (checkoutLine.peek() != null) {
                        int lineSize = 0;
                        Customer removedCustomer = checkoutLine.peek();
                        CustomerEndsCheckout customerEndsCheckout = new CustomerEndsCheckout(removedCustomer,
                                removedCustomer.getCheckoutTime(), checkoutLine);

                        // removes the customer with the smallest shopping time
                        checkoutLine.remove();
                        // sets value of the line behind the removed customer to 0,
                        removedCustomer.setPlaceInLine(lineSize);
                        events.add(customerEndsCheckout);

                        // Checks if there is a line behind the person checking out
                        if (checkoutLine.peek() != null
                                && (removedCustomer.getCheckoutTime() > checkoutLine.peek().getShoppingTime())

                                && (removedCustomer.getShoppingTime() < checkoutLine.peek().getShoppingTime())) {
                            Customer removedCustomer2 = checkoutLine.peek();

                            // since this customer had to wait, the wait time increase by substractingthe
                            // removed customers checkouttime by the current customers shopping time
                            removedCustomer2.setWaitTime(
                                    removedCustomer.getCheckoutTime() - removedCustomer2.getShoppingTime());
                            CustomerEndsCheckout customerEndsCheckout2 = new CustomerEndsCheckout(removedCustomer2,
                                    removedCustomer2.getCheckoutTime(), checkoutLine);
                            // removes the customer with the smallest shopping time
                            checkoutLine.remove();

                            // adds the event to the array list
                            events.add(customerEndsCheckout2);

                            // sets the line to 1
                            lineSize += 1;
                            removedCustomer2.setPlaceInLine(lineSize);
                        } else {
                            // If there is a person not behind the person checking out, the next person
                            // checks out with no wait time
                            if (checkoutLine.peek() != null) {
                                Customer removedCustomer2 = checkoutLine.peek();

                                // the line is set to 0 since there was no one in the front of the customer
                                removedCustomer2.setPlaceInLine(0);

                                // creates new event of customer ending checkout
                                CustomerEndsCheckout customerEndsCheckout2 = new CustomerEndsCheckout(removedCustomer2,
                                        removedCustomer2.getCheckoutTime(), checkoutLine);

                                // removes the customer with the smallest shopping time
                                checkoutLine.remove();

                                // adds the event to the array list
                                events.add(customerEndsCheckout2);
                            }
                        }
                    }

                }

            } else {
                // Only is used if the size of the line is less than 2 (arival simple.txt)
                while (checkoutLine.size() != 0) {
                    // makes new customer with smallest shopping time
                    Customer removedCustomer = checkoutLine.peek();
                    CustomerEndsCheckout customerEndsCheckout = new CustomerEndsCheckout(removedCustomer,
                            removedCustomer.getCheckoutTime(), checkoutLine);

                    // removes the customer with the smallest shopping time
                    checkoutLine.remove();
                    // sets value of the line behind the removed customer to 0,
                    removedCustomer.setPlaceInLine(0);
                    events.add(customerEndsCheckout);

                }
            }
        }

        // sorts events by their times
        Collections.sort(events);

        // calculates waittime
        double waitTime = 0;
        for (int i = 0; i < customers.size(); i++) {
            waitTime += customers.get(i).getWaitTime();
        }

        DecimalFormat df = new DecimalFormat("###.####");
        // outputs all the event logs into the logs.txt file
        try {
            FileWriter fw = new FileWriter("logs.txt");
            for (int i = 0; i < events.size(); i++) {
                fw.write(events.get(i) + "\n");
            }
            fw.write("\nNumber Of Lanes: " + allCheckoutLines.size() + "\n");
            fw.write("Number Of Customers: " + customers.size() + "\n");
            fw.write("Total Wait Time: " + df.format(waitTime) + "\n");
            fw.write("Average Wait Time: " + df.format(waitTime / customers.size()));
            fw.close();
            System.out.println("Sucessfully Outputted to logs.txt");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
