package atlas.view;

import java.util.HashMap;
import java.util.Map;

import atlas.controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class BottonPanel {
    
    VBox vbox = new VBox();
    
    Controller c;
    
    Map<String, Button> button_map = new HashMap<>();
    
    Button play = new Button("Play");
    Button pause = new Button("Pause");
    Button close = new Button("Exit");
    Button zoomUp = new Button("Zoom +");
    Button zoomDown = new Button("Zoom -");
    Button save = new Button("Salva");
    Button load = new Button("Carica");
    
    Image imageOk = new Image("/button_images/ok.png", 20, 20, false, false);
    Image cross = new Image("/button_images/not.png", 20, 20, false, false);
    
    String path = System.getProperty("user.home")+System.getProperty("file.separator")+"ciao.bin";
    
        public BottonPanel(){
            
            play.setGraphic(new ImageView(imageOk));
            pause.setGraphic(new ImageView(cross));
            
            button_map.clear();
            button_map.put(play.getText(), play);
            button_map.put(pause.getText(), pause);
            button_map.put(close.getText(), close);
            button_map.put(zoomUp.getText(), zoomUp);
            button_map.put(save.getText(), save);
            button_map.put(load.getText(), load);
            
            play.setOnAction(e -> {
                c.startSim();
            });

            pause.setOnAction(e -> {
                c.stopSim();
            });
            
            close.setOnAction(e -> {
                System.exit(0);
            });
            
            save.setOnAction(e -> {
                try {
                    c.saveConfig(path);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
            
            load.setOnAction(e -> {
                try {
                    c.loadConfig(path);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
            
            vbox.setSpacing(button_map.size());
            vbox.setAlignment(Pos.BOTTOM_CENTER);
            button_map.entrySet().stream().forEach( b -> {
                Button c = b.getValue();
                vbox.getChildren().add(c);
            });
                
        }
        
        public VBox getBottonBox(){
            return vbox;
        }

}
