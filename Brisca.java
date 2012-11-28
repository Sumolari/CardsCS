import java.util.*;

/**
 * Handles basic rules of Brisca game.
 */
public class Brisca extends Match
{
    private Card triumphCard;
    private boolean areLast3Rounds;
    private Deck deck;
    private ArrayList<Card> cardsInTable;
    
    /**
     * Basic constructor, given a list of players handles all the match.
     * @param ArrayList<Player> players List of players.
     */
    public Brisca( ArrayList<Player> players )
    {
        this.players = players;
        this.areLast3Rounds = false;
        this.cardsInTable = new ArrayList<Card>();
    }
    
    /**
     * Method that handles match.
     */
    public void start() throws Exception
    {
        System.out.println( "\tCreate and randomize deck." );
        
        deck = new Deck();
        
        triumphCard = deck.popCard();
        
        System.out.println( "Triumph is\n" + triumphCard );
        
        System.out.println( "\tGive 3 cards to each player.\n\n" );
        
        for ( Player aPlayer : players )
        {
            for ( int i = 0; i < 3; i++ )
            {
                Card card = deck.popCard();
                System.out.println( "\tGiving \n" + card + "\n to " + aPlayer.name() + "." );
                aPlayer.addCardToHand( card );
            }
            aPlayer.output().println( "Triumph is\n" + triumphCard );
        }
        
        this.newRound();
        
        System.out.println( "Start match" );
    }
    
    /**
     * Method that handles a complete round.
     */
    public void newRound()
    {        
        // First give new cards to each play until hand is full and if they are not in final rounds.
        if ( !this.areLast3Rounds )
        {
            this.sendLongLineToPlayers();
            
            for ( Player aPlayer : players )
            {
                while ( aPlayer.handSize() < 3 )
                {
                    Card card = deck.popCard();
                    System.out.println( "\tGiving \n" + card + "\n to " + aPlayer.name() + "." );
                    aPlayer.addCardToHand( card );
                }
            }
        }
        
        this.sendLongLineToPlayers();
        
        if ( this.areLast3Rounds )
        {
            boolean gameIsFinished = false;
            
            for ( Player aPlayer : players )
            {
                if ( aPlayer.handSize() == 0 )
                {
                    gameIsFinished = true;
                    break;
                }
            }
            
            if ( gameIsFinished )
            {
                finishGame();
            }
        }
        
        // With their hands full, ask first player in list to play a card.
        for ( Player aPlayer : players )
        {
            
            // The first player (winner of the previous round) should be able to change the 7 of triumph by the triumph in table, of the 2 of triumph if triumph is 7.
            
            ArrayList<Player> otherPlayers = new ArrayList<Player>( players );
            otherPlayers.remove( aPlayer );
            
            this.sendToSomePlayers( new String( aPlayer.name() + " must choose a card. Please, wait." ), otherPlayers );
            
            aPlayer.output().println( this.table() ); 
            Card cardPlayed = aPlayer.getCardFromHand();
            
            this.cardsInTable.add( cardPlayed );
            
            this.sendToSomePlayers( new String( aPlayer.name() + " has played:\n" + cardPlayed.toString() ), otherPlayers );
        }
        
        // When all the players have played a card, then compute the winner
        this.sendLongLineToPlayers();
        Player winner = this.winnerPlayer( this.cardsInTable, this.triumphCard.family() );
        winner.addCardWonStack( this.cardsInTable );
        this.sendToAllPlayers( "Winner is " + winner.name() );
        this.sendToAllPlayers( this.scores() );
        
        finishRound( winner );
    }
    
    /**
     * Handles the finish of a round given its winner.
     * @param Player winner Winner of the round.
     */
    public void finishRound( Player winner )
    {
        ArrayList<Player> newOrder = new ArrayList<Player>();
        int _t = 0;
        while ( newOrder.size() < this.players.size() )
        {
            if ( newOrder.size() == 0 )
            {
                if ( this.players.get( _t ).equals( winner ) )
                {
                    newOrder.add( winner );
                }
            }
            else
            {
                newOrder.add( this.players.get( _t ) );
            }
            
            _t = ( _t + 1 ) % this.players.size();
        }
        
        this.players = newOrder;
        
        /*
         * We enter in last 3 rounds when it is not possible to withdraw a card, that happens when:
         *
         * - The deck's size is lower than the amount of players and there is no triumph.
         * - The deck's size is lower than the amount of players even if we add the triumph to the deck.
         * 
         */ 
        if ( ( this.deck.size() - this.players.size() ) < ( this.players.size() - 1 ) )
        {
            this.areLast3Rounds = true;
        }
        
        this.cardsInTable = new ArrayList<Card>();
        
        newRound();
    }
    
    /**
     * Handles the end of the game.
     * 
     * You lose the game.
     */
    public void finishGame()
    {
        this.sendToAllPlayers( "Game is finished. These are the final scores:\n" + this.scores() );
        
    }
    
    /**
     * Returns a String representing the current state of the table.
     * @return String String representing the current state of the table.
     */
    private String table()
    {   
        String res = "Cards in table:\n";
        
        if ( this.cardsInTable.size() < 1 )
        {
            res += "\tTable is empty.";
        }
        else
        {
        
            for ( Card aCard : this.cardsInTable )
            {
                res += "\n" + aCard.toString() + "\n";
            }
            
            res += "\nWinner card:\n" + this.winnerCard( this.cardsInTable, this.triumphCard.family() );
        }
        
        res += "\nTriumph:\n" + this.triumphCard;
        
        return res + "\n";
    }
    
    /**
     * Returns a String showing the score of each player.
     */
    private String scores()
    {
        String res = "\n==== Scores ====\n\n";
        
        for ( Player aPlayer : this.players )
        {
            res += "\t" + aPlayer.name() + ": " + Brisca.scoreForCards( aPlayer.wonCards() ) + "\n";
        }
        
        return res;
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
    public Card winnerCard( Card firstCard, Card secondCard, CardFamily triumph )
    {
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add( firstCard );
        cards.add( secondCard );
        return this.winnerCard( cards, triumph );
    }
    
    /**
     * Given an array list of cards, returns the winner of all of them taking into account the triumph.
     * @param ArrayList<Card> cards List of cards in the table.
     * @param CardFamily triumph Triumph randomly selected at the beginning of the match.
     * @return Card Winner card.
     */
    public Card winnerCard( ArrayList<Card> cards, CardFamily triumph )
    {
        Object[] res = this.winnerCouple( cards, triumph );
        Card card = ( Card ) res[ 0 ];
        return card;
    }
    
    public Player winnerPlayer( ArrayList<Card> cards, CardFamily triumph )
    {
        Object[] res = this.winnerCouple( cards, triumph );
        Integer _t = ( Integer ) res[ 1 ];
        int _s = _t.intValue();
        return this.players.get( _s );
    }
    
    /**
     * Given a list of cards and triumph family, returns the couple { winner card, winner player }.
     * This method is just for winnerCard and winnerPlayer. This method SHOULD NOT BE USED in any other method.
     * 
     * This mehod assumes that player and card's list follow the same order. That means that
     * card with index 1 was played by player with index 1.
     * 
     * @param ArrayList<Card> cards List of cards.
     * @param CardFamily triumph Triumph.
     * @return Object[] 0 => Card Winner card, 1 => Player Winner player
     */
    private Object[] winnerCouple( ArrayList<Card> cards, CardFamily triumph )
    {
        Object[] res = new Object[2];
        int maxValuePos = 0;
        Card winnerCard = null;
        
        if ( cards.size() < 1 ) return null;
        
        Card initialCard = cards.get( 0 );
        
        int _t = 0;
        for ( Card aCard : cards )
        {   
            if ( aCard.family().equals( initialCard.family() ) || aCard.family().equals( triumph ) )
            {
                if ( Brisca.valueForCard( aCard, triumph ) > Brisca.valueForCard( winnerCard, triumph ) )
                {
                    maxValuePos = _t;
                    winnerCard = aCard;
                }
            }
            
            _t++;
        }
        
        res[ 0 ] = winnerCard;
        res[ 1 ] = new Integer( maxValuePos );
        
        return res;
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
