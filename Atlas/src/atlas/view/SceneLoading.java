package atlas.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class SceneLoading extends Scene{
	
	protected static ImageView logo = new ImageView(new Image("/images/" + "logo.png"));
	
	private Group root = new Group();
	
	public SceneLoading() {
		super(new Pane());
		root.getChildren().add(logo);
		this.setRoot(root);
	}
	
}
