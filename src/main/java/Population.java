import com.google.common.base.Function;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Population {

    @Getter
    private List<Chromosom> chromes;

    @Getter
    private int capacity;

    @Getter
    private double start;

    @Getter
    private double end;

    public Population(int capacity) {
        chromes = new ArrayList<Chromosom>(capacity);
        this.capacity = capacity;
    }

    public void addChrome(Chromosom chrome) {
        this.chromes.add(chrome);
    }

    // 20 rozwiazan // 22 bity precyzji
    public void generateRandomPopulation(int precision) {
        for (int i = 0; i < this.capacity; i++) {
            Chromosom chr = new Chromosom(precision);
            chr.randChrome();
            chromes.add(chr);
        }
    }

    public void generateFitnessForChromes(Function<Double, Double> function, double start, double end, double additional) {
        this.start = start;
        this.end = end;
        for (int i = 0; i < this.capacity; i++) {
            chromes.get(i).calculateFitness(function, start, end, additional);
        }
    }

    public void sortPopulationByFitness() {
        this.chromes.sort((o1, o2) -> o1.getFitness() < o2.getFitness() ? -1 : o1.getFitness() == o2.getFitness() ? 0 : 1);
    }

    public void printPopulation() {
        this.chromes.forEach(ch -> {
            System.out.println("Chrome : " + ch.getChrome() + " fitness: " + ch.getFitness());
        });
    }
}
