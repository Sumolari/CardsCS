import java.util.ArrayList;
import java.io.Serializable;

public class PlayerStats implements Serializable
{
    private String name;

    /**
     * Constructor for objects of class PlayerStats.
     * @param Player p Player.
     */
    public PlayerStats( Player p )
    {
        this.name = p.name();
    }

    /**
     * Returns the name of this player.
     * @return String Name of this player.
     */
    public String name() { return this.name; }

    /**
     * Given a list of Player, returns a list of PlayerStats.
     * @param ArrayList<Player> p List of players.
     * @return ArrayList<PlayerStat> List of names of players.
     */
    public static ArrayList<PlayerStats> playersToPlayerStats( ArrayList<Player> p )
    {
        ArrayList<PlayerStats> res = new ArrayList<PlayerStats>();
        
        for( Player pl : p )
        {
            res.add( new PlayerStats( pl ) );
        }
        
        return res;
    }
}
