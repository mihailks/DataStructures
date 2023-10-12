import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class OlympicsImplPerformanceTest {

    private Olympics olympics;
    private InputGenerator inputGenerator;

    class InputGenerator {

        private final String[] COMPETITOR_NAMES = {"Ani", "Ani", "Ivo", "Asd", "Georgi", "Ivan", "Stamat", "Georgi", "Galin", "Mariika", "Ani", "Ani", "Ivo", "Asd", "Georgi", "Ivan", "Stamat", "Georgi", "Galin", "Mariika", "Ani", "Ani", "Ivo", "Asd", "Georgi", "Ivan", "Stamat", "Georgi", "Galin", "Mariika"};
        private final String[] COMPETITION_NAMES = {"Java", "VS", "SoftUniada", "CDiez", "Oracle", "JavaScript", "PHP", "Pascal", "C", "Swift", "Java", "VS", "SoftUniada", "CDiez", "Oracle", "JavaScript", "PHP", "Pascal", "C", "Swift", "Java", "VS", "SoftUniada", "CDiez", "Oracle", "JavaScript", "PHP", "Pascal", "C", "Swift", "Java", "VS", "SoftUniada", "CDiez", "Oracle", "JavaScript", "PHP", "Pascal", "C", "SwiftJava", "VS", "SoftUniada", "CDiez", "Oracle", "JavaScript", "PHP", "Pascal", "C", "SwiftJava", "VS", "SoftUniada", "CDiez", "Oracle", "JavaScript", "PHP", "Pascal", "C", "Swift"};


        List<Competitor> generateCompetitors(int count) {
            List<Competitor> competitors = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                competitors.add(new Competitor(i, COMPETITOR_NAMES[i % COMPETITOR_NAMES.length]));
            }
            return competitors;
        }

        List<Competition> generateCompetitions(int count) {
            List<Competition> competitions = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                competitions.add(new Competition(COMPETITION_NAMES[i % COMPETITION_NAMES.length], i, 5 + i));
            }
            return competitions;
        }
    }

    @Before
    public void setUp() {
        this.olympics = new OlympicsImpl();
        this.inputGenerator = new InputGenerator();
    }

    @Test(timeout = 100)
    public void addCompetitor_10_000_competitors() {

        int initialCount = 10000;

        List<Competitor> competitors = this.inputGenerator.generateCompetitors(initialCount);

        long start = System.currentTimeMillis();

        for (Competitor competitor : competitors) {
            this.olympics.addCompetitor(competitor.getId(), competitor.getName());
        }

        long stop = System.currentTimeMillis();

        long executionTimeInMillis = stop - start;
        Assert.assertTrue(executionTimeInMillis <= 15);
        Assert.assertEquals("Invalid competitors count", this.olympics.competitorsCount(), initialCount);

    }

    @Test(timeout = 1000)
    public void addCompetitor_1_000_000_competitors() {

        int initialCount = 1000000;
        List<Competitor> competitors = this.inputGenerator.generateCompetitors(initialCount);

        long start = System.currentTimeMillis();

        for (Competitor competitor : competitors) {
            this.olympics.addCompetitor(competitor.getId(), competitor.getName());
        }

        long stop = System.currentTimeMillis();


        long executionTimeInMillis = stop - start;

        Assert.assertTrue(executionTimeInMillis <= 550);
        Assert.assertEquals("Invalid competitors count", this.olympics.competitorsCount(), initialCount);

    }

    @Test(timeout = 1000)
    public void addCompetitor_1_000_000_competitors_memory_usage() {

        List<Competitor> competitors = this.inputGenerator.generateCompetitors(1000000);

        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

        for (Competitor competitor : competitors) {
            this.olympics.addCompetitor(competitor.getId(), competitor.getName());
        }
        long usedMemoryInMB = (runtime.totalMemory() - runtime.freeMemory() - usedMemoryBefore) / (1024 * 1024);

        Assert.assertTrue(usedMemoryInMB > 100 && usedMemoryInMB < 200);

    }

    @Test(timeout = 100)
    public void addCompetition_10_000_competitions() {

        int initialCount = 10000;

        List<Competition> competitions = this.inputGenerator.generateCompetitions(initialCount);

        long start = System.currentTimeMillis();

        for (Competition competition : competitions) {
            this.olympics.addCompetition(competition.getId(), competition.getName(), competition.getScore());
        }

        long stop = System.currentTimeMillis();

        long executionTimeInMillis = stop - start;
        Assert.assertTrue(executionTimeInMillis <= 5);
        Assert.assertEquals("Invalid competitors count", this.olympics.competitionsCount(), initialCount);

    }

    @Test(timeout = 500)
    public void addCompetition_1_000_000_competitions() {

        int initialCount = 1000000;
        List<Competition> competitions = this.inputGenerator.generateCompetitions(initialCount);

        long start = System.currentTimeMillis();

        for (Competition competition : competitions) {
            this.olympics.addCompetition(competition.getId(), competition.getName(), competition.getScore());
        }

        long stop = System.currentTimeMillis();


        long executionTimeInMillis = stop - start;

        Assert.assertTrue(executionTimeInMillis <= 250);
        Assert.assertEquals("Invalid competitors count", this.olympics.competitionsCount(), initialCount);

    }

    @Test(timeout = 1000)
    public void addCompetition_1_000_000_competitions_memory_usage() {

        List<Competition> competitions = this.inputGenerator.generateCompetitions(1000000);

        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

        for (Competition competition : competitions) {
            this.olympics.addCompetition(competition.getId(), competition.getName(), competition.getScore());
        }

        long usedMemoryInMB = (runtime.totalMemory() - runtime.freeMemory() - usedMemoryBefore) / (1024 * 1024);

        Assert.assertTrue(usedMemoryInMB > 100 && usedMemoryInMB < 200);

    }


    @Test(timeout = 100)
    public void compete_10_000_competitors_with_competitions() {
        int initialCount = 10000;

        List<Competition> competitions = this.inputGenerator.generateCompetitions(initialCount);
        List<Competitor> competitors = this.inputGenerator.generateCompetitors(initialCount);

        competitions.forEach(c -> this.olympics.addCompetition(c.getId(), c.getName(), c.getScore()));
        competitors.forEach(c -> this.olympics.addCompetitor(c.getId(), c.getName()));

        long start = System.currentTimeMillis();

        for (int i = 0; i < initialCount; i++) {
            this.olympics.compete(competitors.get(i).getId(), competitions.get(i).getId());
        }

        long stop = System.currentTimeMillis();


        long executionTimeInMillis = stop - start;

        Assert.assertTrue(executionTimeInMillis <= 12);
        int count = 0;
        for (Competition c : competitions) {
            count += this.olympics.getCompetition(c.getId()).getCompetitors().size();
        }
        Assert.assertEquals("Invalid competitors count", count, initialCount);
    }

    @Test(timeout = 1500)
    public void compete_1_000_000_competitors_with_competitions() {
        int initialCount = 1000000;
        List<Competition> competitions = this.inputGenerator.generateCompetitions(initialCount);
        List<Competitor> competitors = this.inputGenerator.generateCompetitors(initialCount);

        competitions.forEach(c -> this.olympics.addCompetition(c.getId(), c.getName(), c.getScore()));
        competitors.forEach(c -> this.olympics.addCompetitor(c.getId(), c.getName()));

        long start = System.currentTimeMillis();

        for (int i = 0; i < initialCount; i++) {
            this.olympics.compete(competitors.get(i).getId(), competitions.get(i).getId());
        }

        long stop = System.currentTimeMillis();


        long executionTimeInMillis = stop - start;

        Assert.assertTrue(executionTimeInMillis <= 300);
        int count = 0;
        for (Competition c : competitions) {
            count += this.olympics.getCompetition(c.getId()).getCompetitors().size();
        }
        Assert.assertEquals("Invalid competitors count", count, initialCount);

    }

    @Test(timeout = 2000)
    public void compete_10_000_competitors_with_1_000_competitions() {
        int initialCompetitorsCount = 10000;
        int initialCompetitionsCount = 1000;

        List<Competition> competitions = this.inputGenerator.generateCompetitions(initialCompetitionsCount);
        List<Competitor> competitors = this.inputGenerator.generateCompetitors(initialCompetitorsCount);

        competitions.forEach(c -> this.olympics.addCompetition(c.getId(), c.getName(), c.getScore()));
        competitors.forEach(c -> this.olympics.addCompetitor(c.getId(), c.getName()));

        long start = System.currentTimeMillis();

        for (Competition competition : competitions) {
            for (Competitor competitor : competitors) {
                this.olympics.compete(competitor.getId(), competition.getId());
            }
        }

        long stop = System.currentTimeMillis();


        long executionTimeInMillis = stop - start;

        Assert.assertTrue(executionTimeInMillis <= 1500);
        int count = 0;
        for (Competition c : competitions) {
            count += this.olympics.getCompetition(c.getId()).getCompetitors().size();
        }
        Assert.assertEquals("Invalid competitors count", count, initialCompetitorsCount * initialCompetitionsCount);
    }


    @Test(timeout = 1000)
    public void disqualify_10_000_competitors_with_1_000_competitions() {
        int initialCompetitorsCount = 10000;
        int initialCompetitionsCount = 1000;

        List<Competition> competitions = this.inputGenerator.generateCompetitions(initialCompetitionsCount);
        List<Competitor> competitors = this.inputGenerator.generateCompetitors(initialCompetitorsCount);

        competitions.forEach(c -> this.olympics.addCompetition(c.getId(), c.getName(), c.getScore()));
        competitors.forEach(c -> this.olympics.addCompetitor(c.getId(), c.getName()));

        for (Competition competition : competitions) {
            for (Competitor competitor : competitors) {
                this.olympics.compete(competitor.getId(), competition.getId());
            }
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            this.olympics.disqualify(competitors.get(i).getId(), competitions.get(i).getId());
        }
        long stop = System.currentTimeMillis();


        long executionTimeInMillis = stop - start;

        Assert.assertTrue(executionTimeInMillis <= 2);
        int count = 0;
        for (Competition c : competitions) {
            count += this.olympics.getCompetition(c.getId()).getCompetitors().size();
        }
        Assert.assertEquals("Invalid competitors count", count, 9999000);

    }

    @Test(timeout = 100)
    public void disqualify_10_000_competitors_with_competitions() {
        int initialCount = 10000;
        List<Competition> competitions = this.inputGenerator.generateCompetitions(initialCount);
        List<Competitor> competitors = this.inputGenerator.generateCompetitors(initialCount);

        competitions.forEach(c -> this.olympics.addCompetition(c.getId(), c.getName(), c.getScore()));
        competitors.forEach(c -> this.olympics.addCompetitor(c.getId(), c.getName()));

        for (int i = 0; i < initialCount; i++) {
            this.olympics.compete(competitors.get(i).getId(), competitions.get(i).getId());
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < initialCount; i++) {
            this.olympics.disqualify(competitors.get(i).getId(), competitions.get(i).getId());
        }

        long stop = System.currentTimeMillis();


        long executionTimeInMillis = stop - start;

        Assert.assertTrue(executionTimeInMillis <= 10);
        int count = 0;
        for (Competition c : competitions) {
            count += this.olympics.getCompetition(c.getId()).getCompetitors().size();
        }
        Assert.assertEquals("Invalid competitors count", count, 0);

    }

    @Test(timeout = 1500)
    public void disqualify_1_000_000_competitors_with_competitions() {
        int initialCount = 1000000;

        List<Competition> competitions = this.inputGenerator.generateCompetitions(initialCount);
        List<Competitor> competitors = this.inputGenerator.generateCompetitors(initialCount);

        competitions.forEach(c -> this.olympics.addCompetition(c.getId(), c.getName(), c.getScore()));
        competitors.forEach(c -> this.olympics.addCompetitor(c.getId(), c.getName()));

        for (int i = 0; i < initialCount; i++) {
            this.olympics.compete(competitors.get(i).getId(), competitions.get(i).getId());
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < initialCount; i++) {
            this.olympics.disqualify(competitors.get(i).getId(), competitions.get(i).getId());
        }

        long stop = System.currentTimeMillis();

        long executionTimeInMillis = stop - start;


        Assert.assertTrue(executionTimeInMillis <= 210);
        int count = 0;
        for (Competition c : competitions) {
            count += this.olympics.getCompetition(c.getId()).getCompetitors().size();
        }
        Assert.assertEquals("Invalid competitors count", count, 0);
    }

    @Test(timeout = 100)
    public void getByName_10_000_competitors() {

        int initialCount = 10000;

        List<Competitor> competitors = this.inputGenerator.generateCompetitors(initialCount);

        final String name = "Ani";

        int aniCount = 0;

        for (Competitor competitor : competitors) {
            this.olympics.addCompetitor(competitor.getId(), competitor.getName());
            if (name.equals(competitor.getName())) {
                aniCount++;
            }
        }

        long start = System.currentTimeMillis();
        Iterable<Competitor> byName = this.olympics.getByName(name);
        long stop = System.currentTimeMillis();

        int countByName = Long.valueOf(StreamSupport.stream(byName.spliterator(), false).count()).intValue();

        // todo assert count

        long executionTimeInMillis = stop - start;
        Assert.assertTrue(executionTimeInMillis <= 2);
        Assert.assertEquals("Invalid competitors count", aniCount, countByName);

    }

    @Test(timeout = 1000)
    public void getByName_1_000_000_competitors() {

        int initialCount = 1000000;

        List<Competitor> competitors = this.inputGenerator.generateCompetitors(initialCount);

        final String name = "Ani";

        int aniCount = 0;

        for (Competitor competitor : competitors) {
            this.olympics.addCompetitor(competitor.getId(), competitor.getName());
            if (name.equals(competitor.getName())) {
                aniCount++;
            }
        }

        long start = System.currentTimeMillis();
        Iterable<Competitor> byName = this.olympics.getByName(name);
        long stop = System.currentTimeMillis();

        int countByName = Long.valueOf(StreamSupport.stream(byName.spliterator(), false).count()).intValue();
        long executionTimeInMillis = stop - start;

        Assert.assertTrue(executionTimeInMillis <= 30);
        Assert.assertEquals("Invalid competitors count", aniCount, countByName);

    }

    @Test(timeout = 100)
    public void findCompetitorsInRange_10_000_competitors() {

        int initialCount = 10000;
        List<Competition> competitions = this.inputGenerator.generateCompetitions(initialCount);
        List<Competitor> competitors = this.inputGenerator.generateCompetitors(initialCount);

        competitions.forEach(c -> this.olympics.addCompetition(c.getId(), c.getName(), c.getScore()));
        competitors.forEach(c -> this.olympics.addCompetitor(c.getId(), c.getName()));

        long totalScore = 0;

        for (int i = 0; i < initialCount; i++) {
            Competition competition = competitions.get(i);
            totalScore += competition.getScore();
            this.olympics.compete(competitors.get(i).getId(), competitions.get(i).getId());
        }

        long start = System.currentTimeMillis();

        Iterable<Competitor> competitorsInRange = this.olympics.findCompetitorsInRange(0, totalScore);

        long stop = System.currentTimeMillis();

        int countCompetitorsInRange = Long.valueOf(StreamSupport.stream(competitorsInRange.spliterator(), false).count()).intValue();

        long executionTimeInMillis = stop - start;

        Assert.assertTrue(executionTimeInMillis <= 10);
        Assert.assertEquals("Invalid competitors count", initialCount, countCompetitorsInRange);

    }

    @Test(timeout = 1500)
    public void findCompetitorsInRange_1_000_000_competitors() {

        int initialCount = 1000000;
        List<Competition> competitions = this.inputGenerator.generateCompetitions(initialCount);
        List<Competitor> competitors = this.inputGenerator.generateCompetitors(initialCount);

        competitions.forEach(c -> this.olympics.addCompetition(c.getId(), c.getName(), c.getScore()));
        competitors.forEach(c -> this.olympics.addCompetitor(c.getId(), c.getName()));

        long totalScore = 0;

        for (int i = 0; i < initialCount; i++) {
            Competition competition = competitions.get(i);
            totalScore += competition.getScore();
            this.olympics.compete(competitors.get(i).getId(), competitions.get(i).getId());
        }

        long start = System.currentTimeMillis();

        Iterable<Competitor> competitorsInRange = this.olympics.findCompetitorsInRange(0, totalScore);

        long stop = System.currentTimeMillis();

        int countCompetitorsInRange = Long.valueOf(StreamSupport.stream(competitorsInRange.spliterator(), false).count()).intValue();

        long executionTimeInMillis = stop - start;


        Assert.assertTrue(executionTimeInMillis <= 8);
        Assert.assertEquals("Invalid competitors count", initialCount, countCompetitorsInRange);

    }

    @Test(timeout = 100)
    public void searchWithNameLength_10_000_competitors() {

        int initialCount = 10000;

        List<Competitor> competitors = this.inputGenerator.generateCompetitors(initialCount);
        int countNames = 0;

        for (Competitor competitor : competitors) {
            this.olympics.addCompetitor(competitor.getId(), competitor.getName());
            if (competitor.getName().length() == 3) {
                countNames++;
            }
        }
        long start = System.currentTimeMillis();
        Iterable<Competitor> competitorsByNameLength = this.olympics.searchWithNameLength(3, 3);
        long stop = System.currentTimeMillis();
        long executionTimeInMillis = stop - start;

        int countCompetitorsInRange = Long.valueOf(StreamSupport.stream(competitorsByNameLength.spliterator(), false).count()).intValue();

        Assert.assertTrue(executionTimeInMillis <= 30);
        Assert.assertEquals("Invalid competitors count", countNames, countCompetitorsInRange);
    }

    @Test(timeout = 1500)
    public void searchWithNameLength_1_000_000_competitors() {

        int initialCount = 1000000;

        List<Competitor> competitors = this.inputGenerator.generateCompetitors(initialCount);

        int countNames = 0;

        for (Competitor competitor : competitors) {
            this.olympics.addCompetitor(competitor.getId(), competitor.getName());
            if (competitor.getName().length() == 3) {
                countNames++;
            }
        }
        long start = System.currentTimeMillis();
        Iterable<Competitor> competitorsByNameLength = this.olympics.searchWithNameLength(3, 3);
        long stop = System.currentTimeMillis();
        long executionTimeInMillis = stop - start;

        int countCompetitorsInRange = Long.valueOf(StreamSupport.stream(competitorsByNameLength.spliterator(), false).count()).intValue();

        Assert.assertTrue(executionTimeInMillis <= 110);
        Assert.assertEquals("Invalid competitors count", countNames, countCompetitorsInRange);
    }

    @Test(timeout = 100)
    public void contains_10_000_competitors_with_competitions() {
        int initialCount = 10000;
        List<Competition> competitions = this.inputGenerator.generateCompetitions(1);
        List<Competitor> competitors = this.inputGenerator.generateCompetitors(initialCount);

        competitions.forEach(c -> this.olympics.addCompetition(c.getId(), c.getName(), c.getScore()));
        competitors.forEach(c -> this.olympics.addCompetitor(c.getId(), c.getName()));

        for (int i = 0; i < initialCount; i++) {
            this.olympics.compete(competitors.get(i).getId(), 1);
        }

        long start = System.currentTimeMillis();

        for (Competitor competitor : competitors) {
            boolean contains = this.olympics.contains(1, competitor);
            Assert.assertTrue(contains);
        }

        long stop = System.currentTimeMillis();
        long executionTimeInMillis = stop - start;

        Assert.assertTrue(executionTimeInMillis <= 7);

    }

    @Test(timeout = 1500)
    public void contains_1_000_000_competitors_with_competitions() {
        int initialCount = 1000000;
        List<Competition> competitions = this.inputGenerator.generateCompetitions(1);
        List<Competitor> competitors = this.inputGenerator.generateCompetitors(initialCount);

        competitions.forEach(c -> this.olympics.addCompetition(c.getId(), c.getName(), c.getScore()));
        competitors.forEach(c -> this.olympics.addCompetitor(c.getId(), c.getName()));

        for (int i = 0; i < initialCount; i++) {
            this.olympics.compete(competitors.get(i).getId(), 1);
        }

        long start = System.currentTimeMillis();

        for (Competitor competitor : competitors) {
            boolean contains = this.olympics.contains(1, competitor);
            Assert.assertTrue(contains);
        }

        long stop = System.currentTimeMillis();
        long executionTimeInMillis = stop - start;
        Assert.assertTrue(executionTimeInMillis <= 110);
    }
}