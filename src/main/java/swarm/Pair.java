package swarm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Pair<F, S> {
    private F first;
    private S second;
}