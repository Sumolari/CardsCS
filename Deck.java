import CardExceptions.*;
import java.util.*;

public class Deck
{
    private ArrayList<Card> cards;
    private CardFamily boss;
    
    /**
     * Creates a new Deck with 40 cards.
     */
    public Deck()
    {
   
        this.cards = new ArrayList<Card>();
        
        String[] familyNames = {    CardFamily.kEspadasFamily, 
                                    CardFamily.kBastosFamily, 
                                    CardFamily.kCopasFamily, 
                                    CardFamily.kOrosFamily };
                                    
        for ( String aFamily : familyNames )
        {
            for ( int i = 1; i < 13; i++ )
            {
                if ( i > 7 && i < 10 ) i = 10;
                
                try
                {
                    this.cards.add( new Card( i, aFamily ) );
                }
                catch( Exception e )
                {
                    System.err.println( e.getMessage() );
                    System.exit( -1 );
                }
            }
        }
        
    }
    
    /**
     * Removes top-most card in deck and returns it.
     * @return Card Top-most card in this deck.
     */
    public Card popCard()
    {
        Card card = cards.get( cards.size() - 1 );
        cards.remove( cards.size() -1 );
        
        return card;
    }
    
    @Override public String toString()
    {
        String res = "Deck with " + this.cards.size() + " cards.\n\n";
        
        for ( Card aCard : this.cards )
        {
            res += aCard.toString() + "\n";
        }
        
        return res;
    }
    
    /**
     * Demo method just to print in terminal the Deck.
     */
    public void printDesc()
    {
        System.out.println( this.toString() );
    }
    /**
     * Sorts randomly the elements of an ArrayList<Card>
     */
    public static ArrayList<Card> unsort( ArrayList<Card> a )
    {
        int size = a.size() - 1, e1, e2;
        
        for (int i = 0; i < 10000; i++)
        {
            do{
                e1 = (int) ( Math.random() * size + 1 );
                e2 = (int) ( Math.random() * size + 1 );
            }while(e1 == e2);
        
            Card first = a.get( e1 ), second = a.get( e2 );
            a.remove( e2 );
            a.add( e2, first );
            a.remove( e1 );
            a.add( e1, second );
        }
        
        return a;
    }
}
