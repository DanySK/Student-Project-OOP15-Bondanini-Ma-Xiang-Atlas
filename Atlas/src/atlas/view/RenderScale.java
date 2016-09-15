package atlas.view;

import java.util.ArrayList;
import java.util.List;

import atlas.model.BodyType;
import atlas.utils.Pair;

public enum RenderScale {
	REAL,
	/*
	 * Order: Blackhole - Star - Planet - Dwarf planet - Satellite - Comet -
	 * Asteroid - Fragment - Object
	 */
	SMALL(80, 60, 30, 25, 15, 5, 5, 5, 15), MEDIUM(100, 80, 55, 30, 25, 10, 10, 10, 25), LARGE(150, 110, 80, 60, 50, 25,
			25, 25, 50);

	/* Size for each body type */
	private List<Pair<BodyType, Double>> sizes = new ArrayList<>();

	private RenderScale() {
	}

	private RenderScale(double... values) {
		if (values.length != BodyType.values().length) {
			throw new IllegalStateException();
		}

		for (int i = 0; i < values.length; i++) {
			sizes.add(new Pair<>(BodyType.values()[i], values[i]));
		}
	}

	public double getSize(BodyType type) {
		return sizes.stream().filter(p -> p.getX() == type)
				.findAny().orElseThrow(() -> new IllegalStateException())
				.getY();
	}
}
