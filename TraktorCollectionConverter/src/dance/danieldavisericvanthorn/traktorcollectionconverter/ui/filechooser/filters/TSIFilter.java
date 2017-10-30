package dance.danieldavisericvanthorn.traktorcollectionconverter.ui.filechooser.filters;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class TSIFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		String path = f.getAbsolutePath();
		String type = path.substring(path.length() - 4);
		return isFolder(path) || ".tsi".equals(type);
	}

	private boolean isFolder(String path) {
		return path.substring(path.length() - 1).equals("/");
	}

	@Override
	public String getDescription() {
		return "Traktor Settings File (.tsi)";
	}

}
