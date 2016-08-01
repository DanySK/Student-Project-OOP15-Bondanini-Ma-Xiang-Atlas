package atlas.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.*;

import atlas.model.algorithms.Algorithm;
import atlas.model.algorithms.AlgorithmBruteForce;

/**
 * Brute force N-body implementation
 *
 */
public class ModelImpl implements Model, java.io.Serializable {

	private static final long serialVersionUID = 1670664173059452174L;
	private Algorithm alg;
    private List<Body> bodies = new ArrayList<>();
    private SimClock clock = new SimClock();

    public ModelImpl() {

        this.alg = new AlgorithmBruteForce();
        SimConfig sol = new SimConfig();
        this.bodies.addAll(Arrays.asList(EpochJ2000.SUN.getBody(), sol.getMercury(), sol.getVenus(),
                EpochJ2000.EARTH.getBody(), sol.getMars()));
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
        return null;
    }

    @Override
    public void setClock(SimClock clock) {
        this.clock = clock;

    }

}
