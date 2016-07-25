package atlas.model;

import java.util.*;
import static atlas.model.BodyType.AU;
import static atlas.model.BodyType.DAY_SECONDS;

/**
 * Brute force N-body implementation
 *
 */
public class ModelImpl implements Model{
    
    private List<Body> bodies = new ArrayList<>();
    private SimClock clock = new SimClock();
    
    public ModelImpl(){
        
        SimConfig sol = new SimConfig();
        this.bodies.addAll(Arrays.asList(EpochJ2000.SUN.getBody(), sol.getMercury(), sol.getVenus(), 
                EpochJ2000.EARTH.getBody(), sol.getMars()
        ));
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
        for (Body b : this.bodies) {
            b.resetForce();
            // 2 loops --> N^2 complexity
            for (Body c : this.bodies) {
                if (!b.equals(c)) {
                    b.addForce(c);
                }
            }
            b.updatePos(sec);
        }

    }

    @Override
    public void addBody(Body b) {
       this.bodies.add(b);        
    }

    @Override
    public String getTime() {
        return this.clock.toString();                
    }

}
