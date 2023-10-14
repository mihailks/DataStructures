package org.softuni.exam.structures;

import org.softuni.exam.entities.Actor;
import org.softuni.exam.entities.Movie;
import org.softuni.exam.entities.Video;

import java.util.*;
import java.util.stream.Collectors;

public class MovieDatabaseImpl implements MovieDatabase {
    Map<String, Actor> actors;
    Map<String, Movie> movies;
    Map<String, List<Movie>> actorsAndMovies;
    Set<Actor> noobs;

    public MovieDatabaseImpl() {
        this.actors = new LinkedHashMap<>();
        this.movies = new LinkedHashMap<>();
        this.actorsAndMovies = new LinkedHashMap<>();
        this.noobs = new LinkedHashSet<>();
    }

    @Override
    public void addActor(Actor actor) {
        if (actors.containsKey(actor.getId())) {
            throw new IllegalArgumentException();
        }
        this.actors.put(actor.getId(), actor);
        this.actorsAndMovies.put(actor.getId(), new ArrayList<>());
        noobs.add(actor);
    }

    @Override
    public void addMovie(Actor actor, Movie movie) throws IllegalArgumentException {
        if (!actors.containsKey(actor.getId())) {
            throw new IllegalArgumentException();
        }
        movies.put(movie.getId(), movie);
        actorsAndMovies.get(actor.getId()).add(movie);
        noobs.remove(actor);
    }

    @Override
    public boolean contains(Actor actor) {
        return this.actors.containsKey(actor.getId());
    }

    @Override
    public boolean contains(Movie movie) {
        return this.movies.containsKey(movie.getId());
    }

    @Override
    public Iterable<Movie> getAllMovies() {
        return this.movies.values();
    }

    @Override
    public Iterable<Actor> getNewbieActors() {
        return this.noobs;
    }

    @Override
    public Iterable<Movie> getMoviesOrderedByBudgetThenByRating() {
//        return movies.values()
//                .stream()
//                .sorted(Comparator.comparing(Movie::getBudget).reversed()
//                        .thenComparing(Movie::getRating).reversed())
//                .collect(Collectors.toList());

            return this.movies.values()
                    .stream()
                    .sorted((o1, o2) -> {
                        if (Double.compare(o2.getBudget(), o1.getBudget()) == 0) {
                            return Double.compare(o2.getRating(), o1.getRating());
                        }
                        return Double.compare(o2.getBudget(), o1.getBudget());
                    })
                    .collect(Collectors.toList());
    }

    @Override
    public Iterable<Actor> getActorsOrderedByMaxMovieBudgetThenByMoviesCount() {
        return actors.values()
                .stream()
                .sorted((a1, a2) -> {
                    double first = actorsAndMovies.get(a1.getId())
                            .stream()
                            .mapToDouble(Movie::getBudget)
                            .max().orElse(0);
                    double second = actorsAndMovies.get(a2.getId())
                            .stream()
                            .mapToDouble(Movie::getBudget)
                            .max().orElse(0);
                    int result = Double.compare(second, first);
                    if (result == 0) {
                        int sizeMoviesFirst = actorsAndMovies.get(a1.getId()).size();
                        int sizeMoviesSecond = actorsAndMovies.get(a2.getId()).size();
                        result = Integer.compare(sizeMoviesSecond, sizeMoviesFirst);
                    }
                    return result;
                }).collect(Collectors.toList());

    }

    @Override
    public Iterable<Movie> getMoviesInRangeOfBudget(double lower, double upper) {
        return movies.values()
                .stream()
                .filter(movie -> movie.getBudget() <= upper)
                .filter(movie -> movie.getBudget() >= lower)
                .sorted(Comparator.comparing(Movie::getRating).reversed())
                .collect(Collectors.toList());
    }
}











