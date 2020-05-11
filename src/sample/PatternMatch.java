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

    // Return the int that codes for this card's suit.
    public int getSuit() { return suit; }
    // Return the int that codes for this card's value.
    public int getValue() { return value; }
    // Return the int that codes for this card's color.
    public int getColor() { return color; }

    public String getSuitAsString() {
        // Return a String representing the card's suit.
        // (If the card's suit is invalid, "??" is returned.)
        switch ( suit ) {
            case SPADES:   return "Spades";
            case HEARTS:   return "Hearts";
            case DIAMONDS: return "Diamonds";
            case CLUBS:    return "Clubs";
            default:       return "??";
        }
    }

    public String getColorAsString() {
        switch (color) {
            case BLACK: return "Black";
            case RED:   return "Red";
            default:    return "??";
        }
    }

    public String getValueAsString() {
        // Return a String representing the card's value.
        // If the card's value is invalid, "??" is returned.
        switch ( value ) {
            case 1:   return "Ace";
            case 2:   return "2";
            case 3:   return "3";
            case 4:   return "4";
            case 5:   return "5";
            case 6:   return "6";
            case 7:   return "7";
            case 8:   return "8";
            case 9:   return "9";
            case 10:  return "10";
            case 11:  return "Jack";
            case 12:  return "Queen";
            case 13:  return "King";
            default:  return "??";
        }
    }
}
