import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

public class Test22 {

    private Board board;

    @Before
    public void setUp() {
        this.board = new BoardImpl();
    }


    @Test
    public void searchByLevel_should_return_correct_count_of_cards() {
        //Arrange
        Card card = new Card("Best card", 10, 20, 5);
        Card card2 = new Card("Cool chocolate", 10, 15, 5);
        Card card3 = new Card("Cosmic magic", 6, 8, 2);
        Card card4 = new Card("Combat card", 10, 8, 2);
        Card card5 = new Card("NotCombat card", 10, 8, 2);
        Card card6 = new Card("Special card", 10, 8, 2);

        //Act
        board.draw(card);
        board.draw(card2);
        board.draw(card3);
        board.draw(card4);
        board.draw(card5);
        board.draw(card6);

        int expected = 4;

        Iterator<Card> result = board.searchByLevel(2).iterator();
        int actualCount = 0;
        while (result.hasNext()) {
            actualCount++;
            result.next();
        }
        //Assert
        Assert.assertEquals(expected, actualCount);

    }
}
