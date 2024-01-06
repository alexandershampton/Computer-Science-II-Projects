//Alexander Shampton
//CS-1181L-07
//Due: 2/13/2022

import java.util.*;
import java.io.*;

public class GeneticAlgorithm {

    // Reads in a data =ile with the format shown below and creates and returns an
    // Array List of Item objects.
    // item1_label, item1_weight, item1_value
    // item2_label, item2_weight, item2_value
    // ...
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

    //CREATES THE INITIAL POPULATION OF CHROMOSOMES
    public static ArrayList<Chromosome> initializePopulation(ArrayList<Item> items, int populationSize)
            throws FileNotFoundException {
        Chromosome chromosome = new Chromosome(items);
        ArrayList<Chromosome> populationSizeChromosome = new ArrayList<Chromosome>();

        // CREATES THE THE POPULATION SIZE AMOUNT OF CHROMOSOMES AND ADDS THEM TO THE
        // POPULATIONSIZECHROMOSOME ARRAY LIST
        for (int i = 0; i < populationSize; i++) {
            //populationSizeChromosome.add(chromosome);
            //Chromosome chromosome = new Chromosome(items);
            chromosome = new Chromosome(readData("items.txt"));
            populationSizeChromosome.add(chromosome);
        }

        return (populationSizeChromosome);
    }

    public static void main(String args[]) throws Exception {
        Random random = new Random();
        ArrayList<Chromosome> initialPopulation = new ArrayList<Chromosome>();
        ArrayList<Chromosome> nextGeneration = new ArrayList<Chromosome>();
        int initialPopulationSize = 10;
        initialPopulation = initializePopulation(readData("items.txt"), initialPopulationSize);

        // ADDS INITIAL POPULATION TO NEXTGENERATION
        for (int i = 0; i < initialPopulation.size(); i++) {
            nextGeneration.add(initialPopulation.get(i));
        }

        for (int j = 0; j < 2000; j++) {

            // CROSSES A RANDOM CHROMOSOME FROM AN INITIAL GENERATION WITH ANOTHER RANDOM
            // CHROMOSOME FROM THE INITIAL GENERATION AND ADDS THE CROSS OVER "CHILD" TO THE
            // NEXT GENERATION
            int randomCrossover = random.nextInt(initialPopulation.size());
            int secondRandomCrossover = random.nextInt(initialPopulation.size());
            Chromosome child = initialPopulation.get(randomCrossover)
                    .crossover(initialPopulation.get(secondRandomCrossover));
            nextGeneration.add(child);

            // MUTATES 10% OF THE NEXT GENERATION
            int tenPercentOfPopulation = (int) (nextGeneration.size() - (nextGeneration.size() * .90));
            for (int k = 0; k < tenPercentOfPopulation; k++) {
                int randomChromosome = random.nextInt(nextGeneration.size());
                nextGeneration.get(randomChromosome).mutate();
            }

            // CLEARS OUT THE INITIAL POPULATION
            initialPopulation = new ArrayList<Chromosome>();
            Collections.sort(nextGeneration);

            // ADDS THE TOP 10 OF THE NEXT GENERATION TO THE INITIAL POPULATION
            for (int l = 0; l < 10; l++) {
                initialPopulation.add(nextGeneration.get(l));
            }
        }

        Collections.sort(nextGeneration);
        // OUTPUTS THE FINAL TOP 10 OF THE NEXT GENERATION
        System.out.println(
                "TOP 10 FITNESS OF NEXT GENERATION \n---------------------------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < 10; i++) {
            System.out.println(i+1 + ": " + nextGeneration.get(i));
        }
    }
}