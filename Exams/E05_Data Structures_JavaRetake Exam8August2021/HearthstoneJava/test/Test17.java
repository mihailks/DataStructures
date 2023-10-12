import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

public class Test17 {

    private Board board;

    @Before
    public void setUp() {
        this.board = new BoardImpl();
    }


    @Test
    public void getBestOfRange_should_have_right_count() {
        //Arrange
        Card card = new Card("Gnome the grudge", 10, 20, 5);
        Card card2 = new Card("Magic Card", 10, 15, 5);
        Card card3 = new Card("No magic Card", 6, 8, 3);
        Card card4 = new Card("Simple card", 10, 8, 3);


        //Act
        board.draw(card);
        board.draw(card2);
        board.draw(card3);
        board.draw(card4);

        Iterator<Card> result = board.getBestInRange(8, 15).iterator();
        int actualCount = 0;
        while (result.hasNext()) {
            actualCount++;
            result.next();
        }
        //Assert
        Assert.assertEquals(3, actualCount);

    }
}
