package atlas.view;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import atlas.model.Body;
import atlas.model.BodyType;
import atlas.utils.Pair;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class SceneMain extends Scene {

	private static final String FILE_SEP = System.getProperty("file.separator");
	private static final String RES_DIR = System.getProperty("user.dir") + FILE_SEP + "res";

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

		this.setRoot(root);

		this.setCommands();
		/* CSS */
		File file = new File(RES_DIR + FILE_SEP + "css" + FILE_SEP + "uiStyle.css");
		URL url = null;
		try {
			url = file.toURI().toURL();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getStylesheets().clear();
		this.getStylesheets().add(url.toExternalForm());
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
			// salvare poszioni in viewImpl, ed eliminarle dopo aver aggiunto il
			// planet
			view.setMousePos(new Pair<Double, Double>(e.getX(), e.getY()));
			System.out.println("X: " + e.getX() + "Y: " + e.getY());
			view.notifyObservers(SimEvent.MOUSE_CLICKED);
		});

		this.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.SECONDARY) {
				ViewImpl.getView().setSelectedBody(null);
				ViewImpl.getView().notifyObservers(SimEvent.ESC);
			}
		});

	}

	protected void draw(List<Body> bodies, double scale, Pair<Double, Double> translate, String time, int fps) {
		RenderScale scaleType = this.cruise.viewMenu.getSelectedScale();
		Set<BodyType> disabledTrail = this.cruise.viewMenu.getDisableTrailTypes();

		this.cruise.labelTime.setText(time);
		if (this.infoMenu.isShown()) {
			this.infoMenu.update(ViewImpl.getView().getSelectedBody());
		}
		this.renderPanel.render(bodies, scaleType, scale, translate, fps, disabledTrail);
	}
}
