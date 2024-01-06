//Alexander Shampton
//CS-1181L-07
//Due: 2/13/2022

public class Item {
    // A label for the item, such as “Jewelry”or“Kindle”
    private final String name;

    private final double weight;
    
    // The value of the item rounded to the nearest dollar
    private final int value;

    // Indicates whether the item should be taken or not
    private boolean included;

    // // Initializes the Item’s fields to the values that are passed in; the included field is initialized to false
    public Item(String name, double weight, int value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.included = false;
    }

    // // Initializes this item’s fields to the be the same as the other item’s
    public Item(Item other) {
        this.name = other.getName();
        this.weight = other.getWeight();
        this.value = other.getValue();
        this.included = other.isIncluded();
    }
    
    //RETURNS THE NAME
    public String getName() {
        return (name);
    }

    //RETURNS THE WEIGHT
    public double getWeight() {
        return (weight);
    }

    //RETURNS THE VALUE
    public int getValue() {
        return (value);
    }

    //GETTER FOR INCLUDED VARIABLE
    public boolean isIncluded() {
        return(included);
    }

    // Setter for the item’s included field

    public void setIncluded(boolean included) {
        this.included = included;
    }

    // // Displays the item in the form <name> (<weight> lbs, $<value>)
    public String toString() {
        return (getName() + " (" + getWeight() + " lbs, $" + getValue() +")");
    }

}