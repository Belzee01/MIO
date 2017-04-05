package swarm;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {

    @Getter
    private List<Particle> particles;

    @Getter
    private int populationSize;

    @Getter
    private Double xStart;
    @Getter
    private Double xEnd;

    @Getter
    private Double yStart;
    @Getter
    private Double yEnd;

    public Population(int populationSize) {
        particles = new ArrayList<>();
        this.populationSize = populationSize;
    }

    public Population(int populationSize, Double xStart, Double xEnd, Double yStart, Double yEnd) {
        particles = new ArrayList<>(populationSize);
        this.populationSize = populationSize;

        this.xStart = xStart;
        this.xEnd = xEnd;

        this.yStart = yStart;
        this.yEnd = yEnd;
    }

    public void initializeRandomParticles() {
        for(int i = 0; i < this.populationSize; i++) {
            Random r = new Random();
            /// For x placement
            double xPlace = xEnd + (xStart - xEnd) * r.nextDouble();

            /// For y placement
            double yPlace = yEnd + (yStart - yEnd) * r.nextDouble();

            /// For x vel
            double xVel = xEnd + (xStart - xEnd) * r.nextDouble();

            /// For y vel
            double yVel = yEnd + (yStart - yEnd) * r.nextDouble();

            Particle particle = new Particle(new Pair<>(xPlace, yPlace), new Pair<>(xVel, yVel));
            particles.add(particle);
        }
    }

    public void printPopulation() {
        this.particles.forEach(p -> {
            System.out.println(String.format("X :%s  Y :%s ; XVel :%s  YVel :%s ; Fitness :%s ", p.getPlacement().getFirst(), p.getPlacement().getSecond(), p.getVelocity().getFirst(), p.getVelocity().getSecond(), p.getFitness()));
        });
    }
}
