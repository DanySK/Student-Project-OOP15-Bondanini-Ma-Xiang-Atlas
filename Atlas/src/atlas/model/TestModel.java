package atlas.model;

import java.util.Date;

public class TestModel {
    
    static long UPTIMES = 360000 /* 24 * 366*60*60*/; //hours
    static int mod = 1;
    
    public static void main(String[] args) {   
        long t =0;
        long time = System.nanoTime();
        for(int i = 0; i < UPTIMES; i++){
            t += i;
            new Date(t);
//            System.out.println(new Date(t));            
        }
        time = System.nanoTime() - time;
        System.out.println("Created "+UPTIMES + " in (ns) " + time);
        
        
        Body b = new BodyImpl.Builder().mass(5e11)
                                       .type(BodyType.STAR)
                                       .build();
        System.out.println(b.getProperties());
        b.getProperties().setParent(b);
        b.getProperties().setTemperature(100);
        System.out.println(b.getProperties());
        
        
        //test trail
//        try {
//            Model m = new Model();
//            for(int i = 0; i < UPTIMES; i++){
//                m.updateSim(1);
//                for( Body a : m.getBodiesToRender()){
////                    System.out.println(a.getTrail());
//                    Collection<Pair<Double,Double>> c = a.getTrail();
//                    for(Pair<Double,Double> p : c){
//                        double x = p.getX();
//                        double y = p.getY();
//                    }
//                }
//                System.out.println(i);
//            }
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
        Body parent = EpochJ2000.SUN.getBody();
        Body son = EpochJ2000.EARTH.getBody();
        Body earth = new BodyImpl.Builder()
                                    .type(BodyType.PLANET)
                                    .mass(BodyType.EARTH_MASS)
                                    .posX(BodyType.AU)
                                    .velX(0)
                                    .velY(0)
                                    .build();
        double op = (2*Math.PI)/Math.sqrt((BodyType.G*parent.getMass()) ) * Math.pow(earth.distanceTo(parent), 1.5);
        op = op/(86400);
        System.out.println(op + " Distance from sun is " + son.distanceTo(parent));
        
        
        //testing custom clock
        SimClock c1 = new SimClock();
        SimClock c2 = new SimClock(EpochJ2000.TIME_MILLS);
        System.out.println("c1 = " + c1 + "\tc2 = " + c2 );
        
        c1.update(1000L*86400L*8000L);
        c2.update(1000L*86400L*8000L);

        System.out.println("c1 = " + c1 + "\tc2 = " + c2 );
        
    }
    
    
    private static String checkQuad(Body a) {
        //first or second quad
        if(a.getPosX() >= 0.0){
            return a.getPosY() > 0 ? "Quad == I" : "Quad == II";
        } else { // third or forth quad
            return a.getPosY() > 0 ? "Quad == III" : "Quad == IV";
        }
        
    }
}