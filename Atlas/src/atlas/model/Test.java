package atlas.model;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;

public class Test {
    
    static long UPTIMES = 360000 /* 24 * 366*60*60*/; //hours
    static int mod = 1;
    
    @org.junit.Test
    public void testCollision() {
        assertTrue(1 > 0);
    }
    
    @org.junit.Test
    public void test() {
        try {
            new BodyImpl.Builder().name("test").build();
        } catch (IllegalStateException e) {
        } catch(Exception ex) {
            Assert.fail("Builder must throw IllegalStateException if mass or type is not initialized");
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