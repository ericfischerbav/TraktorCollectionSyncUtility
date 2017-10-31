package dance.danieldavisericvanthorn.traktorcollectionconverter.enums;

public enum ErrorCase {

	FILE_UPDATE_NOT_POSSIBLE("FUNP");

	private String code;

	private ErrorCase(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

}
