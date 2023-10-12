import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Test04 {

    private Board board;

    @Before
    public void setUp() {
        this.board = new BoardImpl();
    }


    @Test
    public void contains_should_return_false_with_invalid_name() {
        //Arrange
        Card card = new Card("Gnome the grudge", 10, 20, 5);
        Card card2 = new Card("Best hearthstone card", 10, 15, 5);


        //Act
        board.draw(card);
        board.draw(card2);

        boolean actual = board.contains("Invalid");
        //Assert
        Assert.assertFalse(actual);

    }
}
