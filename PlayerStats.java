import java.util.ArrayList;
import java.io.Serializable;

/**
 * Write a description of class PlayerStats here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PlayerStats implements Serializable
{
    private String name;

    /**
     * Constructor for objects of class PlayerStats
     */
    public PlayerStats( Player p )
    {
        this.name = p.name();
    }
    
    
    public String name(){ return name; }

    // Bitch please
    
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
