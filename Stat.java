import java.util.*;

public class Stat
{
    private ArrayList<PlayerStats>  players;
    private PlayerStats             winner;
    private ArrayList<Integer>      scores;
    
    /**
     * Creates a new Stat with given players and scores.
     * @param ArrayList<Player> players List of players.
     * @param ArrayList<Integer> scores List of scores.
     */
    public Stat( ArrayList<PlayerStats> players, ArrayList<Integer> scores )
    {
        this.players    = players;
        this.scores     = scores;
        
        int _maxScore = -1;
        for ( int _t = 0; _t < players.size(); _t++ )
        {
            if ( scores.get( _t ) > _maxScore )
            {
                this.winner = players.get( _t );
                _maxScore = scores.get( _t );
            }
        }
    }
    
    @Override public String toString()
    {
        String res = "= Match between: ";
                
        for ( int _t = 0; _t < this.players.size(); _t++ )
        {
            res += this.players.get( _t ).name();
            
            if ( _t + 2 < this.players.size() ) res += ", ";
            else if ( _t + 1 < this.players.size() ) res += " and ";
        }
        
        res += ".\n";
        
        for ( int _t = 0; _t < this.players.size(); _t++ )
        {
            res += "\t" + this.players.get( _t ).name() + ": " + this.scores.get( _t ) + " points";
            
            if ( this.players.get( _t ).equals( winner ) )
            {
                res += " (winner)";
            }
            
            res += ".\n";
        }
        
        return res;
    }
}
