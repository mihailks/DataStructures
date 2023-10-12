import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Test05 {

    private Board board;

    @Before
    public void setUp() {
        this.board = new BoardImpl();
    }


    @Test
    public void contains_should_return_true_with_valid_name() {
        //Arrange
        Card card = new Card("Gnome the grudge", 10, 20, 5);
        Card card2 = new Card("Best hearthstone card", 10, 15, 5);


        //Act
        board.draw(card);
        board.draw(card2);

        boolean result = board.contains("Gnome the grudge");
        //Assert
        Assert.assertTrue(result);

    }
}
