package atlas.model;

import java.util.List;

/**
 * This algorithm computes, for each body, the net force from all other bodies.
 * Complexity is N^2.
 * Pros: accurate results, simple.
 * Cons: impossible to use when there are a lot of elements.
 * 
 */
public class AlgorithmBruteForce implements Algorithm {

    @Override
    public void exceuteUpdate(List<Body> bodies, double sec) {
        for (Body b : bodies) {
            b.resetForce();
            // 2 loops --> N^2 complexity
            for (Body c : bodies) {
                if (!b.equals(c)) {
                    b.addForce(c);
                }
            }
            b.updatePos(sec);
        }        
    }
}
