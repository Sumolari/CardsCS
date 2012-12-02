import java.util.*;

/**
 * Base class for creating matches.
 */
public abstract class Match
{
    protected ArrayList<Player> players;
    
    /**
     * Returns list of players in this match.
     * @return ArrayList<Player> Players in this match.
     */
    public ArrayList<Player> players() { return this.players; }
    
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
    
    /**
     * Sends a long separator to all players.
     */
    public void sendLongLineToPlayers()
    {
        this.sendToAllPlayers( "\n==============================\n\n");
    }
    
    /**
     * Start the match.
     */
    public void start() throws Exception
    {
        this.finishGame();
    }
    
    /**
     * Method called when game should finish.
     */
    public void finishGame()
    {
        this.sendToAllPlayers( "Game is finished." );
        
        System.exit(0);
    }
}
