package atlas.view;

import java.util.List;

import atlas.model.Body;
import atlas.utils.Pair;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class SceneMain extends Scene {
	
	protected BorderPane root = new BorderPane();
	protected CruiseControl cruise;
	protected RenderScreen renderPanel;
	protected MenuOption optionMenu;
	protected MenuInfo infoMenu;
	protected Image background;
	
	private View view;
	
	public SceneMain() {
		super(new Pane());
		this.view = ViewImpl.getView();
		this.renderPanel = new RenderScreen();
		this.cruise = new CruiseControl();
		this.optionMenu = new MenuOption();
		this.infoMenu = new MenuInfo();
		
		this.root.setMinSize(0, 0);
		this.root.setCenter(renderPanel);
		this.root.setBottom(cruise);
		this.root.setLeft(optionMenu);
		this.root.setRight(infoMenu);
		this.root.setStyle("-fx-border-color: aqua;");
		
		this.setRoot(root);
		
		this.setCommands();
	}


    private void setCommands() {
        this.setKeyboardCommands();
        this.setScrollCommands();
        this.setMouseCommands();
    }

    private void setScrollCommands() {
        this.renderPanel.setOnScroll(e -> {
            if (e.getDeltaY() > 0) {
                this.view.notifyObservers(SimEvent.MOUSE_WHEEL_UP);
            } else {
                this.view.notifyObservers(SimEvent.MOUSE_WHEEL_DOWN);
            }
        });
    }

    private void setKeyboardCommands() {
        root.setOnKeyPressed(k -> {
            switch (k.getCode()) {
            case W:
                view.notifyObservers(SimEvent.W);
                break;
            case A:
                view.notifyObservers(SimEvent.A);
                break;
            case S:
                view.notifyObservers(SimEvent.S);
                break;
            case D:
                view.notifyObservers(SimEvent.D);
                break;

            case ESCAPE:
                view.notifyObservers(SimEvent.ESC);
                break;

            case SPACE:
                view.notifyObservers(SimEvent.SPACEBAR_PRESSED);
                break;
            default:
                break;
            }
        });
    }

    private void setMouseCommands() {
        this.renderPanel.setOnMouseClicked(e -> {
            // MouseEvent lastEv = view.getLastMouseEvent().orElseGet(null);
            // ????
            // lastEv = e;MouseEvent lastEv =
            // view.getLastMouseEvent().orElseGet(null);
            // lastEv = e;
            view.notifyObservers(SimEvent.MOUSE_CLICKED);
        });

    }

    protected void draw(List<Body> bodies, double scale, Pair<Double, Double> translate, String time, int fps) {
        this.cruise.labelTime.setText(time);
        this.infoMenu.update(ViewImpl.getView().getSelectedBody());
        this.renderPanel.render(bodies, scale, translate, fps);
    }
}
