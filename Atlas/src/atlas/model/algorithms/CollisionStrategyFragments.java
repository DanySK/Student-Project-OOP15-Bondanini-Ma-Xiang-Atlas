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

	private static double ATTRACTING_FRAGMENTS_PERCENTAGE = 0.1;

	private int numFragments;

	public CollisionStrategyFragments(int fragments) {
		this.numFragments = fragments;
	}

	@Override
	public List<Body> manageCollision(List<Body> sim, Body a, Body b) {
		if (this.detectCollision(a, b)) {
			//target = the smaller one | targeted is the bigger one
			Body target = a.getMass() < b.getMass() ? a : b;
			Body targeted = a.getMass() > b.getMass() ? a : b;
//			sim.remove(target);
			switch(target.getType()) {
			case STAR:
			case BLACKHOLE:
				//need to do something else
			case PLANET:
			case DWARF_PLANET:
			case SATELLITE:
				sim.addAll(this.spawnFragments(target, targeted));
				break;
			default:
				targeted.setMass(target.getMass() + targeted.getMass());
				break;
			}
		}
		return sim;
	}

	private List<Body> spawnFragments(Body body, Body parent) {
		List<Body> fragments = new ArrayList<>();
		int numAttract = (int) (numFragments * ATTRACTING_FRAGMENTS_PERCENTAGE);
		int multiplier = 10;
		int offset = 3;
		Random rand = new Random();

		for (int i = 0; i < numFragments; i++, numAttract--) {
			double reduction = (rand.nextDouble() / (rand.nextInt(11) + 1));
			Double temp = null;
			if (body.getProperties().getTemperature().isPresent()
					&& parent.getProperties().getTemperature().isPresent()) {
				temp = body.getProperties().getTemperature().get() + parent.getProperties().getTemperature().get();
			}
			Body fragment = new BodyImpl.Builder().name(body.getName().concat("_FRAGMENT").concat("" + i))
					.type(BodyType.FRAGMENT)
					.mass(body.getMass() * reduction)
					.posX(body.getPosX() + rand.nextDouble() * rand.nextInt(100000))
					.posY(body.getPosY() + rand.nextDouble() * rand.nextInt(100000))
					.velX(body.getVelX() * (rand.nextDouble() * multiplier - offset))
					.velY(body.getVelY() * (rand.nextDouble() * multiplier - offset))
					.properties(
							new Body.Properties(body.getProperties().getRadius() * reduction,
									body.getProperties().getRotationPeriod() * (long) reduction,
							null, parent, temp))
					.build();
			fragment.setAttracting(numAttract > 0);
			numAttract = numAttract > 0 ? numAttract - 1 : 0;

			fragments.add(fragment);
		}
		return fragments;
	}
}
