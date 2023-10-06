package org.softuni.exam.structures;

import org.softuni.exam.entities.User;
import org.softuni.exam.entities.Video;

import java.util.*;
import java.util.stream.Collectors;

public class ViTubeRepositoryImpl implements ViTubeRepository {
    Map<String, User> users;
    Map<String, Video> videos;
    Map<String, Set<Video>> userVideos;
    Map<String, User> passiveUsers;

    public ViTubeRepositoryImpl() {
        users = new LinkedHashMap<>();
        videos = new LinkedHashMap<>();
        userVideos = new LinkedHashMap<>();
        passiveUsers = new LinkedHashMap<>();
    }

    @Override
    public void registerUser(User user) {
        users.put(user.getId(), user);
        userVideos.put(user.getId(), new HashSet<>());
        passiveUsers.put(user.getId(), user);
    }

    @Override
    public void postVideo(Video video) {
        videos.put(video.getId(), video);
    }

    @Override
    public boolean contains(User user) {
        return users.containsKey(user.getId());
    }

    @Override
    public boolean contains(Video video) {
        return videos.containsKey(video.getId());
    }

    @Override
    public Iterable<Video> getVideos() {
        return videos.values();
    }

    @Override
    public void watchVideo(User user, Video video) throws IllegalArgumentException {
        if (!users.containsKey(user.getId()) || !videos.containsKey(video.getId())) {
            throw new IllegalArgumentException();
        }
        video.setViews(video.getViews() + 1);
        userVideos.get(user.getId()).add(video);
        passiveUsers.remove(user.getId());

    }

    @Override
    public void likeVideo(User user, Video video) throws IllegalArgumentException {
        if (!users.containsKey(user.getId()) || !videos.containsKey(video.getId())) {
            throw new IllegalArgumentException();
        }
        video.setLikes(video.getLikes() + 1);
        userVideos.get(user.getId()).add(video);
        passiveUsers.remove(user.getId());
    }

    @Override
    public void dislikeVideo(User user, Video video) throws IllegalArgumentException {
        if (!users.containsKey(user.getId()) || !videos.containsKey(video.getId())) {
            throw new IllegalArgumentException();
        }
        video.setDislikes(video.getDislikes() + 1);
        userVideos.get(user.getId()).add(video);
        passiveUsers.remove(user.getId());
    }

    @Override
    public Iterable<User> getPassiveUsers() {
        return passiveUsers.values();
    }

    @Override
    public Iterable<Video> getVideosOrderedByViewsThenByLikesThenByDislikes() {
        return videos.values()
                .stream()
                .sorted(Comparator.comparing(Video::getViews).reversed()
                        .thenComparing(Video::getLikes).reversed()
                        .thenComparing(Video::getDislikes))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<User> getUsersByActivityThenByName() {

        return users.values()
                .stream()
                .sorted((u1, u2) -> {
                    int firstUser = userVideos.get(u2.getId()).size();
                    int secondUser = userVideos.get(u1.getId()).size();

                    int result = Integer.compare(firstUser, secondUser);

                    if (result == 0) {
                        double firstLikes = userVideos.get(u2.getId())
                                .stream()
                                .mapToDouble(Video::getLikes)
                                .sum() +
                                userVideos.get(u2.getId())
                                        .stream()
                                        .mapToDouble(Video::getDislikes)
                                        .sum();
                        double secondLikes = userVideos.get(u1.getId())
                                .stream()
                                .mapToDouble(Video::getLikes)
                                .sum() +
                                userVideos.get(u1.getId())
                                        .stream()
                                        .mapToDouble(Video::getDislikes)
                                        .sum();

                        result = Double.compare(firstLikes, secondLikes);
                        if (result == 0) {
                            result = u1.getUsername().compareTo(u2.getUsername());
                        }
                    }
                    return result;
                }).collect(Collectors.toList());

    }
}
