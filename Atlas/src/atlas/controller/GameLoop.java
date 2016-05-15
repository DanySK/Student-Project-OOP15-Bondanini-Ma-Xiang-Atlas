package atlas.controller;

import atlas.model.*;
import atlas.view.*;

/*
 * Tipologia di Game Loop: UPD Costante con MaxSkipFrame
 * Questo Game Loop ci permette di oscillare dinamicamente tra 50 e 5 FPS.
 * */

public class GameLoop extends Thread {

    /*
     * Per ora fps fissi
     */
    private final static int FPS = 50;
    private final static int SKIP_TICKS = 1000 / FPS;
    private final static int MAX_FRAMESKIP = 10;
    private volatile int loop;
    private long next_game_tick;
    private StatusSim status;
    private ModelInterface model = new Model();
    private ViewInterface view;

    public GameLoop(ViewInterface v) {
        this.view = v;
        this.status = StatusSim.STOPPED;
    }

    public void run() {
        while (!status.equals(StatusSim.EXIT)) {
            this.next_game_tick = System.currentTimeMillis();
            while (status.equals(StatusSim.RUNNING)) {

                long lastFrame = System.currentTimeMillis();

                this.loop = 0;
                while ((System.currentTimeMillis() > this.next_game_tick) && (this.loop < MAX_FRAMESKIP)) {
                    this.model.updateSim(6);
                    this.next_game_tick += SKIP_TICKS;
                    this.loop++;
                }

                // sleep for 1 ms if too fast (and furious) or if status =
                // stopped
                while (System.currentTimeMillis() - lastFrame < 20 || status.equals(StatusSim.STOPPED)) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // if(status.equals(Stat))
                }
                // timeSLF = time since last frame
                long timeSLF = System.currentTimeMillis() - lastFrame;
                // rendering();
                this.view.render(model.getBodiesToRender());
                long FPS = 1000/timeSLF;
                System.out.println("timeSLF = " + timeSLF + " --> FPS  = " + FPS);
            }
        }
    }

    private synchronized void setStatus(StatusSim status) {
        this.status = status;
    }

    public void setRunning() {
        if (!this.status.equals(StatusSim.RUNNING)) {
            this.setStatus(StatusSim.RUNNING);
        }
    }

    public void setStopped() {
        if (!this.status.equals(StatusSim.STOPPED)) {
            this.setStatus(StatusSim.STOPPED);
        }
    }

    public void resumeSim() {
        if (!this.status.equals(StatusSim.RUNNING)) {
            this.setStatus(StatusSim.RUNNING);
        }

    }

    public StatusSim getStatus() {
        return this.status;
    }

}
