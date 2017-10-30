package dance.danieldavisericvanthorn.traktorcollectionconverter.enums;

import dance.danieldavisericvanthorn.traktorcollectionconverter.ui.filechooser.filters.FolderFilter;
import dance.danieldavisericvanthorn.traktorcollectionconverter.ui.filechooser.filters.NMLFilter;
import dance.danieldavisericvanthorn.traktorcollectionconverter.ui.filechooser.filters.TSIFilter;
import dance.danieldavisericvanthorn.traktorcollectionconverter.ui.filechooser.filters.TraktorFileTypeFilter;

public enum TraktorFileType {

	SETTINGS(new TSIFilter()), COLLECTION(new NMLFilter()), FOLDER(new FolderFilter());

	private TraktorFileTypeFilter filter;

	private TraktorFileType(TraktorFileTypeFilter filter) {
		this.filter = filter;
	}

	public TraktorFileTypeFilter getFileFilter() {
		return filter;
	}

	public String getFileTypeEnding() {
		return filter.getFileTypeEnding();
	}

}
