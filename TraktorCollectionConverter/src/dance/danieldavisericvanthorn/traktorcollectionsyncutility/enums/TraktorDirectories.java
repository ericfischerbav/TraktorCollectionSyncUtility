package dance.danieldavisericvanthorn.traktorcollectionsyncutility.enums;

public enum TraktorDirectories {

	ROOT("Root path"), ITUNES("ITunes Music Library"), MUSIC("Music Folders"), LOOPS("Loops Folder"), REMIXSETS(
			"Remix Sets Folder"), RECORDINGS("Recordings Folder");

	private String name;

	private TraktorDirectories(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
