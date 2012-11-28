import java.util.*;
import java.io.*;
import java.net.*;

public class ServerApp
{
   public static int MAXIMUM_CLIENTS    = 2;
   public static int PORT               = 7777;
  
   private static ArrayList<Player> players     = new ArrayList<Player>();
   private static boolean gameFinished          = false;
   private static InputStream adminInput;
   private static OutputStream adminOutput;
   private static int totalPlayers;
   
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
           System.err.println( e.getMessage() );
           e.printStackTrace( System.err );
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
               
               // Check if user is admin
               if ( players.size() < 1 )
               {
                   System.out.println( "\tAdmin connected:\t\t" + socket.getInetAddress() );
                   output.println( "\nWhat's your name?");
                   
                   Player admin = new Player( input.nextLine().trim(), socket );
                   
                   players.add( admin );
                   
                   admin.output().println( "\n" + admin.name() + ", your match has been created.");
                   
                   boolean inputWasOk = false;
                   do
                   {
                       try
                       {
                           admin.output().println( "How many players do you want to play with?" );
                           totalPlayers = admin.input().nextInt();
                           
                           if ( totalPlayers > MAXIMUM_CLIENTS )
                           {
                               totalPlayers = MAXIMUM_CLIENTS;
                           }
                           
                           inputWasOk = true;
                           
                       }
                       catch ( InputMismatchException ime )
                       {
                           admin.output().println( "That was not a number!" );
                       }
                   } while ( !inputWasOk );
                   
                   admin.output().println( "Please, wait until there connects " + ( totalPlayers - 1 ) + " more player" + ( ( totalPlayers > 2 ) ? "s." : "." ) );
               }
               // If not admin, notifies admin
               else
               {
                   Player admin = players.get( 0 );
                   
                   System.out.println( "\tUser connected:\t\t" + socket.getInetAddress() );
                   
                   String aName = "Just a Random Name for a Random User.";
                   
                   boolean usedName = true;
                   
                   while ( usedName )
                   {
                       output.println( "\nWhat's your name?");
                       
                       aName = input.nextLine().trim();
                       
                       usedName = false;
                       for ( Player aPlayer : players )
                       {
                           if ( aPlayer.name().equals( aName ) )
                           {
                               usedName = true;
                               output.println( "\nName already in use!" );
                           }
                       }
                    }
                   
                   Player newPlayer = new Player( aName, socket );
                   players.add( newPlayer );
                   
                   admin.output().println( "\n" + newPlayer.name() + ", has joined." );
                   newPlayer.output().println( "\n" + newPlayer.name() + ", you have joined " + admin.name() + " match." );
               }
               
           }
           
           run();
           
           System.out.println( "This message SHOULD NOT BE PRINTED" );
           
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
