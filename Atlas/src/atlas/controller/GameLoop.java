package atlas.controller;

import atlas.model.*;
import atlas.view.*;

/**
 * The GameLoop is the main Thread of Simulation, that synchronizes Model and
 * View
 * 
 * @author andrea
 */

public class GameLoop extends Thread {// Velocità minima 1s:1s, massima 100 anni
    private float speed;
    private long precision;
    private final static int FPS = 50;
    private final static int SKIP_TICKS = 1000 / FPS;
    private final static int MAX_FRAMESKIP = 10;
    private volatile int loop;
    private long next_game_tick;
    private StatusSim status;
    private Model model = new ModelImpl();
    private View view;
    private UI ui;
    private static final long MIN_UNIT = 60;
    private static final long HOUR_UNIT = 3600;
    private static final long DAY_UNIT = 86400;
    private static final long YEAR_UNIT = 31536000;

    /**
     * Creates new GameLoop and sets the status of Simulation to Stop
     * 
     * @param v
     *            The ViewInterface object
     */
    public GameLoop() {
        this.status = StatusSim.RUNNING;
        // Partenza Standard di 10 gg/sec
        this.ui = UI.Day_Sec;
        this.speed = 10;
        this.setSpeed(ui, speed);

    }

    /**
     * SKIP_TICK detta i tempi del ciclo. Se ci metto più di skip ticks a fare
     * un update eseguo più update, saltando alcuni frame (abbasso il frame
     * Rate), se sono più veloce attendo per non velocizzare la simulazione.
     */

    public void run() {
        while (!status.equals(StatusSim.EXIT)) {
            this.next_game_tick = System.currentTimeMillis();
            while (status.equals(StatusSim.RUNNING)) {
                long lastFrame = System.currentTimeMillis();
                this.loop = 0;
                while ((System.currentTimeMillis() > this.next_game_tick) && (this.loop < MAX_FRAMESKIP)) {
                    for (int i = 0; i < speed; i++) {
                        this.model.updateSim(precision / FPS);
                    }
                    this.next_game_tick += SKIP_TICKS;
                    this.loop++;
                }
                // sleep for 1 ms if too fast (dormo fino a il tempo dall'ultimo
                // frame renderizzato sia uguale a skip ticks, per non
                // velocizzare la simulazione)
                while (System.currentTimeMillis() - lastFrame < SKIP_TICKS) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // quando sono in pausa passo al model lo Status
                }
                // timeSLF = time since last frame
                long timeSLF = System.currentTimeMillis() - lastFrame;
                // rendering();
                this.view.render(model.getBodiesToRender());
                long FPS = 1000 / timeSLF;
                System.out.println("timeSLF = " + timeSLF + " --> FPS = " + FPS);
            }

            // render when is stopped shish
            this.view.render(model.getBodiesToRender());
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method sets the Status
     * 
     * @param status
     *            StatusSim object
     */
    private synchronized void setStatus(StatusSim status) {
        this.status = status;
    }

    /**
     * This method sets the Status to Running calling setStatus()
     */
    public void setRunning() {
        if (!this.status.equals(StatusSim.RUNNING)) {
            this.setStatus(StatusSim.RUNNING);
        }
    }

    /**
     * This method sets the Status to Stopped calling setStatus()
     */
    public void setStopped() {
        if (!this.status.equals(StatusSim.STOPPED)) {
            this.setStatus(StatusSim.STOPPED);
        }
    }

    /**
     * This method sets the Status to Stopped calling setStatus()
     */
    public void setExit() {
        this.setStatus(StatusSim.EXIT);
    }

    /**
     * This method returns the current Status
     * 
     * @return
     */
    public synchronized StatusSim getStatus() {
        return this.status;
    }

    public void setView(View v) {
        this.view = v;
    }

    public void setSpeed(UI ui, float speed) throws IllegalArgumentException {
        if (speed <= 1000 || speed > 0) {
            this.speed = speed;
            switch (ui) {
            case Min_Sec:
                this.precision = MIN_UNIT;
                break;

            case Hour_Sec:
                this.precision = HOUR_UNIT;
                break;

            case Day_Sec:
                this.precision = DAY_UNIT;
                break;

            case Year_Sec:
                this.precision = YEAR_UNIT;
                break;
                
            default:
                break;
            }
        } else {
            throw new IllegalArgumentException();
        }

    }

    private enum UI {
        Min_Sec, Hour_Sec, Day_Sec, Year_Sec
    }

}
