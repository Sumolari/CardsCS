package CardExceptions;

/**
 * This class represents the exception that raises when a card has a value lower than one or greater than 12.
 */
public class InvalidCardValueException extends Exception
{

	public InvalidCardValueException()
	{
		super( "Invalid value for card. Minimum value is 1 and maximum is 12." );
	}

	public InvalidCardValueException( String message )
	{
		super( message );
	}

}