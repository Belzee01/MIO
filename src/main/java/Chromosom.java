
import com.google.common.base.Function;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class Chromosom {

    @Getter
    @Setter
    private int precision;

    @Setter
    @Getter
    private double fitness;

    @Setter
    @Getter
    private String chrome;

    private final String MAX_VALUE_BINARY;

    private final Integer MAX_VALUE_DEC;

    public Chromosom(int precision) {
        this.precision = precision;

        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < precision; i++) {
            sb.append("1");
        }
        MAX_VALUE_BINARY = sb.toString();
        MAX_VALUE_DEC = Integer.parseInt(MAX_VALUE_BINARY, 2);
    }

    public void randChrome() {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < precision; i++) {
            if (Math.random() < 0.5) {
                sb.append("0");
            } else {
                sb.append("1");
            }
        }
        this.chrome = sb.toString();
    }

    private Double getFenotype(String chrome, double start, double end) {
        double temp = (double)Integer.parseInt(this.chrome, 2) / (MAX_VALUE_DEC/(Math.abs(end - start)));
        temp = temp - 1.0;

        return temp;
    }

    public void calculateFitness(Function<Double, Double> function, double start, double end, double additional) {
        this.fitness = function.apply(getFenotype(this.chrome, start, end)) + additional;
//        System.out.println("getFenotype : " + chrome + " start: " + start + " end: " +end + " fitness: " + this.fitness);
    }
}
