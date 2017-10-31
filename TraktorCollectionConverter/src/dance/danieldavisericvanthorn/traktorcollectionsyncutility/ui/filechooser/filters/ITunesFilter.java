package dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.filechooser.filters;

import java.io.File;

import javax.swing.JFileChooser;

public class ITunesFilter extends TraktorFileTypeFilter {

	@Override
	public boolean accept(File f) {
		String path = f.getAbsolutePath();
		try {
			String type = path.substring(path.length() - getFileTypeEnding().length());
			return f.isDirectory() || getFileTypeEnding().equals(type);
		} catch (IndexOutOfBoundsException e) {
			return f.isDirectory();
		}
	}

	@Override
	public String getDescription() {
		return "ITunes Library (" + getFileTypeEnding() + ")";
	}

	@Override
	public String getFileTypeEnding() {
		return "iTunes Music Library.xml";
	}

	@Override
	public int getFileSelectionMode() {
		return JFileChooser.FILES_ONLY;
	}

}
