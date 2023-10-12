import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Test19 {

    private Board board;

    @Before
    public void setUp() {
        this.board = new BoardImpl();
    }


    @Test
    public void listCardsBySufficks_should_return_empty_collection_with_invalid_sufficks() {
        //Arrange
        Card card = new Card("Best card", 10, 20, 5);
        Card card2 = new Card("Cool card", 10, 15, 5);
        Card card3 = new Card("No magic Card", 6, 8, 3);
        Card card4 = new Card("Simple card", 10, 8, 2);

        //Act
        board.draw(card);
        board.draw(card2);
        board.draw(card3);
        board.draw(card4);


        //Assert
        Assert.assertFalse("Empty collection expected", this.board.listCardsByPrefix("Mo").iterator().hasNext());


    }
}
