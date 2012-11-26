import CardExceptions.*;
import java.util.*;

public class Hand
{

    private ArrayList<Card> cards;

    /**
     * Creates an empty hand.
     */
    public Hand()
    {
        this.cards = new ArrayList<Card>();
    }
    
    /**
     * Adds given card to this hand.
     * @param Card card Card to add.
     */
    public void addCard( Card card )
    {
        this.cards.add( card );
    }
    
    /**
     * Plays given card and removes it from hand.
     * @param Card card Card to be played.
     */
    private void playCard( Card card ) throws NotOwnedCardException
    {
        if ( this.cards.contains( card ) )
        {
            System.out.println( "Player chooses:\n" + card );
            this.cards.remove( card );
        }
        else
        {
            throw new NotOwnedCardException( "This card is not owned by this player: " + card );
        }
    }
    
    /**
     * Asks the user for a card in their hand to play. 
     * This method handles all possiblities of invalid 
     * input and invalid card, as well as choosing cards 
     * that are not in player's hand.
     * @todo Vendría bien reescribir los bucles while por bucles do...while para darle más coherencia al código y que se entendiese mejor
     */
    public Card askForCard()
    {
        
        boolean inputWasCorrect = false; 
        boolean cardWasCorrect  = false;
        boolean cardWasInHand   = false;
        boolean userIsSure      = false;
        
        Card card = null;
        
        Scanner kbd = new Scanner( System.in );
        
        // Start confirmation check
        while ( !userIsSure )
        {
            System.out.println( this.toString() + "\nWhat card do you want to play? (use {value of card} {family of card} syntax)" );
            
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
                            value = kbd.nextInt();
                            family = kbd.next().trim();
                            kbd.nextLine();
                            
                            inputWasCorrect = true;
                        }
                        catch( Exception e )
                        {
                            System.out.println( "Invalid syntax, try again. Rembember that the syntax is {value} {family}, for instance: 7 espadas" );
                            kbd.nextLine();
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
                        System.out.println( "That card does not exist. Rembember that the syntax is {value} {family}, for instance: 7 espadas" );
                    }
                    
                }
                // End card check
                
                cardWasCorrect = false;
            
                if ( this.cards.contains( card ) )
                {
                    cardWasInHand = true;
                }
                else
                {
                    System.out.println( "That card is not in your hand!" );
                }
               
            }
            // End hand check
            
            cardWasInHand = false;
            
            System.out.print( "=====\nAre you sure that you want to play this card?\n\n" + card + "\n\n=====\n" );
            
            String selection = "";
            
            do
            {
                System.out.print( "Use 'yes' or 'no': " );
                selection = kbd.next().trim().toLowerCase();
                kbd.nextLine();
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
    
    @Override public String toString()
    {
        String res = "Hand with " + this.cards.size() + " cards.\n\n";
        
        for ( Card aCard : this.cards )
        {
            res += aCard.toString() + "\n";
        }
        
        return res;
    } 
}
