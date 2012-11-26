import java.net.*;
import java.util.*;
import java.io.*;

public class Player
{
    private String playerName;
    private Socket socket;
    
    public Player( String playerName, Socket socket )
    {
        this.playerName = playerName;
        this.socket = socket;
    }
    
    public String name() { return this.playerName; }
    public Socket socket() { return this.socket; }
    
    public OutputStream outputStream() throws Exception { return this.socket.getOutputStream(); }
    public InputStream inputStream() throws Exception { return this.socket.getInputStream(); }
    
    public PrintWriter output() throws Exception { return new PrintWriter( this.outputStream(), true ); }
    public Scanner input() throws Exception { return new Scanner( this.inputStream() ); }
}
