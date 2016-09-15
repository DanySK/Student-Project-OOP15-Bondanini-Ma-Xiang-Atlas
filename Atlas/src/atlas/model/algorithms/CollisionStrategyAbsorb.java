package atlas.model.algorithms;

import java.util.List;

import atlas.model.Body;

public class CollisionStrategyAbsorb extends CollisionStrategy {

	private static final long serialVersionUID = 2765549760719768625L;

	@Override
	public List<Body> manageCollision(List<Body> sim, Body a, Body b) {

		// Dectect the collision first
		if (this.detectCollision(a, b)) {
			Body target = a.getMass() < b.getMass() ? a : b;
			Body targeted = target.equals(a) ? b : a;

			// Radius increases according to the masses ratio
			targeted.getProperties().setRadius( target.getProperties().getRadius() + targeted.getProperties().getRadius());
			targeted.setMass(target.getMass() + targeted.getMass());
			// The smaller body is removed
			sim.remove(target);
		}
		return sim;
	}

}
