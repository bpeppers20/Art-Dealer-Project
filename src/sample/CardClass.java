package sample;

import java.util.ArrayList;

// Class where the cards are given a key-value to be accessed by
public class CardClass
{
    // Card Values
    private String name; // Name of cards
    private int cardValue; // Value of Cards to be accessed by

    // Card Attributes
    public boolean isBlack, isRed, isSpade, isClub, isHeart, isDiamond;

    // Set values in constructor. Hard code cards for consistency
    public CardClass (int value, String Sname, boolean colorBlack, boolean colorRed,
                      boolean suitSpade, boolean suitClub, boolean suitHeart, boolean suitDia)
    {
        name = Sname;
        cardValue = value;
        isBlack = colorBlack;
        isRed = colorRed;
        isSpade = suitSpade;
        isClub = suitClub;
        isHeart = suitHeart;
        isDiamond = suitDia;
    }
    public int getCardValue ()
    {
        return cardValue;
    }

    public static ArrayList<CardClass> createDeck ()
    {
        // Class to create the 52 card deck based on open face deck
        ArrayList<CardClass> deck = new ArrayList<CardClass>();
        //Spades
        deck.add(new CardClass(0,"SpadeAce",true,false,true,false,false,false));
        deck.add(new CardClass(1,"Spade2",true,false,true,false,false,false));
        deck.add(new CardClass(2,"Spade3",true,false,true,false,false,false));
        deck.add(new CardClass(3,"Spade4",true,false,true,false,false,false));
        deck.add(new CardClass(4,"Spade5",true,false,true,false,false,false));
        deck.add(new CardClass(5,"Spade6",true,false,true,false,false,false));
        deck.add(new CardClass(6,"Spade7",true,false,true,false,false,false));
        deck.add(new CardClass(7,"Spade8",true,false,true,false,false,false));
        deck.add(new CardClass(8,"Spade9",true,false,true,false,false,false));
        deck.add(new CardClass(9,"Spade10",true,false,true,false,false,false));
        deck.add(new CardClass(10,"SpadeJack",true,false,true,false,false,false));
        deck.add(new CardClass(11,"SpadeQueen",true,false,true,false,false,false));
        deck.add(new CardClass(12,"SpadeKing",true,false,true,false,false,false));

        //Hearts
        deck.add(new CardClass(13,"HeartAce",false,true,false,false,true,false));
        deck.add(new CardClass(14,"Heart2",false,true,false,false,true,false));
        deck.add(new CardClass(15,"Heart3",false,true,false,false,true,false));
        deck.add(new CardClass(16,"Heart4",false,true,false,false,true,false));
        deck.add(new CardClass(17,"Heart5",false,true,false,false,true,false));
        deck.add(new CardClass(18,"Heart6",false,true,false,false,true,false));
        deck.add(new CardClass(19,"Heart7",false,true,false,false,true,false));
        deck.add(new CardClass(20,"Heart8",false,true,false,false,true,false));
        deck.add(new CardClass(21,"Heart9",false,true,false,false,true,false));
        deck.add(new CardClass(22,"Heart10",false,true,false,false,true,false));
        deck.add(new CardClass(23,"HeartJack",false,true,false,false,true,false));
        deck.add(new CardClass(24,"HeartQueen",false,true,false,false,true,false));
        deck.add(new CardClass(25,"HeartKing",false,true,false,false,true,false));

        //Diamonds
        deck.add(new CardClass(26,"DiamondAce",false,true,false,false,false,true));
        deck.add(new CardClass(27,"Diamond2",false,true,false,false,false,true));
        deck.add(new CardClass(28,"Diamond3",false,true,false,false,false,true));
        deck.add(new CardClass(29,"Diamond4",false,true,false,false,false,true));
        deck.add(new CardClass(30,"Diamond5",false,true,false,false,false,true));
        deck.add(new CardClass(31,"Diamond6",false,true,false,false,false,true));
        deck.add(new CardClass(32,"Diamond7",false,true,false,false,false,true));
        deck.add(new CardClass(33,"Diamond8",false,true,false,false,false,true));
        deck.add(new CardClass(34,"Diamond9",false,true,false,false,false,true));
        deck.add(new CardClass(35,"Diamond10",false,true,false,false,false,true));
        deck.add(new CardClass(36,"DiamondJack",false,true,false,false,false,true));
        deck.add(new CardClass(37,"DiamondQueen",false,true,false,false,false,true));
        deck.add(new CardClass(38,"DiamondKing",false,true,false,false,false,true));

        //Clubs
        deck.add(new CardClass(39,"ClubAce",true,false,false,true,false,false));
        deck.add(new CardClass(40,"Club2",true,false,false,true,false,false));
        deck.add(new CardClass(41,"Club3",true,false,false,true,false,false));
        deck.add(new CardClass(42,"Club4",true,false,false,true,false,false));
        deck.add(new CardClass(43,"Club5",true,false,false,true,false,false));
        deck.add(new CardClass(44,"Club6",true,false,false,true,false,false));
        deck.add(new CardClass(45,"Club7",true,false,false,true,false,false));
        deck.add(new CardClass(46,"Club8",true,false,false,true,false,false));
        deck.add(new CardClass(47,"Club9",true,false,false,true,false,false));
        deck.add(new CardClass(48,"Club10",true,false,false,true,false,false));
        deck.add(new CardClass(49,"ClubJack",true,false,false,true,false,false));
        deck.add(new CardClass(50,"ClubQueen",true,false,false,true,false,false));
        deck.add(new CardClass(51,"ClubKing",true,false,false,true,false,false));
        return (deck);
    }

}
