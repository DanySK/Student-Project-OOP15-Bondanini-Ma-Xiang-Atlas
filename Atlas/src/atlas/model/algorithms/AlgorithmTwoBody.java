package atlas.model.algorithms;

import java.util.List;

import atlas.model.Body;
import atlas.model.BodyImpl;

/**
 * Alan add detail!
 *
 */
public class AlgorithmTwoBody implements Algorithm {

	@Override
	public List<Body> exceuteUpdate(List<Body> bodies, double sec) {
		for (Body b : bodies) {
			b.resetForce();
			Body p = b.getProperties().getParent().orElseGet(null);
			if (p != null) {
				b.addForce(p);
			} else {
				// select largest body of the first one
//				Body target = bodies.stream().filter(i -> !i.equals(b))
//						.max((i, j) -> (int) i.getMass() - (int) j.getMass()).orElseGet(null);
//				if( target != null) {
//					b.addForce(target);
//				}
				for (Body c : bodies) {
					if (!b.equals(c)) {
						b.addForce(c);
					}
				}
			}
			b.updatePos(sec);
		}
		return bodies;
	}
}