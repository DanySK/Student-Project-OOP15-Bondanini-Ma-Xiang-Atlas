package atlas.model.algorithms;

import java.util.ArrayList;
import java.util.List;

import atlas.model.Body;

/**
 * This algorithm computes, for each body, the net force from all other bodies.
 * Complexity is N^2. Pros: accurate results, simple. Cons: impossible to use
 * when there are a lot of elements.
 * 
 */
public class AlgorithmBruteForce extends AlgorithmGeneric {

	private static final long serialVersionUID = -766146245161256993L;

	public AlgorithmBruteForce(CollisionStrategy collisionStrategy) {
		super();
		super.collisionStrategy = collisionStrategy;
	}

	@Override
	public ArrayList<Body> exceuteUpdate(ArrayList<Body> bodies, double sec) {
		// 2 loops --> N^2 complexity
		ArrayList<Body> copy = new ArrayList<>(bodies);
		for (Body b : copy) {
			b.resetForce();
			for (Body c : copy) {
				if (b != null && !b.equals(c)) {
					if(bodies.contains(b) && bodies.contains(c)) {
						this.collisionStrategy.manageCollision(bodies, b, c);
					}
					b.addForce(c);
				}
			}
			b.updatePos(sec);
		}
		return bodies;
	}
//	public ArrayList<Body> exceuteUpdate(ArrayList<Body> bodies, double sec) {
//		// 2 loops --> N^2 complexity
//		bodies.stream().filter(i -> i.isAttracting()).forEach(b -> {
//			b.resetForce();
//			for (Body c : bodies) {
//				if (b != null && !b.equals(c)) {
//					this.collisionStrategy.manageCollision(bodies, b, c);
//					b.addForce(c);
//				}
//			}
//			b.updatePos(sec);
//		});
//		return bodies;
//	}
}

// public class AlgorithmBruteForce implements Algorithm, java.io.Serializable {
//
// private static final long serialVersionUID = -766146245161256993L;
//
// @Override
// public ArrayList<Body> exceuteUpdate(ArrayList<Body> bodies, double sec) {
// // 2 loops --> N^2 complexity
// for (Body b : bodies) {
// b.resetForce();
// for (Body c : bodies) {
// if (!b.equals(c)) {
// b.addForce(c);
// }
// }
// b.updatePos(sec);
// }
// return bodies;
// }
// }