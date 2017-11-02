package dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.filechooser.filters;

import javax.swing.filechooser.FileFilter;

public abstract class TraktorFileTypeFilter extends FileFilter {

	public abstract String getFileTypeEnding();

	public abstract int getFileSelectionMode();

}
