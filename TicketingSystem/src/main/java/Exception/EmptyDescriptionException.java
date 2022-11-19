package Exception;

public class EmptyDescriptionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmptyDescriptionException()
	{
		super("Every new Ticket must have a valid description");
	}
}
