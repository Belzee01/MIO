

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GA {

    @Getter
    private Population population;

    private Population newPopulation;

    private List<Pair<Chromosom, Chromosom>> parents;

    public GA(Population population) {
        this.population = population;

        this.parents = new ArrayList<>();
    }

    public void selectParents() {

        while(population.getChromes().size() > 12) {
            final Chromosom[] newParents = {null, null};
            for (int i = 0; i < 2; i++) {
                final double[] maxFitness = {0.0};
                population.getChromes().forEach(ch -> {
                    maxFitness[0] += Math.abs(ch.getFitness());
                });

//                System.out.println("Max fitness : " + maxFitness[0]);

                Random r = new Random();
                final double[] randomValue = {0.0 + (100.0 - 0.0) * r.nextDouble()};
                List<Double> percentages = new ArrayList<>();
                population.getChromes().forEach(ch -> {
                    percentages.add((ch.getFitness()/maxFitness[0])*100);
                });

                final double[] initialPercent = {0.0};
                for (Double p : percentages) {
                    initialPercent[0] += p;
                    if (randomValue[0] <= initialPercent[0]) {
                        newParents[i] = population.getChromes().get(percentages.indexOf(p));
                        population.getChromes().remove(newParents[i]);
                        break;
                    }
                }

//                System.out.println(i + " parent: " + newParents[i].getFitness());
            }

            parents.add(new Pair<>(newParents[0], newParents[1]));

//            System.out.println("Population size : " + population.getChromes().size());
        }

        parents.forEach(p -> {
            System.out.println("First parent : " + p.getFirst().getChrome() + " ------ Second parent : " + p.getSecond().getChrome());
        });
    }

    public void crossOver() {

        System.out.println("Cross over!");
        int parentChromeLength = parents.get(0).getFirst().getChrome().length();
        this.newPopulation = new Population(population.getCapacity());

        parents.forEach(p -> {
            Random random = new Random();
            int randomNumber = random.nextInt(parentChromeLength - 1) + 1;

            Chromosom firstParent = p.getFirst();
            Chromosom secondParent = p.getSecond();
            StringBuilder sb1 = new StringBuilder("");
            StringBuilder sb2 = new StringBuilder("");

            for (int i = 0; i < randomNumber; i++) {
                sb1.append(firstParent.getChrome().charAt(i));
                sb2.append(secondParent.getChrome().charAt(i));
            }
            for (int i = randomNumber; i < parentChromeLength; i++) {
                sb1.append(secondParent.getChrome().charAt(i));
                sb2.append(firstParent.getChrome().charAt(i));
            }

            Chromosom firstChild = new Chromosom(firstParent.getPrecision()).setChrome(sb1.toString());
            Chromosom secondChild = new Chromosom(secondParent.getPrecision()).setChrome(sb2.toString());

            this.newPopulation.addChrome(firstChild);
            this.newPopulation.addChrome(secondChild);

            System.out.println("New 1 child : " + firstChild + " new 2 child: " +secondChild);
        });
        this.population.getChromes().addAll(this.newPopulation.getChromes());
        this.parents.clear();
    }

    public void mutate() {
        System.out.println("Mutation! ");
        this.population.getChromes().forEach(ch -> {
            Random random = new Random();
            int index = random.nextInt(ch.getChrome().length());

            int mutationPercentage = random.nextInt(1000);
            if(mutationPercentage <= 1) {
                System.out.println("Bit to be mutated: " + index);
                System.out.println("Mutating ...");
                StringBuilder sb = new StringBuilder(ch.getChrome());
                char c = ch.getChrome().charAt(index);
                if (c == '1')
                    sb.setCharAt(index, '0');
                else
                    sb.setCharAt(index, '1');

                ch.setChrome(sb.toString());
            }
        });
    }
}
