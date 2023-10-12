
public class OlympicsImpl implements Olympics {

    public OlympicsImpl() {

    }


    @Override
    public void addCompetitor(int id, String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addCompetition(int id, String name, int score) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void compete(int competitorId, int competitionId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void disqualify(int competitionId, int competitorId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Competitor> findCompetitorsInRange(long min, long max) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Competitor> getByName(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Competitor> searchWithNameLength(int minLength, int maxLength) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean contains(int competitionId, Competitor comp) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int competitionsCount() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int competitorsCount() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Competition getCompetition(int id) {
        throw new UnsupportedOperationException();
    }
}
