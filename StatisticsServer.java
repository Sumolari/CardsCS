import java.util.*;
import java.io.*;
import java.net.*;

public class StatisticsServer extends Thread
{
    private Socket socket;
    
    public static int MAXIMUM_CLIENTS    = 2;
    public static int PORT               = 7778;
    
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
        // TODO!
        return new ArrayList<Stat>();
    }
    
    /**
     * Adds given stat to disk.
     * @param Stat stat Stat to add.
     */
    public static void addStatToDisk( Stat stat )
    {
        // TODO!
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
            System.out.println( "Something went wrong, bro :( " );
        } 
        
    }
    
    /**
     * Alias for System.out.println( String message ).
     * @param String message String to print in standard output.
     */
    public static void println ( String message ) { System.out.println( message ); }
}
