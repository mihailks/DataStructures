import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Test20 {

    private Board board;

    @Before
    public void setUp() {
        this.board = new BoardImpl();
    }


    @Test
    public void listCardsBySufficks_should_return_correct_order_of_cards() {
        //Arrange
        Card card = new Card("Best card", 10, 20, 5);
        Card card2 = new Card("Cool chocolate", 10, 15, 5);
        Card card3 = new Card("Cosmic magic", 6, 8, 3);
        Card card4 = new Card("Combat card", 10, 8, 2);


        //Act
        board.draw(card);
        board.draw(card2);
        board.draw(card3);
        board.draw(card4);

        List<Card> expected = new ArrayList<Card>() {
            {
                add(new Card("Cosmic magic", 6, 8, 3));
                add(new Card("Combat card", 10, 8, 2));
                add(new Card("Cool chocolate", 10, 15, 5));
            }
        };

        Iterable<Card> actual = board.listCardsByPrefix("Co");

        //Assert
        Assert.assertEquals(expected, actual);

    }
}
