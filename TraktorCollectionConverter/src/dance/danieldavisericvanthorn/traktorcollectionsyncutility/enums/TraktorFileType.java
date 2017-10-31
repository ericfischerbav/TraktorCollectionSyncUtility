package dance.danieldavisericvanthorn.traktorcollectionsyncutility.enums;

import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.filechooser.filters.FolderFilter;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.filechooser.filters.NMLFilter;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.filechooser.filters.TSIFilter;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.filechooser.filters.TraktorFileTypeFilter;

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
