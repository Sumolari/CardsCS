import java.util.*;
import java.io.*;
import java.net.*;

public class ServerApp
{
   public static int MAXIMUM_CLIENTS    = 2;
   public static int PORT               = 7777;
  
   private static ArrayList<Socket> connections = new ArrayList<Socket>();
   private static ArrayList<Player> players     = new ArrayList<Player>();
   private static InputStream adminInput;
   private static OutputStream adminOutput;
   
   public static void run()
   {
       System.out.println( "RUN!" );
       
       /*
       
       try
       {
           
       
          
           //HERE GOES GAME LOGIC!
                       
          
          //println( "\tConnection with " + this.socket.getInetAddress() + " closed." );
          //.socket.close();
          
        }
        catch ( IOException ioe )
        {
            System.out.println( "Something went wrong, bro :( " );
        }
        
        */
   }
    
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
               Socket socket = ss.accept();
               
               // Add connection with user
               connections.add( socket );
               
               // Open streams to interact with user
               Scanner input = new Scanner( socket.getInputStream() ); 
               PrintWriter output = new PrintWriter( socket.getOutputStream(), true );
                   
               // Check if user is admin
               if ( connections.size() == 1 )
               {
                   System.out.println( "\tAdmin connected:\t\t" + socket.getInetAddress() );
                   output.println( "\nWhat's your name?");
                   
                   Player admin = new Player( input.nextLine().trim(), socket );
                   
                   players.add( admin );
                   
                   admin.output().println( "\n" + admin.name() + ", your match has been created. Please, wait until there is at least one more player." );
               }
               // If not admin, notifies admin
               else
               {
                   Player admin = players.get( 0 );
                   
                   System.out.println( "\tUser connected:\t\t" + socket.getInetAddress() );
                   output.println( "\nWhat's your name?");
                   
                   Player newPlayer = new Player( input.nextLine().trim(), socket );
                   
                   admin.output().println( "\n" + newPlayer.name() + ", has joined." );
                   newPlayer.output().println( "\n" + newPlayer.name() + ", you have joined " + admin.name() + " match." );
               }
               
           }
           
           run();
       }
       catch ( IOException ioe )
       {
           System.out.println( "Something went wrong, bro :( " );
       }
       catch ( Exception e )
       {
           System.out.println( "Something went REALLY wrong" );
       }
   }
}
