package atlas.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import atlas.model.Model;
import atlas.model.ModelImpl;

public class testLoad {
	
	private double unit;

	private boolean checkFileExists(File f) {
        return f.exists() && !f.isDirectory();
    }
	
    public void saveConfig(Model m, String path) throws IOException, IllegalArgumentException {
        File f = new File(path);
        if (this.checkFileExists(f)) {
            throw new IllegalArgumentException();
        }
        
        try (OutputStream bstream = new BufferedOutputStream(new FileOutputStream(f));
                ObjectOutputStream ostream = new ObjectOutputStream(bstream);) {
            ostream.writeObject(m);
            ostream.writeDouble(this.unit);
        }
    }

    public Model loadConfig(String path) throws IOException, IllegalArgumentException {
        File f = new File(path);
        if (!this.checkFileExists(f)) {
            throw new IllegalArgumentException();
        }
        Model m = new ModelImpl();
        try (InputStream bstream = new BufferedInputStream(new FileInputStream(f));
                ObjectInputStream ostream = new ObjectInputStream(bstream);) {
            m = (Model)ostream.readObject();
            this.unit = ostream.readDouble();   
            System.out.println("unit = " + unit);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Content of the file is not suitable.");
        }
        
        return m;
    }

	public static void main(String[] args) {
		Model model = new ModelImpl();
		
		testLoad l = new testLoad();
		
		String path = System.getProperty("user.dir") + "/res/" + "prova.bin";
		
		try {
			l.saveConfig(model, path);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Model mloaded = new ModelImpl();
		try {
			mloaded = l.loadConfig(path);
		} catch (IllegalArgumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("MODEL");
		model.getBodiesToRender().forEach( i -> {
			System.out.println(i);
		});
		
		System.out.println("LOADED ------------------------------------------->");
		mloaded.getBodiesToRender().forEach( i -> {
			System.out.println(i);
		});
	}

}
