package atlas.model.algorithms;

import java.util.List;

import atlas.model.Body;

public abstract class CollisionStrategy implements java.io.Serializable {

	private static final long serialVersionUID = 2351707986649495199L;
	
	protected final boolean detectCollision(Body a, Body b) {
		return a.distanceTo(b) <= a.getProperties().getRadius() + b.getProperties().getRadius();
	}
	
	public abstract List<Body> manageCollision(List<Body> sim, Body a, Body b);
}