import java.util.*;
public class Event implements Comparable<Event> {
    ArrayList<String> logs;
    Customer customer;
    double time;

    Event(Customer customer, double time){
        this.customer = customer;
        this.time = time;
    }

    //Sorts a event list by their times
    public int compareTo(Event other){
        if(this.time+customer.getWaitTime()>other.time+customer.getWaitTime()){
            return(1);
        }else if(other.time+customer.getWaitTime()>this.time+customer.getWaitTime()){
            return(-1);
        }else{
            return(0);
        }
    }
} 

