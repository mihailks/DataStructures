import java.util.*;
import java.util.stream.Collectors;


public class RoyaleArena implements IArena {
    //    private Set<Battlecard> cards;
    Map<Integer, Battlecard> cardsByID;

    public RoyaleArena() {
//        this.cards = new HashSet<>();
        this.cardsByID = new HashMap<>();
    }

    @Override
    public void add(Battlecard card) {
        cardsByID.putIfAbsent(card.getId(), card);
    }

    @Override
    public boolean contains(Battlecard card) {
        return cardsByID.containsKey(card.getId());
    }

    @Override
    public int count() {
        return cardsByID.size();
    }

    @Override
    public void changeCardType(int id, CardType type) {
        if (!this.contains(cardsByID.get(id))) {
            throw new IllegalArgumentException();
        }
        cardsByID.get(id).setType(type);
    }

    @Override
    public Battlecard getById(int id) {
        return cardsByID.get(id);
    }

    @Override
    public void removeById(int id) {
        cardsByID.remove(id);
    }

    @Override
    public Iterable<Battlecard> getByCardType(CardType type) {
        return cardsByID
                .values()
                .stream()
                .filter(c -> c.getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Battlecard> getByTypeAndDamageRangeOrderedByDamageThenById(CardType type, int lo, int hi) {
        return cardsByID
                .values()
                .stream()
                .filter(c -> c.getType().equals(type) && c.getDamage() > lo && c.getDamage() < hi)
                .sorted(Comparator.comparing(Battlecard::getDamage)
                        .thenComparing(Battlecard::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Battlecard> getByCardTypeAndMaximumDamage(CardType type, double damage) {
        return cardsByID
                .values()
                .stream()
                .filter(c -> c.getType().equals(type) && c.getDamage() <= damage)
                .sorted(Comparator.comparing(Battlecard::getDamage).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Battlecard> getByNameOrderedBySwagDescending(String name) {
        return cardsByID
                .values()
                .stream()
                .filter(c -> c.getName().equals(name))
                .sorted(Comparator.comparing(Battlecard::getSwag).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Battlecard> getByNameAndSwagRange(String name, double lo, double hi) {
        return cardsByID
                .values()
                .stream()
                .filter(c -> c.getName().equals(name) && c.getSwag() > lo && c.getSwag() < hi)
                .sorted(Comparator.comparing(Battlecard::getSwag).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Battlecard> getAllByNameAndSwag() {
        return null;
    }

    @Override
    public Iterable<Battlecard> findFirstLeastSwag(int n) {
        return null;
    }

    @Override
    public Iterable<Battlecard> getAllInSwagRange(double lo, double hi) {
        return null;
    }

    @Override
    public Iterator<Battlecard> iterator() {
        return null;
    }
}
