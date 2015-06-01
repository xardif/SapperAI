package pl.edu.amu.wmi.sapper.clones;


public class Population {
    Individual[] individuals;


    // Tworzenie populacji
    public Population(int populationSize, boolean initialise) {
        individuals = new Individual[populationSize];
        // Inicjalizacja osobników
        if (initialise) {
            // Tworzenie osobników
            for (int i = 0; i < size(); i++) {
                Individual newIndividual = new Individual();
                newIndividual.generateIndividual();
                saveIndividual(i, newIndividual);
            }
        }
    }


    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public Individual getFittest() {
        Individual fittest = individuals[0];
        // Pêtla po wszystkich osobnikach w celu znalezenia najlepszego
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    /* Public methods */
    // Rozmiar populacji
    public int size() {
        return individuals.length;
    }

    // Zapisywanie osobników
    public void saveIndividual(int index, Individual indiv) {
        individuals[index] = indiv;
    }
}
