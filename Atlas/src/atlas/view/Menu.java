package atlas.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Menu extends Application {
    
    Button play = new Button();
    Image imageOk = new Image(getClass().getResourceAsStream("ok.png"));
    DropShadow shadow = new DropShadow();
    
    public void start(Stage primaryStage){
       
        play.setText("Play");
        play.setFont(new Font("Roboto Thin", 24));
        play.setGraphic(new ImageView(imageOk));
        
        play.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            play.setEffect(shadow);
        });
    
        play.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            play.setEffect(null);
        });
        
        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background: #98FF98;");
        root.getChildren().add(play);
        
        Scene scene = new Scene(root, 300, 300);
        
        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args){
        launch(args);
    }

}
