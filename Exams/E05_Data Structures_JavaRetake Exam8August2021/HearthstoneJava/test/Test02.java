import org.junit.Before;
import org.junit.Test;

public class Test02 {

    private Board board;

    @Before
    public void setUp() {
        this.board = new BoardImpl();
    }


    @Test(expected = IllegalArgumentException.class)
    public void draw_should_throw_exception_with_existing_name() {
        //Arrange
        Card card = new Card("Gnome the grudge", 10, 20, 5);
        Card card2 = new Card("Gnome the grudge", 10, 15, 5);


        //Act
        board.draw(card);


        //Assert
        board.draw(card2);
    }
}
