package chinook.exception;

public class NoInvoiceLinesException extends Exception {
	private static final long serialVersionUID = 1L;

	public NoInvoiceLinesException(String message) {
		super(message);
	}

}
