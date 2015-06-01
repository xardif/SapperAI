package pl.edu.amu.wmi.sapper.clones;

public class GA {
    public static void main(String[] args) {

        // Ustawiamy skille osobnika na te, które chcemy uzyskaæ
        FitnessCalc.setSolution("1111000000000000000000000000000000000000000000000000000000001111");

        // Tworzymy pocz¹tkow¹ populacje
        Population myPop = new Population(50, true);

        // Ewolucja populacji dopóki nie znajdziemy optymalnego rozwi¹zania
        int generationCount = 0;
        while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
            generationCount++;
            System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
            myPop = Algorithm.evolvePopulation(myPop);
        }
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Genes:");
        System.out.println(myPop.getFittest());

    }
}
