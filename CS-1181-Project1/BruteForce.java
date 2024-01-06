import java.util.*;
import java.io.*;

public class BruteForce {
    public static ArrayList<Item> getOptimalSet(ArrayList<Item> items,ArrayList<Item> newItems, Item item){
        if (items.size() == 0) {
            System.out.println(item + "\n");
        } else {
            for (int i = items.size(); i > 0; i--) {
                newItems.add(item);
                //items.remove(i);
                //newItems.add(item);
                getOptimalSet(items,newItems, items.get(i+1));
            }
        }
        return(items);
    }

    public static ArrayList<Item> readData(String filename) throws FileNotFoundException {
        Scanner fileReader = new Scanner(new File(filename));
        ArrayList<Item> arrayList = new ArrayList<Item>();
        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            String[] result = line.split(", ");
            double weight = Double.parseDouble(result[1]);
            int value = Integer.parseInt(result[2]);
            Item item = new Item(result[0], weight, value);
            arrayList.add(item);
        }
        return (arrayList);
    }

    public static void main(String [] args){           
        try{
            ArrayList<Item> items = readData("items.txt"); 
            ArrayList<Item> newItems = new ArrayList<>(); 
            Item item = items.get(0);
            System.out.println(getOptimalSet(items, newItems, item));
        }catch(FileNotFoundException e){

        }
    }   
}
