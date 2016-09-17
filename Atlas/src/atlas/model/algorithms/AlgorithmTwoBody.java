package atlas.model.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import atlas.model.Body;

public class AlgorithmTwoBody extends Algorithm {

	private static final long serialVersionUID = -7785821713075379790L;

	private static final int BODIES_TO_CONSIDER = 5;

	@Override
	public ArrayList<Body> exceuteUpdate(ArrayList<Body> bodies, double sec) {
		// select largest body of the first one
		List<Body> ordered = bodies.stream().sorted((i, j) -> (int) (j.getMass() - i.getMass()))
				.collect(Collectors.toList());
		
		List<Body> targets = new LinkedList<>();
		for (int i = 0; i < BODIES_TO_CONSIDER && i < ordered.size(); i++) {
			targets.add(ordered.get(i));
		}

		for (Body b : bodies) {
			if (b.isAttracting()) {
				b.resetForce();

				/* Add the force from the parent */
				b.getProperties().getParent().ifPresent(i -> {
					if(ordered.contains(i)) {
						b.addForce(i);
					}
				});
				
				/* Add the force from a few selected bodies */
				targets.forEach(j -> {
					if(!b.equals(j) && !j.equals(b.getProperties().getParent().orElse(b)) ){
						b.addForce(j);
					}
				});

				b.updatePos(sec);
			}
		}
		return bodies;
	}
}
