//Alexander Shampton
//CS-1181L-07
//Due: 2/13/2022

import java.util.ArrayList;
import java.util.Random;

public class Chromosome extends ArrayList<Item> implements
        Comparable<Chromosome> {
    private static Random rng = new Random();

    public Chromosome() {
    }

    public Chromosome(ArrayList<Item> items) {
        // GOES THROUGH EVERY ITEM IN THE ARRAY LIST AND MAKES THEIR INCLUDED VALUE TRUE
        // OR FALSE RANDOMLY, THEN THE LIST OF ITEMS ARE ADDED TO THIS OBJECT
        for (int i = 0; i < items.size(); i++) {
            int randomNum = rng.nextInt(2);
            if (randomNum == 0) {
                items.get(i).setIncluded(true);
            } else {
                items.get(i).setIncluded(false);
            }
            this.add(items.get(i));
        }
    }

    public Chromosome crossover(Chromosome other) {
        // CREATES RANDOM NUMBERS FOR EACH ITEM AND IF THE ITEM'S RANDOM NUMBER IS ABOVE
        // 5, IT SETS THE ITEM'S INCLUDED VARIABLE TO THE OTHER ITEM'S
        for (int i = 0; i < this.size(); i++) {
            int randomNum = rng.nextInt(10) + 1;
            if (randomNum > 5) {
                this.get(i).setIncluded(other.get(i).isIncluded());
            } else {
                this.get(i).setIncluded(this.get(i).isIncluded());
            }
        }
        return (this);
    }

    // TAKES EVERY ITEM AND CREATES A RANDOM NUMBER AND IF THAT RANDOM NUMBER IS
    // EQUAL TO THE SET RANDOM NUMBER THE INCLUDED VARIABLE IS CHANGED
    public void mutate() {
        int randomNumtoMutate = rng.nextInt(10) + 1;
        for (int i = 0; i < this.size(); i++) {
            int randomNum = rng.nextInt(10) + 1;
            if (randomNum == randomNumtoMutate) {
                if (this.get(i).isIncluded() == true) {
                    this.get(i).setIncluded(false);
                } else {
                    this.get(i).setIncluded(true);
                }
            }
        }
    }

    // CHECKS EVERY ITEM THAT IS INCLUDED AND ADDS ITS FITNESS AND VALUES, AND IF
    // THE VALUES ARE OVER 10, THE FITNESS IS 0. IF THE VALUES DO NOT GO OVER 10,
    // THE FITNESS IS RETURNED
    public int getFitness() {
        double fitness = 0;
        int valuesAdded = 0;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).isIncluded()) {
                fitness += this.get(i).getWeight();
                valuesAdded += this.get(i).getValue();
            }
        }
        if (fitness < 10) {
            return (valuesAdded);
        } else {
            return (0);
        }
    }

    // COMPARES FITNESS TO SORT ARRAY ACCORDING TO FITNESS
    public int compareTo(Chromosome other) {
        if (this.getFitness() < other.getFitness()) {
            return (1);
        } else if (this.getFitness() > other.getFitness()) {
            return (-1);
        } else {
            return (0);
        }
    }

    // PRINTS THE CHROMOSOME AND ITS FITNESS
    public String toString() {
        String returnStatement = "";
        for (Item item : this) {
            if (item.isIncluded()) {
                returnStatement += item + " ";
            }
        }
        return (returnStatement + "\nFitness of Chromosome: " + this.getFitness() + "\n");
    }
}
