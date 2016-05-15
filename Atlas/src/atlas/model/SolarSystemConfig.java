package atlas.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * EPOCH 2000-01-01
 * 
 * @author maxx
 *
 */
public class SolarSystemConfig {

    private static double AU = 149597870.700 * 1000;
    List<Body> list = new ArrayList<>();
    private static double t = 60 * 60 * 24;

    public SolarSystemConfig() {

        double sunvx = 5.374260940168565E-06 * AU / t;
        double sunvy = -7.410965396701423E-06 * AU / t;
        this.list.add(new BodyImpl("sun", 0, 0, sunvx, sunvy, BodyImpl.solarmass));
    }

    public Body getSun() {
        double sunvx = 5.374260940168565E-06 * AU / t;
        double sunvy = -7.410965396701423E-06 * AU / t;
        return new BodyImpl("sun", 0, 0, sunvx, sunvy, BodyImpl.solarmass);
    }

    public Body getMercury() {
        double px = -1.478672233442572E-01 * AU;
        double py = -4.466929775364947E-01 * AU;

        double vx = (2.117424563261189E-02 * AU) / t;
        double vy = (-7.105386404267509E-03 * AU) / t;
        final double mass = 3.302e23;

        return new BodyImpl("mercury", px, py, vx, vy, mass);
    }

    public Body getVenus() {
        double px = -7.257693602841776E-01 * AU;
        double py = -2.529582082587794E-02 * AU;

        double vx = (5.189070188671264E-04 * AU) / t;
        double vy = (-2.031355258779472E-02 * AU) / t;
        final double mass = 48.685e23;

        return new BodyImpl("venus", px, py, vx, vy, mass);
    }

    public Body getEarth() {
        // EARTH
        double px = -1.756637922977121E-01 * AU;
        double py = 9.659912850526894E-01 * AU;

        double vx = (-1.722857156974861E-02 * AU) / t;
        double vy = (-3.015071224668472E-03 * AU) / t;
        final double earthmass = 5.972e24;

        return new BodyImpl("earth", px, py, vx, vy, earthmass);
    }

    public Body getMars() {
        double px = 1.383221922520998E+00 * AU;
        double py = -2.380174081741852E-02 * AU;

        double vx = (7.533013850513374E-04 * AU) / t;
        double vy = (1.517888771209419E-02 * AU) / t;
        final double mass = 6.4185e23;

        return new BodyImpl("mars", px, py, vx, vy, mass);
    }

    public Body getJupiter() {
        double px = 3.996321311604079E+00 * AU;
        double py = 2.932561211517849E+00 * AU;

        double vx = (-4.558376589062087E-03 * AU) / t;
        double vy = (6.439863212743527E-03 * AU) / t;
        final double mass = 1898.16e24;

        return new BodyImpl("jupiter", px, py, vx, vy, mass);
    }
    
    public Body getSaturn() {
        double px = 6.401416889764079E+00 * AU;
        double py = 6.401416889764079E+00 * AU;

        double vx = (-4.285166239724135E-03* AU) / t;
        double vy = (3.884579926013634E-03  * AU) / t;
        final double mass = 5.68319e26;

        return new BodyImpl("saturn", px, py, vx, vy, mass);
    }
    
    public Body getUrnus() {
        double px = 1.442337843936191E+01 * AU;
        double py = -1.373845030140273E+01 * AU;

        double vx = (2.683840344076723E-03* AU) / t;
        double vy = (2.665016541217006E-03  * AU) / t;
        final double mass = 86.8103e24;

        return new BodyImpl("uranus", px, py, vx, vy, mass);
    }
    
    public Body getNeptune() {
        double px = 1.680361764335729E+01 * AU;
        double py = -2.499544328458694E+01 * AU;

        double vx = (2.584589572083790E-03* AU) / t;
        double vy = (1.768943546348834E-03  * AU) / t;
        final double mass = 102.41e24;

        return new BodyImpl("neptune", px, py, vx, vy, mass);
    }
    
    public List<Body> getAll(){
        return Arrays.asList(this.getSun(), this.getMercury(), this.getVenus(), this.getEarth(),
                this.getMars(), this.getJupiter(), this.getSaturn(), this.getUrnus(),this.getNeptune());
    }
}
