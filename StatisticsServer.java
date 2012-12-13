import java.util.*;
import java.io.*;
import java.net.*;

public class StatisticsServer extends Thread
{
    private Socket socket;
    
    private static final int PORT               = 7778;
    private static final File f                 = new File( "StatisticsFile" );
    
    // This is the change I want to make.
    
    /**
     * Constructor of class StatisticsServer.
     * @param Socket socket Socket to connect with client.
     */
    public StatisticsServer( Socket socket )
    {
        this.socket = socket;
        println( "\tUser connected:\t\t" + this.socket.getInetAddress() );
       
        try
        {
            PrintWriter output = new PrintWriter( socket.getOutputStream(), true );
           
            for ( Stat aS : getStatsFromDisk() )
            {
                   output.println( aS );
            }
            
            output.close();
            socket.close();
        }
        catch ( Exception e )
        {
        }
    }
    
    /**
     * Retrieve a list of matches and stats from disk.
     * @return ArrayList<Stat> Stats retrieved.
     */
    private static ArrayList<Stat> getStatsFromDisk()
    {
        ArrayList<Stat> stats = null;
        
        try 
        {
            ObjectInputStream in = new ObjectInputStream ( new FileInputStream ( "StatisticsFile" ) );

            stats = ( ArrayList<Stat> ) in.readObject();
            
            in.close();
        }
        catch ( FileNotFoundException fnf ) { System.err.println( "File does not exist." ); }
        catch ( ClassNotFoundException cnf ) { System.err.println( "Cannot find class." ); }
        catch ( IOException io ) { System.err.println( "Problem when reading Stats from file." ); }
        catch ( Exception e ) { System.err.println( "Other problem when reading Stats from file." ); }
        
        return stats;
    }
    
    /**
     * Adds given stat to disk.
     * @param Stat stat Stat to add.
     */
    public static void addStatToDisk( Stat stat )
    {
        try
        {
            ArrayList<Stat> a = ( f.exists() ) ? getStatsFromDisk() : new ArrayList<Stat>();

            a.add( stat );
            
            ObjectOutputStream out = new ObjectOutputStream ( new FileOutputStream ( f, false ) );
            
            out.writeObject( a );
            
            out.close();
        }
        catch ( IOException io ) { System.err.println( "Problem when writing Stats into file." ); }
        catch ( Exception e ) { System.err.println( "Other problem when writing Stats into file" ); }
    }
    
    /**
     * Deletes the Statistics file if there is one.
     */
    public static void deleteStatsFile()
    {
        if ( f.exists() ) f.delete();
    }
    
    /**
     * Starts the statistics server.
     */
    public static void main()
    {       
        println( "Starting server..." );
       
        try
        {
            ServerSocket ss = new ServerSocket( PORT );
           
            println( "\tListening on port " + PORT + ".\n" );
       
            println( "Activity log:" );
       
            while ( true )
            {
                Thread t = new StatisticsServer( ss.accept() );
                t.start();
            }
        } 
        catch ( IOException ioe )
        {
            System.out.println( "Something went wrong, bro :(" );
        } 
        
    }
    
    /**
     * Alias for System.out.println( String message ).
     * @param String message String to print in standard output.
     */
    public static void println( String message ) { System.out.println( message ); }
}
