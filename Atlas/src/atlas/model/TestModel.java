package atlas.model;

import java.util.List;
import java.time.Clock;
import java.util.Date;

public class TestModel {
    
    static long UPTIMES = 360 /* 24 * 366*60*60*/; //hours
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