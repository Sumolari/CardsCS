import java.util.*;

/**
 * Handles basic rules of Brisca game.
 */
public class Brisca
{
    private ArrayList<Player> players;
    
    /**
     * Basic constructor, given a list of players handles all the match.
     * @param ArrayList<Player> players List of players.
     */
    public Brisca( ArrayList<Player> players )
    {
        this.players = players;
    }
    
    /**
     * Method that handles match.
     */
    public void start() throws Exception
    {
        System.out.println( "\tCreate and randomize deck." );
        
        Deck deck = new Deck();
        
        System.out.println( "\tGive 3 cards to each player.\n\n" );
        
        for ( Player aPlayer : players )
        {
            for ( int i = 0; i < 3; i++ )
            {
                Card card = deck.popCard();
                System.out.println( "\tGiving \n" + card + "\n to each " + aPlayer.name() + "." );
                aPlayer.addCard( card );
        
            }
        }
        
        System.out.println( "Start match" );
    }
    
    /**
     * Returns the value of a card taking into account the triumph and card's value.
     * 
     * With this algorithm...
     * 1 -> 14
     * 2 -> 2
     * 3 -> 13
     * 4 -> 4
     * 5 -> 5
     * 6 -> 6
     * 7 -> 7
     * S -> 10
     * C -> 11
     * R -> 12
     *
     * Adds 14 if it is a triumph
     * 
     * @param Card card The card which value will be returned.
     * @param CardFamily triumph The triumph selected at the beginning of the match.
     * @return int Unique value of the card, to be used to compare it with other cards.
     */
    private static int valueForCard( Card card, CardFamily triumph )
    {
        if ( card == null ) return 0; // This condition may happen when computing value of a non-initialized card
        
        int cardValue = card.value();
        if ( card.value() == 1 ) cardValue += 13; // 1 wins all the other values
        if ( card.value() == 3 ) cardValue += 10; // 3 wins all the other values but 1
        if ( card.family().equals( triumph ) ) cardValue += 14; // Triumphs wins all other families.
        return cardValue;
    }

    /**
     * Returns the winner card following brisca's rules.
     * @param Card firstCard First card. It is assumed that it was the first to be in the table.
     * @param Card secondCard Second card. It is assumed that this card was played after the first one.
     * @param CardFamily triumph Triumph os this match.
     * @return Card Winner card.
     */
    public static Card winnerCard( Card firstCard, Card secondCard, CardFamily triumph )
    {
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add( firstCard );
        cards.add( secondCard );
        return Brisca.winnerCard( cards, triumph );
    }
    
    /**
     * Given an array list of cards, returns the winner of all of them taking into account the triumph.
     * @param ArrayList<Card> cards List of cards in the table.
     * @param CardFamily triumph Triumph randomly selected at the beginning of the match.
     * @return Card Winner card.
     */
    public static Card winnerCard( ArrayList<Card> cards, CardFamily triumph )
    {
        int maxValuePos = 0;
        Card winnerCard = null;
        
        int _t = 0;
        for ( Card aCard : cards )
        {   
            if ( Brisca.valueForCard( aCard, triumph ) > Brisca.valueForCard( winnerCard, triumph ) )
            {
                maxValuePos = _t;
                winnerCard = aCard;
            }
            
            _t++;
        }
        
        return winnerCard;
    }
    
    /**
     * Returns the score for a given list of cards. This method does NOT take into account the last-round-10-points.
     * 
     * Scores:
     * 1 -> 11
     * 3 -> 10
     * S -> 2
     * C -> 3
     * R -> 4
     * Any other: 0
     * 
     * @param ArrayList<Card> cards Cards which score will be computed.
     * @return int Total score for that list of cards.
     */
    public static int scoreForCards( ArrayList<Card> cards )
    {
        int totalScore = 0;
        for ( Card aCard : cards )
        {
            switch ( aCard.value() )
            {
                case 1: 
                    totalScore += 11;
                    break;
                case 3: 
                    totalScore += 10;
                    break;
                case 10: // S 
                    totalScore += 2;
                    break;
                case 11: // C 
                    totalScore += 3;
                    break;
                case 12: // R 
                    totalScore += 4;
                    break;
                default: break;
            }
        }
        
        return totalScore;
    }
}
