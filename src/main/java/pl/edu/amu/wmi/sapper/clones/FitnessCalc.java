package pl.edu.amu.wmi.sapper.clones;



public class FitnessCalc {

        static byte[] solution = new byte[21];
        /* Public methods */

        public static void setSolution(byte[] newSolution) {
            solution = newSolution;
        }


        static void setSolution(String newSolution) {
            solution = new byte[newSolution.length()];

            for (int i = 0; i < newSolution.length(); i++) {
                String character = newSolution.substring(i, i + 1);
                if (character.contains("0") || character.contains("1")) {
                    solution[i] = Byte.parseByte(character);
                } else {
                    solution[i] = 0;
                }
            }
        }


        static int getFitness(Individual individual) {
            int fitness = 0;

            for (int i = 0; i < individual.size() && i < solution.length; i++) {
                if (individual.getGene(i) == solution[i]) {
                    fitness++;
                }
            }
            return fitness;
        }


        static int getMaxFitness() {
            int maxFitness = solution.length;
            return maxFitness;
        }
}
