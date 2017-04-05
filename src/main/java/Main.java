import swarm.Particle;
import swarm.Swarm;

import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Function<swarm.Pair<Double, Double>, Double> function
                = (swarm.Pair<Double, Double> a) -> Math.pow(a.getFirst(), 2.0) + Math.pow(a.getSecond(), 2.0) -20 * (Math.cos(Math.PI*a.getFirst()) + Math.cos(Math.PI*a.getSecond()) - 2.0);

        final double c1 = 1.0;
        final double c2 = 1.0;

        swarm.Pair<Double, Double> xEdge = new swarm.Pair<>(-10.0, 10.0);
        swarm.Pair<Double, Double> yEdge = new swarm.Pair<>(-10.0, 10.0);

        final int populationSize = 40;

        Swarm swarm = new Swarm(xEdge, yEdge, populationSize);

        swarm.evaluateFitnessInPopulation(function);

        Particle gBest = swarm.getGlobalBest();

        swarm.getPopulation().printPopulation();

        System.out.println("gBest: " +gBest);

        swarm.updateParticlePlacementAndVelocity(c1, c2, gBest.getPersonalBest());

        System.out.println("\n--------------------------------------------\n");
        swarm.getPopulation().printPopulation();
    }
}
