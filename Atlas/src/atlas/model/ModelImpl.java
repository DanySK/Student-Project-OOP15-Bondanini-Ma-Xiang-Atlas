package atlas.model;

import java.util.ArrayList;
import java.util.List;

import atlas.model.algorithms.Algorithm;
import atlas.model.algorithms.AlgorithmBruteForce;
import atlas.model.algorithms.CollisionStrategyFragments;

/**
 * Brute force N-body implementation
 *
 */
public class ModelImpl implements Model, java.io.Serializable {

	private static final long serialVersionUID = 1670664173059452174L;
	private Algorithm alg;
    private ArrayList<Body> bodies = new ArrayList<>();
    private SimClock clock = new SimClock();
    
//    public static void main(String s[]) {
//    	Model m = new ModelImpl();
//    	
//    	for(int i = 0 ; i < 200000; i ++) {
//    		m.updateSim(1000);
//    		System.out.println(m);
//    	}
//    }

    @Override
	public String toString() {
		return "ModelImpl [ clock=" + clock + "bodies=" + bodies +"]";
	}


	public ModelImpl() {

        this.alg = new AlgorithmBruteForce(new CollisionStrategyFragments(5));
//        Body b = EpochJ2000.EARTH.getBody();
//        b.setName("sun");
//        b.setMass(EpochJ2000.EARTH.getBody().getMass()+1000);
//        b.setPosX(-b.getPosX());
//        b.setPosY(0);
//        b.setVelocity(new Pair<>(new Double(-4000), new Double(0)));
//        
//        Body c = new BodyImpl(EpochJ2000.EARTH.getBody());
//        c.setName("earth");
//        c.setPosX(0);
//        c.setPosY(0);
//        c.setVelocity(new Pair<>(new Double(+4000), new Double(0)));
//        this.bodies.addAll(Arrays.asList(b,c));
        this.clock.setEpoch(EpochJ2000.TIME_MILLS);
        for(EpochJ2000 b : EpochJ2000.values()) {
        	this.bodies.add(b.getBody());
        }
    }

    // private static double circlev(double rx, double ry) {
    // double solarmass = 1.98892e30;
    // double r2 = Math.sqrt(rx * rx + ry * ry);
    // double numerator = (6.67e-11) /** 1e6 */ * solarmass;
    // return Math.sqrt(numerator / r2);
    // }

    @Override
    public List<Body> getBodiesToRender() {
        return this.bodies;
    }

    @Override
    public void updateSim(double sec) {
        // update bodies position
        this.bodies = this.alg.exceuteUpdate(bodies, sec);
        // update clock/date
        this.clock.update((long) sec);
    }

    @Override
    public void setAlgorithm(Algorithm algorithm) {
        this.alg = algorithm;
    }

    @Override
    public void addBody(Body b) {
        this.bodies.add(b);
    }

    @Override
    public String getTime() {
        return this.clock.toString();
    }

    @Override
    public SimClock getClock() {
        return this.clock;
    }

    @Override
    public void setClock(SimClock clock) {
        this.clock = clock;

    }

}
