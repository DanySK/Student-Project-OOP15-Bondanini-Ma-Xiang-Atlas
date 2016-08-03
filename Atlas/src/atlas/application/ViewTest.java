//package atlas.application;
//
//import java.awt.Dimension;
//import java.util.List;
//import java.util.Optional;
//
//import javax.swing.JFrame;
//
//import atlas.model.Body;
//import atlas.view.SimEvent;
//import atlas.view.View;
//import javafx.scene.control.Button;
//
//public class ViewTest extends JFrame implements View{
//
//    private PanelTest panel;
//    
//    public ViewTest() {
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setVisible(true);
//        this.setSize(1200, 1000);
//        this.setTitle("view Test");
//        
//        panel = new PanelTest(this.size());
//        
//        this.add(this.panel);
//        
//        
//        this.setVisible(true);
//    }
//    
//    @Override
//    public void render(List<Body> b) {
//        this.panel.update(b);
//        this.panel.repaint();        
//    }
//
//	@Override
//	public void notifyObservers(SimEvent event) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public Optional<Body> getSelectedBody() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//    
//}
