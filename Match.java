import java.util.*;

/**
 * Base class for creating matches.
 */
public abstract class Match
{
    protected ArrayList<Player> players;
    
    /**
     * Sends given message to all players.
     * @param String message Message to send.
     */
    public void sendToAllPlayers( String message )
    {
        this.sendToSomePlayers( message, this.players );
    }
    
    /**
     * Send given message to given array of players.
     * @param String message Message to send.
     * @param ArrayList<Player> players List of players that will receive message.
     */
    public void sendToSomePlayers( String message, ArrayList<Player> players )
    {
        for ( Player aPlayer : players )
        {
            aPlayer.output().println( message );
        }
    }
    
    public void sendLongLineToPlayers()
    {
        this.sendToAllPlayers( "\n==============================\n\n");
    }
}
