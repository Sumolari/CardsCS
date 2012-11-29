import java.net.*;
import java.util.*;
import java.io.*;

public class Player
{
    private String playerName;
    private Socket socket;
    private Hand hand;
    private ArrayList<Card> wonCards;
    
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
        this.wonCards = new ArrayList<Card>();
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
     * Returns whether this player has given card or not.
     * @param Card card to be checked.
     * @return boolean True if this player has given card, false if not.
     */
    public boolean has( Card card ) { return this.hand.has( card ); }
    
    /**
     * Adds given card to player's hand.
     * @param Card card Card to be added.
     */
    public void addCardToHand( Card card )
    { 
        this.output().println( "The following card has been added to your hand:\n" + card );
        this.hand.addCard( card );
    }
    
    /**
     * Removes given card from hand.
     * @param Card card Card to be removed.
     */
    public void removeCardFromHand( Card card )
    {
        this.output().println( "The following card has been removed from your hand:\n" + card );
        this.hand.removeCard( card );
    }
    
    /**
     * Asks player for a card and returns it.
     * @return Card Card chosen by player.
     */
    public Card getCardFromHand()
    {
        return this.askForCard( null );
    }
    
    /**
     * Asks player for a card of given families. If player does not have any card matching this conditions, he can play any card.
     * @param ArrayList<CardFamily> families Families required.
     * @return Card Card of given family or triumph.
     */
    public Card getCardFromHandForced( ArrayList<CardFamily> families )
    {
        return this.askForCard( families );
    }
    
    /**
     * Adds given list of cards to won stack.
     * @param ArrayList<Card> cards Cards to be added to stack of won cards.
     */
    public void addCardWonStack( ArrayList<Card> cards )
    {
        this.wonCards.addAll( cards );
    }
    
    /**
     * Adds given card to the list of cards won by this player.
     * @param Card card Card to be added.
     */
    public void addCardWonStack( Card card )
    {
        this.wonCards.add( card );
    }
    
    /**
     * Returns the list of cards won by this player.
     * @return ArrayList<Card> Cards won by this player.
     */
    public ArrayList<Card> wonCards()
    {
        return this.wonCards;
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
     */
    private Card askForCard()
    {
        return this.askForCard( null );
    }
    
    /**
     * Asks the user for a card in their hand to play. 
     * This method handles all possiblities of invalid 
     * input and invalid card, as well as choosing cards 
     * that are not in player's hand.
     * @param ArrayList<CardFamily> requiredFamilies Required families. Use null if there is no required family.
     * @return Card Card selected by player.
     */
    private Card askForCard( ArrayList<CardFamily> requiredFamilies )
    {

        boolean familyIsCorrect = ( !this.hand.hasOfFamily( requiredFamilies ) ) ? true : false;
        
        
        boolean userIsSure      = false;
        
        Card card = null;
        
        // Start confirmation check
        do
        {
            boolean inputWasCorrect = false; 
            
            this.output().println( "\nYour hand:\n" );
            this.output().println( this.hand );
            this.output().println( this.name() + ", what card do you want to play?" );
            
            // Start hand check
            do
            {
                boolean cardWasCorrect = false;
                
                // Start card check
                do
                {
                    int value = -1;
                    String family = "";
                    
                    // Start syntax check
                    do
                    {
                        try
                        {
                            if ( this.hand.hasOfFamily( requiredFamilies ) )
                            {
                                String alert = "\nRebember: You have to play a card of ";
                                
                                for ( int _i = 0; _i < requiredFamilies.size(); _i++ )
                                {
                                    alert += requiredFamilies.get( _i );
                                    if ( _i + 2 < requiredFamilies.size() ) alert += ", ";
                                    else if ( _i + 1 < requiredFamilies.size() ) alert += " or ";
                                }
                                
                                this.output().println( alert + "." );
                            }
                            
                            //this.output().println( "\n\tValue (S=10, C=11, R=12): " );
                            this.output().println( "\n\tValue (1-7, S, C, R): " );
                            char val = this.input().nextLine().trim().charAt(0);
                            if ( val >= 49 && val <= 55 ) value = (int) ( val - 48 );
                            else
                            {
                                switch ( val )
                                {
                                    case 'S':                       //Trama: si sale la mayuscula, ejecuta hasta la minuscula y ahorro repetir lineas xD
                                    case 's': value = 10; break;
                                    
                                    case 'C': 
                                    case 'c': value = 11; break;

                                    case 'R': 
                                    case 'r': value = 12; break;
                                    
                                    default: throw new Exception(); //si no es ni un numero ni una de estas letras, lanzo Exception
                                    
                                }
                            }
                            //value = this.input().nextInt();                            
                            
                            this.output().println( "\n\tFamily (Oros, Copas, Bastos, Espadas): " );
                            char fam = this.input().nextLine().trim().charAt( 0 );
                            
                            switch ( fam )
                            {
                                case 'O':                       //Trama: si sale la mayuscula, ejecuta hasta la minuscula y ahorro repetir lineas xD
                                case 'o': family = "oros"; break;
                                
                                case 'C': 
                                case 'c': family = "copas"; break;
                                
                                case 'B': 
                                case 'b': family = "bastos"; break;
                                
                                case 'E': 
                                case 'e': family = "espadas"; break;
                                    
                                default: throw new Exception(); //si no es una de estas letras, lanzo Exception
                                    
                            }
                            //family = this.input().nextLine().trim(); 
                            
                            inputWasCorrect = true;
                        }
                        catch ( Exception e )
                        {
                            this.output().println( "Invalid syntax, try again. Rembember that the syntax is {value} {family}, for instance: 7 espadas." );
                            this.input().nextLine();
                            inputWasCorrect = false;
                        }
                        
                        if ( this.hand.hasOfFamily( requiredFamilies ) )
                        {
                            try
                            {
                                inputWasCorrect = requiredFamilies.contains( new CardFamily( family ) );
                            }
                            catch ( Exception icfe )
                            {
                                inputWasCorrect = false;
                            }
                        }
                        
                    } while ( !inputWasCorrect );
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
                        
                } while ( !cardWasCorrect );
                // End card check
            
                if ( ! this.hand.has( card ) )
                {
                    this.output().println( "That card is not in your hand!" );
                }
               
            } while ( !this.hand.has( card ) );
            // End hand check
            
            this.output().println( "=====\nAre you sure that you want to play this card?\n\n" + card + "\n\n=====\n" );
            
            String selection = "";
            
            do
            {
                this.output().println( "Use 'Yes' or 'No': " );
                selection = this.input().nextLine().trim().toUpperCase().substring(0,1);
            } while ( "YN".indexOf( selection ) == -1 );
            
            userIsSure = selection.equals( "Y" );
            
        } while ( !userIsSure );
        // End confirmation check
        
        this.hand.removeCard( card );
        
        return card;
    }
    
    /**
     * Asks the user for a boolean.
     * @param String message Message to be shown to player.
     * @return boolean Boolean indicating if player accepts or not.
     */
    public boolean askForBoolean( String message )
    {
        String selection = "";
        
        do
        {
            this.output().println( message );
            this.output().println( "Use 'Yes' or 'No': " );
            selection = this.input().nextLine().trim().toUpperCase().substring( 0, 1 );
        } while ( "YN".indexOf( selection ) == -1 );
        
        return selection.equals( "Y" );
    }
}
