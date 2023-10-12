import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PTest14 {

    protected class InputGenerator {


        private int[] damage = {3, 4, 5, 6, 7, 8, 10, 9};

        private int[] scores = {7, 15, 6, 9, 12, 3, 8};

        private int[] level = {1, 2, 3, 4, 5};

        public List<Card> generateCards(int count) {
            var names = generateNames(count);
            List<Card> competitors = new ArrayList<>();
            for (int i = 1; i <= count; i++) {

                competitors.add(new Card(names.get(i % names.size()), damage[i % damage.length] + i, scores[i % scores.length] + i, level[i % level.length]));
            }
            return competitors;
        }

        public List<String> generateNames(int count) {

            var list = new ArrayList<String>();
            for (int i = 0; i < count; i++) {
                list.add(String.format("%s card", UUID.randomUUID().toString() + i));
            }

            return list;
        }

    }

    private Board board;
    private InputGenerator inputGenerator;

    @Before
    public void setUp() {
        this.board = new BoardImpl();
        this.inputGenerator = new InputGenerator();
    }

    @Test
    public void play_150_000_card_search_by_level() {

        int initialCount = 150000;

        List<Card> cards = this.inputGenerator.generateCards(initialCount);

        int c = 0;
        for (Card card : cards) {
            if (card.getLevel() == 2) c++;
            this.board.draw(card);
        }

        long start = System.currentTimeMillis();
        int count = Long.valueOf(this.board.searchByLevel(2).spliterator().getExactSizeIfKnown()).intValue();
        long stop = System.currentTimeMillis();
        long executionTimeInMillis = stop - start;
        Assert.assertTrue(executionTimeInMillis <= 65);
        Assert.assertEquals(c, count);
    }
}
