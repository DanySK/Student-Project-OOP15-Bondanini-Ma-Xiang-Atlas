package atlas.model;

import java.util.List;

public class TestModel {
    
    static long UPTIMES = 1 * 24 * 366; //hours
    static int mod = 1;

    public static void main(String[] args) {
        
        ModelInterface m = new Model();
        String lastQuad = "Quad == da";
        
        for(long i=0; i < UPTIMES; i++){
            
                List<Body> l = m.getBodiesToRender();
                for(Body b : l) {
                    //around initial position
                    if(b.getPosX() >= -2.629E10 && b.getPosX() >= -2.628E10){
                        if(b.getPosY() >= 1.445E11 && b.getPosY() <= 1.4459E11){
                            System.out.println(i+")"+b + "\n");
                        }
                    }
                    //print last one
                    if( i == UPTIMES - 1) {
                        System.out.println(i+")"+b + "\n");
                    }
                }
            m.updateSim(1);
        }

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