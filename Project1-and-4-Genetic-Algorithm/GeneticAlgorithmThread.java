import java.util.*;
import java.io.*;

public class GeneticAlgorithmThread extends Thread {
    private int start;
    private int end;
    private ArrayList<Chromosome> bestChromosomes;

    public GeneticAlgorithmThread(int start, int end, ArrayList<Chromosome> bestChromosomes) {
        this.start = start;
        this.end = end;
        this.bestChromosomes = bestChromosomes;
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

    //CREATES THE INITIAL POPULATION OF CHROMOSOMES
    public static ArrayList<Chromosome> initializePopulation(ArrayList<Item> items, int populationSize)
            throws FileNotFoundException {
        Chromosome chromosome = new Chromosome(items);
        ArrayList<Chromosome> populationSizeChromosome = new ArrayList<Chromosome>();

        // CREATES THE THE POPULATION SIZE AMOUNT OF CHROMOSOMES AND ADDS THEM TO THE
        // POPULATIONSIZECHROMOSOME ARRAY LIST
        for (int i = 0; i < populationSize; i++) {
            populationSizeChromosome.add(chromosome);
            chromosome = new Chromosome(readData("more_items.txt"));
        }

        return (populationSizeChromosome);
    }

    public ArrayList<Chromosome> getBestChromosomes(){
        return(bestChromosomes);
    }
    public void run() {
        ArrayList<Chromosome> initialPopulation = new ArrayList<>();
        try{
            initialPopulation = initializePopulation(readData("more_items.txt"), 100);

        }catch(FileNotFoundException e){

        }
        ArrayList<Chromosome> nextGeneration = new ArrayList<>();

        // ADDS INITIAL POPULATION TO NEXTGENERATION
        for (int i = 0; i < initialPopulation.size(); i++) {
            nextGeneration.add(initialPopulation.get(i));
        }

        for (int j = start; j < end; j++) {
            Random random = new Random();
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

            //exception
            Collections.sort(nextGeneration);

            // ADDS THE TOP 10 OF THE NEXT GENERATION TO THE INITIAL POPULATION
            for (int l = 0; l < 10; l++) {
                initialPopulation.add(nextGeneration.get(l));
            }
        }
        for (int l = 0; l < 10; l++) {
            bestChromosomes.add(initialPopulation.get(l));
        }

    }
}
