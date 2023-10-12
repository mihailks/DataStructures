import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Test14 {

    private Board board;

    @Before
    public void setUp() {
        this.board = new BoardImpl();
    }


    @Test
    public void remove_card_should_decrease_count_of_cards_in_deck() {
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

        board.remove("Magic Card");
        board.remove("Simple card");
        //Assert
        Assert.assertEquals(2, board.count());

    }
}
