import java.text.DecimalFormat;

public class CustomerEndsCheckout extends Event{
    private CheckoutLine checkoutLine;

    public CustomerEndsCheckout(Customer customer, double time,  CheckoutLine checkoutLine){
        super(customer, time);
        this.checkoutLine = checkoutLine;
    }
    
    //outputs the event
    public String toString(){
        DecimalFormat df = new DecimalFormat("###.##");  
         if(checkoutLine instanceof ExpressCheckoutLine){
            return(df.format(customer.getShoppingTime() + ((customer.getCustomerOrderSize()*0.1) +1) + customer.getWaitTime())+ ": Finished Checkout Customer " + customer.getCustomerId() + " on Lane " + (checkoutLine.getLaneNumber()) + " (0) (" + (df.format(customer.getWaitTime())) + " minute wait, " + customer.getPlaceInLine() + " people in line -- finished shopping at " + df.format(customer.getShoppingTime()) +", got to the front of the line at " + df.format(customer.getShoppingTime() + customer.getWaitTime()));
        }else{
            return(df.format(time + customer.getWaitTime())+ ": Finished Checkout Customer " + customer.getCustomerId() + " on Lane " + (checkoutLine.getLaneNumber()) + " (0) (" + (df.format(customer.getWaitTime())) + " minute wait, " + customer.getPlaceInLine() + " people in line -- finished shopping at " + df.format(customer.getShoppingTime()) +", got to the front of the line at " + df.format(customer.getShoppingTime() + customer.getWaitTime()));

        }
    }
}
