import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoyaleArenaTest {
    RoyaleArena arena;
    public RoyaleArenaTest() {
        this.arena = new RoyaleArena();
    }

    @BeforeEach
    void setUp() {
        arena.add(new Battlecard(1, CardType.SPELL, "a", 1, 1));
        arena.add(new Battlecard(2, CardType.RANGED, "b", 2, 2));
        arena.add(new Battlecard(3, CardType.BUILDING, "c", 3, 3));
        arena.add(new Battlecard(4, CardType.SPELL, "d", 4, 4));
        arena.add(new Battlecard(5, CardType.MELEE, "e", 5, 5));
        arena.add(new Battlecard(6, CardType.SPELL, "f", 6, 6));
        arena.add(new Battlecard(7, CardType.MELEE, "g", 7, 7));
        arena.add(new Battlecard(8, CardType.SPELL, "h", 8, 8));
        arena.add(new Battlecard(9, CardType.BUILDING, "i", 9, 9));
        arena.add(new Battlecard(10, CardType.RANGED, "j", 10, 10));

    }

    @Test
    void testAdd() {
        Assertions.assertEquals(10, arena.count());
    }

    @Test void testContains() {
        Assertions.assertTrue(arena.contains(new Battlecard(1, CardType.SPELL, "a", 1, 1)));
    }

    @Test void testCount() {
        Assertions.assertEquals(10, arena.count());
    }

    @Test void testChangeCardType() {
        arena.changeCardType(1, CardType.BUILDING);
        Assertions.assertEquals(CardType.BUILDING, arena.getById(1).getType());
    }

    @Test void testGetById() {
        Assertions.assertEquals(1, arena.getById(1).getId());
    }

    @Test
    public void testRemoveById() {
        arena.removeById(1);
        Assertions.assertEquals(9, arena.count());
    }

    @Test
    public void testGetByCardType() {
        List<Battlecard> cards = (List<Battlecard>) arena.getByCardType(CardType.SPELL);
        int count = 0;
        for (Battlecard card : cards) {
            count++;
        }
        assertEquals(cards.get(0).getDamage(), 8);
        arena.add(new Battlecard(11, CardType.SPELL, "k", 11, 11));
        cards = (List<Battlecard>) arena.getByCardType(CardType.SPELL);
        assertEquals(cards.get(0).getDamage(), 11);
        Assertions.assertEquals(4, count);
    }







}
