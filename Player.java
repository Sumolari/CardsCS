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
    
    public String name() { return this.playerName; }
    public Socket socket() { return this.socket; }
    
    public void addCard( Card card ) throws Exception
    { 
        this.output().println( "The following card has been added to your hand:\n" + card );
        this.hand.addCard( card );
    }
    
    public OutputStream outputStream() throws Exception { return this.socket.getOutputStream(); }
    public InputStream inputStream() throws Exception { return this.socket.getInputStream(); }
    
    public PrintWriter output() throws Exception { return new PrintWriter( this.outputStream(), true ); }
    public Scanner input() throws Exception { return new Scanner( this.inputStream() ); }
}
