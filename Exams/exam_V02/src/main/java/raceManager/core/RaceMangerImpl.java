package raceManager.core;

import raceManager.models.Athlete;

import java.util.*;
import java.util.stream.Collectors;

public class RaceMangerImpl implements RaceManger {
    Athlete lastToFinish = null;

    Set<Athlete> allAthlete;
    Deque<Athlete> athletesWhoDidNotStart;
    List<Athlete> startedAthletes;
    Set<Athlete> retiredAthletes;
    Deque<Athlete> finishedAthletes;

    public RaceMangerImpl() {
        allAthlete = new HashSet<>();
        startedAthletes = new ArrayList<>();
        retiredAthletes = new HashSet<>();
        finishedAthletes = new ArrayDeque<>();
        athletesWhoDidNotStart = new ArrayDeque<>();
    }

    @Override
    public void enroll(Athlete athlete) {
        if (allAthlete.contains(athlete)) {
            throw new IllegalArgumentException();
        }
        allAthlete.add(athlete);
        athletesWhoDidNotStart.offer(athlete);
    }

    @Override
    public boolean isEnrolled(Athlete athlete) {
        return allAthlete.contains(athlete);
    }

    @Override
    public void start() {
        if (athletesWhoDidNotStart.peek() == null) {
            throw new IllegalArgumentException();
        }
        startedAthletes.add(athletesWhoDidNotStart.poll());
    }

    @Override
    public void retire(Athlete athlete) {
        if (!startedAthletes.contains(athlete)) {
            throw new IllegalArgumentException();
        }

        startedAthletes.remove(athlete);

        retiredAthletes.add(athlete);

        athlete.hasFinished = true;
        lastToFinish = athlete;
    }

    @Override
    public void finish(Athlete athlete) {
        if (!startedAthletes.contains(athlete)) {
            throw new IllegalArgumentException();
        }
        finishedAthletes.push(athlete);
        startedAthletes.remove(athlete);
        athlete.hasFinished = true;
        lastToFinish = athlete;
    }

    @Override
    public Athlete getLastFinishedAthlete() {
        if (lastToFinish == null) {
            throw new IllegalArgumentException();
        }
        return lastToFinish;
    }

    @Override
    public int currentRacingCount() {
        return startedAthletes.size();
    }

    @Override
    public Collection<Athlete> getAllAthletesByAge() {
//        Set<Athlete> allAthletes = new HashSet<>();
//        allAthletes.addAll(race);
//        allAthletes.addAll(retiredAthletes);
        return allAthlete
                .stream()
                .sorted(Comparator.comparing(Athlete::getAge))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Athlete> getAllNotFinishedAthletes() {
//        Set<Athlete> notFinished = new HashSet<>();
//        notFinished.addAll(athletesWhoDidNotStart);
//        notFinished.addAll(retiredAthletes);
//        return notFinished
//                .stream()
//                .sorted(Comparator.comparing(Athlete::getName))
//                .collect(Collectors.toList());

        return allAthlete
                .stream()
                .filter(a -> a.hasFinished)
                .sorted(Comparator.comparing(Athlete::getName))
                .collect(Collectors.toList());
    }

    @Override
    public Iterator<Athlete> getScoreBoard() {
        return finishedAthletes.iterator();

//        Iterator<Athlete> iterator = new Iterator<Athlete>() {
//            int index = finishedAthletes.size() - 1;
//
//            @Override
//            public boolean hasNext() {
//                return index >= 0;
//            }
//
//            @Override
//            public Athlete next() {
//                return finishedAthletes.pop();
//            }
//        };
//        return iterator;
    }
}
