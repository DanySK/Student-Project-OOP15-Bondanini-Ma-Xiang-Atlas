package atlas.model.algorithms;

import java.util.List;

import atlas.model.Body;

/**
 * This algorithm computes, for each body, the net force from all other bodies.
 * Complexity is N^2.
 * Pros: accurate results, simple.
 * Cons: impossible to use when there are a lot of elements.
 * 
 */
public class AlgorithmBruteForce implements Algorithm {

    @Override
    public List<Body> exceuteUpdate(List<Body> bodies, double sec) {
    	// 2 loops --> N^2 complexity
        for (Body b : bodies) {
            b.resetForce();
            for (Body c : bodies) {
                if (!b.equals(c)) {
                    b.addForce(c);
                }
            }
            b.updatePos(sec);
        }
        return bodies;
    }
}
