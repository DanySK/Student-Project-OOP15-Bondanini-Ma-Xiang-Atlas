package atlas.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import atlas.model.BodyType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;

public class ViewMenuOption extends MenuBar {

	private Menu size = new Menu("Scale");
	private Menu trail = new Menu("Hide trails");

	private RenderScale selectedScale = RenderScale.REAL;
	private List<CheckMenuItem> trailItems = new ArrayList<>();
	private Set<BodyType> selectedTypes = new HashSet<>();

	public ViewMenuOption() {
		
		/*Setting scale options*/
		ToggleGroup group = new ToggleGroup();
		for (RenderScale s : RenderScale.values()) {
			RadioMenuItem item = new RadioMenuItem(s.toString());
			item.setToggleGroup(group);
			this.size.getItems().add(item);
			if(s == RenderScale.REAL) {
				item.setSelected(true);
			}
			item.setOnAction(a -> {
				this.selectedScale = s;
				System.out.println(s);
			});
		}
		
		/*Setting Item - ALL*/
		CheckMenuItem trailAll = new CheckMenuItem("All");
		this.trail.getItems().add(trailAll);
		trailAll.setOnAction(a -> {
			if(!trailAll.isSelected()) {
				this.disableAllTrail(true);
			} else {
				this.disableAllTrail(false);
			}
			this.trailItems.forEach(i -> {
				i.setSelected(false);
			});
			System.out.println(selectedTypes);
		});
		
		/*Setting specific body types items*/
		for(BodyType bt : BodyType.values()) {
			CheckMenuItem item = new CheckMenuItem(bt.toString());
			trailItems.add(item);
			this.trail.getItems().add(item);
			
			item.setOnAction( i -> {
				if(!item.isSelected() ) {
					selectedTypes.remove(bt);
				} else {
					if(trailAll.isSelected()) {
						this.disableAllTrail(true);
					}
					selectedTypes.add(bt);
				}
				trailAll.setSelected(false);
				System.out.println(selectedTypes);
			} );
		}

		this.getMenus().addAll(size, trail);

	}
	
	/**
	 * 
	 * @return the body types to turn off trails.
	 */
	public Set<BodyType> getDisableTrailTypes() {
		return this.selectedTypes;
	}
	
	public RenderScale getSelectedScale() {
		return selectedScale;
	}
	
	/**
	 * Disable or enable all simulation trails.
	 */
	protected void disableAllTrail(boolean disable) {
		if(disable) {
			selectedTypes.removeAll(Arrays.asList(BodyType.values()));			
		} else {
			selectedTypes.addAll(Arrays.asList(BodyType.values()));
		}
	}

}
