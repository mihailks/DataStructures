package org.softuni.exam;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.softuni.exam.entities.Actor;
import org.softuni.exam.entities.Movie;
import org.softuni.exam.structures.MovieDatabase;
import org.softuni.exam.structures.MovieDatabaseImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class MovieDatabaseTests {
    private interface InternalTest {
        void execute();
    }

    private MovieDatabase movieDatabase;

    private Actor getRandomActor() {
        return new Actor(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                (int) Math.min(1, Math.random() * 1_000_000_000));
    }

    private Movie getRandomMovie() {
        return new Movie(
                UUID.randomUUID().toString(),
                (int) Math.min(1, Math.random() * 1_000_000_000),
                UUID.randomUUID().toString(),
                Math.min(1, Math.random() * 1_000_000_000),
                Math.min(1, Math.random() * 1_000_000_000));
    }

    @Before
    public void setup() {
        this.movieDatabase = new MovieDatabaseImpl();
    }

    public void performCorrectnessTesting(InternalTest[] methods) {
        Arrays.stream(methods)
                .forEach(method -> {
                    this.movieDatabase = new MovieDatabaseImpl();;

                    try {
                        method.execute();
                    } catch (IllegalArgumentException ignored) { }
                });

        this.movieDatabase = new MovieDatabaseImpl();
    }

    // Correctness Tests

    @Test
    public void testContains_WithExistentActor_ShouldReturnTrue() {
        Actor actor = getRandomActor();
        this.movieDatabase.addActor(actor);

        assertTrue(this.movieDatabase.contains(actor));
    }

    @Test
    public void testContains_WithNonExistentActor_ShouldReturnFalse() {
        Actor actor = getRandomActor();
        this.movieDatabase.addActor(actor);

        assertFalse(this.movieDatabase.contains(getRandomActor()));
    }

    @Test
    public void testContains_WithExistentMovie_ShouldReturnTrue() {
        Actor actor = getRandomActor();
        this.movieDatabase.addActor(actor);
        Movie movie = getRandomMovie();
        this.movieDatabase.addMovie(actor, movie);

        assertTrue(this.movieDatabase.contains(movie));
    }

    @Test
    public void testContains_WithNonExistentMovie_ShouldReturnFalse() {
        Actor actor = getRandomActor();
        this.movieDatabase.addActor(actor);
        Movie movie = getRandomMovie();
        this.movieDatabase.addMovie(actor, movie);

        assertFalse(this.movieDatabase.contains(getRandomMovie()));
    }

    @Test
    public void testGetNewbieActors_WithData_ShouldReturnCorrectResults() {
        Actor actor = getRandomActor();
        this.movieDatabase.addActor(actor);
        Actor actor2 = getRandomActor();
        this.movieDatabase.addActor(actor2);
        Actor actor3 = getRandomActor();
        this.movieDatabase.addActor(actor3);
        Movie movie1 = getRandomMovie();
        Movie movie2 = getRandomMovie();

        this.movieDatabase.addMovie(actor, movie1);
        this.movieDatabase.addMovie(actor, movie2);

        Set<Actor> set =
                StreamSupport.stream(this.movieDatabase.getNewbieActors().spliterator(), false)
                        .collect(Collectors.toSet());

        assertEquals(set.size(), 2);
        assertTrue(set.contains(actor2));
        assertTrue(set.contains(actor3));
    }

    // Performance Tests

    @Test
    public void testContainsUser_With100000Results_ShouldPassQuickly() {
        this.performCorrectnessTesting(new InternalTest[] {
                this::testContains_WithExistentActor_ShouldReturnTrue,
                this::testContains_WithNonExistentActor_ShouldReturnFalse
        });

        int count = 100000;

        Actor actor = null;

        for (int i = 0; i < count; i++)
        {
            if(i == count / 2)  {
                actor = getRandomActor();
                this.movieDatabase.addActor(actor);
            } else {
                this.movieDatabase.addActor(getRandomActor());
            }

        }

        long start = System.currentTimeMillis();

        this.movieDatabase.contains(actor);

        long stop = System.currentTimeMillis();

        long elapsedTime = stop - start;

        assertTrue(elapsedTime <= 5);
    }

    @Test
    public void testFirstSort(){
        //getMoviesOrderedByBudgetThenByRating
        fillData();

        Iterable<Movie> moviesOrderedByBudgetThenByRating = movieDatabase.getMoviesOrderedByBudgetThenByRating();

    }

    private void fillData() {
        Actor actor0 = new Actor("0","test0",20);
        Actor actor1 = new Actor("1","test1",20);
        Actor actor2 = new Actor("2","test2",20);
        Actor actor3 = new Actor("3","test3",20);
        Actor actor4 = new Actor("4","test4",20);
        Actor actor5 = new Actor("5","test5",20);
        Actor actor6 = new Actor("6","test6",20);

        Movie movie0 = new Movie("0m", 100, "m0", 5.0, 100.25);
        Movie movie1 = new Movie("1m", 100, "m1", 5.1, 101.25);
        Movie movie2 = new Movie("2m", 100, "m2", 5.2, 105.25);
        Movie movie3 = new Movie("3m", 100, "m3", 5.3, 105.25);
        Movie movie4 = new Movie("4m", 100, "m4", 5.4, 104.25);
        Movie movie5 = new Movie("5m", 100, "m5", 5.5, 105.25);
        Movie movie6 = new Movie("6m", 100, "m6", 5.6, 106.25);

        movieDatabase.addActor(actor0);
        movieDatabase.addActor(actor1);
        movieDatabase.addActor(actor2);
        movieDatabase.addActor(actor3);
        movieDatabase.addActor(actor4);
        movieDatabase.addActor(actor5);
        movieDatabase.addActor(actor6);

        movieDatabase.addMovie(actor4,movie4);
        movieDatabase.addMovie(actor0,movie0);
        movieDatabase.addMovie(actor6,movie6);
        movieDatabase.addMovie(actor1,movie1);
        movieDatabase.addMovie(actor3,movie3);
        movieDatabase.addMovie(actor2,movie2);
        movieDatabase.addMovie(actor5,movie5);
    }

    @Test
    public void testGetMoviesOrderedByBudgetThenByRatingShouldReturnCorrectResult() {
        Actor actor = getRandomActor();
        Movie movie_1 = new Movie("1", 1, "1", 1.0, 1.0);
        Movie movie_2 = new Movie("2", 1, "2", 1.0, 100.0);
        Movie movie_3 = new Movie("3", 1, "3", 2.0, 100.0);
        Movie movie_4 = new Movie("4", 1, "4", 1.0, 1.0);
        Movie movie_5 = new Movie("5", 1, "5", 1.0, 100.0);
        Movie movie_6 = new Movie("6", 1, "6", 1.0, 10.0);

        this.movieDatabase.addActor(actor);
        this.movieDatabase.addMovie(actor, movie_1);
        this.movieDatabase.addMovie(actor, movie_2);
        this.movieDatabase.addMovie(actor, movie_3);
        this.movieDatabase.addMovie(actor, movie_4);
        this.movieDatabase.addMovie(actor, movie_5);
        this.movieDatabase.addMovie(actor, movie_6);

        String[] expected = {"3", "2", "5", "6", "1", "4"};
        Iterable<Movie> movieIterable = this.movieDatabase.getMoviesOrderedByBudgetThenByRating();
        List<Movie> movies = StreamSupport.stream(movieIterable.spliterator(), false).collect(Collectors.toList());
        int counter = 0;
        for (Movie movie : movies) {
            Assert.assertEquals(expected[counter++], movie.getId());
        }
    }

    @Test
    public void testGetMoviesOrderedByBudgetThenByRatingShouldReturnEmptyCollection() {
        Actor actor = getRandomActor();
        this.movieDatabase.addActor(actor);

        Iterable<Movie> movieIterable = this.movieDatabase.getMoviesOrderedByBudgetThenByRating();
        List<Movie> movies = StreamSupport.stream(movieIterable.spliterator(), false).collect(Collectors.toList());
        Assert.assertEquals(0, movies.size());
    }


    @Test
    public void testGetMoviesInRangeOfBudgetShouldReturnCorrectResult() {
        Actor actor = getRandomActor();
        Movie movie_1 = new Movie("1", 1, "1", 6.0, 100.0);
        Movie movie_2 = new Movie("2", 1, "2", 4.0, 50.0);
        Movie movie_3 = new Movie("3", 1, "3", 1.0, 100.0);
        Movie movie_4 = new Movie("4", 1, "4", 3.0, 10.0);
        Movie movie_5 = new Movie("5", 1, "5", 2.0, 50.0);
        Movie movie_6 = new Movie("6", 1, "6", 5.0, 10.0);

        this.movieDatabase.addActor(actor);
        this.movieDatabase.addMovie(actor, movie_1);
        this.movieDatabase.addMovie(actor, movie_2);
        this.movieDatabase.addMovie(actor, movie_3);
        this.movieDatabase.addMovie(actor, movie_4);
        this.movieDatabase.addMovie(actor, movie_5);
        this.movieDatabase.addMovie(actor, movie_6);

        String[] expected = {"1", "2", "5", "3"};
        Iterable<Movie> movieIterable = this.movieDatabase.getMoviesInRangeOfBudget(50, 100);
        List<Movie> movies = StreamSupport.stream(movieIterable.spliterator(), false).collect(Collectors.toList());
        int counter = 0;
        for (Movie movie : movies) {
            Assert.assertEquals(expected[counter++], movie.getId());
        }
    }

    @Test
    public void testGetMoviesInRangeOfBudgetShouldReturnEmptyCollection() {
        Actor actor = getRandomActor();
        Movie movie_1 = new Movie("1", 1, "1", 6.0, 100.0);
        Movie movie_2 = new Movie("2", 1, "2", 4.0, 50.0);
        Movie movie_3 = new Movie("3", 1, "3", 1.0, 100.0);
        Movie movie_4 = new Movie("4", 1, "4", 3.0, 10.0);
        Movie movie_5 = new Movie("5", 1, "5", 2.0, 50.0);
        Movie movie_6 = new Movie("6", 1, "6", 5.0, 10.0);

        this.movieDatabase.addActor(actor);
        this.movieDatabase.addMovie(actor, movie_1);
        this.movieDatabase.addMovie(actor, movie_2);
        this.movieDatabase.addMovie(actor, movie_3);
        this.movieDatabase.addMovie(actor, movie_4);
        this.movieDatabase.addMovie(actor, movie_5);
        this.movieDatabase.addMovie(actor, movie_6);


        Iterable<Movie> movieIterable = this.movieDatabase.getMoviesInRangeOfBudget(0.01, 9.99);
        List<Movie> movies = StreamSupport.stream(movieIterable.spliterator(), false).collect(Collectors.toList());
        Assert.assertEquals(0, movies.size());
    }

    @Test
    public void testGetActorsOrderedByMaxMovieBudgetThenByMoviesCountReturnCorrectResult() {
        Actor actor_1 = new Actor("1", "1", 1);
        Actor actor_2 = new Actor("2", "2", 2);
        Actor actor_3 = new Actor("3", "3", 3);

        Movie movie_1 = new Movie("1", 1, "1", 6.0, 100.0);
        Movie movie_2 = new Movie("2", 1, "2", 4.0, 50.0);
        Movie movie_3 = new Movie("3", 1, "3", 1.0, 100.0);
        Movie movie_4 = new Movie("4", 1, "4", 3.0, 10.0);
        Movie movie_5 = new Movie("5", 1, "5", 2.0, 50.0);
        Movie movie_6 = new Movie("6", 1, "6", 5.0, 10.0);

        this.movieDatabase.addActor(actor_3);
        this.movieDatabase.addActor(actor_2);
        this.movieDatabase.addActor(actor_1);

        this.movieDatabase.addMovie(actor_2, movie_1);
        this.movieDatabase.addMovie(actor_2, movie_2);
        this.movieDatabase.addMovie(actor_1, movie_3);
        this.movieDatabase.addMovie(actor_1, movie_4);
        this.movieDatabase.addMovie(actor_1, movie_6);
        this.movieDatabase.addMovie(actor_3, movie_5);
        this.movieDatabase.addMovie(actor_3, movie_6);

        String[] expected = {actor_1.getId(), actor_2.getId(), actor_3.getId()};

        Iterable<Actor> actorIterable = this.movieDatabase.getActorsOrderedByMaxMovieBudgetThenByMoviesCount();
        int counter = 0;
        for (Actor actor : actorIterable) {
            Assert.assertEquals(expected[counter++], actor.getId());
        }
    }

    @Test
    public void testGetActorsOrderedByMaxMovieBudgetThenByMoviesCountReturnEmptyCollection() {
        Iterable<Actor> actorIterable = this.movieDatabase.getActorsOrderedByMaxMovieBudgetThenByMoviesCount();
        List<Actor> actors = StreamSupport.stream(actorIterable.spliterator(), false).collect(Collectors.toList());
        Assert.assertEquals(0, actors.size());
    }

    @Test
    public void test_getMoviesOrderedByBudgetThenByRating_ShouldOrderOnOrderOfInput() {
        Actor actor = getRandomActor();
        this.movieDatabase.addActor(actor);
        Movie movie_1 = new Movie("1", 1, "1", 6, 100.0);
        Movie movie_2 = new Movie("2", 1, "2", 6, 100.0);
        Movie movie_3 = new Movie("3", 1, "3", 6, 100.0);
        Movie movie_4 = new Movie("4", 1, "4", 6, 100.0);
        Movie movie_5 = new Movie("5", 1, "5", 6, 100.0);
        Movie movie_6 = new Movie("6", 1, "6", 6, 100.0);

        this.movieDatabase.addMovie(actor, movie_6);
        this.movieDatabase.addMovie(actor, movie_4);
        this.movieDatabase.addMovie(actor, movie_3);
        this.movieDatabase.addMovie(actor, movie_2);
        this.movieDatabase.addMovie(actor, movie_1);
        this.movieDatabase.addMovie(actor, movie_5);

        String[] expected = {"6", "4", "3", "2", "1", "5"};
        Iterable<Movie> movieIterable = this.movieDatabase.getMoviesOrderedByBudgetThenByRating();
        int counter = 0;
        for (Movie movie : movieIterable) {
            Assert.assertEquals(expected[counter++], movie.getId());
        }
    }

    @Test
    public void testGetMoviesOrderedByBudgetThenByRatingShouldReturnCorrectlySortedCollection() {
        Actor actor = getRandomActor();
        Movie movie_1 = new Movie("1", Integer.MAX_VALUE, "1", 5.0, 100.0);
        Movie movie_2 = new Movie("2", Integer.MIN_VALUE, "2", 2.0, 50.0);
        Movie movie_3 = new Movie("3", Integer.MAX_VALUE, "3", 1.0, 100.0);
        Movie movie_4 = new Movie("4", Integer.MIN_VALUE, "4", 3.0, 10.0);
        Movie movie_5 = new Movie("5", Integer.MAX_VALUE, "5", 4.0, 50.0);
        Movie movie_6 = new Movie("6", Integer.MIN_VALUE, "6", 6.0, 100.0);
        Movie movie_7 = new Movie("7", Integer.MAX_VALUE, "7", 1.0, 100.0);
        Movie movie_8 = new Movie("8", Integer.MIN_VALUE, "8", 5.0, 10.0);
        Movie movie_9 = new Movie("9", Integer.MAX_VALUE, "9", 5.0, 100.0);
        Movie movie_10 = new Movie("10", Integer.MIN_VALUE, "10", 0.0, -10.0);
        Movie movie_11 = new Movie("11", Integer.MAX_VALUE, "11", 3.0, 10.0);
        Movie movie_12 = new Movie("12", Integer.MIN_VALUE, "12", -0.0, -10.0);
        Movie movie_13 = new Movie("13", Integer.MAX_VALUE, "13", -5.0, -100.0);
        Movie movie_14 = new Movie("14", Integer.MIN_VALUE, "14", 5.0, -100.0);
        Movie movie_15 = new Movie("15", Integer.MAX_VALUE, "15", 5.0, 10.0);

        this.movieDatabase.addActor(actor);
        this.movieDatabase.addMovie(actor, movie_1);
        this.movieDatabase.addMovie(actor, movie_2);
        this.movieDatabase.addMovie(actor, movie_3);
        this.movieDatabase.addMovie(actor, movie_4);
        this.movieDatabase.addMovie(actor, movie_5);
        this.movieDatabase.addMovie(actor, movie_6);
        this.movieDatabase.addMovie(actor, movie_7);
        this.movieDatabase.addMovie(actor, movie_8);
        this.movieDatabase.addMovie(actor, movie_9);
        this.movieDatabase.addMovie(actor, movie_10);
        this.movieDatabase.addMovie(actor, movie_11);
        this.movieDatabase.addMovie(actor, movie_12);
        this.movieDatabase.addMovie(actor, movie_13);
        this.movieDatabase.addMovie(actor, movie_14);
        this.movieDatabase.addMovie(actor, movie_15);

        String[] expected = {"6", "1", "9", "3", "7", "5", "2", "8", "15", "4", "11", "10", "12", "14", "13"};
        Iterable<Movie> movieIterable = this.movieDatabase.getMoviesOrderedByBudgetThenByRating();
        int counter = 0;
        for (Movie movie : movieIterable) {
            Assert.assertEquals(expected[counter++], movie.getId());
        }
    }
}
