package atlas.model;

public enum BodyType {
	BLACKHOLE("Blackhole"),
	STAR("Star"),
	PLANET("Planet"),
	DWARF_PLANET("Dwarf planet"),
	SATELLITE("Satellite"),
	COMET("Comet"),
	ASTEROID("Asteroid"),
	FRAGMENT("Fragment");
    
    private String nameType;
    
    private BodyType(final String name){
        this.nameType = name;
    }
    
    public String toString(){
        return this.nameType;
    }
    
    /**
     * G constant from CODATA 2010
     */
    public static final double G = 6.67384e-11; // gravitational constant
    
    /**
     * Astronomical unit in meters
     */
    public static final double AU = 149597870.700 * 1000;
    
    /**
     * Seconds in a day
     */
    public static final int DAY_SECONDS = 60*60*24;
    
    /**
     * Data from NASA Jet Propulsion Laboratory (link = http://ssd.jpl.nasa.gov)
     */
    public static final double SOLAR_MASS = 1.988544e30;
    public static final double MERCURY_MASS = 0.330104e24;
    public static final double VENUS_MASS = 4.86732e24;
    public static final double EARTH_MASS = 5.97219e24;
    public static final double MARS_MASS = 0.641693e24;
    public static final double JUPITER_MASS = 1898.13e24;
    public static final double SATURN_MASS = 568.319e24;
    public static final double URANUS_MASS = 86.8103e24; 
    public static final double NEPTUNE_MASS = 102.410e24;
    public static final double PLUTO_MASS = 0.01309e24;
}
