package atlas.application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;

import atlas.model.Body;
import atlas.utils.Pair;

public class PanelTest extends JPanel{
    
    private List<Body> bodies;
    private Dimension dim;
    Pair<Integer, Integer> center;
    private boolean virgin = true;
    private double unitX, unitY;
    
    public PanelTest(Dimension d){
        this.setBackground(Color.BLACK);
        this.dim = d;
        this.center = new Pair<>((int)this.dim.getWidth()/2, (int)this.dim.getHeight()/2);
    }
    
    public synchronized  void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        
//            unitX = (double)this.center.getX() / this.bodies.get(1).getPosX();
//            unitY = (double)this.center.getY() / this.bodies.get(1).getPosY();
        
        if(this.virgin == true){
//            unitX = (double)this.center.getX() / this.bodies.stream().max(
//                    (x,y) -> Math.abs((int)x.getPosX()) - Math.abs((int)y.getPosX())).get().getPosX();
            unitY = (double)this.center.getY() / this.bodies.stream().max(
                    (x,y) -> Math.abs((int)x.getPosY()) - Math.abs((int)y.getPosY())).get().getPosY();
            unitX /= 2;
            unitY /= 2;
            this.virgin = false;
        }
        
//        System.out.println("UnitX = " + unitX + " uniteY = " + unitY);
        this.bodies.forEach(b -> {
            int x = this.center.getX() + (int)((b.getPosX() * unitY) -50 );
            int y = this.center.getY() - (int)((b.getPosY() * unitY) -50 );
            System.out.println("Drawing body = " + b.getName() + " | px = " + b.getPosX() + " py = " + b.getPosY());
//            System.out.println("Real X = " + b.getPosX() + "RealY = " + b.getPosY());
            
            String s = b.getName();

            g.drawString(s, x, y);
            if(b.getName().equals(new String("sun"))) {
                g.drawRect(x, y, 5, 5);
            }else {
                g.drawRect(x, y, 5, 5);
                Collection<Pair<Double,Double>> c = new ArrayDeque<>(b.getTrail());
                for(Pair<Double,Double> p : c){
                    int x1 = this.center.getX() + (int)((double)p.getX() * unitY -50);
                    int y1 = this.center.getY() - (int)((double)p.getY() * unitY -50);
                    
                    g.drawRect(x1, y1, 1, 1);
                }
//                int sciax[] = {1,24,54,76,100}; 
//                int sciay[] = {1,24,54,76,100};
//                g.drawPolyline(sciax, sciay, size);
                
            }
            
        });
    }
    
    void update(List<Body> b){
        this.bodies = b;
    }
}