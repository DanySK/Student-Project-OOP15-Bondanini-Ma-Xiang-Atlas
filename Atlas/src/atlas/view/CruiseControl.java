package atlas.view;

import atlas.utils.Units;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * This class contains part of the UI which is used to control the simulation.
 * It provides the following functionalities: 
 * - Play/Pause 
 * - Show date or time of the simulation 
 * - A way to change the simulation speed 
 * - EDIT and ADD buttons 
 * - Search and eventually info/screenshots 
 * 
 * @author MaXX
 *
 */
public class CruiseControl extends HBox {
	protected Button buttonPlay = new Button("PLAY");// setGraphic
	protected Button buttonStop = new Button("STOP");
	protected Button buttonSpeed = new Button("SPEED");
	protected Button buttonEdit = new Button("EDIT");
        protected Button buttonAdd = new Button("ADD");
	private boolean play;

	protected Label labelTime = new Label("Sample: 01/01/2000");

	protected TextField textSpeed = new TextField();
	protected ChoiceBox<Units> choiceSpeedUnit = new ChoiceBox<>();

	

	protected Button buttonSearch = new Button();
	
	private View view;

	public CruiseControl(View v) {
		this.view = v;
		this.getChildren().add(0, buttonStop);
		this.getChildren().add(1, labelTime);
		this.getChildren().add(2, textSpeed);
		this.getChildren().add(3, choiceSpeedUnit);
		this.getChildren().add(4, buttonSpeed);
		this.getChildren().add(5, buttonEdit);
		this.getChildren().add(6, buttonAdd);
		this.getChildren().add(7, buttonSearch);
		this.play = false;
		this.choiceSpeedUnit.getItems().addAll(Units.values());
		
		/*Setting actions*/
		EventHandler<ActionEvent> stopPlayHandler = new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e) {
				switchPlayStop();
				if(e.getSource().equals(buttonPlay) || e.getSource().equals(buttonStop)) {
					CruiseControl.this.view.notifyObservers(SimEvent.SPACEBAR_PRESSED);
				} else {
					new IllegalAccessException("Button unknow(not play nor stop)");
				}
			}			
		};
		this.buttonPlay.setOnAction(stopPlayHandler);
		this.buttonStop.setOnAction(stopPlayHandler);
		
		this.buttonSpeed.setOnAction(e -> {
		    view.notifyObservers(SimEvent.SPEED_CHANGED);
		});
	}

	private void switchPlayStop() {
		this.getChildren().remove(this.play ? this.buttonPlay : this.buttonStop);
		this.getChildren().add(0, this.play ? this.buttonStop : this.buttonPlay);
		this.play = !this.play;
	}
}
