package atlas.application;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;

import atlas.model.Body;
import atlas.view.ViewInterface;
import javafx.scene.control.Button;

public class ViewTest extends JFrame implements ViewInterface{

    private PanelTest panel;
    
    public ViewTest() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(1200, 1000);
        this.setTitle("view Test");
        
        panel = new PanelTest(this.size());
        
        this.add(this.panel);
        
        
        this.setVisible(true);
    }
    
    @Override
    public void render(List<Body> b) {
        this.panel.update(b);
        this.panel.repaint();        
    }

    @Override
    public Button getPlayButton() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Button getPauseButton() {
        // TODO Auto-generated method stub
        return null;
    }

}
