package sample;

/*
    Uses the same basic structure of Card just to store the
    value and suit of cards that will match a pattern
 */
public class PatternMatch {
    public final static int SPADES = 0,       // Codes for the 4 suits.
            HEARTS = 1,
            DIAMONDS = 2,
            CLUBS = 3;

    public final static int ACE = 1,          // Codes for the non-numeric cards.
            JACK = 11,        //   Cards 2 through 10 have their
            QUEEN = 12,       //   numerical values for their codes.
            KING = 13;

    public final static int BLACK = 0, RED = 1;

    private final int suit;   // The suit of this card, one of the constants
    //    SPADES, HEARTS, DIAMONDS, CLUBS.

    private final int value;  // The value of this card, from 1 to 13.

    private final int color;

    public PatternMatch(int theValue, int theSuit, int theColor) {
        this.value = theValue;
        this.suit = theSuit;
        this.color = theColor;
    }
}
