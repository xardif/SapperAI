import org.junit.Test;
import pl.edu.amu.wmi.sapper.clones.Algorithm;
import pl.edu.amu.wmi.sapper.clones.FitnessCalc;
import pl.edu.amu.wmi.sapper.clones.Population;
import pl.edu.amu.wmi.sapper.clones.Skills;


public class GATest {
    @Test
    public  void test() {
        Skills solution= new Skills();

        // Set a candidate solution
        //FitnessCalc.setSolution(solution.getskills());

        /* Create an initial population */
        Population myPop = new Population(50, true);
        
        // Evolve our population until we reach an optimum solution
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
