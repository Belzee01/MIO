
public class Main {
    public static void main(String[] args) {

        Population population = new Population(20);

        population.generateRandomPopulation(22);

        population.generateFitnessForChromes((Double x) -> x*Math.sin(10*Math.PI * x) + 1.0, -1.0, 2.0, 10.0);

        population.sortPopulationByFitness();

        GA ga = new GA(population);

        for (int i = 0; i < 100; i++) {
            System.out.println("\n---------------------------ITERATION : " + i);

            ga.selectParents();
            ga.crossOver();
            ga.mutate();

            population = ga.getPopulation();
            population.generateFitnessForChromes((Double x) -> x*Math.sin(10*Math.PI * x) + 1.0, -1.0, 2.0, 10.0);
            ga.getPopulation().sortPopulationByFitness();
        }

        System.out.println("Function maximum: " + (ga.getPopulation().getChromes().get(19).getFitness()-10.0));
    }
}
