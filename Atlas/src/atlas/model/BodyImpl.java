package atlas.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Optional;

public class BodyImpl implements Body {

    private BodyType type;
    private String name;
    private double posx, posy;
    private double velx, vely;
    private double fx, fy;
    private double mass;
    private Properties properties = new Properties();

    public BodyImpl() {
    }
    
    /*Deprecated*/
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
    public BodyType getType() {
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
        double dy = b.getPosY() - this.posy;
        double dist = Math.sqrt(dx * dx + dy * dy);
        double F = (BodyType.G * this.getMass() * b.getMass()) / (dist * dist + EPS * EPS);
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
        this.velx += dt * fx / mass;
        this.vely += dt * fy / mass;
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
    public void setTotalVelocity(double vt){
        
    }

    @Override
    public String toString() {
        NumberFormat formatter = new DecimalFormat("0.### ### ### ### ###E0");
        double AU = 149597870.700 * 1000;

        return "[Name: " + this.name + ", posX/AU = " + formatter.format(this.posx / AU) + ", posY/AU = "
                + formatter.format(this.posy / AU) + "]" + "\n"
                /*
                 * + "| forceX =" + formatter.format(this.fx) + " forceY =" +
                 * formatter.format(this.fy)
                 */
                + "|| velX =" + formatter.format(this.velx / AU * 86400) + " velY ="
                + formatter.format(this.vely / AU * 86400);
    }

    public Properties getProperties() {
        return this.properties;
    }

    public static class Properties {
        private double radius;
        private long rotationPeriod;

        /* Optional properties */
        private Optional<Body> parent = Optional.empty();
        private Optional<Double> temperature = Optional.empty();
        
        public double getRadius() {
            return radius;
        }
        public void setRadius(double radius) {
            this.radius = radius;
        }
        public long getRotationPeriod() {
            return rotationPeriod;
        }
        public void setRotationPeriod(long rotationPeriod) {
            this.rotationPeriod = rotationPeriod;
        }
        public Optional<Body> getParent() {
            return parent;
        }
        public void setParent(Body parent) {
            this.parent = Optional.ofNullable(parent);
        }
        public Optional<Double> getTemperature() {
            return temperature;
        }
        public void setTemperature(double temperature) {
            this.temperature = Optional.ofNullable(temperature);
        }
    }

    public static class Builder {
        private BodyType type;
        private String name;
        private double posx, posy;
        private double velx, vely;
        private double mass;

        public Builder type(BodyType type) {
            this.type = type;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder posX(double x) {
            this.posx = x;
            return this;
        }

        public Builder posY(double y) {
            this.posy = y;
            return this;
        }

        public Builder velX(double vx) {
            this.velx = vx;
            return this;
        }

        public Builder velY(double vy) {
            this.vely = vy;
            return this;
        }

        public Builder mass(double mass) {
            this.mass = mass;
            return this;
        }
        
        public BodyImpl build() {
            if (this.type == null || this.mass == 0) {
                throw new IllegalStateException();
            }
            BodyImpl b = new BodyImpl(this.type, this.name, this.posx, this.posy, this.velx, this.vely, this.mass);
            return b;
        }
    }

    public static void main(String s[]) {
        BodyImpl b = new BodyImpl.Builder().mass(1.0).posX(1).posY(1).type(BodyType.STAR).build();

        System.out.println("mass = " + b.getMass() + " adding properties....");
//        BodyImpl a = new BodyImpl();
        System.out.println(
                "temp = " + b.getProperties().getTemperature() + " parent body = " + b.getProperties().getParent());

    }
}
