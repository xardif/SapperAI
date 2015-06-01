package pl.edu.amu.wmi.sapper.clones;



public class FitnessCalc {

        static byte[] solution = new byte[64];
        /* Public methods */
        // Ustawienie parametr�w kandydata jako ci�g bit�w
        public static void setSolution(byte[] newSolution) {
            solution = newSolution;
        }

        // Reprezentacja skilli kandydata za pomoc� 0 i 1
        static void setSolution(String newSolution) {
            solution = new byte[newSolution.length()];
            // P�tla po ka�dym znaku i zapisywanie do byte array'a
            for (int i = 0; i < newSolution.length(); i++) {
                String character = newSolution.substring(i, i + 1);
                if (character.contains("0") || character.contains("1")) {
                    solution[i] = Byte.parseByte(character);
                } else {
                    solution[i] = 0;
                }
            }
        }

        // Oblicznie fitnessa osobnika przez por�wnanie do do stworzonego kandydata
        static int getFitness(Individual individual) {
            int fitness = 0;
            //P�tla po genach osobnika i por�wanie ich z genami kandydata
            for (int i = 0; i < individual.size() && i < solution.length; i++) {
                if (individual.getGene(i) == solution[i]) {
                    fitness++;
                }
            }
            return fitness;
        }

        // Optymalny fitness
        static int getMaxFitness() {
            int maxFitness = solution.length;
            return maxFitness;
        }
}
