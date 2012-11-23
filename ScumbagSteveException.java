/**
 * This class represents the exception that raises when the user makes an intentional mistake.
 */
public class ScumbagSteveException extends Exception
{

	public ScumbagSteveException()
	{
		super( "S. Steve was here." );
	}

	public ScumbagSteveException( String message )
	{
		super( message );
	}

}