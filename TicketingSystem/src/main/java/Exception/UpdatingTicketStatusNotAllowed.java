package Exception;

public class UpdatingTicketStatusNotAllowed extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public UpdatingTicketStatusNotAllowed()
	{
		
		super("You can only update the status of pending tickets");
	}

}
