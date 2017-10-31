package dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.filechooser.filters;

import java.io.File;

import javax.swing.JFileChooser;

public class FolderFilter extends TraktorFileTypeFilter {

	@Override
	public String getFileTypeEnding() {
		return null;
	}

	@Override
	public boolean accept(File f) {
		return f.isDirectory();
	}

	@Override
	public String getDescription() {
		return "Folder";
	}

	@Override
	public int getFileSelectionMode() {
		return JFileChooser.DIRECTORIES_ONLY;
	}

}
