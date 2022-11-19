package Exception;

public class InvalidAmountException extends RuntimeException{

	private static final long serialVersionUID = 1L;

  public InvalidAmountException()
  {
	  super("The amount you entered is invalid, please choose another amount");
  }

}
