package pl.edu.amu.wmi.sapper.clones;

public class GA {
    public static void main(String[] args) {

        // Ustawiamy skille osobnika na te, kt�re chcemy uzyska�
        FitnessCalc.setSolution("1111000000000000000000000000000000000000000000000000000000001111");

        // Tworzymy pocz�tkow� populacje
        Population myPop = new Population(50, true);

        // Ewolucja populacji dop�ki nie znajdziemy optymalnego rozwi�zania
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
