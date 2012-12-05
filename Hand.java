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
     * Removes given card from hand if it is in this hand.
     * @param Card card Card to be removed.
     */
    public void removeCard( Card card )
    {
        if ( this.has( card ) ) this.cards.remove( card );
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
     * Returns whether this hand has at least one card of given family.
     * @param ArrayList<CardFamily> families Families to be checked.
     * @return Boolean True if this hand has a card of given family, false if not.
     */
    public boolean hasOfFamily( ArrayList<CardFamily> families )
    {
        if ( families == null ) return false;
        
        for ( CardFamily aFam : families )
        {
            if ( this.familiesInHand().contains( aFam ) )
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Returns a list of CardFamilies in this hand.
     * @return ArrayList<CardFamily> List of families in this hand.
     */
    private ArrayList<CardFamily> familiesInHand()
    {
        ArrayList<CardFamily> res = new ArrayList<CardFamily>();
        
        for ( Card aCard : this.cards )
        {
            if ( !res.contains( aCard.family() ) )
            {
                res.add( aCard.family() );
            }
        }
        
        return res;
    }
    
    /**
     * Returns the amount of cards in this hand.
     * @return int Amount of cards in this hand.
     */
    public int size()
    {
        return this.cards.size();
    }
    
    @Override public String toString()
    {
        String res = "Hand with " + this.cards.size() + " cards.\n\n";
        
        for ( Card aCard : this.cards )
        {
            res += "\t ___ ";
        }
        
        res += "\n";
        
        for ( Card aCard : this.cards )
        {
            res += "\t|" + aCard.family() + "| ";
        }
        
        res += "\n";
        
        for ( Card aCard : this.cards )
        {
            res += "\t| " + aCard.symbol() + " | ";
        }
        
        res += "\n";
        
        for ( Card aCard : this.cards )
        {
            res += "\t|___| ";
        }
        
        res += "\n\n";
        
        return res;
    } 
}
