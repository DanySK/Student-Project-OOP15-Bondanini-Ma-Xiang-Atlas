package atlas.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static atlas.model.BodyType.*;

/**
 * EPOCH 2000-01-01
 * 
 * @author maxx
 *
 */
public class SimConfig {
    
    List<Body> list = new ArrayList<>();

    public SimConfig() {
    }

    public Body getSun() {
        return EpochJ2000.SUN.getBody();
    }

    public Body getMercury() {
        double px = -1.478672233442572E-01 * AU;
        double py = -4.466929775364947E-01 * AU;

        double vx = (2.117424563261189E-02 * AU) / DAY_SECONDS;
        double vy = (-7.105386404267509E-03 * AU) / DAY_SECONDS;

        return new BodyImpl("Mercury", px, py, vx, vy, BodyType.MERCURY_MASS);
    }

    public Body getVenus() {
        double px = -7.257693602841776E-01 * AU;
        double py = -2.529582082587794E-02 * AU;

        double vx = (5.189070188671264E-04 * AU) / DAY_SECONDS;
        double vy = (-2.031355258779472E-02 * AU) / DAY_SECONDS;

        return new BodyImpl("Venus", px, py, vx, vy, BodyType.VENUS_MASS);
    }

    public Body getEarth() {
        return EpochJ2000.EARTH.getBody();
    }

    public Body getMars() {
        double px = 1.383221922520998E+00 * AU;
        double py = -2.380174081741852E-02 * AU;

        double vx = (7.533013850513374E-04 * AU) / DAY_SECONDS;
        double vy = (1.517888771209419E-02 * AU) / DAY_SECONDS;

        return new BodyImpl("mars", px, py, vx, vy, BodyType.MARS_MASS);
    }

    public Body getJupiter() {
        double px = 3.996321311604079E+00 * AU;
        double py = 2.932561211517849E+00 * AU;

        double vx = (-4.558376589062087E-03 * AU) / DAY_SECONDS;
        double vy = (6.439863212743527E-03 * AU) / DAY_SECONDS;

        return new BodyImpl("jupiter", px, py, vx, vy, BodyType.JUPITER_MASS);
    }
    
    public Body getSaturn() {
        double px = 6.401416889764079E+00 * AU;
        double py = 6.401416889764079E+00 * AU;

        double vx = (-4.285166239724135E-03* AU) / DAY_SECONDS;
        double vy = (3.884579926013634E-03  * AU) / DAY_SECONDS;

        return new BodyImpl("saturn", px, py, vx, vy, BodyType.SATURN_MASS);
    }
    
    public Body getUrnus() {
        double px = 1.442337843936191E+01 * AU;
        double py = -1.373845030140273E+01 * AU;

        double vx = (2.683840344076723E-03* AU) / DAY_SECONDS;
        double vy = (2.665016541217006E-03  * AU) / DAY_SECONDS;

        return new BodyImpl("uranus", px, py, vx, vy, BodyType.URANUS_MASS);
    }
    
    public Body getNeptune() {
        double px = 1.680361764335729E+01 * AU;
        double py = -2.499544328458694E+01 * AU;

        double vx = (2.584589572083790E-03* AU) / DAY_SECONDS;
        double vy = (1.768943546348834E-03  * AU) / DAY_SECONDS;

        return new BodyImpl("neptune", px, py, vx, vy, BodyType.NEPTUNE_MASS);
    }
    
    public List<Body> getAll(){
        return Arrays.asList(this.getSun(), this.getMercury(), this.getVenus(), this.getEarth(),
                this.getMars(), this.getJupiter(), this.getSaturn(), this.getUrnus(),this.getNeptune());
    }
}
