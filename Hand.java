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
     * Returns whether this hand contains given card or not.
     * @param Card card Card to be checked.
     * @return boolean True if card is in this hand, false if not.
     */
    public boolean has( Card card )
    {
        return this.cards.contains( card );
    }
    
    /**
     * Returns the amount of cards in this hand.
     * @return int Amount of cards in this hand.
     */
    public int size()
    {
        return this.cards.size();
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
