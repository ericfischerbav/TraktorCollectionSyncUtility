package dance.danieldavisericvanthorn.traktorcollectionconverter.ui.filechooser;

import javax.swing.filechooser.FileFilter;

import dance.danieldavisericvanthorn.traktorcollectionconverter.ui.filechooser.filters.TSIFilter;

public enum TraktorFileType {

	SETTINGS(new TSIFilter()), COLLECTION(null);

	private FileFilter filter;

	private TraktorFileType(FileFilter filter) {
		this.filter = filter;
	}

	public FileFilter getFileFilter() {
		return filter;
	}

}
