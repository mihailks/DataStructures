import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.stream.StreamSupport;

public class OlympicsImplCorrectnessTest {

    private Olympics olympics;

    @Before
    public void setUp() {
        this.olympics = new OlympicsImpl();
    }

    @Test
    public void addCompetitor_count_should_increase() {

        this.olympics.addCompetitor(1, "Ani");
        this.olympics.addCompetitor(2, "Ivan");
        this.olympics.addCompetitor(3, "Galin");
        this.olympics.addCompetitor(4, "Kali");

        final int expectedCount = 4;
        final int actualCount = this.olympics.competitorsCount();

        Assert.assertEquals("Competitors count should increase", expectedCount, actualCount);

    }

    @Test(expected = IllegalArgumentException.class)
    public void addCompetitor_with_existing_id_should_throw_exception() {

        this.olympics.addCompetitor(1, "Ani");
        this.olympics.addCompetitor(3, "Galin");
        this.olympics.addCompetitor(4, "Kali");
        this.olympics.addCompetitor(1, "Ivan");

    }

    @Test
    public void addCompetition_count_should_increase() {
        this.olympics.addCompetition(1, "SoftUniada", 500);
        this.olympics.addCompetition(2, "CodeWizard", 600);
        this.olympics.addCompetition(3, "Programming Basics", 100);
        this.olympics.addCompetition(4, "CSharpForDummies", 20);

        final int expectedCount = 4;
        final int actualCount = this.olympics.competitionsCount();

        Assert.assertEquals("Competitions count should increase", expectedCount, actualCount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addCompetition_with_existing_id_should_throw_exception() {

        this.olympics.addCompetition(1, "SoftUniada", 500);
        this.olympics.addCompetition(2, "CodeWizard", 600);
        this.olympics.addCompetition(3, "Programming Basics", 100);
        this.olympics.addCompetition(1, "CSharpForDummies", 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void compete_with_invalid_competition_id_should_throw_exception() {
        this.olympics.addCompetition(1, "SoftUniada", 500);
        this.olympics.addCompetitor(5, "Ani");
        this.olympics.compete(5, 1);

        this.olympics.compete(5, 7);

    }

    @Test(expected = IllegalArgumentException.class)
    public void compete_with_invalid_competitor_id_should_throw_exception() {
        this.olympics.addCompetition(1, "SoftUniada", 500);
        this.olympics.addCompetitor(5, "Ani");
        this.olympics.compete(5, 1);

        this.olympics.compete(3, 1);
    }

    @Test
    public void compete_with_valid_competitors_in_competition() {

        this.olympics.addCompetition(1, "SoftUniada", 500);

        this.olympics.addCompetitor(5, "Ani");
        this.olympics.compete(5, 1);

        this.olympics.addCompetitor(1, "Ivan");
        this.olympics.compete(1, 1);

        this.olympics.addCompetitor(2, "Stamat");
        this.olympics.compete(2, 1);

        final int expectedCompetitorsCount = 3;
        final int actualCompetitorsCount = this.olympics.getCompetition(1).getCompetitors().size();

        Assert.assertEquals("Invalid competitors count", expectedCompetitorsCount, actualCompetitorsCount);

    }

    @Test
    public void compete_with_valid_competitors_in_competition_score_changed() {

        this.olympics.addCompetition(1, "SoftUniada", 500);

        this.olympics.addCompetitor(5, "Ani");
        this.olympics.compete(5, 1);

        this.olympics.addCompetitor(1, "Ivan");
        this.olympics.compete(1, 1);

        this.olympics.addCompetitor(2, "Stamat");
        this.olympics.compete(2, 1);

        final int expectedCompetitorsScore = 1500;
        int actualCompetitorsScore = 0;

        for (Competitor competitor : this.olympics.getCompetition(1).getCompetitors()) {
            actualCompetitorsScore += competitor.getTotalScore();
        }

        Assert.assertEquals("Invalid competitors score", expectedCompetitorsScore, actualCompetitorsScore);

    }

    @Test(expected = IllegalArgumentException.class)
    public void getCompetition_with_invalid_id_should_throw_exception() {
        final int id = 1;
        final String name = "SoftUniada";
        final int score = 500;
        this.olympics.addCompetition(id, name, score);
        this.olympics.getCompetition(Integer.MIN_VALUE);

    }

    @Test
    public void getCompetition_should_return_correct_competition() {
        final int id = 1;
        final String name = "SoftUniada";
        final int score = 500;
        this.olympics.addCompetition(id, name, score);
        final Competition competition = this.olympics.getCompetition(id);
        boolean isCorrectCompetition = competition.getId() == id && competition.getName().equals(name) && competition.getScore() == score;

        Assert.assertTrue("Incorrect competition data", isCorrectCompetition);

    }

    @Test(expected = IllegalArgumentException.class)
    public void disqualify_should_throw_exception_with_invalid_competition_id() {
        this.olympics.addCompetition(1, "CompetitionOne", 50);
        this.olympics.addCompetitor(1, "Ani");
        this.olympics.compete(1, 1);
        this.olympics.disqualify(2, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void disqualify_should_throw_exception_with_invalid_competitor_id() {
        this.olympics.addCompetition(1, "CompetitionOne", 50);
        this.olympics.addCompetitor(1, "Ani");
        this.olympics.addCompetitor(3, "Ani");
        this.olympics.compete(1, 1);
        this.olympics.disqualify(1, 3);
    }

    @Test
    public void disqualify_should_decrease_competitors_count_in_competition() {

        this.olympics.addCompetition(1, "SoftUniada", 500);

        this.olympics.addCompetitor(5, "Ani");
        this.olympics.compete(5, 1);

        this.olympics.addCompetitor(1, "Ivan");
        this.olympics.compete(1, 1);

        this.olympics.addCompetitor(2, "Stamat");
        this.olympics.compete(2, 1);
        this.olympics.disqualify(1, 5);
        final int expectedCompetitorsCount = 2;
        final int actualCompetitorsCount = this.olympics.getCompetition(1).getCompetitors().size();

        Assert.assertEquals("Invalid competitors count", expectedCompetitorsCount, actualCompetitorsCount);

    }

    @Test
    public void disqualify_should_not_decrease_all_competitors_count() {

        this.olympics.addCompetition(1, "SoftUniada", 500);

        this.olympics.addCompetitor(5, "Ani");
        this.olympics.compete(5, 1);

        this.olympics.addCompetitor(1, "Ivan");
        this.olympics.compete(1, 1);

        this.olympics.addCompetitor(2, "Stamat");
        this.olympics.compete(2, 1);
        this.olympics.disqualify(1, 5);
        final int expectedCompetitorsCount = 3;
        final int actualCompetitorsCount = this.olympics.competitorsCount();

        Assert.assertEquals("Invalid competitors count", expectedCompetitorsCount, actualCompetitorsCount);

    }

    @Test
    public void disqualify_should_reduce_score() {

        this.olympics.addCompetition(1, "SoftUniada", 500);

        this.olympics.addCompetitor(5, "Ani");
        this.olympics.compete(5, 1);

        this.olympics.addCompetitor(1, "Ivan");
        this.olympics.compete(1, 1);

        this.olympics.addCompetitor(2, "Stamat");
        this.olympics.compete(2, 1);
        this.olympics.disqualify(1, 5);

        final int expectedScore = 0;
        final int actualCompetitorsScore = Long.valueOf(this.olympics.getByName("Ani").iterator().next().getTotalScore()).intValue();


        Assert.assertEquals("Invalid competitors score", expectedScore, actualCompetitorsScore);

    }

    @Test(expected = IllegalArgumentException.class)
    public void getByName_should_throw_exception_with_invalid_name() {
        this.olympics.addCompetitor(5, "Ani");
        this.olympics.addCompetitor(2, "Stamat");

        this.olympics.getByName("Misho");

    }

    @Test
    public void getByName_count_should_be_correct() {
        this.olympics.addCompetitor(5, "Ani");
        this.olympics.addCompetitor(2, "Stamat");
        this.olympics.addCompetitor(3, "Stamat");
        Iterator<Competitor> iterator = this.olympics.getByName("Stamat").iterator();
        int expectedCount = 2;
        int actualCount = 0;
        while (iterator.hasNext()) {
            actualCount++;
            iterator.next();
        }

        Assert.assertEquals("Incorrect count of competitors", expectedCount, actualCount);

    }

    @Test
    public void getByName_order_should_be_correct() {
        this.olympics.addCompetitor(5, "Ani");
        this.olympics.addCompetitor(2, "Stamat");
        this.olympics.addCompetitor(3, "Stamat");
        this.olympics.addCompetitor(8, "Stamat");
        this.olympics.addCompetitor(22, "Stamat");
        this.olympics.addCompetitor(15, "Stamat");
        final int[] ids = {2, 3, 8, 15, 22};
        boolean isCorrectOrder = true;

        Iterator<Competitor> iterator = this.olympics.getByName("Stamat").iterator();

        for (int i = 0; i < ids.length && iterator.hasNext(); i++) {
            if (iterator.next().getId() != ids[i]) {
                isCorrectOrder = false;
                break;
            }
        }

        Assert.assertTrue("Incorrect order of competitors", isCorrectOrder);

    }

    @Test
    public void findCompetitorsInRange_with_invalid_range_should_return_empty_collection() {
        this.olympics.addCompetition(1, "SoftUniada", 500);
        this.olympics.addCompetition(2, "Java", 600);

        this.olympics.addCompetitor(5, "Ani");
        this.olympics.compete(5, 1);
        this.olympics.compete(5, 2);

        this.olympics.addCompetitor(1, "Ivan");
        this.olympics.compete(1, 1);

        this.olympics.addCompetitor(2, "Stamat");
        this.olympics.compete(2, 1);
        this.olympics.compete(2, 2);

        Assert.assertFalse("Empty collection expected", this.olympics.findCompetitorsInRange(500,501).iterator().hasNext());

    }

    @Test
    public void findCompetitorsInRange_with_min_value_exclusive() {
        this.olympics.addCompetition(1, "SoftUniada", 500);
        this.olympics.addCompetition(2, "Java", 600);

        this.olympics.addCompetitor(5, "Ani");
        this.olympics.compete(5, 1);
        this.olympics.compete(5, 2);

        this.olympics.addCompetitor(1, "Ivan");
        this.olympics.compete(1, 1);

        this.olympics.addCompetitor(2, "Stamat");
        this.olympics.compete(2, 2);

        long actualCount = StreamSupport.stream(this.olympics.findCompetitorsInRange(500, 600).spliterator(), false).count();

        Assert.assertEquals("The min value must be exclusive", 1L, actualCount);

    }

    @Test
    public void findCompetitorsInRange_with_max_value_inclusive() {
        this.olympics.addCompetition(1, "SoftUniada", 500);
        this.olympics.addCompetition(2, "Java", 600);

        this.olympics.addCompetitor(5, "Ani");
        this.olympics.compete(5, 1);
        this.olympics.compete(5, 2);

        this.olympics.addCompetitor(1, "Ivan");
        this.olympics.compete(1, 1);

        this.olympics.addCompetitor(2, "Stamat");
        this.olympics.compete(2, 1);
        this.olympics.compete(2, 2);

        final int expectedCount = 2;
        int actualCount = 0;

        for (Competitor competitor : this.olympics.findCompetitorsInRange(500, 1100)) {
            actualCount++;
        }

        Assert.assertEquals("The max value must be inclusive", expectedCount, actualCount);

    }

    @Test
    public void findCompetitorsInRange_correct_order() {
        this.olympics.addCompetition(1, "SoftUniada", 500);
        this.olympics.addCompetition(2, "Java", 600);

        this.olympics.addCompetitor(5, "Ani");
        this.olympics.compete(5, 1);
        this.olympics.compete(5, 2);

        this.olympics.addCompetitor(1, "Ivan");
        this.olympics.compete(1, 1);

        this.olympics.addCompetitor(2, "Stamat");
        this.olympics.compete(2, 1);
        this.olympics.compete(2, 2);

        final int[] ids = {1, 2, 5};
        boolean isCorrectOrder = true;

        Iterator<Competitor> iterator = this.olympics.findCompetitorsInRange(400, 1100).iterator();

        for (int i = 0; i < ids.length && iterator.hasNext(); i++) {
            if (iterator.next().getId() != ids[i]) {
                isCorrectOrder = false;
                break;
            }
        }

        Assert.assertTrue("Incorrect order of competitors", isCorrectOrder);

    }


    @Test
    public void searchWithNameLength_with_invalid_range_should_return_empty_collection() {
        this.olympics.addCompetitor(1, "Ani");
        this.olympics.addCompetitor(2, "Georgi");
        this.olympics.addCompetitor(3, "Ivan");
        this.olympics.addCompetitor(4, "Stamat");
        this.olympics.addCompetitor(5, "Georgi");
        this.olympics.addCompetitor(6, "Ivo");
        this.olympics.addCompetitor(7, "Galin");
        this.olympics.addCompetitor(8, "Mariika");
        this.olympics.addCompetitor(9, "Asd");
        this.olympics.addCompetitor(10, "Ani");

        boolean hasCompetitors = this.olympics.searchWithNameLength(0, 2).iterator().hasNext();

        Assert.assertFalse("Collection must be empty", hasCompetitors);
    }

    @Test
    public void searchWithNameLength_min_value_inclusive() {
        this.olympics.addCompetitor(1, "Ani");
        this.olympics.addCompetitor(10, "Ani");
        this.olympics.addCompetitor(6, "Ivo");
        this.olympics.addCompetitor(9, "Asd");
        this.olympics.addCompetitor(2, "Georgi");
        this.olympics.addCompetitor(3, "Ivan");
        this.olympics.addCompetitor(4, "Stamat");
        this.olympics.addCompetitor(5, "Georgi");
        this.olympics.addCompetitor(7, "Galin");
        this.olympics.addCompetitor(8, "Mariika");

        int expectedCount = 4;
        int actualCount = 0;

        for (Competitor competitor : this.olympics.searchWithNameLength(3, 3)) {
            actualCount++;
        }
        Assert.assertEquals("Min value must be inclusive", expectedCount, actualCount);
    }

    @Test
    public void searchWithNameLength_max_value_inclusive() {
        this.olympics.addCompetitor(1, "Ani");
        this.olympics.addCompetitor(10, "Ani");
        this.olympics.addCompetitor(6, "Ivo");
        this.olympics.addCompetitor(9, "Asd");
        this.olympics.addCompetitor(2, "Georgi");
        this.olympics.addCompetitor(3, "Ivan");
        this.olympics.addCompetitor(4, "Stamat");
        this.olympics.addCompetitor(5, "Georgi");
        this.olympics.addCompetitor(7, "Galin");
        this.olympics.addCompetitor(8, "Mariika");

        int expectedCount = 10;
        int actualCount = 0;

        for (Competitor competitor : this.olympics.searchWithNameLength(3, 7)) {
            actualCount++;
        }
        Assert.assertEquals("Max value must be inclusive", expectedCount, actualCount);
    }

    @Test
    public void searchWithNameLength_correct_order() {
        this.olympics.addCompetitor(1, "Ani");
        this.olympics.addCompetitor(10, "Ani");
        this.olympics.addCompetitor(6, "Ivo");
        this.olympics.addCompetitor(9, "Asd");
        this.olympics.addCompetitor(2, "Georgi");
        this.olympics.addCompetitor(3, "Ivan");
        this.olympics.addCompetitor(4, "Stamat");
        this.olympics.addCompetitor(5, "Georgi");
        this.olympics.addCompetitor(7, "Galin");
        this.olympics.addCompetitor(8, "Mariika");

        final int[] ids = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        boolean isCorrectOrder = true;

        Iterator<Competitor> iterator = this.olympics.searchWithNameLength(3, 7).iterator();

        for (int i = 0; i < ids.length && iterator.hasNext(); i++) {
            if (iterator.next().getId() != ids[i]) {
                isCorrectOrder = false;
                break;
            }
        }

        Assert.assertTrue("Incorrect order of competitors", isCorrectOrder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void contains_should_throw_exception_with_invalid_id() {
        this.olympics.addCompetition(1, "SoftUniada", 500);
        Competitor competitor = new Competitor(5, "Ani");
        this.olympics.addCompetitor(5, "Ani");

        this.olympics.contains(2, competitor);
    }

    @Test
    public void contains_should_return_true_with_valid_data() {
        this.olympics.addCompetition(1, "SoftUniada", 500);
        Competitor competitor = new Competitor(5, "Ani");
        this.olympics.addCompetitor(5, "Ani");

        this.olympics.contains(1, competitor);
    }

    @Test
    public void contains_should_return_false_with_invalid_data() {
        this.olympics.addCompetition(1, "SoftUniada", 500);
        Competitor competitor = new Competitor(5, "Gosho");
        this.olympics.addCompetitor(5, "Ani");

        this.olympics.contains(1, competitor);
    }
}