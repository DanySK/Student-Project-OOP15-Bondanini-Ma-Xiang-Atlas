package atlas.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import atlas.controller.ControllerImpl;
import atlas.controller.Controller;
import atlas.model.Body;
import atlas.utils.Pair;
import atlas.utils.Units;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ViewTest extends Application implements View {

    Map<String, ImageView> planet_map = new HashMap<>();
    
    List<Label> label_list = new ArrayList<>();
    double x, y;
    StackPane rootPane = new StackPane();
    Controller c;
    double posy = 1;
    boolean bool = true;
    double unit = 0.0;
    private Optional<Body> selectedBody; 
    BottonPanel vbox;
    
    @Override
    public void notifyObservers(SimEvent event) {
            ControllerImpl.getIstanceOf().update(event);
    }

    @Override
    public Optional<Body> getSelectedBody() {
            return this.selectedBody;
    }

    public void start(Stage primaryStage) {

        c = ControllerImpl.getIstanceOf();
        c.setView(this);

        // Azzero le mappe

        planet_map.clear();

        // In futuro scrivere un action-listener, dato che tanti pulsanti
        // avranno la stessa funzione e ci sarebbe ripetizione di codice
        /* earth.setOnAction(e -> {
            c.setAdding();
            c.setNextBody(EpochJ2000.EARTH.getBody());
        });*/

        // Setto lo sfondo

        rootPane.setStyle("-fx-background-image: url('/planet_images/star.png'); "
                + "-fx-background-position: center center; " + "-fx-background-repeat: stretch;");
        
        // Setto la scena e le assegno uno stile da un file css

        Scene scene = new Scene(rootPane);
        scene.getStylesheets().add(getClass().getResource("/css/testcss.css").toExternalForm());
        
        rootPane.getChildren().add(vbox.getBottonBox());

        /*
         * Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
         * 
         * screenSize.getHeight();         * 
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
        
        //zoomUp.setOnAction(e -> {
            //this.unit = c.zoomUp();
           /* planet_map.entrySet().stream().forEach(a -> {
                ImageView im = a.getValue();
                im.setFitHeight(im.getFitHeight()*1.2);
                im.setFitWidth(im.getFitWidth()*1.2);
                planet_map.replace(a.getKey(), im);
            });*/
        //});
        

        //zoomDown.setOnAction(e -> {
            //this.unit = c.zoomDown();
           /* planet_map.entrySet().stream().forEach(a -> {
                ImageView im = a.getValue();
                im.setFitHeight(im.getFitHeight()*0.8);
                im.setFitWidth(im.getFitWidth()*0.8);
                planet_map.replace(a.getKey(), im);
            });*/
        //});

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
            double h = unit* a.getProperties().getRadius();
            System.out.println("this is h" +  h);
            if (planet_map.containsKey(a.getName())) {
                planet_map.get(a.getName()).relocate((x + (a.getPosX() * unit)), (y - (a.getPosY() * unit)));
                for (Label i : label_list){
                	System.out.println("Label text = " + i.getText() + " | a = " + a.getName());
                	System.out.println("Pos = " + i.getLayoutX());
                    if (i.getText().equals(a.getName())){
                        i.relocate((x + (a.getPosX() * unit)), (y - (a.getPosY() * unit)) + 10);
                    }
                }
                //Con lo stream e' meglio
//                this.label_list.stream().filter(p -> p.getText().equals(a.getName())).findFirst()
//                .ifPresent(i -> i.relocate((x + (a.getPosX() * unit)), (y - (a.getPosY() * unit)) + 10));
                Collection<Pair<Double,Double>> q = a.getTrail();
                for (Pair<Double, Double> p : q){
                    
                }
                System.out.println(x + (a.getPosX() * unit) + "PosX schermo");
                System.out.println(y - (a.getPosY() * unit) + "PosY schermo");
                System.out.println(x + (a.getPosX()) + "PosX real");
                System.out.println(y - (a.getPosY()) + "PosY real");
            }

            else {
            	Label l = new Label(a.getName());
                label_list.add(l);
                planet_map.put(a.getName(),
                        new ImageView(new Image("/planet_images/" + a.getName().toLowerCase() + ".png")));
                if(h>1){
                    planet_map.get(a.getName()).setFitHeight(h);
                    planet_map.get(a.getName()).setFitWidth(h);
                } else {
                    planet_map.get(a.getName()).setFitHeight(1);
                    planet_map.get(a.getName()).setFitWidth(1);
                }
                planet_map.get(a.getName()).setPreserveRatio(true);
                planet_map.get(a.getName()).relocate(x, y);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        rootPane.getChildren().add(planet_map.get(a.getName()));
                        rootPane.getChildren().add(l);
                    }
                });

            }
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public Optional<MouseEvent> getLastMouseEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<Integer, Units> getSpeedInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSaveName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetViewLayout() {
		// TODO Auto-generated method stub
		
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
