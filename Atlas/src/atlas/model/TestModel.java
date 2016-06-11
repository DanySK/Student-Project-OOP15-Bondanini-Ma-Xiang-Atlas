package atlas.model;

import java.util.List;

import atlas.utils.Pair;

import java.time.Clock;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public class TestModel {
    
    static long UPTIMES = 360000 /* 24 * 366*60*60*/; //hours
    static int mod = 1;

    public static void main(String[] args) {
        Date simDate = new Date(0);   
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
        double op = (2*Math.PI)/Math.sqrt((BodyType.G*parent.getMass()) ) * Math.pow(son.distanceTo(parent), 1.5);
        op = op/(86400);
        System.out.println(op);

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