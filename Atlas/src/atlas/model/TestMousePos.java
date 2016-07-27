package atlas.model;

import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;

public class TestMousePos extends Thread {

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        
        while(true){
            System.out.print("\r Mouse position : " + MouseInfo.getPointerInfo().getLocation());
        }
    }
   
    public static void main (String s[]) {
        new TestMousePos().run();
        MouseStuff m = new MouseStuff();
//        m.mouseMoved(MouseInfo.getPointerInfo());
    }
    
    
}
