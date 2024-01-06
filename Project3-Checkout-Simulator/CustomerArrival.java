public class CustomerArrival extends Event{
    public CustomerArrival(Customer customer, double time) {
        super(customer,time);
    }

    //outputs the event
    public String toString(){
        return(time + ": Arrival Customer " + customer.getCustomerId());
    }
}
