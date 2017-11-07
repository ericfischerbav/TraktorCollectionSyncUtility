package dance.danieldavisericvanthorn.traktorcollectionsyncutility.converter.model;

import java.util.ArrayList;
import java.util.List;

import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.enums.ErrorCase;

public class Folder implements Comparable<Folder> {

	public static final String COLLECTION_SPLITTER = "/:";

	private String name = "";
	private List<String> path = new ArrayList<>();

	public Folder(String path) {
		String[] folders = path.split(COLLECTION_SPLITTER);
		for (String string : folders) {
			this.path.add(string);
		}
		name = folders[folders.length - 1];
	}

	/**
	 * Checks, if the given {@link Folder} is a subfolder of this object.
	 *
	 * @param folder
	 * @return
	 */
	public boolean hasSubfolder(Folder folder) {
		int index = 0;
		for (String string : path) {
			if (!string.equals(folder.getPath().get(index))) {
				return false;
			}
			index++;
		}
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Folder)) {
			return false;
		}

		Folder castedObj = (Folder) obj;

		return this.toString().equals(castedObj.toString());

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String string : path) {
			sb.append(string);
			sb.append(COLLECTION_SPLITTER);
		}
		return sb.toString();
	}

	@Override
	public int compareTo(Folder o) {
		if (this.equals(o)) {
			return 0;
		}

		if (this.hasSubfolder(o)) {
			return 1;
		}

		if (o.hasSubfolder(this)) {
			return -1;
		}

		throw new IllegalArgumentException(ErrorCase.GIVEN_FOLDER_NOT_COMPARABLE.getCode());

	}

	public String getName() {
		return name;
	}

	public List<String> getPath() {
		return path;
	}

}
