package atlas.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import atlas.utils.Counter;

public class SimClock implements java.io.Serializable {

    private static final long serialVersionUID = -1532724227194921810L;

    private Counter time;
    private Optional<Long> epochOffset = Optional.empty();
    private static SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * Default clock don't have a date, just a time counter.
     */
    public SimClock() {
        this.time = new Counter();
    }

    /**
     * Construct a clock which displays date based on the epoch.
     * 
     * @param offset
     *            relative to UTC epoch.
     */
    public SimClock(long offset) {
        this();
        this.epochOffset = Optional.ofNullable(offset);
    }

    /**
     * Increments the time counter
     * 
     * @param dt
     *            delta time to increment
     */
    public void update(long dt) {
        this.time.increment(dt);
    }

    /**
     * Time from the start of the simulation.
     * 
     * @return amount of time without any offset
     */
    public long currentSimTime() {
        return this.time.get();
    }
    
    /**
     * Resets the time counter.
     */
    public void resetClock() {
        this.time = new Counter();
    }
    
    /**
     * Removes the offset form the UTC epoch.
     */
    public void removeEpoch() {
        this.epochOffset = Optional.empty();
    }
    
    /**
     * This method sets a new offset form the UTC epoch 
     * @param offset
     */
    public void setEpoch(long offset) {
        this.epochOffset = Optional.ofNullable(offset);
    }
    
    public String toString() {
        if (this.epochOffset.isPresent()) {
            return FORMATTER.format(new Date(this.time.get() + this.epochOffset.get()));
        } else {
            // Feature da implementare : unita' di misura tempo variabile...
            return new StringBuilder().append((int) (this.time.get() / (1000 * 86400))).append(" days").toString();
        }
    }
}
