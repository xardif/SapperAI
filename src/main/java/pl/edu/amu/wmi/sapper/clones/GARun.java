package pl.edu.amu.wmi.sapper.clones;


import pl.edu.amu.wmi.sapper.map.objects.types.BombType;

import java.util.Queue;
import java.util.Random;

public class GARun {
    public String runGA(Queue<BombType> result){
        Skills skills = new Skills();

        String targetSkills = skills.getskills(result);
        System.out.println("!!!!!! Pre Target Skills: " + targetSkills);
        FitnessCalc.setSolution(targetSkills);

		/* Create an initial population */
        Population myPop = new Population(50, true);

        // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        Random rand = new Random();
        int limit = rand.nextInt(3) + 1;
        while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness() && generationCount < limit) {
            generationCount++;
            System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
            myPop = Algorithm.evolvePopulation(myPop);
        }

        String actualSkills = myPop.getFittest().toString();

        System.out.println("!!!!!! Target Skills: " + targetSkills);
        System.out.println("!!!!!! Actual Skills: " + actualSkills);
        return actualSkills;
    }
}
