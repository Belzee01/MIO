package swarm;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

public class Swarm {

    @Getter
    @Setter
    private Population population;

    public Swarm(Pair<Double, Double> xEdge, Pair<Double, Double> yEdge, int populationSize) {
        this.population = new Population(populationSize, xEdge.getFirst(), xEdge.getSecond(), yEdge.getFirst(), yEdge.getSecond());
        this.population.initializeRandomParticles();
    }

    public void evaluateFitnessInPopulation(Function<Pair<Double, Double>, Double> function) {
        this.population.getParticles().forEach(p -> {
            p.calculateFitness(function);
        });
    }

    public Particle getGlobalBest() {
        sortPopulationByFitness();
        return this.population.getParticles().get(0);
    }

    private void sortPopulationByFitness() {
        this.population.getParticles().sort((o1, o2) -> o1.getFitness() < o2.getFitness() ? -1 : Objects.equals(o1.getFitness(), o2.getFitness()) ? 0 : 1);
    }

    public void updateParticlePlacementAndVelocity(double c1, double c2, Pair<Double, Double> gBest) {
        this.population.getParticles().forEach(p -> {
            Random random = new Random();
            double w1 = c1 + (0.0 - c1) * random.nextDouble();
            double w2 = c2 + (0.0 - c2) * random.nextDouble();

            Pair<Double, Double> temp1 = new Pair<>(w1 * (p.getPersonalBest().getFirst() - p.getPlacement().getFirst()),
                    w1 * (p.getPersonalBest().getSecond() - p.getPlacement().getSecond()));

            Pair<Double, Double> temp2 = new Pair<>(w2 * (gBest.getFirst() - p.getPlacement().getFirst()),
                    w2 * (gBest.getSecond() - p.getPlacement().getSecond()));

            Pair<Double, Double> newVelocity = new Pair<>(
                    p.getVelocity().getFirst() + temp1.getFirst() + temp2.getFirst(),
                    p.getVelocity().getSecond() + temp2.getSecond() + temp1.getSecond()
            );

            System.out.println("New Velocity: " + newVelocity);
            p.setVelocity(newVelocity);

            Pair<Double, Double> newPlacement = new Pair<>(
                    evaluateEdgeValueX(p.getPlacement().getFirst() + p.getVelocity().getFirst()),
                    evaluateEdgeValueY(p.getPlacement().getSecond() + p.getVelocity().getSecond())
            );

            p.setPlacement(newPlacement);
        });
    }

    private double evaluateEdgeValueX(Double value) {
        boolean flag = false;
        double newValue = value;
        do {
            if (value > this.population.getXEnd()) {
                double temp = Math.abs(value - this.population.getXEnd());
                newValue = this.population.getXEnd() - temp;
            } flag = true;
            if(value < this.population.getXStart()) {
                flag = false;
                double temp = Math.abs(value - this.population.getXStart());
                newValue = this.population.getXEnd() + temp;
            }
        } while (!flag);
        return newValue;
    }

    private double evaluateEdgeValueY(Double value) {
        boolean flag = false;
        double newValue = value;
        do {
            if (value > this.population.getXEnd()) {
                double temp = Math.abs(newValue - this.population.getYEnd());
                newValue = this.population.getYEnd() - temp;
            } flag = true;
            if(value < this.population.getYStart()) {
                flag = false;
                double temp = Math.abs(newValue - this.population.getYStart());
                newValue = this.population.getXEnd() + temp;
            }
        } while (!flag);
        return newValue;
    }
}
