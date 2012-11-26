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
    
    public OutputStream output() throws Exception { return this.socket.getOutputStream(); }
    public InputStream input() throws Exception { return this.socket.getInputStream(); }
}
