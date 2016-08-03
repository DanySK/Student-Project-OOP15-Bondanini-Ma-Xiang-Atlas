package atlas.model.algorithms;

import java.util.ArrayList;

import atlas.model.Body;

public abstract class AlgorithmGeneric implements Algorithm, java.io.Serializable {

	private static final long serialVersionUID = -766146245161256993L;
	
	protected CollisionStrategy collisionStrategy;
	
	@Override
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
	
}
