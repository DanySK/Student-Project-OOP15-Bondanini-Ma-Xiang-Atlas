package atlas.model;

import java.util.*;

/**
 * Brute force N-body implementation
 *
 */
public class Model implements ModelInterface{
    
    private List<Body> bodies = new ArrayList<>();
    private static double AU = 149597870.700*1000;
    private static double T = 60*60*24;//86400
    private long steps = 0;
    
    public Model(){
        //Constructor -> String name, double posx, double posy, double velx, double vely, double mass
        //SUN
        double sunpx = -7.139143380212696E-03 * AU;
        double sunpy = -2.792019770161695E-03 * AU;
        double sunvx = 5.374260940168565E-06 * AU / T;
        double sunvy = -7.410965396701423E-06 * AU / T;
        //EARTH
        double px = -1.756637922977121E-01 * AU;
        double py = 9.659912850526894E-01 * AU;

        double vx = (-1.722857156974861E-02 * AU) / T;
        double vy = (-3.015071224668472E-03 * AU) / T;
        //epoch 2000-01-01
//        this.bodies.add(new BodyImpl("sun", 0, 0, sunvx, sunvy, BodyType.SOLAR_MASS));
//        this.bodies.add(new BodyImpl("earth",px, py, vx, vy-vy, BodyType.EARTH_MASS*100000));
//        this.bodies.add(new BodyImpl("mars",-px/2, -py*2, vx/100, vy/100, BodyType.EARTH_MASS*100000));
        
        ////////////////////////////////////////////
        
        SolarSystemConfig sol = new SolarSystemConfig();
        this.bodies.addAll(Arrays.asList(sol.getSun(), sol.getMercury(), sol.getVenus(), 
                sol.getEarth(), sol.getMars()
        ));
        
//        px = -1.777871530146587E-01 * AU;
//        py = 9.643743958957228E-01 * AU;
//
//        vx = (-1.690468993933486E-02 * AU) / t;
//        vy = (-3.477070764654480E-03 * AU) / t;
//        this.bodies.add(new BodyImpl("moon", px, py, vx, vy, 734.9e20));
//        this.bodies.addAll(sol.getAll());
    }
    
//    private static double circlev(double rx, double ry) {
//        double solarmass = 1.98892e30;
//        double r2 = Math.sqrt(rx * rx + ry * ry);
//        double numerator = (6.67e-11) /** 1e6 */ * solarmass;
//        return Math.sqrt(numerator / r2);
//    }

    @Override
    public List<Body> getBodiesToRender() {
        return this.bodies;
    }

    @Override
    public void updateSim(int hours) {
            
        for(int f = 0; f < 60 * 60 * hours; f++){
            for (Body b : this.bodies) {
//                if(!b.getName().equals("sun")){
                    b.resetForce();
                    // 2 loops --> N^2 complexity
                    for (Body c : this.bodies) {
                        if (!b.equals(c)) {
                            b.addForce(c);
                        }
                    }
//                }
                    b.updatePos((double) 1); 
            }
            
            this.steps++;
        }
    }

}
