import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Test11 {

    private Board board;

    @Before
    public void setUp() {
        this.board = new BoardImpl();
    }


    @Test
    public void play_with_card_with_zero_or_less_health_should_throw_exception() {
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

        board.play("Gnome the grudge", "Magic Card");
        board.play("Gnome the grudge", "Magic Card");
        board.play("Gnome the grudge", "Magic Card");

        //Assert
        Assert.assertEquals(0, card2.getHealth());
        Assert.assertEquals(4, board.count());

    }
}
