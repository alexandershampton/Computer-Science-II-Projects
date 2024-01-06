import java.text.DecimalFormat;
import java.util.*;

public class CustomerPicksLine extends Event {
    public CustomerPicksLine(Customer customer, double time) {
        super(customer, time);
    }

    public int choosingLine(ArrayList<CheckoutLine> checkoutLines) {
        Collections.sort(checkoutLines);
        int numToReturn = 0;
        for (int i = 0; i < checkoutLines.size(); i++) {
            // checks if this checkout line is a express checkoutline and if the customers
                // order size is less than or equal to 12, and if this is true it returns the
                // index number of the express lane
            if (checkoutLines.get(i) instanceof ExpressCheckoutLine && customer.getCustomerOrderSize() <= 12) {
                numToReturn = i;
                break;

            // checks if this checkoutline is a regular checkoutline, and adds the customer
            // no matter what their order size is, and returns the index of the first
            // regular lane
            } else if (checkoutLines.get(i) instanceof RegularCheckoutLine) {
                numToReturn = i;
                break;
            }
        }
        return (numToReturn);
    }
    // outputs the event
    public String toString() {
        DecimalFormat df = new DecimalFormat("#####.##");
        if (customer.getCustomerOrderSize() > 12) {
            return (df.format(customer.getShoppingTime()) + ": Finished Shopping Customer " + customer.getCustomerId()
                    + "\nMore than 12, chose Lane "
                    + customer.getLaneNumber());
        } else {
            return (df.format(customer.getShoppingTime()) + ": Finished Shopping Customer " + customer.getCustomerId()
                    + "\n12 or Fewer, chose Lane "
                    + customer.getLaneNumber());
        }
    }
}
