import java.util.*;
import java.util.stream.Collectors;

public class BoardImpl implements Board {
//    private List<Card> cards;
    private Map<String , Card> cards;
    int minHealth = Integer.MAX_VALUE;
    Card minHealthCard = null;

    public BoardImpl() {
//        cards = new LinkedList<>();
        cards = new HashMap<>();
    }

    @Override
    public void draw(Card card) {
        if (this.contains(card.getName())){
            throw new IllegalArgumentException();
        }
        cards.put(card.getName(), card);
    }

    @Override
    public Boolean contains(String name) {
        return cards.containsKey(name);
    }

    @Override
    public int count() {
        return this.cards.size();
    }

    @Override
    public void play(String attackerCardName, String attackedCardName) {
        Card attacker = cards.get(attackerCardName);
        Card attacked = cards.get(attackedCardName);
        if (attacker == null || attacked == null){
            throw new IllegalArgumentException();
        }
        if (attacker.getHealth()<=0){
            return;
        }
        if (attacker.getLevel() != attacked.getLevel()){
            throw new IllegalArgumentException();
        }
        if (attacked.getHealth()<=0){
            return;
        }
        attacked.setHealth(attacked.getHealth() - attacker.getDamage());
        if(attacked.getHealth()<=0){
            attacker.setScore(attacker.getScore() + attacked.getLevel());
        }
        if(attacked.getHealth()<minHealth){
            minHealth = attacked.getHealth();
            minHealthCard = attacked;
        }
    }

    @Override
    public void remove(String name) {
        if (!this.contains(name)){
            throw new IllegalArgumentException();
        }
        cards.remove(name);
    }

    @Override
    public void removeDeath() {
        cards
                .values()
                .removeIf(card -> card.getHealth() <= 0);
    }

    @Override
    public Iterable<Card> getBestInRange(int start, int end) {
        return cards.values()
                .stream()
                .filter(card -> card.getScore() >= start && card.getScore() <= end)
                .sorted((c1, c2) -> c2.getLevel()- c1.getLevel())
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Card> listCardsByPrefix(String prefix) {
        return cards.values()
                .stream()
                .filter(card -> card.getName().startsWith(prefix))
                .sorted(Comparator.comparing(Card::reverseName)
                        .thenComparing(Card::getLevel))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Card> searchByLevel(int level) {
        return cards.values()
                .stream()
                .filter(card -> card.getLevel() == level)
                .sorted((c1, c2) -> Integer.compare(c2.getScore(), c1.getScore()))
                .collect(Collectors.toList());
    }

    @Override
    public void heal(int health) {
//        String cardNameSmallest = cards.values()
//                .stream().min((c1, c2) -> Integer.compare(c1.getHealth(), c2.getHealth())   )
//                .get()
//                .getName();
//        cards.get(cardNameSmallest).setHealth(cards.get(cardNameSmallest).getHealth() + health);
        minHealthCard.setHealth(minHealthCard.getHealth() + health);
    }
}
