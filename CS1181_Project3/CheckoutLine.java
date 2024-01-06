import java.util.*;
public class CheckoutLine extends PriorityQueue<Customer> implements Comparable<CheckoutLine>{
    double simClock;
    int laneNumber;
    int lineSize;
    double waitTime;

    CheckoutLine(int laneNumber, double simClock, int lineSize, double waitTime){
        this.simClock = simClock;
        this.laneNumber = laneNumber;
        this.lineSize = lineSize;
        this.waitTime = waitTime;
    }

    //gets the checkout line size
    public int getCheckoutLineSize(){
        return(this.size());
    }

    //gets the lane number
    public int getLaneNumber(){
        return(laneNumber);
    }
    
    //sorts the line by the size
    public int compareTo(CheckoutLine otherCheckoutLine){
        if(this.getCheckoutLineSize()>otherCheckoutLine.getCheckoutLineSize()){
            return(1);
        }else if(otherCheckoutLine.getCheckoutLineSize()>this.getCheckoutLineSize()){
            return(-1);
        }else{
            return(0);
        }
    }


}
