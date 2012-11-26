import java.net.*;
import java.util.*;
import java.io.*;

public class Player
{
    private String playerName;
    private Socket socket;
    private Hand hand;
    
    /**
     * Creates a new player given its name and its socket.
     * @param String playerName Name of the player.
     * @param Socket socket Socket for the connection.
     * @param Hand hand Hand of the player.
     */
    public Player( String playerName, Socket socket )
    {
        this.playerName = playerName;
        this.socket = socket;
        this.hand = new Hand();
    }
    
    /**
     * Returns player's name.
     * @return String Player's name.
     */
    public String name() { return this.playerName; }
    
    /**
     * Returns connection's socket.
     * @param Socket Conncetion's socket.
     */
    public Socket socket() { return this.socket; }
    
    /**
     * Adds given card to player's hand.
     * @param Card card Card to be added.
     */
    public void addCard( Card card )
    { 
        this.output().println( "The following card has been added to your hand:\n" + card );
        this.hand.addCard( card );
    }
    
    /**
     * Asks player for a card and returns it.
     * @return Card Card chosen by player.
     */
    public Card getCard()
    {
        return this.hand.askForCard();
    }
    
    /**
     * Returns output stream for this connection.
     * @return OutputStream Output stream for this connection.
     */
    public OutputStream outputStream() throws Exception { return this.socket.getOutputStream(); }
    
    /**
     * Returns input stream for this connection.
     * @return InputStream Input stream for this connection.
     */
    public InputStream inputStream() throws Exception { return this.socket.getInputStream(); }
    
    /**
     * PrintWriter to interact with this player.
     * @return PrintWriter Output to interact with this player.
     */
    public PrintWriter output()
    { 
        try
        {
            return new PrintWriter( this.outputStream(), true );
        }
        catch ( Exception e )
        {
            System.err.println( "Error getting output with player" );
            return null;
        }
    }
    
    /**
     * Scanner to interact with this player.
     * @return Scanner Input to interact with this player.
     */
    public Scanner input()
    { 
        try
        {
            return new Scanner( this.inputStream() ); 
        }
        catch ( Exception e )
        {
            System.err.println( "Error getting input with player" );
            return null;
        }     
    }
}
