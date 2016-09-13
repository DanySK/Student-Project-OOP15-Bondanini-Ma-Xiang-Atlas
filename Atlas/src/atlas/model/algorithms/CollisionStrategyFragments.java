package atlas.model.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import atlas.controller.ControllerImpl;
import atlas.model.Body;
import atlas.model.BodyImpl;
import atlas.model.BodyType;

public class CollisionStrategyFragments extends CollisionStrategy {

	private static final long serialVersionUID = -1811886471163319254L;

	private static double ATTRACTING_FRAGMENTS_PERCENTAGE = 100;
	// number of times to shrink mass
	private static int MAX_REDUCTION = 5000; // smallest fragment
	private static int MIN_REDUCTION = 10; // largest fragment
	
	private static double ANG_OFFSET = 5;

	private int numFragments;

	public CollisionStrategyFragments(int fragments) {
		this.numFragments = fragments;
	}

	@Override
	public List<Body> manageCollision(List<Body> sim, Body a, Body b) {
		if (this.detectCollision(a, b)) {
			// target = the smaller one | targeted is the bigger one
			Body target = a.getMass() < b.getMass() ? a : b;
			Body targeted = target.equals(a) ? b : a;
			switch (target.getType()) {
			case STAR:
			case BLACKHOLE:
				// need to do something else
			case PLANET:
			case DWARF_PLANET:
			case SATELLITE:
				sim.addAll(this.spawnFragments(target, targeted));
				break;
			default:
				targeted.setMass(target.getMass() + targeted.getMass());
				break;
			}
			sim.remove(target);
		}
		return sim;
	}

	private List<Body> spawnFragments(Body body, Body parent) {
		List<Body> fragments = new ArrayList<>();

		int numAttract = (int) (numFragments * 100 / ATTRACTING_FRAGMENTS_PERCENTAGE);
		Random rand = new Random();

		for (int i = 0; i < numFragments; i++, numAttract--) {
			double reduction = rand.nextInt(MAX_REDUCTION - MIN_REDUCTION + 1) + MIN_REDUCTION;
			System.out.println("reduction = " + reduction);
			
			double rotAngle = i % 2 == 0 ? Math.toRadians(i*ANG_OFFSET) : - Math.toRadians(i*ANG_OFFSET);
			double x = body.getPosX() * Math.cos(rotAngle) - body.getPosY() * Math.sin(rotAngle);
			double y = body.getPosX() * Math.sin(rotAngle) + body.getPosY() * Math.cos(rotAngle);
			double length = Math.sqrt(x * x + y * y);
			double multiplier = (length + body.getProperties().getRadius()) / length;
			x *= multiplier;
			y *= multiplier;
			
			Double temp = null;
			if (body.getProperties().getTemperature().isPresent()
					&& parent.getProperties().getTemperature().isPresent()) {
				temp = body.getProperties().getTemperature().get() + parent.getProperties().getTemperature().get();
			}
			Body fragment = new BodyImpl.Builder().name(body.getName().concat("_FRAGMENT").concat("" + i))
					.type(BodyType.FRAGMENT).mass(body.getMass() / reduction)
					.posX(x)
					.posY(y)
					.velX(body.getVelX() / ( numFragments - i + 1 ))
					.velY(body.getVelY() / ( numFragments - i + 1 ))
					.properties(new Body.Properties(body.getProperties().getRadius() / numFragments,
							body.getProperties().getRotationPeriod() / (long) reduction, null, parent, temp))
					.build();
			fragment.setAttracting(numAttract > 0);
			numAttract = numAttract > 0 ? numAttract : 0;

			fragments.add(fragment);
		}
		return fragments;
	}
}
