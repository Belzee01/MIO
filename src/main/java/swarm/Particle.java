package swarm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.function.Function;

@ToString
public class Particle {

    @Getter
    @Setter
    private Pair<Double, Double> placement;

    @Getter
    @Setter
    private Pair<Double, Double> velocity;

    @Getter
    @Setter
    private Pair<Double, Double> personalBest;

    @Getter
    @Setter
    private Double oldBest;

    @Getter
    @Setter
    private Double fitness;

    public Particle(Pair<Double, Double> placement, Pair<Double, Double> velocity) {
        this.placement = placement;
        this.velocity = velocity;
        this.personalBest = new Pair<>(0.0, 0.0);
        this.oldBest = Double.MAX_VALUE;
    }

    public void calculateFitness(Function<Pair<Double, Double>, Double> function) {
        this.fitness = function.apply(placement);

        if (this.fitness < this.oldBest) {
            this.oldBest = this.fitness;
            this.personalBest.setFirst(this.placement.getFirst());
            this.personalBest.setSecond(this.placement.getSecond());
        }
    }
}
