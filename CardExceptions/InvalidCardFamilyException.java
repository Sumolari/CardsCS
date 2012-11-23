package CardExceptions;

/**
 * This class represents the exception that raises when a card has an invalid family.
 */
public class InvalidCardFamilyException extends Exception
{

	public InvalidCardFamilyException()
	{
		super( "Invalid value for card family. Please use the predefined ones." );
	}

	public InvalidCardFamilyException( String message )
	{
		super( message );
	}

}