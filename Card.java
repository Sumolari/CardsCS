import CardExceptions.*;

public class Card
{

    private int value;
    private CardFamily family;
    
    /**
     * Creates a new card with given value and family.
     * @int value Value of the card. Valid values are numbers from 1 to 12, both included.
     * @int family String representing the family name. It is strongly recommended to use one of the 4 constants representing family names.
     * @exception InvalidCardValueException This exception raises when value is not in range.
     */
    public Card( int value, CardFamily family ) throws InvalidCardValueException
    {
        if ( value < 1 || value > 12 )
            throw new InvalidCardValueException();
    
        this.value = value;
        this.family = family;
    }
    
    /**
     * Creates a new card with given value and family.
     * @int value Value of the card. Valid values are numbers from 1 to 12, both included.
     * @int family String representing the family name. It is strongly recommended to use one of the 4 constants representing family names.
     * @exception InvalidCardValueException This exception raises when value is not in range.
     * @exception InvalidCardFamilyException This exception raises when the family is not valid.
     */
    public Card( int value, String family ) throws InvalidCardValueException, InvalidCardFamilyException
    {
        this ( value, new CardFamily ( family ) );
    }
    
    /**
     * Returns the symbol of the value of the card.
     * @return String String representing the value of the card. An integer for values lower than 10 and S, C and R for other ones.
     */
    private String symbol()
    {
        switch ( this.value )
        {
            case 10:
                return "S";     // Sota
            case 11:
                return "C";     // Caballo
            case 12:
                return "R";     // Rey
            default:
                return "" + this.value;
        }
    }
    
    @Override public String toString()
    {
        String  res =   " ___ \n";
                res +=  "|" + this.family + "|\n";
                res +=  "| " + this.symbol() + " |\n";
                res +=  "|___|\n";
                
        return res;
    }

    @Override public boolean equals( Object o )
    {
        if ( o instanceof Card ) {
            Card c = ( Card ) o;
            return ( c.value == this.value && c.family.equals( this.family ) );
        }
        
        return false;
    }
}