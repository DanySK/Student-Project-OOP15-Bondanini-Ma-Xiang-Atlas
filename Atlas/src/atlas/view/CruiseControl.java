package atlas.view;

import javax.swing.text.AbstractDocument.BranchElement;

import atlas.utils.Units;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * This class contains part of the UI which is used to control the simulation.
 * It provides the following functionalities: - Play/Pause - Show date or time
 * of the simulation - A way to change the simulation speed - EDIT and ADD
 * buttons - Search and eventually info/screenshots
 * 
 * @author MaXX
 *
 */
public class CruiseControl extends BorderPane {
	protected Button buttonPlay = new Button("PLAY");// setGraphic
	protected Button buttonStop = new Button("STOP");
	protected Button buttonSpeed = new Button("SPEED");
	protected Button buttonEdit = new Button("EDIT");
	protected Button buttonAdd = new Button("ADD");
	protected Button buttonCenter = new Button("CENTER");
	protected Button buttonLockMars = new Button("LockMars"); //To delete
	protected Button buttonESC = new Button("ESC");
	private boolean play;

	protected Label labelTime = new Label("Sample: 01/01/2000");

	protected TextField textSpeed = new TextField();
	protected ChoiceBox<Units> choiceSpeedUnit = new ChoiceBox<>();

	private ViewMenuOption viewMenu = new ViewMenuOption();
	protected Button buttonSearch = new Button();
	private HBox left = new HBox();
	private HBox center = new HBox();
	private HBox right = new HBox();

	private View view;

	public CruiseControl() {
		this.view = ViewImpl.getView();
		left.getChildren().add(0, buttonStop);
		left.getChildren().add(1, labelTime);
		left.setAlignment(Pos.CENTER);
		left.setSpacing(10);
		left.getChildren().add(2, textSpeed);
		left.getChildren().add(3, choiceSpeedUnit);
		left.getChildren().add(4, buttonSpeed);
		center.getChildren().add(buttonEdit);
		center.getChildren().add(buttonAdd);
		center.setAlignment(Pos.CENTER);
		
		right.getChildren().add(viewMenu);
		right.getChildren().add(buttonSearch);
		right.getChildren().add(buttonCenter);
		right.getChildren().add(buttonLockMars);
		right.getChildren().add(buttonESC);
		
		this.setLeft(left);
		this.setCenter(center);
		this.setRight(right);
		this.play = false;
		this.choiceSpeedUnit.getItems().addAll(Units.values());

		/* Setting actions */
		this.setActions();
	}
	
	private void switchPlayStop() {
		left.getChildren().remove(this.play ? this.buttonPlay : this.buttonStop);
		left.getChildren().add(0, this.play ? this.buttonStop : this.buttonPlay);
		this.play = !this.play;
	}
	
	/*Sets node actions*/
	private void setActions() {
	    EventHandler<ActionEvent> stopPlayHandler = new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent e) {
	            switchPlayStop();
	            if (e.getSource().equals(buttonPlay) || e.getSource().equals(buttonStop)) {
	                CruiseControl.this.view.notifyObservers(SimEvent.SPACEBAR_PRESSED);
	            } else {
	                new IllegalAccessException("Button unknow(not play nor stop)");
	            }
	        }
	    };
	    
	    this.buttonPlay.setOnAction(stopPlayHandler);
	    this.buttonStop.setOnAction(stopPlayHandler);
	    
	    this.buttonAdd.setOnAction(e -> ViewImpl.getView().notifyObservers(SimEvent.ADD) );
	    
	    this.buttonCenter.setOnAction(e -> ViewImpl.getView().notifyObservers(SimEvent.CENTER));
	    
	    this.buttonSpeed.setOnAction(e -> {
	        view.notifyObservers(SimEvent.SPEED_CHANGED);
	    });
	    
	    this.buttonLockMars.setOnAction(e -> {
	        view.setSelectedBody(view.getBodies().get(2));
	        view.notifyObservers(SimEvent.LOCK);
	    });
	    
	    this.buttonESC.setOnAction(e -> {
	        ViewImpl.getView().setSelectedBody(null);
	        this.view.notifyObservers(SimEvent.ESC);
	    });
	}
}
