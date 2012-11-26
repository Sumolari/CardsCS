import java.util.*;
import java.io.*;
import java.net.*;

public class ServerApp
{
   public static int MAXIMUM_CLIENTS    = 4;
   public static int PORT               = 7777;
    
   private static ArrayList<Socket> connections = new ArrayList<Socket>();
   private static ArrayList<Player> players     = new ArrayList<Player>();
    
   /*
   public static void run()
   {
       try
       {
          Scanner input = new Scanner( this.socket.getInputStream() );
          PrintWriter output = new PrintWriter( this.socket.getOutputStream(), true );
       
          
           //HERE GOES GAME LOGIC!
                       
          println( "\tConnection with " + this.socket.getInetAddress() + " closed." );
          this.socket.close();
        }
        catch ( IOException ioe )
        {
            System.out.println( "Something went wrong, bro :( " );
        }
   }
   */
    
   public static void main( String[] args )
   {   
       System.out.println( "Starting server..." );
       
       try
       {
           ServerSocket ss = new ServerSocket( PORT );
           
           System.out.println( "\tListening on port " + PORT + ".\n" );
       
           System.out.println( "Activity log:" );
       
           while ( /* USER DID NOT WRITE A FINISH COMMAND AND */ connections.size() < MAXIMUM_CLIENTS )
           {
               connections.add( ss.accept() );
               
               Scanner input = new Scanner( connections.get( 0 ).getInputStream() ); 
               PrintWriter output = new PrintWriter( connections.get( 0 ).getOutputStream(), true );
                   
               if ( connections.size() == 1 )
               {
                   System.out.println( "\tAdmin connected:\t\t" + connections.get( 0 ).getInetAddress() );
                   output.println( "\nYour match has been created. Please, wait until there is at least one more player." );
               }
               else
               {
                   System.out.println( "\tUser connected:\t\t" + connections.get( connections.size() - 1 ) );
               }
               
               
               
           }
           
           run();
       } 
       catch ( IOException ioe )
       {
           System.out.println( "Something went wrong, bro :( " );
       }
   }
}
