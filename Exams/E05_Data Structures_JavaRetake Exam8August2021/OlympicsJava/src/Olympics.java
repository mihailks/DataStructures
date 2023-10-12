public interface Olympics {

    void addCompetitor(int id, String name);

    void addCompetition(int id, String name, int score);

    void compete(int competitorId, int competitionId);

    void disqualify(int competitionId, int competitorId);

    Iterable<Competitor> findCompetitorsInRange(long min, long max);

    Iterable<Competitor> getByName(String name);

    Iterable<Competitor> searchWithNameLength(int minLength, int maxLength);

    Boolean contains(int competitionId, Competitor comp);

    int competitionsCount();

    int competitorsCount();

    Competition getCompetition(int id);
}
