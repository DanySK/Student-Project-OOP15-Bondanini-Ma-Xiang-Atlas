package atlas.view;

import java.io.File;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

/**
 * Generic file tab pane, files are organized in different sections, separated by tabs.
 * @author maxx
 *
 */
public class InputFilePane extends TabPane{
	
	private File selectedPath;
//	private Map<String, List<File>> files = new HashMap<>();
	
	/**
	 * Creates a tab pane using a map, each tab represents a folder and its the content the files
	 * @param path the root path from which to search the files
	 */
	public InputFilePane(Map<File, List<File>> files) {
	    
//	    File root = new File( path ); 
//	    if(root == null || !root.exists()) {
//	        throw new IllegalStateException("root file does not exits!");
//	    }
//	    
//        File[] list = root.listFiles(); 
//
//        //populates the map with files
//        for ( File f : list ) { 
//            if ( f.isDirectory() ) {
//                System.out.println( "Dir:" + f.getAbsoluteFile() );
//                for(File fs : f.listFiles()) {
//                    if(fs.isFile()) {
//                        files.merge(f.getName(), Arrays.asList(fs), (o,n) -> {
//                            o.addAll(n);
//                            return o;
//                        });
//                        System.out.println( "File:" + fs.getAbsoluteFile() );
//                    }
//                }
//            }
//        } 
        
        //creates the tabs and buttons
		files.entrySet().forEach(i -> {
			Tab tab = new Tab(i.getKey().getName());
			VBox content = new VBox();
			i.getValue().forEach(j -> {
				Button btn = new Button(j.getName());
				btn.setOnAction(e -> {
					selectedPath = j;
					//Create lateral pane
				});
				content.getChildren().add(btn);
			});
			tab.setContent(content);
			this.getTabs().add(tab);
		});
		this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
	}
	
	public String getSelectedPath() {
		return selectedPath.getAbsolutePath();
	}
}