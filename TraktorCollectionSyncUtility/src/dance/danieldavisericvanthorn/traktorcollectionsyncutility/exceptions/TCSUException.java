package dance.danieldavisericvanthorn.traktorcollectionsyncutility.exceptions;

import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.enums.ErrorCase;

public class TCSUException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 3285949942835409170L;

	public TCSUException(String message) {
		super(message);
	}

	public TCSUException(ErrorCase errorCase) {
		super(errorCase.getCode());
	}
}
