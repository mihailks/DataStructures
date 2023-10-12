import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test23 {

    private Board board;

    @Before
    public void setUp() {
        this.board = new BoardImpl();
    }


    @Test
    public void searchByLevel_should_return_correct_order_of_cards() {
        //Arrange
        Card card = new Card("Best card", 10, 20, 5);
        Card card2 = new Card("Cool chocolate", 10, 15, 5);
        Card card3 = new Card("Cosmic magic", 6, 8, 2);
        Card card4 = new Card("Combat card", 10, 4, 2);
        Card card5 = new Card("NotCombat card", 10, 9, 2);
        Card card6 = new Card("Special card", 10, 3, 2);

        //Act
        board.draw(card);
        board.draw(card2);
        board.draw(card3);
        board.draw(card4);
        board.draw(card5);
        board.draw(card6);

        List<Card> expected = new ArrayList<>() {{
            add(new Card("NotCombat card", 10, 9, 2));
            add(new Card("Cosmic magic", 6, 8, 2));
            add(new Card("Combat card", 10, 4, 2));
            add(new Card("Special card", 10, 3, 2));
        }};
        Iterator<Card> actual = board.searchByLevel(2).iterator();


        //Assert
        for (int i = 0; i < 4 && actual.hasNext(); i++) {
            Assert.assertEquals(expected.get(i),actual.next());
        }

    }
}
