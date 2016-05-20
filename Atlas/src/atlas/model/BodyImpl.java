package atlas.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BodyImpl implements Body {

    private BodyType type;
    private String name;
    private double posx, posy;
    private double velx, vely;
    private double fx, fy;
    private double mass;
    private Properties properties;

    // to be eliminated
    public BodyImpl() {
    }

    /* Deprecated */
    public BodyImpl(String name, double posx, double posy, double velx, double vely, double mass) {
        this.name = name;
        this.posx = posx;
        this.posy = posy;
        this.velx = velx;
        this.vely = vely;
        this.mass = mass;
        properties = new Properties();
    }

    public BodyImpl(BodyType type, String name, double posx, double posy, double velx, double vely, double mass,
            Properties properties) {
        this.type = type;
        this.name = name;
        this.posx = posx;
        this.posy = posy;
        this.velx = velx;
        this.vely = vely;
        this.mass = mass;
        this.properties = properties;
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
        double F = (BodyType.G * this.getMass() * b.getMass()) / (dist * dist + EPS);
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
        // updates the
        double currentAngle = this.properties.getRotationAngle();
        long rotationPeriod = this.properties.getRotationPeriod();
        if (rotationPeriod != 0) {
            currentAngle += dt / (double) rotationPeriod;
            this.properties.setRotationAngle(currentAngle >= 360 ? currentAngle - 360 : currentAngle);
        }
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

    // Algoritmo da sistemare... angolo non va bene
    @Override
    public void setTotalVelocity(double vt) {
        if (vt < 0) {
            throw new IllegalStateException();
        }
        double velocity = Math.sqrt(velx * velx + vely * vely);
        double angle = Math.acos(this.velx / velocity);
        this.velx = vt * Math.cos(angle);
        this.vely = vt * Math.sin(angle);
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

    public static class Builder {
        private BodyType type;
        private String name;
        private double posx, posy;
        private double velx, vely;
        private double mass;
        private Properties properties = new Properties();

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

        public Builder properties(BodyImpl.Properties properties) {
            this.properties = properties;
            return this;
        }

        public BodyImpl build() {
            if (this.type == null || this.mass == 0) {
                throw new IllegalStateException();
            }
            BodyImpl b = new BodyImpl(this.type, this.name, this.posx, this.posy, this.velx, this.vely, this.mass,
                    this.properties);
            return b;
        }
    }

    static void main(String s[]) {
        BodyImpl b = new BodyImpl.Builder().mass(1.0).posX(1).posY(1).type(BodyType.STAR).build();

        System.out.println("mass = " + b.getMass() + " adding properties....");
        // BodyImpl a = new BodyImpl();
        System.out.println(
                "temp = " + b.getProperties().getTemperature() + " parent body = " + b.getProperties().getParent());

    }
}