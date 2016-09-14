package atlas.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import atlas.model.algorithms.Algorithm;
import atlas.model.algorithms.AlgorithmBruteForce;
import atlas.model.algorithms.CollisionStrategyFragments;
import atlas.utils.Pair;


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

        this.alg = new AlgorithmBruteForce(new CollisionStrategyFragments(20));
        
        double radius = EpochJ2000.SUN.getBody().getProperties().getRadius()*2;
        
//        Body x = EpochJ2000.URANUS.getBody();
//        Body y = EpochJ2000.VENUS.getBody();
//        
//        this.bodies.addAll(Arrays.asList(x,y));
//        
//        x.setPosX(5e3);
//        x.setPosY(5e3);
//        x.getProperties().setRadius(radius*3);
//        x.setVelocity(new Pair<Double,Double>(new Double(0), new Double(0)));
//        x.getProperties().setRotationPeriod(60000000L);;
//        
//        y.setPosY(5e3);
//        y.setPosX(y.getPosX()/3);
//        y.getProperties().setRadius(radius);
//        y.setVelocity(new Pair<Double,Double>(new Double(5000), new Double(0)));
    }
	
	public ModelImpl(EpochJ2000[] epoch) {
	    this();
	    this.clock.setEpoch(EpochJ2000.TIME_MILLS);
	    for(EpochJ2000 b : epoch) {
//	        if(!b.equals(EpochJ2000.EARTH)) {
	            this.bodies.add(b.getBody());
//	        }
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
