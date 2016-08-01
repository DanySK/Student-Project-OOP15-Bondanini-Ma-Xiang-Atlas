package atlas.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import atlas.controller.ControllerImpl;
import atlas.controller.Controller;
import atlas.model.Body;
import atlas.model.BodyType;
import atlas.model.EpochJ2000;
import atlas.utils.EventType;
import atlas.utils.MyMouse;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.MouseInfo;

public class ViewImpl extends Application implements View {

    Map<String, ImageView> planet_map = new HashMap<>();
    Map<String, Button> button_map = new HashMap<>();
    double x, y;
    BorderPane rootPane = new BorderPane();
    Pane root1 = new Pane();
    Controller c;
    double posy = 1;
    boolean bool = true;
    double unit = 0.0;
    double cont = 1.0;

    public void start(Stage primaryStage) {

        c = ControllerImpl.getIstanceOf();
        c.setView(this);
        Button play = new Button("Play");
        Button pause = new Button("Pause");
        Label zoomLabel = new Label("Zoom");
        Button zoomUp = new Button("+");
        Button zoomDown = new Button("-");
        Button save = new Button("Salva");
        Button load = new Button("Carica");
        Image imageOk = new Image("/button_images/ok.png", 20, 20, false, false);
        Image cross = new Image("/button_images/not.png", 20, 20, false, false);
        
        

        zoomLabel.relocate(x, posy);
        
        // Azzero le mappe

        planet_map.clear();
        button_map.clear();

        // Inserisco i pulsanti nella mappa

        button_map.put(play.getText(), play);
        button_map.put(pause.getText(), pause);
        button_map.put(zoomUp.getText(), zoomUp);
        button_map.put(save.getText(), save);
        button_map.put(load.getText(), load);
        

        // Setto i pulsanti

        play.setGraphic(new ImageView(imageOk));
        pause.setGraphic(new ImageView(cross));

        play.setOnAction(e -> {
            c.startSim();
        });

        pause.setOnAction(e -> {
            c.stopSim();
        });

        // In futuro scrivere un action-listener, dato che tanti pulsanti
        // avranno la stessa funzione e ci sarebbe ripetizione di codice
        /* earth.setOnAction(e -> {
            c.setAdding();
            c.setNextBody(EpochJ2000.EARTH.getBody());
        });*/

        // Setto lo sfondo

        rootPane.setStyle("-fx-background-image: url('/planet_images/star.png'); "
                + "-fx-background-position: center center; " + "-fx-background-repeat: stretch;");

        // Setto i pannelli

        rootPane.setCenter(root1);
        BorderPane.setAlignment(root1, Pos.CENTER);

        // Setto la scena e le assegno uno stile da un file css

        Scene scene = new Scene(rootPane);
        scene.getStylesheets().add(getClass().getResource("/css/testcss.css").toExternalForm());

        /*
         * Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
         * 
         * screenSize.getHeight();
         * 
         * screenSize.getWidth();
         */

        // this method closes the program
        primaryStage.setOnCloseRequest(e -> {
            this.c.exit();
            System.exit(0);
        });

        // set Stage boundaries to visible bounds of the main screen

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

        x = (primaryStage.getWidth() / 2) - 50;
        y = (primaryStage.getHeight() / 2) - 50;

        // Sposta bottoni

        play.relocate(primaryStage.getWidth() - 80, primaryStage.getHeight() - 100);
        pause.relocate(primaryStage.getWidth() - 80, primaryStage.getHeight() - 60);
        
        save.relocate(primaryStage.getWidth() - 80, primaryStage.getHeight() - 300);
        load.relocate(primaryStage.getWidth() - 80, primaryStage.getHeight() - 340);
        
        
        zoomLabel.relocate(primaryScreenBounds.getMaxX() - 80, primaryScreenBounds.getMaxY() - 220);
        zoomUp.relocate(primaryScreenBounds.getMaxX() - 80, primaryScreenBounds.getMaxY() - 180);
        zoomDown.relocate(primaryScreenBounds.getMaxX() - 80, primaryScreenBounds.getMaxY() - 140);
        
        zoomUp.setOnAction(e -> {
            this.unit = c.zoomUp();
            cont+=1;
        });
        

        zoomDown.setOnAction(e -> {
            this.unit = c.zoomDown();
            cont-=0.2;
        });

        // Aggiunta bottoni al pannello

        root1.getChildren().add(play);
        root1.getChildren().add(pause);
        root1.getChildren().add(zoomLabel);
        root1.getChildren().add(zoomUp);
        root1.getChildren().add(zoomDown);
        root1.getChildren().add(load);
        root1.getChildren().add(save);

        c.startSim();

    }

    public void render(List<Body> b) {
        //Prende lo Zoom inizale solo la prima volta..
        if (bool) {
            this.unit = c.getUnit();
            this.bool = false;
            System.out.println("unitView " + unit);
        }

        for (Body a : b) {
            if (planet_map.containsKey(a.getName())) {
                planet_map.get(a.getName()).relocate((x + (a.getPosX() * unit)), (y - (a.getPosY() * unit)));
                System.out.println(x + (a.getPosX() * unit) + "PosX schermo");
                System.out.println(y - (a.getPosY() * unit) + "PosY schermo");
                System.out.println(x + (a.getPosX()) + "PosX real");
                System.out.println(y - (a.getPosY()) + "PosY real");
            }

            else {
                planet_map.put(a.getName(),
                        new ImageView(new Image("/planet_images/" + a.getName().toLowerCase() + ".png")));
                planet_map.get(a.getName()).setFitHeight(50*cont);
                planet_map.get(a.getName()).setFitWidth(50*cont);
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

    public Button getPlayButton() {
        return button_map.get("Play");
    }

    public Button getPauseButton() {
        return button_map.get("Pause");
    }

    /*
     * Not Work -> Capire come funziona con javaFX public void
     * mouseClicked(MouseEvent e) { if (c.isAdding()) { try {
     * c.update(EventType.ADDING_BODY, null,
     * Optional.of(MouseInfo.getPointerInfo().getLocation().getX()),
     * Optional.of(MouseInfo.getPointerInfo().getLocation().getX()));
     * 
     * } catch (IllegalArgumentException e1) { e1.printStackTrace(); } catch
     * (IOException e1) { e1.printStackTrace(); } }
     */

}
