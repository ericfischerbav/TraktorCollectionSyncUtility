package dance.danieldavisericvanthorn.traktorcollectionsyncutility.converter.container;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class Folder {

	private String name = "";
	private List<String> path = new ArrayList<>();

	public Folder(String path) {
		String[] folders = path.split(Matcher.quoteReplacement("\\"));
		for (String string : folders) {
			this.path.add(string);
		}
		name = folders[folders.length - 1];
	}

	public String getName() {
		return name;
	}

	public List<String> getPath() {
		return path;
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

		return this.path.equals(castedObj.getPath());

	}

}
