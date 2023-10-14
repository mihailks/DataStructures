import java.util.*;
import java.util.stream.Collectors;

public class OlympicsImpl implements Olympics {

    private Map<Integer, Competitor> competitors;
    private Map<Integer, Competition> competitions;

    public OlympicsImpl() {
        this.competitors = new HashMap<>();
        this.competitions = new HashMap<>();
    }


    @Override
    public void addCompetitor(int id, String name) {
        if (competitors.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        competitors.put(id, new Competitor(id, name));
    }

    @Override
    public void addCompetition(int id, String name, int score) {
        if (competitions.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        competitions.put(id, new Competition(name, id, score));
    }

    @Override
    public void compete(int competitorId, int competitionId) {
        if (!competitors.containsKey(competitorId) || !competitions.containsKey(competitionId)) {
            throw new IllegalArgumentException();
        }
        int currentCompetitionScore = competitions.get(competitionId).getScore();
        Competitor currentCompetitor = competitors.get(competitorId);
        currentCompetitor.setTotalScore(currentCompetitor.getTotalScore() + currentCompetitionScore);
        competitions.get(competitionId).getCompetitors().add(currentCompetitor);
    }

    @Override
    public void disqualify(int competitionId, int competitorId) {
        if (!competitors.containsKey(competitorId) || !competitions.containsKey(competitionId)) {
            throw new IllegalArgumentException();
        }
        int currentCompetitionScore = competitions.get(competitionId).getScore();
        Competitor currentCompetitor = competitors.get(competitorId);
        if (!competitions.get(competitionId).getCompetitors().contains(currentCompetitor)) {
            throw new IllegalArgumentException();
        }
        currentCompetitor.setTotalScore(currentCompetitor.getTotalScore() - currentCompetitionScore);
        competitions.get(competitionId).getCompetitors().remove(currentCompetitor);
//        competitors.remove(competitorId);
    }

    @Override
    public Iterable<Competitor> findCompetitorsInRange(long min, long max) {
        return competitors.values()
                .stream()
                .filter(c -> c.getTotalScore() > min && c.getTotalScore() <= max)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Competitor> getByName(String name) {
        List<Competitor> competitorsByName;
        competitorsByName = competitors.values()
                .stream()
                .filter(c -> c.getName().equals(name))
                .collect(Collectors.toList());

        if (competitorsByName.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return competitorsByName.stream()
                .sorted(Comparator.comparing(Competitor::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Competitor> searchWithNameLength(int minLength, int maxLength) {
        return competitors.values()
                .stream()
                .filter(c -> c.getName().length() >= minLength && c.getName().length() <= maxLength)
                .sorted(Comparator.comparing(Competitor::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean contains(int competitionId, Competitor comp) {
        if (competitions.get(competitionId) == null) {
            throw new IllegalArgumentException();
        }
        return competitions.get(competitionId).getCompetitors().contains(comp);
    }

    @Override
    public int competitionsCount() {
        return competitions.size();
    }

    @Override
    public int competitorsCount() {
        return competitors.size();
    }

    @Override
    public Competition getCompetition(int id) {
        if (!competitions.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        return competitions.get(id);
    }
}
