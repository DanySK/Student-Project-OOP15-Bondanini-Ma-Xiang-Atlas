package atlas.model.algorithms;

import java.util.ArrayList;

import atlas.model.Body;

public class Algorithm implements java.io.Serializable {

	private static final long serialVersionUID = -766146245161256993L;

	protected CollisionStrategy collisionStrategy = new CollisionStrategyFragments();

	/**
	 * It updates the simulation according to a specific n-body algorithm
	 * implementation.
	 * 
	 * @param bodies
	 *            input bodies to be updated
	 * @param sec
	 *            time step of the update
	 * @return the updated bodies
	 */
	public ArrayList<Body> exceuteUpdate(ArrayList<Body> bodies, double sec) {
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
	
	/**
	 * 
	 * @return the current algorithm's collision system
	 */
	public CollisionStrategy getCollisionStrategy() {
		return this.collisionStrategy;
	}

	/**
	 * Sets a new collision strategy, changes the collision rules.
	 * 
	 * @param strategy
	 *            the new strategy
	 */
	public void setCollisionStrategy(CollisionStrategy strategy) {
		this.collisionStrategy = strategy;
	}

}
