package atlas.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import atlas.controller.Controller;
import atlas.controller.ControllerInterface;
import atlas.model.Body;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class View extends Application implements ViewInterface {

    Map<String, ImageView> planet_map = new HashMap<>();
    Map<String, Button> button_map = new HashMap<>();
    double x, y;
    BorderPane rootPane = new BorderPane();
    Pane root1 = new Pane();
    ControllerInterface c;
    double posy = 1;
    boolean bool = true;

    public void start(Stage primaryStage) {

        c = new Controller(this);
        Button play = new Button("Play");
        Button pause = new Button("Pause");
        Image imageOk = new Image("/atlas/resources/button_images/ok.png", 20, 20, false, false);
        Image cross = new Image("/atlas/resources/button_images/not.png", 20, 20, false, false);

        // Azzero le mappe

        planet_map.clear();
        button_map.clear();
        
        //Inserisco i pulsanti nella mappa
        
        button_map.put(play.getText(), play);
        button_map.put(pause.getText(), pause);

        // Setto i pulsanti

        play.setGraphic(new ImageView(imageOk));
        pause.setGraphic(new ImageView(cross));
        
        play.setOnAction(e -> {
            c.startSim();
        });
        
        pause.setOnAction(e -> {
            c.stopSim();
        });

        // Setto lo sfondo

        rootPane.setStyle("-fx-background-image: url('/atlas/resources/planet_images/star.png'); "
                + "-fx-background-position: center center; " + "-fx-background-repeat: stretch;");

        // Setto i pannelli

        rootPane.setCenter(root1);
        BorderPane.setAlignment(root1, Pos.CENTER);

        // Setto la scena e le assegno uno stile da un file css

        Scene scene = new Scene(rootPane);
        scene.getStylesheets().add(getClass().getResource("/atlas/resources/css/testcss.css").toExternalForm());

        
       /* Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        screenSize.getHeight();

        screenSize.getWidth();*/
        
        
        //this method closes the program
        primaryStage.setOnCloseRequest(e -> {
        	this.c.exit();
        	System.exit(0);
        });
        
        
        //set Stage boundaries to visible bounds of the main screen 
        
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        
        // Setto lo Stage facendolo partire a schermo intero

        primaryStage.setScene(scene);
        primaryStage.setTitle("Atlas");
        primaryStage.centerOnScreen();
        primaryStage.setFullScreen(true);
        primaryStage.show();

        // Posto x e y come origine degli assi

        x = (primaryStage.getWidth() / 2)-50;
        y = (primaryStage.getHeight() / 2)-50;

        // Sposta bottoni

        play.relocate(primaryStage.getWidth() - 80, primaryStage.getHeight() - 100);
        pause.relocate(primaryStage.getWidth() - 80, primaryStage.getHeight() - 60);

        // Aggiunta bottoni al pannello

        root1.getChildren().add(play);
        root1.getChildren().add(pause);

        c.startSim();

    }

    public void render(List<Body> b) {
        
        for (Body a : b) {
            if (Math.abs(a.getPosY()) > posy && bool) {
                bool = false;
                posy = a.getPosY();
            }
        }

        double unit = y / (posy*3);
        
        for (Body a : b) {
            if (planet_map.containsKey(a.getName())) {
                planet_map.get(a.getName()).relocate((x + (a.getPosX() * unit)), (y - (a.getPosY() * unit)));
                System.out.println(x + (a.getPosX() * unit));
                System.out.println(y - (a.getPosY() * unit));
            }

            else {
                planet_map.put(a.getName(),
                        new ImageView(new Image("/atlas/resources/planet_images/" + a.getName() + ".png")));
                planet_map.get(a.getName()).setFitHeight(50);
                planet_map.get(a.getName()).setFitWidth(50);
                planet_map.get(a.getName()).setPreserveRatio(true);
                planet_map.get(a.getName()).relocate(x, y);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        root1.getChildren().add(planet_map.get(a.getName()));
                    }
                });

            }
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public Button getPlayButton(){
        return button_map.get("Play");
    }
    
    public Button getPauseButton(){
        return button_map.get("Pause");
    }

}
