import java.net.*;

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
}
