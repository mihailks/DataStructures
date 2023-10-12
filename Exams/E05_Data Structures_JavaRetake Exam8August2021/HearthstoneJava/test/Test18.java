import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test18 {

    private Board board;

    @Before
    public void setUp() {
        this.board = new BoardImpl();
    }


    @Test
    public void getBestOfRange_should_be_in_same_order() {
        //Arrange
        Card card = new Card("Gnome the grudge", 10, 20, 5);
        Card card2 = new Card("Magic Card", 10, 15, 5);
        Card card3 = new Card("No magic Card", 6, 8, 3);
        Card card4 = new Card("Simple card", 10, 8, 2);

        //Act
        board.draw(card);
        board.draw(card2);
        board.draw(card3);
        board.draw(card4);

        List<Card> expected = new ArrayList<>() {
            {
                add(new Card("Magic Card", 10, 15, 5));
                add(new Card("No magic Card", 6, 8, 3));
                add(new Card("Simple card", 10, 8, 2));

            }
        };
        Iterator<Card> actual = board.getBestInRange(8, 15).iterator();

        for (int i = 0; i < expected.size() && actual.hasNext(); i++) {

            Assert.assertEquals(expected.get(i), actual.next());
        }


    }
}
