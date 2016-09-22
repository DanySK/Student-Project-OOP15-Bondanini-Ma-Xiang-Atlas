package atlas.view;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/*
 * 
 */
public abstract class MenuHidable extends BorderPane {
	
	protected static final double TOP_WIDTH = 300;
	protected static final double TOP_HEIGHT = 200;
	
	protected Button btn;	
	
	public MenuHidable() {
		/*Set all children's font in the css file*/
		this.setId("menu-hidable");
		
		btn = new Button("|||");
		btn.setMaxHeight(Double.MAX_VALUE);
		btn.setOnAction(e -> {
			if(isShown()) {
				hideContent();
			} else {
				showContent();
			}
		});
	}
	
	public abstract void showContent();
	
	public void hideContent() {
		this.getChildren().removeIf(i -> !i.equals(btn));
	}
	
	public boolean isShown() {
		return this.getChildren().size() > 1 && this.getChildren().contains(btn);
	}
}