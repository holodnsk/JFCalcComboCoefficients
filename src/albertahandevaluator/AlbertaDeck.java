package albertahandevaluator;

/***************************************************************************
Copyright (c) 2000:
      University of Alberta,
      Deptartment of Computing Science
      Computer Poker Research Group

    See "Liscence.txt"
***************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
*  A Deck of 52 Cards which can be dealt and shuffled
*  @author  Aaron Davidson
*/

public class AlbertaDeck {
   public static final int NUM_CARDS = 52;
   private AlbertaCard[] gAlbertaCards = new AlbertaCard[NUM_CARDS];
   private char position; // top of deck
   private Random r = new Random();
   
   /**
    * Constructor.
    */
   public AlbertaDeck() {
      position = 0;
      for (int i=0;i<NUM_CARDS;i++) {
         gAlbertaCards[i] = new AlbertaCard(i);
      }
   }
   
   /**
    * Constructor w/ shuffle seed.
    * @param seed the seed to use in randomly shuffling the deck.
    */
   public AlbertaDeck(long seed) {
      this();
      if (seed == 0) { 
         seed = System.currentTimeMillis();
      }
      r.setSeed(seed);
   }
    
    /**
     * Get all cards from deck
     */
    public List<AlbertaCard> getAllCards() {
        return new ArrayList<>(Arrays.asList(gAlbertaCards));
    }
   
   /**
    * Places all cards back into the deck.
    * Note: Does not sort the deck.
    */
   public synchronized void reset() { position = 0; }
     
   /**
    * Shuffles the cards in the deck.
    */
   public synchronized void shuffle() {
      AlbertaCard tempAlbertaCard;
      int   i,j;
      for (i=0; i<NUM_CARDS; i++) {
         j = i + randInt(NUM_CARDS-i);
         tempAlbertaCard = gAlbertaCards[j];
         gAlbertaCards[j] = gAlbertaCards[i];
         gAlbertaCards[i] = tempAlbertaCard;
      }
      position = 0;
   }
   
   /**
    * Obtain the next card in the deck.
    * If no cards remain, a null card is returned
    * @return the card dealt
    */
   public synchronized AlbertaCard deal() {
      return (position < NUM_CARDS ? gAlbertaCards[position++] : null);
   }
   
   /**
    * Obtain the next card in the deck.
    * If no cards remain, a null card is returned
    * @return the card dealt
    */
   public synchronized AlbertaCard dealCard() {
      return extractRandomCard();
   }
   
   /**
    * Find position of Card in Deck.
    */
   public synchronized int findCard(AlbertaCard c) {
      int i = position;
      int n = c.getIndex();
      while (i < NUM_CARDS && n != gAlbertaCards[i].getIndex())
         i++;
      return (i < NUM_CARDS ? i : -1);
   }
   
   private synchronized int findDiscard(AlbertaCard c) {
      int i = 0;
      int n = c.getIndex();
      while (i < position && n != gAlbertaCards[i].getIndex())
         i++;  
      return (n == gAlbertaCards[i].getIndex() ? i : -1);
   }
   
   /**
    * Remove all cards in the given hand from the Deck.
    */
   public synchronized void extractHand(AlbertaHand h) {
      for (int i=1;i<=h.size();i++)
         this.extractCard(h.getCard(i));
   }
   
   /**
    * Remove a card from within the deck.
    * @param c the card to remove.
    */
   public synchronized void extractCard(AlbertaCard c) {
      int i = findCard(c);
      if (i != -1) {
         AlbertaCard t = gAlbertaCards[i];
         gAlbertaCards[i] = gAlbertaCards[position];
         gAlbertaCards[position] = t;
         position++;
      } else {
         System.err.println("*** ERROR: could not find card " + c);
         Thread.currentThread().dumpStack();
      }
   }
   
   /**
    * Remove and return a randomly selected card from within the deck.
    */
   public synchronized AlbertaCard extractRandomCard() {
      int pos = position+randInt(NUM_CARDS-position);
      AlbertaCard c = gAlbertaCards[pos];
      gAlbertaCards[pos] = gAlbertaCards[position];
      gAlbertaCards[position] = c;
      position++;
      return c;
   }
   
   /**
    * Return a randomly selected card from within the deck without removing it.  
    */
   public synchronized AlbertaCard pickRandomCard() {
      return gAlbertaCards[position+randInt(NUM_CARDS-position)];
   }
   
   /**
    * Place a card back into the deck.
    * @param c the card to insert.
    */
   public synchronized void replaceCard(AlbertaCard c) {
      int i = findDiscard(c);
      if (i != -1) {
         position--;
         AlbertaCard t = gAlbertaCards[i];
         gAlbertaCards[i] = gAlbertaCards[position];
         gAlbertaCards[position] = t;
      }
   }
   
   /**
    * Obtain the position of the top card. 
    * (the number of cards dealt from the deck)
    * @return the top card index
    */
   public synchronized int getTopCardIndex() {
      return position;
   }
   
   
   /**
    * Obtain the number of cards left in the deck
    */
   public synchronized int cardsLeft() {
      return NUM_CARDS-position;
   }
   
   /**
    * Obtain the card at a specific index in the deck.
    * Does not matter if card has been dealt or not.
    * If i < topCardIndex it has been dealt.
    * @param i the index into the deck (0..51)
    * @return the card at position i
    */
   public synchronized AlbertaCard getCard(int i) {
      return gAlbertaCards[i];
   }
   
   public String toString() {
      StringBuffer s = new StringBuffer();
      s.append("* ");
      for (int i=0;i<position;i++)
         s.append(gAlbertaCards[i].toString()+" ");
      s.append("\n* ");
      for (int i=position;i<NUM_CARDS;i++)
         s.append(gAlbertaCards[i].toString()+" ");
      return s.toString();
   }
   
   private int randInt(int range) {
      return (int)(r.nextDouble()*range);
   }
    

}
