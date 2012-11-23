package CardExceptions;

/**
 * This class represents the exception that raises when a card has a value lower than one or greater than 12.
 */
public class NotOwnedCardException extends Exception
{

	public NotOwnedCardException()
	{
		super( "This card is not owned by this player." );
	}

	public NotOwnedCardException( String message )
	{
		super( message );
	}

}