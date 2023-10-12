import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Test06 {

    private Board board;

    @Before
    public void setUp() {
        this.board = new BoardImpl();
    }


    @Test
    public void count_should_return_zero_when_deck_has_no_elements() {

        //Act;

        int count = board.count();
        //Assert

        Assert.assertEquals(0, count);

    }
}
