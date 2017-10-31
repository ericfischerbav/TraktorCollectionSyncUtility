package dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.filechooser.filters;

import java.io.File;

import javax.swing.JFileChooser;

public class NMLFilter extends TraktorFileTypeFilter {

	@Override
	public boolean accept(File f) {
		String path = f.getAbsolutePath();
		String type = path.substring(path.length() - 4);
		return isFolder(path) || getFileTypeEnding().equals(type);
	}

	private boolean isFolder(String path) {
		File f = new File(path);
		return f.isDirectory();
	}

	@Override
	public String getDescription() {
		return "Traktor Collection File (" + getFileTypeEnding() + ")";
	}

	@Override
	public String getFileTypeEnding() {
		return ".nml";
	}

	@Override
	public int getFileSelectionMode() {
		return JFileChooser.FILES_ONLY;
	}

}
