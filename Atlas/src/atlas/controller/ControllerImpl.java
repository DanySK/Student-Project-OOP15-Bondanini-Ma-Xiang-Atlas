package atlas.controller;

import java.io.IOException;

import atlas.model.EpochJ2000;
import atlas.model.Model;
import atlas.model.ModelImpl;
import atlas.model.algorithms.AlgorithmBruteForce;
import atlas.model.algorithms.CollisionStrategyAbsorb;
import atlas.model.algorithms.CollisionStrategyFragments;
import atlas.view.SimEvent;
import atlas.view.View;
import atlas.view.ViewImpl;

/**
 * Implementation of ControllerInterface
 * 
 * @author andrea
 *
 */
public class ControllerImpl implements Controller {

    private GameLoop gLoop;
    private static ControllerImpl ctrl = null;
    private View view;
    private volatile Model model; // check volatile
    private InputManager inputManager;

    /**
     * Creation of new Controller
     * 
     * @param v
     *            ViewInterface Object
     */
    private ControllerImpl() {
        this.model = new ModelImpl(EpochJ2000.values());
        gLoop = new GameLoop(model);
    }

    public static ControllerImpl getIstanceOf() {
        return (ControllerImpl.ctrl == null ? new ControllerImpl() : ControllerImpl.ctrl);
    }

    @Override
    public void startSim() {
        gLoop.setRunning();
        gLoop.start();
    }

    @Override
    public void stopSim() {
        gLoop.setStopped();
    }

    @Override
    public void exit() {
    	ViewImpl.getView().onClose();
//        gLoop.setExit();    
    }

    public void setView(View v) {
        this.view = v;
        this.gLoop.setView(v);
        this.inputManager = new InputManagerImpl(this.view, this.model, this.gLoop, this.view.getReference());
    }

    @Override
    public void update(SimEvent event) {
        switch (event) {
        case START:
            this.startSim();
            break;

        case STOP:
            this.stopSim();
            break;

        case EXIT:
            this.exit();
            break;

        case ADD:
            this.inputManager.addMode();
            break;

        case EDIT:  
            this.inputManager.mouseMultiClick();
            this.inputManager.changeStatus(Status.EDIT);
            break;

        case CENTER:
            this.inputManager.initialReference();
            break;

        case LOCK:
//            this.inputManager.lockVenuse();
//            this.inputManager.changeStatus(Status.LOCK);
        	ViewImpl.getView().setCameraLocked(true);
            break;
            
        case NEW_SIM:
            this.model = new ModelImpl();
            this.gLoop.setModel(model);
            break;

        case SAVE_SIM:
            try {
                this.inputManager.saveConfig();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
            
        case SAVE_BODY:
            try {
                this.inputManager.saveBody();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
            
        case UPDATE_BODY:
        	synchronized(model.getBodiesToRender()) {
        		ViewImpl.getView().getSelectedBody().ifPresent(i -> i.updateInfo(ViewImpl.getView().getBodyUpdateInfo().get()));
        	}
        	break;

        case LOAD:
            try {
                this.model = this.inputManager.loadConfig().orElse(this.model);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;

        case SPEED_CHANGED:
            try {
                this.inputManager.changeSpeed();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            break;

        case MOUSE_CLICKED:
            this.inputManager.mouseClicked();
            break;

        case MOUSE_PRESSED:
            this.inputManager.mousePressed();
            break;

        case MOUSE_RELEASED:
            this.inputManager.mouseReleased();
            break;

        case MOUSE_WHEEL_UP:
            this.inputManager.zoomUp();
            break;

        case MOUSE_WHEEL_DOWN:
            this.inputManager.zoomDown();
            break;

        case ESC:
//            this.inputManager.ESC();
        	ViewImpl.getView().setCameraLocked(false);
            break;

        case W:
            this.inputManager.wSlide();
            break;

        case A:
            this.inputManager.aSlide();
            break;

        case S:
            this.inputManager.sSlide();
            break;

        case D:
            this.inputManager.dSlide();
            break;

        case SPACEBAR_PRESSED:
            this.inputManager.spaceBar();
            break;
        
        case COLLISION_ONE:
        	this.model.setCollsion(new CollisionStrategyFragments());
        	break;        	
        case COLLISION_TWO:
        	this.model.setCollsion(new CollisionStrategyAbsorb());
        	System.out.println("Collision Absorb");
        	break;
        case NBODY_ONE:
        	this.model.setAlgorithm(new AlgorithmBruteForce());
        	break;
        case NBODY_TWO:
//        	this.model.setAlgorithm(new AlgorithmBernesHut());
        	for(int i = 0; i < 10; i++ ) {
        		System.out.println("Algorithm (Bernes Hut) not ready yet!!!!");
        	}
        	break;
        default:
            break;
        }

    }
}
