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

        
//        double AU = BodyType.AU;
//        int t = 86400;
//        double sunpx = -7.139143380212696E-03 * AU;
//        double sunpy = -2.792019770161695E-03 * AU;
//        double sunvx = 5.374260940168565E-06 * AU / t;
//        double sunvy = -7.410965396701423E-06 * AU / t;
//        //EARTH
//        double px = -1.756637922977121E-01 * AU;
//        double py = 9.659912850526894E-01 * AU;
//
//        double vx = (-1.722857156974861E-02 * AU) / t;
//        double vy = (-3.015071224668472E-03 * AU) / t;
//        final double earthmass = 5.972e24;
//        //epoch 2000-01-01
//        this.bodies.add(new BodyImpl("sun", 0, 0, sunvx, sunvy, BodyType.SOLAR_MASS));
//        this.bodies.add(new BodyImpl("earth",px, py, vx, vy-vy, earthmass*100000));
//        this.bodies.add(new BodyImpl("mars",-px/2, -py*2, vx/100, vy/100, earthmass*100000));
    }
	
	public ModelImpl(EpochJ2000[] epoch) {
	    this();
	    this.clock.setEpoch(EpochJ2000.TIME_MILLS);
	    for(EpochJ2000 b : epoch) {
	        if(!b.equals(EpochJ2000.EARTH)) {
	            this.bodies.add(b.getBody());
	        }
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
