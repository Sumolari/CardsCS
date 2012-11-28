import java.net.*;
import java.util.*;
import java.io.*;

public class Player
{
    private String playerName;
    private Socket socket;
    private Hand hand;
    
    /**
     * Creates a new player given its name and its socket.
     * @param String playerName Name of the player.
     * @param Socket socket Socket for the connection.
     * @param Hand hand Hand of the player.
     */
    public Player( String playerName, Socket socket )
    {
        this.playerName = playerName;
        this.socket = socket;
        this.hand = new Hand();
    }
    
    /**
     * Returns player's name.
     * @return String Player's name.
     */
    public String name() { return this.playerName; }
    
    /**
     * Returns connection's socket.
     * @param Socket Conncetion's socket.
     */
    public Socket socket() { return this.socket; }
    
    /**
     * Adds given card to player's hand.
     * @param Card card Card to be added.
     */
    public void addCard( Card card )
    { 
        this.output().println( "The following card has been added to your hand:\n" + card );
        this.hand.addCard( card );
    }
    
    /**
     * Asks player for a card and returns it.
     * @return Card Card chosen by player.
     */
    public Card getCard()
    {
        return this.askForCard();
    }
    
    /**
     * Returns output stream for this connection.
     * @return OutputStream Output stream for this connection.
     */
    public OutputStream outputStream() throws Exception { return this.socket.getOutputStream(); }
    
    /**
     * Returns input stream for this connection.
     * @return InputStream Input stream for this connection.
     */
    public InputStream inputStream() throws Exception { return this.socket.getInputStream(); }
    
    /**
     * PrintWriter to interact with this player.
     * @return PrintWriter Output to interact with this player.
     */
    public PrintWriter output()
    { 
        try
        {
            return new PrintWriter( this.outputStream(), true );
        }
        catch ( Exception e )
        {
            System.err.println( "Error getting output with player" );
            return null;
        }
    }

    /**
     * Scanner to interact with this player.
     * @return Scanner Input to interact with this player.
     */
    public Scanner input()
    { 
        try
        {
            return new Scanner( this.inputStream() ); 
        }
        catch ( Exception e )
        {
            System.err.println( "Error getting input with player" );
            return null;
        }     
    }
    
    /**
     * Returns the amount of cards in this player's hand.
     * @return int Amount of cards in this player's hand.
     */
    public int handSize()
    {
        return this.hand.size();
    }
    
    /**
     * Asks the user for a card in their hand to play. 
     * This method handles all possiblities of invalid 
     * input and invalid card, as well as choosing cards 
     * that are not in player's hand.
     * @todo Vendría bien reescribir los bucles while por bucles do...while para darle más coherencia al código y que se entendiese mejor
     */
    private Card askForCard()
    {
        
        boolean inputWasCorrect = false; 
        boolean cardWasCorrect  = false;
        boolean cardWasInHand   = false;
        boolean userIsSure      = false;
        
        Card card = null;
        
        // Start confirmation check
        while ( !userIsSure )
        {
            this.output().println( "\nYour hand:\n" );
            
            this.output().println( this.hand );
            
            this.output().println( this.name() + ", what card do you want to play?" );
            
            // Start hand check
            while ( !cardWasInHand )
            {
                // Start card check
                while ( !cardWasCorrect )
                {
                    int value = -1;
                    String family = "";
                    
                    // Start syntax check
                    while ( !inputWasCorrect )
                    {
                        try
                        {
                            this.output().println( "\n\tValue (S=10, C=11, R=12): " );
                            value = this.input().nextInt();
                            
                            this.output().println( "\n\tFamily (oros, copas, bastos, espadas): " );
                            family = this.input().nextLine().trim(); 
                            
                            inputWasCorrect = true;
                        }
                        catch( Exception e )
                        {
                            this.output().println( "Invalid syntax, try again. Rembember that the syntax is {value} {family}, for instance: 7 espadas." );
                            this.input().nextLine();
                            inputWasCorrect = false;
                        }
                    }
                    // End syntax check
                    
                    inputWasCorrect = false;
                    
                    try
                    {
                        card = new Card( value, family );
                        
                        cardWasCorrect = true;
                    }
                    catch( Exception e )
                    {
                        this.output().println( "That card ( " + value + " of " + family + " ) does not exist. Rembember that the syntax is {value} {family}, for instance: 7 espadas" );
                    }
                        
                }
                // End card check
                
                cardWasCorrect = false;
            
                if ( this.hand.has( card ) )
                {
                    cardWasInHand = true;
                }
                else
                {
                    this.output().println( "That card is not in your hand!" );
                }
               
            }
            // End hand check
            
            cardWasInHand = false;
            
            this.output().println( "=====\nAre you sure that you want to play this card?\n\n" + card + "\n\n=====\n" );
            
            String selection = "";
            
            do
            {
                this.output().println( "Use 'yes' or 'no': " );
                selection = this.input().nextLine().trim().toLowerCase();
            } while ( !selection.equals( "yes" ) && !selection.equals( "no" ) );
            
            userIsSure = selection.equals( "yes" );
            
            if ( !userIsSure )
            {
                inputWasCorrect = false;
                cardWasCorrect  = false;
                cardWasInHand   = false;
            }
        }
        // End confirmation check
        
        return card;
    }
}
