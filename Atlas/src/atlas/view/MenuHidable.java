package atlas.view;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/*
 * 
 */
public abstract class MenuHidable extends BorderPane {
	
	protected Button btn;
	
	
	public MenuHidable() {
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