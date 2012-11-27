import java.util.*;
import java.io.*;
import java.net.*;

public class ServerApp
{
   public static int MAXIMUM_CLIENTS    = 2;
   public static int PORT               = 7777;
  
   private static ArrayList<Player> players     = new ArrayList<Player>();
   private static InputStream adminInput;
   private static OutputStream adminOutput;
   private static boolean gameFinished = false;
   
   public static void run()
   {
       System.out.println( "\n\n\tRUN!" );
       
       Brisca match = new Brisca( players );
       
       try
       {
           match.start();
       }
       catch ( Exception e )
       {
           System.out.println( "Exception in run()" );
       }
       
       System.out.println( "\n\n\tGame finished!" );
   }
    
   public static void main( String[] args )
   {   
       System.out.println( "Starting server..." );
       
       try
       {
           ServerSocket ss = new ServerSocket( PORT );
           
           System.out.println( "\tListening on port " + PORT + ".\n" );
       
           System.out.println( "Activity log:" );
       
           while ( /* USER DID NOT WRITE A FINISH COMMAND AND */ players.size() < MAXIMUM_CLIENTS )
           {
               Socket socket = ss.accept();
               
               // Open streams to interact with user
               Scanner input = new Scanner( socket.getInputStream() ); 
               PrintWriter output = new PrintWriter( socket.getOutputStream(), true );
               
               //Put reading (inputStream) of number of players here, set MAXIMUM_PLAYERS variable (change it to not final) to value read.
               //Checking of valid number of players (loop)
               
               // Check if user is admin
               if ( players.size() < 1 )
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
                   players.add( newPlayer );
                   
                   admin.output().println( "\n" + newPlayer.name() + ", has joined." );
                   newPlayer.output().println( "\n" + newPlayer.name() + ", you have joined " + admin.name() + " match." );
               }
               
           }
           
           run();
           
           //loop that rotates through players until end of game
           for (int i = 0; i < players.size(); i++){
               Player currentPlayer = players.get( i );
               Scanner input = new Scanner( currentPlayer.socket().getInputStream() ); 
               PrintWriter output = new PrintWriter( currentPlayer.socket().getOutputStream(), true );
               
               currentPlayer.output().println( "It is your turn, select a card from your hand : " );
               String selection = input.nextLine().trim();
               
               if ( selection.equals( "QUIT" ) ){ gameFinished = true; }
               
               if ( gameFinished ){ break; }
               
               if ( i == players.size() - 1 ) i = -1;
           }
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
