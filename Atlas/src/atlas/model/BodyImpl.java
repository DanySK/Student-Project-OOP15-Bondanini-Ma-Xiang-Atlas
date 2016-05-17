package atlas.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BodyImpl implements Body{
    
    private BodyType type;
    private String name;
    private double posx, posy;
    private double velx, vely;
    private double fx, fy;
    private double mass;
    
    public BodyImpl(){}
    
    public BodyImpl(String name, double posx, double posy, double velx, double vely, double mass) {
        this.name = name;
        this.posx = posx;
        this.posy = posy;
        this.velx = velx;
        this.vely = vely;
        this.mass = mass;
    }
    
    public BodyImpl(BodyType type, String name, double posx, double posy, double velx, double vely, double mass) {
        this(name, posx, posy, velx, vely, mass);
        this.type = type;
    }
    
    @Override
    public BodyType getType(){
        return this.type;
    }
    
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getMass() {
        return this.mass;
    }

    @Override
    public void addForce(Body b) {
        double EPS = 1; // softening parameter (just to avoid infinities)
        double dx = b.getPosX() - this.posx;
        double dy = b.getPosY()- this.posy;
        double dist = Math.sqrt(dx * dx + dy * dy);
        double F = (BodyType.G * this.getMass()* b.getMass()) / (dist * dist + EPS * EPS);
        this.fx += F * dx / dist;
        this.fy += F * dy / dist;
    }

    @Override
    public void resetForce() {
        this.fx = 0.0;
        this.fy = 0.0;
    }

    @Override
    public void updatePos(double dt) {
        this.velx += dt * fx/mass;
        this.vely += dt * fy/mass;
        this.posx += dt * velx;
        this.posy += dt * vely;        
    }

    @Override
    public double distanceTo(Body b) {
        double dx = this.posx - b.getPosX();
        double dy = this.posy - b.getPosY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public double getPosX() {
        return this.posx;
    }

    @Override
    public double getPosY() {
        return this.posy;
    }

    @Override
    public double getVelX() {
        return this.velx;
    }

    @Override
    public double getVelY() {
        return this.vely;
    }
    
    @Override
    public String toString() {
        NumberFormat formatter = new DecimalFormat("0.### ### ### ### ###E0");
        double AU = 149597870.700*1000;
        
        return "[Name: " + this.name + ", posX/AU = " 
                + formatter.format(this.posx / AU) + ", posY/AU = " 
                + formatter.format(this.posy / AU) +"]" + "\n"
                /*+ "| forceX =" + formatter.format(this.fx)
                + " forceY =" + formatter.format(this.fy)*/
                + "|| velX =" + formatter.format(this.velx / AU *86400)
                + " velY =" + formatter.format(this.vely / AU *86400);
    }
}
