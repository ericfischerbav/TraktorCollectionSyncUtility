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

	public boolean hasSubfolder(Folder folder) {
		return false;
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
