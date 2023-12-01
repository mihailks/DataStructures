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
        List<Battlecard> cardsByType = cardsByID
                .values()
                .stream()
                .filter(c -> c.getType().equals(type))
                .sorted(Comparator.comparing(Battlecard::getDamage).reversed()
                        .thenComparing(Battlecard::getId))
                .collect(Collectors.toList());
        if (cardsByType.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        return cardsByType;
    }

    @Override
    public Iterable<Battlecard> getByTypeAndDamageRangeOrderedByDamageThenById(CardType type, int lo, int hi) {
        List<Battlecard> cardsByType = cardsByID
                .values()
                .stream()
                .filter(c -> c.getType().equals(type) && c.getDamage() > lo && c.getDamage() < hi)
                .sorted(Comparator.comparing(Battlecard::getDamage).reversed()
                        .thenComparing(Battlecard::getId))
                .collect(Collectors.toList());
        if (cardsByType.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        return cardsByType;
    }

    @Override
    public Iterable<Battlecard> getByCardTypeAndMaximumDamage(CardType type, double damage) {
        List<Battlecard> cardsByType = cardsByID
                .values()
                .stream()
                .filter(c -> c.getType().equals(type) && c.getDamage() <= damage)
                .sorted(Comparator.comparing(Battlecard::getDamage).reversed()
                        .thenComparing(Battlecard::getId))
                .collect(Collectors.toList());
        if (cardsByType.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        return cardsByType;
    }

    @Override
    public Iterable<Battlecard> getByNameOrderedBySwagDescending(String name) {
        List<Battlecard> cardsByType = cardsByID
                .values()
                .stream()
                .filter(c -> c.getName().equals(name))
                .sorted(Comparator.comparing(Battlecard::getSwag).reversed()
                        .thenComparing(Battlecard::getId))
                .collect(Collectors.toList());
        if (cardsByType.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        return cardsByType;
    }

    @Override
    public Iterable<Battlecard> getByNameAndSwagRange(String name, double lo, double hi) {
        List<Battlecard> cardsByType = cardsByID
                .values()
                .stream()
                .filter(c -> c.getName().equals(name) && c.getSwag() >= lo && c.getSwag() < hi)
                .sorted(Comparator.comparing(Battlecard::getSwag).reversed()
                        .thenComparing(Battlecard::getId))
                .collect(Collectors.toList());
        if (cardsByType.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        return cardsByType;
    }

    @Override
    public Iterable<Battlecard> getAllByNameAndSwag() {
        Map<String, Set<Battlecard>> cardsByName = new HashMap<>();
        for (Battlecard card : cardsByID.values()) {
            cardsByName.putIfAbsent(card.getName(), new HashSet<>());
            cardsByName.get(card.getName()).add(card);
        }

        List<Battlecard> result = new ArrayList<>();
        for (Set<Battlecard> cards : cardsByName.values()) {
            Battlecard maxSwag = cards
                    .stream()
                    .max(Comparator.comparing(Battlecard::getSwag))
                    .get();
            result.add(maxSwag);
        }
        return result;
    }

    @Override
    public Iterable<Battlecard> findFirstLeastSwag(int n) {
        if (n > cardsByID.size()) {
            throw new UnsupportedOperationException();
        }
        return cardsByID
                .values()
                .stream()
                .sorted(Comparator.comparing(Battlecard::getSwag)
                        .thenComparing(Battlecard::getId))
                .limit(n)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Battlecard> getAllInSwagRange(double lo, double hi) {
        return cardsByID
                .values()
                .stream()
                .filter(c -> c.getSwag() >= lo && c.getSwag() <= hi)
                .sorted(Comparator.comparing(Battlecard::getSwag)
                        .thenComparing(Battlecard::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Iterator<Battlecard> iterator() {
        return cardsByID.values().iterator();
    }
}
