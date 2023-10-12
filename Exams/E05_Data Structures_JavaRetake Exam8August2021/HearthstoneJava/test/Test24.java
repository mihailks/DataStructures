import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Test24 {

    private Board board;

    @Before
    public void setUp() {
        this.board = new BoardImpl();
    }


    @Test
    public void heal_should_work_correct()
    {
        //Arrange
        Card card = new Card("Best card", 10, 20, 5);
        Card card2 = new Card("Cool chocolate", 10, 15, 5);
        Card card3 = new Card("Cosmic magic", 6, 8, 2);
        Card card4 = new Card("Combat card", 10, 4, 2);
        Card card5 = new Card("NotCombat card", 10, 9, 2);
        Card card6 = new Card("Special card", 10, 3, 2);


        //Act
        board.draw(card);
        board.draw(card2);
        board.draw(card3);
        board.draw(card4);
        board.draw(card5);
        board.draw(card6);

        board.play("NotCombat card", "Special card");
        board.play("NotCombat card", "Special card");
        board.play("Cosmic magic", "NotCombat card");
        board.play("Cosmic magic", "NotCombat card");
        board.play("Cosmic magic", "NotCombat card");
        board.play("Cosmic magic", "NotCombat card");

        board.heal(13);

        //Assert
        Assert.assertEquals(9, card5.getHealth());

    }
}
