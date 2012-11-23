import CardExceptions.*;

public class CardFamily
{
    public static final String kEspadasFamily   = "espadas";
    public static final String kBastosFamily    = "bastos";
    public static final String kCopasFamily     = "copas";
    public static final String kOrosFamily      = "oros";
    
    private static final int kUnknownFamilyInt  = -1;
    private static final int kEspadasFamilyInt  = 0;
    private static final int kBastosFamilyInt   = 1;
    private static final int kCopasFamilyInt    = 2;
    private static final int kOrosFamilyInt     = 3;
    
    private int family;

    /**
     * Instanciates a new family with given name.
     * @param familyName String representing the name of the family.
     * @exception InvalidCardFamilyException This exception raises when the family is not valid.
     */
    public CardFamily( String familyName ) throws InvalidCardFamilyException
    {
        this.family = familyInt( familyName );
        
        if ( this.family == kUnknownFamilyInt )
            throw new InvalidCardFamilyException();
    }
    
    /**
     * Given a family name, returns the integer used internally to manage it.
     * @param familyName String representing the name of the family.
     * @return int Integer used internally to work with the family. If the family is not valid, returns kUnknownFamilyInt.
     */
    private static int familyInt( String familyName )
    {
        String[] familyNames = { kEspadasFamily, kBastosFamily, kCopasFamily, kOrosFamily };
        int[] familyInts = { kEspadasFamilyInt, kBastosFamilyInt, kCopasFamilyInt, kOrosFamilyInt };
        
        familyName = familyName.toLowerCase();
        
        for ( int i = 0; i < familyNames.length; i++ )
            if ( familyNames[ i ].equals( familyName ) )
                return familyInts[ i ];
                
        return kUnknownFamilyInt;
    }

    @Override public String toString()
    {
        switch ( this.family )
        {
            case 0:
                return "esp";
            case 1:
                return "bas";
            case 2:
                return "cop";
            case 3:
                return "oro";
            default:
                return "???";
        }
    }
    
    @Override public boolean equals( Object o )
    {
        if ( o instanceof CardFamily ) {
            CardFamily cf = ( CardFamily ) o;
            return ( cf.family == this.family );
        }
        
        return false;
    }
}
