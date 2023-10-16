package raceManager.core;

import com.sun.source.tree.AssertTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import raceManager.models.Athlete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class RaceManagerTests {

    private RaceManger raceManager;

    private Athlete athlete = new Athlete("First", 20);

    @Before
    public void setup() {
        raceManager = new RaceMangerImpl();
    }

    @Test
    public void enroll() {
        raceManager.enroll(athlete);

        assertTrue(raceManager.isEnrolled(athlete));
    }

    @Test
    public void enrollMultipleAthletes() {
        List<Athlete> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Athlete current = new Athlete("Athlete " + i, i);

            list.add(current);
        }

        for (Athlete athlete1 : list) {
            raceManager.enroll(athlete1);
        }

        for (Athlete athlete1 : list) {
            assertTrue(raceManager.isEnrolled(athlete1));
        }

        assertFalse(raceManager.isEnrolled(athlete));
    }

    @Test
    public void retireRunningAthlete() {
        raceManager.enroll(athlete);
        raceManager.start();

        raceManager.retire(athlete);

        assertEquals(0, raceManager.currentRacingCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void finishNotEnrolledAthlete() {
        raceManager.finish(athlete);
    }

    @Test
    public void getLastFinishedAthlete() {
        raceManager.enroll(athlete);
        raceManager.start();
        raceManager.finish(athlete);

        assertEquals(athlete, raceManager.getLastFinishedAthlete());
    }

    @Test
    public void getAllNonFinishedAthletesWithEmptyManager() {
        Collection<Athlete> allAthletesByAge = raceManager.getAllNotFinishedAthletes();

        assertTrue(allAthletesByAge.isEmpty());
    }

    @Test
    public void getLastFinishedAthleteWithMultiple() {

        Athlete athlete1 = new Athlete("First", 20);
        Athlete athlete2 = new Athlete("Second", 21);
        Athlete athlete3 = new Athlete("third", 21);
        Athlete athlete4 = new Athlete("four", 21);
        Athlete athlete5 = new Athlete("five", 21);

        raceManager.enroll(athlete1);
        raceManager.enroll(athlete2);
        raceManager.enroll(athlete3);
        raceManager.enroll(athlete4);
        raceManager.enroll(athlete5);
        raceManager.start();
        raceManager.start();
        raceManager.start();
        raceManager.start();
        raceManager.start();
        raceManager.finish(athlete1);
        raceManager.finish(athlete2);
        raceManager.finish(athlete3);
        raceManager.finish(athlete4);
        raceManager.finish(athlete5);

        Iterator<Athlete> scoreBoard = raceManager.getScoreBoard();
    }

    @Test
    public void testAllNotFinished() {

        Athlete athlete1 = new Athlete("First", 20);
        Athlete athlete2 = new Athlete("Second", 21);
        Athlete athlete3 = new Athlete("third", 21);
        Athlete athlete4 = new Athlete("four", 21);
        Athlete athlete5 = new Athlete("Wfive", 21);

        raceManager.enroll(athlete1);
        raceManager.enroll(athlete2);
        raceManager.enroll(athlete3);
        raceManager.enroll(athlete4);
        raceManager.enroll(athlete5);
        raceManager.start();
        raceManager.start();
        raceManager.start();
        raceManager.start();
        raceManager.finish(athlete1);
        raceManager.retire(athlete2);
        raceManager.finish(athlete4);

        Collection<Athlete> allNotFinishedAthletes = raceManager.getAllNotFinishedAthletes();
    }

    @Test
    public void testLastFinished() {

        Athlete athlete1 = new Athlete("First", 20);
        Athlete athlete2 = new Athlete("Second", 21);
        Athlete athlete3 = new Athlete("third", 21);
        Athlete athlete4 = new Athlete("four", 21);
        Athlete athlete5 = new Athlete("Wfive", 21);

        raceManager.enroll(athlete1);
        raceManager.enroll(athlete2);
        raceManager.enroll(athlete3);
        raceManager.enroll(athlete4);
        raceManager.enroll(athlete5);
        raceManager.start();
        raceManager.start();
        raceManager.start();
        raceManager.start();
        raceManager.start();
        raceManager.finish(athlete1);
        raceManager.finish(athlete5);
        raceManager.finish(athlete2);
        raceManager.finish(athlete3);
        raceManager.finish(athlete4);

        assertEquals(athlete4, raceManager.getLastFinishedAthlete());
    }

    @Test
    public void allByAge() {

        Athlete athlete1 = new Athlete("First", 30);
        Athlete athlete2 = new Athlete("Second", 35);
        Athlete athlete3 = new Athlete("third", 22);
        Athlete athlete4 = new Athlete("four", 15);
        Athlete athlete5 = new Athlete("Wfive", 77);

        raceManager.enroll(athlete1);
        raceManager.enroll(athlete2);
        raceManager.enroll(athlete3);
        raceManager.enroll(athlete4);
        raceManager.enroll(athlete5);
        raceManager.start();
        raceManager.start();
        raceManager.start();
        raceManager.start();
        raceManager.start();
        raceManager.finish(athlete1);
        raceManager.finish(athlete5);
        raceManager.finish(athlete2);
        raceManager.finish(athlete3);
        raceManager.finish(athlete4);

        Collection<Athlete> allAthletesByAge = raceManager.getAllAthletesByAge();
    }

    @Test
    public void testCurrentRaceCount() {

        Athlete athlete1 = new Athlete("First", 30);
        Athlete athlete2 = new Athlete("Second", 35);
        Athlete athlete3 = new Athlete("third", 22);
        Athlete athlete4 = new Athlete("four", 15);
        Athlete athlete5 = new Athlete("Wfive", 77);

        raceManager.enroll(athlete1);
        raceManager.enroll(athlete2);
        raceManager.enroll(athlete3);
        raceManager.enroll(athlete4);
        raceManager.enroll(athlete5);
        raceManager.start();
        raceManager.start();
        raceManager.start();
        raceManager.start();
        raceManager.start();
        raceManager.finish(athlete1);
        raceManager.finish(athlete5);


        assertEquals(3, raceManager.currentRacingCount());
    }

    @Test
    public void testEmptyStart() {

        Athlete athlete1 = new Athlete("First", 30);
        Athlete athlete2 = new Athlete("Second", 35);
        Athlete athlete3 = new Athlete("third", 22);
        Athlete athlete4 = new Athlete("four", 15);
        Athlete athlete5 = new Athlete("Wfive", 77);

        raceManager.enroll(athlete1);
        raceManager.enroll(athlete2);
        raceManager.enroll(athlete3);
        raceManager.enroll(athlete4);
        raceManager.enroll(athlete5);



        assertEquals(0, raceManager.currentRacingCount());
    }

    @Test
    public void testAllFinishRaceCount() {

        Athlete athlete1 = new Athlete("First", 30);
        Athlete athlete2 = new Athlete("Second", 35);
        Athlete athlete3 = new Athlete("third", 22);
        Athlete athlete4 = new Athlete("four", 15);
        Athlete athlete5 = new Athlete("Wfive", 77);

        raceManager.enroll(athlete1);
        raceManager.enroll(athlete2);
        raceManager.enroll(athlete3);
        raceManager.enroll(athlete4);
        raceManager.enroll(athlete5);
        raceManager.start();
        raceManager.start();
        raceManager.start();
        raceManager.start();
        raceManager.start();
        raceManager.finish(athlete1);
        raceManager.finish(athlete2);
        raceManager.finish(athlete3);
        raceManager.finish(athlete4);
        raceManager.finish(athlete5);


        assertEquals(0, raceManager.currentRacingCount());
    }


}