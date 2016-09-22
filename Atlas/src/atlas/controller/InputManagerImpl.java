package atlas.controller;

import java.awt.Point;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import atlas.model.Body;
import atlas.model.Model;
import atlas.utils.Pair;
import atlas.view.View;
import atlas.view.ViewImpl;

public class InputManagerImpl implements InputManager {

	private static final String FILE_SEP = System.getProperty("file.separator");
	private static final String RES_DIR = System.getProperty("user.dir") + FILE_SEP + "res";
	private static final String SAVE_DIR = RES_DIR + FILE_SEP + "saves";
	private static final String SAVE_LOCATION = SAVE_DIR + FILE_SEP + "presets";
	private static final String ADD_DIR = RES_DIR + FILE_SEP + "bodies";

	private View view;
	private Model model;
	private GameLoop gLoop;
	private DragPositions threadDrag;
	private Status status = Status.DEFAULT;
	private Optional<Body> bodyToAdd = Optional.empty();

	double scale = 1.4000000000000000E-9;
	Pair<Double, Double> reference;
	Pair<Double, Double> initialReference;

	public InputManagerImpl(View view, Model model, GameLoop gLoop, Pair<Double, Double> reference) {
		this.view = view;
		this.model = model;
		this.gLoop = gLoop;
		this.initialReference = reference;
		this.reference = reference;
		this.threadDrag = new DragPositions(this.scale, this.reference);
	}

	@Override
	public void addMode() {
		Optional<File> f = this.view.getLoadFile("Add body", "ADD", this.getFiles(ADD_DIR));

		if (!f.isPresent()) {
			System.out.println("Operation CANCELED");
			return;
		}

		if (!this.checkFileExists(f.get())) {
			throw new IllegalArgumentException();
		}

		try (InputStream bstream = new BufferedInputStream(new FileInputStream(f.get()));
				ObjectInputStream ostream = new ObjectInputStream(bstream);) {
			this.bodyToAdd = Optional.ofNullable((Body) ostream.readObject());
		} catch (Exception e) {
			throw new IllegalArgumentException("Content of the file is not suitable.");
		}

		if (this.bodyToAdd.isPresent()) {
			this.status = Status.ADDING;
		}
	}

	@Override
	public void mouseClicked() {
		if (this.status.equals(Status.ADDING)) {
			if (!this.bodyToAdd.isPresent()) {
				throw new IllegalStateException("Body to add is not present!");
			}
			this.bodyToAdd.get()
					.setPosX((this.view.getMousePos().getX() - this.view.getWindow().getX() - this.reference.getX())
							/ this.scale);
			this.bodyToAdd.get()
					.setPosY((this.view.getMousePos().getY() - this.view.getWindow().getY() - this.reference.getY())
							/ -this.scale);
			this.gLoop.setNextBodyToAdd(this.bodyToAdd.get());
		}
		this.status = Status.DEFAULT;
	}

	@Override
	public void mouseMultiClick() {
		if (!this.status.equals(Status.EDIT)) {
			this.threadDrag.start();
		}

	}

	@Override
	public void mousePressed() {

	}

	@Override
	public void mouseReleased() {
	}

	@Override
	public void ESC() {
		if (this.status.equals(Status.ADDING)) {
			this.view.deleteNextBody();
		}
		this.status = Status.DEFAULT;
	}

	@Override
	public void zoomUp() { // Da cambiare in un metodo solo per non ripetere il
							// codice
		this.scale *= 1.10;
		if (this.threadDrag.isAlive()) {
			this.threadDrag.setScale(this.scale);
		}
		this.view.updateReferce(this.reference, this.scale);

	}

	@Override
	public void zoomDown() {
		this.scale *= 0.90;
		if (this.threadDrag.isAlive()) {
			this.threadDrag.setScale(this.scale);
		}
		this.view.updateReferce(this.reference, this.scale);

	}

	@Override
	public void wSlide() {
		this.reference = new Pair<Double, Double>(this.reference.getX(), this.reference.getY() + 25);
		this.view.updateReferce(this.reference, this.scale);
		this.setDefault();
	}

	@Override
	public void sSlide() {
		this.reference = new Pair<Double, Double>(this.reference.getX(), this.reference.getY() - 25);
		this.view.updateReferce(this.reference, this.scale);
		this.setDefault();
	}

	@Override
	public void aSlide() {
		this.reference = new Pair<Double, Double>(this.reference.getX() + 25, this.reference.getY());
		this.view.updateReferce(this.reference, this.scale);
		this.setDefault();
	}

	@Override
	public void dSlide() {
		this.reference = new Pair<Double, Double>(this.reference.getX() - 25, this.reference.getY());
		this.view.updateReferce(this.reference, this.scale);
		this.setDefault();

	}

	@Override
	public void spaceBar() {
		if (this.gLoop.getStatus().equals(StatusSim.RUNNING)) {
			this.gLoop.setStopped();
		} else {
			this.gLoop.setRunning();
		}
	}

	@Override
	public void saveConfig() throws IOException, IllegalArgumentException {
		Optional<File> f = getSaveFile(SAVE_LOCATION);
		// do nothing if user cancels the operation
		if (!f.isPresent()) {
			return;
		}

		try (OutputStream bstream = new BufferedOutputStream(new FileOutputStream(f.get()));
				ObjectOutputStream ostream = new ObjectOutputStream(bstream);) {
			ostream.writeObject(this.model);
			ostream.writeDouble(this.scale);
			ostream.writeLong(this.gLoop.getUnit());
			ostream.writeInt(this.gLoop.getSpeed());
		}
	}

	@Override
	public void saveBody() throws IOException, IllegalArgumentException {
		if (!ViewImpl.getView().getSelectedBody().isPresent()) {
			throw new IllegalArgumentException();
		}
		Optional<File> f = getSaveFile(
				ADD_DIR + FILE_SEP + ViewImpl.getView().getSelectedBody().get().getType().toString().toLowerCase());
		// do nothing if user cancels the operation
		if (!f.isPresent()) {
			return;
		}

		try (OutputStream bstream = new BufferedOutputStream(new FileOutputStream(f.get()));
				ObjectOutputStream ostream = new ObjectOutputStream(bstream);) {
			ostream.writeObject(ViewImpl.getView().getSelectedBody().get());
		}
	}

	/* Gets the file to save to, asking the user to provide a name */
	private Optional<File> getSaveFile(String path) {
		Optional<String> saveName = this.view.getSaveName();
		if (!saveName.isPresent() || saveName.isPresent() && saveName.get().length() <= 0) {
			System.out.println("Operation CANCELED");
			return Optional.empty();
		}
		String dir = path + FILE_SEP + saveName.get();
		File f = new File(dir);
		if (this.checkFileExists(f)) {
			throw new IllegalArgumentException("Cannot save, file name already exits!");
		}
		return Optional.ofNullable(f);
	}

	@Override
	public Optional<Model> loadConfig() throws IOException, IllegalArgumentException {
		Optional<File> f = this.view.getLoadFile("Load configuration", "LOAD", this.getFiles(SAVE_DIR));
		if (!f.isPresent()) {
			System.out.println("Operation CANCELED");
			return Optional.empty();
		}

		if (!this.checkFileExists(f.get())) {
			throw new IllegalArgumentException("Cannot load, file doesn't exits!");
		}
		try (InputStream bstream = new BufferedInputStream(new FileInputStream(f.get()));
				ObjectInputStream ostream = new ObjectInputStream(bstream);) {
			this.model = (Model) ostream.readObject();
			this.scale = ostream.readDouble();
			long unit = ostream.readLong();
			int speed = ostream.readInt();
			this.gLoop.setModel(model);
			gLoop.setValue(unit, speed);
			ViewImpl.getView().setSelectedBody(null);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Content of the file is not suitable.");
		}

		return Optional.ofNullable(this.model);
	}

	/**
	 * Gets the files in the following way: from a root directory it adds only
	 * the children directories as keys, and then it adds the latter's files as
	 * value.
	 * 
	 * @param path
	 * @return
	 */
	private Map<File, List<File>> getFiles(String path) {
		Map<File, List<File>> files = new HashMap<>();
		File root = new File(path);
		if (root == null || !root.exists()) {
			throw new IllegalStateException("root file does not exits!");
		}

		File[] list = root.listFiles();
		// populates the map with files
		for (File f : list) {
			if (f.isDirectory()) {
				for (File fs : f.listFiles()) {
					if (fs.isFile()) {
						files.merge(f, Arrays.asList(fs), (o, n) -> {
							List<File> l = new ArrayList<>(o);
							l.addAll(n);
							return l;
						});
					}
				}
			}
		}
		return files;
	}

	private boolean checkFileExists(File f) {
		return f.exists() && !f.isDirectory();
	}

	@Override
	public void changeSpeed() {
		this.view.getSpeedInfo().ifPresent(i -> this.gLoop.setValue(i.getY().getValue(), i.getX()));
	}

	@Override
	public void changeStatus(Status status) {
		this.status = status;

	}

	@Override
	public void initialReference() {
		this.model.getBodiesToRender().stream().max((a, b) -> (int) (a.getMass() - b.getMass()))
				.ifPresent(i -> this.reference = new Pair<>(i.getPosX() * -scale, i.getPosY() * scale));
		this.view.updateReferce(this.reference, this.scale);
	}

	private void setDefault() {
		this.status = Status.DEFAULT;
	}

}
