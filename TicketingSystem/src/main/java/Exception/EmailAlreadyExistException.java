package Exception;

public class EmailAlreadyExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EmailAlreadyExistException(){
		super("The email is already exist, please use a different email");
	}
	

}
