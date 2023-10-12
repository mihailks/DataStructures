
public interface Board {

    void draw(Card card);

    Boolean contains(String name);

    int count();

    void play(String attackerCardName, String attackedCardName);

    void remove(String name);

    void removeDeath();

    Iterable<Card> getBestInRange(int start, int end);

    Iterable<Card> listCardsByPrefix(String prefix);

    Iterable<Card> searchByLevel(int level);

    void heal(int health);
}
