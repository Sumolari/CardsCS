/**
 * This class represents the exception that raises when the user makes a mistake.
 */
public class StupidUserException extends Exception
{

	public StupidUserException()
	{
		super( "Error between keyboard and chair." );
	}

	public StupidUserException( String message )
	{
		super( message );
	}

}