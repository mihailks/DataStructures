import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

public class Test21 {

    private Board board;

    @Before
    public void setUp() {
        this.board = new BoardImpl();
    }


    @Test
    public void listCardsBySufficks_should_return_correct_count_of_cards() {
        //Arrange
        Card card = new Card("Best card", 10, 20, 5);
        Card card2 = new Card("Cool card", 10, 15, 5);
        Card card3 = new Card("Cosmic magic Card", 6, 8, 3);
        Card card4 = new Card("Combat card", 10, 8, 2);


        //Act
        board.draw(card);
        board.draw(card2);
        board.draw(card3);
        board.draw(card4);

        Iterator<Card> result = board.listCardsByPrefix("Co").iterator();
        int actualCount = 0;
        while (result.hasNext()) {
            actualCount++;
            result.next();
        }
        //Assert
        Assert.assertEquals(3, actualCount);

    }
}
