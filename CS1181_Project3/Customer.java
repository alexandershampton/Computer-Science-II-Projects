
public class Customer implements Comparable<Customer>{

    //time the customer arrives
    private double timeOfArrival;

    //the number of items ordered
    private int orderSize;

    //the average time to pick up each item
    private double averageTimeForEachItem;

    //the customer's id number
    private int customerID;

    //the customer's lane lumber
    private int laneNumber;

    //the customer's current time
    private double simClock;

    //the customer's place in line
    private int placeInLine;

    private double waitTime;

    Customer(double timeOfArrival, int orderSize, double averageTimeForEachItem, int customerID, int laneNumber, double simClock, int placeInLine,double waitTime){
        this.timeOfArrival = timeOfArrival;
        this.orderSize = orderSize;
        this.averageTimeForEachItem = averageTimeForEachItem;
        this.customerID = customerID;
        this.laneNumber = laneNumber;
        this.simClock = simClock;
        this.placeInLine = placeInLine;
        this.waitTime = waitTime;
    }

    //outputs the customer's values
    public String toString(){
        return("Time Arrived: " + timeOfArrival + " Size of Order: " + orderSize + " Average Time Picking Each Item: " + averageTimeForEachItem + " Customer: " + customerID);
    }

    //Returns the time when the customer reaches the line
    public double getShoppingTime(){
        return((orderSize*averageTimeForEachItem) + timeOfArrival);
    }

    //Returns the time when the customer arrives at the store
    public double getArrivalTime(){
        return(timeOfArrival);
    }
    
    //Returns the customers ID Number
    public int getCustomerId(){
        return(customerID);
    }
    
    //Returns the customer's order size
    public int getCustomerOrderSize(){
        return(orderSize);
    }

    //Returns the customers Lane Number
    public int getLaneNumber(){
        return(laneNumber);
    }

    //Returns the the current time the customer is at
    //Returns checkout time when removed from list
    public double getSimClock(){
        return(simClock);
    }

    //Returns the time where the customer checks out 
    public double getCheckoutTime(){
        return(getShoppingTime() + getProcessTime());
        //return(timeOfArrival + (timeOfArrival - simClock));
    }

    //Returns the customer's place in line
    public int getPlaceInLine(){
        return(placeInLine);
    }
    //Sets the customer's lane number
    public void setLaneNumber(int laneNum){
        laneNumber = laneNum;
    }

    //Sets the line
    public void setPlaceInLine(int lineSize){
        placeInLine = lineSize;
    }

    //The time it takes for the customer to go through the the scanning
    public double getProcessTime(){
        return((orderSize*0.05)+2);
    }

    //Returns the waiting time of the customer
    public double getWaitTime(){
        return(waitTime);
    }

    public void setWaitTime(double previousTime){
        waitTime += previousTime;
    }

    //Sorts the customers by the time they are done shopping 
    public int compareTo(Customer other){
        if(this.getShoppingTime()> other.getShoppingTime()){
            return(1);
        }else if(other.getShoppingTime()>this.getShoppingTime()){
            return(-1);
        }else{
            return(0);
        }
    }

}
