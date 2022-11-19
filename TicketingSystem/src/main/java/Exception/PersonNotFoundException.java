package Exception;

public class PersonNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
public PersonNotFoundException()
{
	 super("The user is not exist");
}

}
