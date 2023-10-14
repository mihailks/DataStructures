package org.softuni.exam;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.softuni.exam.entities.User;
import org.softuni.exam.entities.Video;
import org.softuni.exam.structures.ViTubeRepository;
import org.softuni.exam.structures.ViTubeRepositoryImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class ViTubeRepositoryTests {
    private interface InternalTest {
        void execute();
    }

    private ViTubeRepository viTubeRepository;

    private User getRandomUser() {
        return new User(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString());
    }

    private Video getRandomVideo() {
        return new Video(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                Math.min(0, Math.random() * 1_000_000_000),
                (int) Math.min(0, Math.random() * 1_000_000_000),
                (int) Math.min(0, Math.random() * 1_000_000_000),
                (int) Math.min(0, Math.random() * 1_000_000_000));
    }

    @Before
    public void setup() {
        this.viTubeRepository = new ViTubeRepositoryImpl();
    }

    public void performCorrectnessTesting(InternalTest[] methods) {
        Arrays.stream(methods)
                .forEach(method -> {
                    this.viTubeRepository = new ViTubeRepositoryImpl();

                    try {
                        method.execute();
                    } catch (IllegalArgumentException ignored) { }
                });

        this.viTubeRepository = new ViTubeRepositoryImpl();
    }

    // Correctness Tests

    @Test
    public void testContains_WithExistentUser_ShouldReturnTrue() {
        User user = getRandomUser();
        this.viTubeRepository.registerUser(user);

        assertTrue(this.viTubeRepository.contains(user));
    }

    @Test
    public void testContains_WithNonExistentUser_ShouldReturnFalse() {
        User user = getRandomUser();
        this.viTubeRepository.registerUser(user);

        assertFalse(this.viTubeRepository.contains(getRandomUser()));
    }

    @Test
    public void testContains_WithExistentVideo_ShouldReturnTrue() {
        Video video = getRandomVideo();
        this.viTubeRepository.postVideo(video);

        assertTrue(this.viTubeRepository.contains(video));
    }

    @Test
    public void testContains_WithNonExistentVideo_ShouldReturnFalse() {
        Video video = getRandomVideo();
        this.viTubeRepository.postVideo(video);

        assertFalse(this.viTubeRepository.contains(getRandomVideo()));
    }

    @Test
    public void testGetVideos_WithData_ShouldReturnCorrectResults() {
        Video video1 = getRandomVideo();
        Video video2 = getRandomVideo();
        Video video3 = getRandomVideo();

        this.viTubeRepository.postVideo(video1);
        this.viTubeRepository.postVideo(video2);
        this.viTubeRepository.postVideo(video3);

        Set<Video> set =
                StreamSupport.stream(this.viTubeRepository.getVideos().spliterator(), false)
                        .collect(Collectors.toSet());

        assertEquals(set.size(), 3);
        assertTrue(set.contains(video1));
        assertTrue(set.contains(video2));
        assertTrue(set.contains(video3));
    }

    // Performance Tests

    @Test
    public void testContainsUser_With100000Results_ShouldPassQuickly() {
        this.performCorrectnessTesting(new InternalTest[] {
                this::testContains_WithExistentUser_ShouldReturnTrue,
                this::testContains_WithNonExistentUser_ShouldReturnFalse
        });

        int count = 100000;

        User userToContain = null;

        for (int i = 0; i < count; i++)
        {
            if(i == count / 2)  {
                userToContain = getRandomUser();
                this.viTubeRepository.registerUser(userToContain);
            } else {
                this.viTubeRepository.registerUser(getRandomUser());
            }

        }

        long start = System.currentTimeMillis();

        this.viTubeRepository.contains(userToContain);

        long stop = System.currentTimeMillis();

        long elapsedTime = stop - start;

        assertTrue(elapsedTime <= 5);
    }

    @Test
    public void testGetVideos_With100000Results_ShouldPassQuickly() {
        this.performCorrectnessTesting(new InternalTest[] {
                this::testGetVideos_WithData_ShouldReturnCorrectResults,
        });

        int count = 100000;

        for (int i = 0; i < count; i++)
        {
            this.viTubeRepository.postVideo(getRandomVideo());
        }

        long start = System.currentTimeMillis();

        this.viTubeRepository.getVideos();

        long stop = System.currentTimeMillis();

        long elapsedTime = stop - start;

        assertTrue(elapsedTime <= 5);
    }

    @Test
    public void testVideosStorage(){
        User randomUser1 = getRandomUser();
        User randomUser2 = getRandomUser();
        User randomUser3 = getRandomUser();
        User randomUser4 = getRandomUser();
        viTubeRepository.registerUser(randomUser1);
        viTubeRepository.registerUser(randomUser2);
        viTubeRepository.registerUser(randomUser3);
        viTubeRepository.registerUser(randomUser4);

        Video randomVideo1 = getRandomVideo();
        Video randomVideo2 = getRandomVideo();
        Video randomVideo3 = getRandomVideo();
        Video randomVideo4 = getRandomVideo();
        viTubeRepository.postVideo(randomVideo1);
        viTubeRepository.postVideo(randomVideo2);
        viTubeRepository.postVideo(randomVideo3);
        viTubeRepository.postVideo(randomVideo4);

        viTubeRepository.watchVideo(randomUser1,randomVideo1);
        viTubeRepository.likeVideo(randomUser1,randomVideo1);
        Iterable<Video> videos = viTubeRepository.getVideos();

    }
    @Test
    public void testGetPassiveUsersShouldReturnCorrectResult() {
        User user_1 = getRandomUser();
        User user_2 = getRandomUser();
        User user_3 = getRandomUser();
        User user_4 = getRandomUser();
        User user_5 = getRandomUser();

        Video video_1 = getRandomVideo();

        this.viTubeRepository.registerUser(user_1);
        this.viTubeRepository.registerUser(user_2);
        this.viTubeRepository.registerUser(user_3);
        this.viTubeRepository.registerUser(user_4);
        this.viTubeRepository.registerUser(user_5);

        this.viTubeRepository.postVideo(video_1);
        this.viTubeRepository.watchVideo(user_1, video_1);
        this.viTubeRepository.likeVideo(user_3, video_1);
        this.viTubeRepository.dislikeVideo(user_5, video_1);

        Iterable<User> userIterable = this.viTubeRepository.getPassiveUsers();

        Set<User> set = StreamSupport.stream(userIterable.spliterator(), false).collect(Collectors.toSet());
        Assert.assertEquals(2, set.size());
    }

    @Test
    public void testGetPassiveUsersShouldReturnEmptyCollection() {
        User user_1 = getRandomUser();
        User user_2 = getRandomUser();
        User user_3 = getRandomUser();

        Video video_1 = getRandomVideo();

        this.viTubeRepository.registerUser(user_1);
        this.viTubeRepository.registerUser(user_2);
        this.viTubeRepository.registerUser(user_3);
        this.viTubeRepository.postVideo(video_1);
        this.viTubeRepository.watchVideo(user_1, video_1);
        this.viTubeRepository.likeVideo(user_2, video_1);
        this.viTubeRepository.dislikeVideo(user_3, video_1);

        Iterable<User> userIterable = this.viTubeRepository.getPassiveUsers();

        Set<User> set = StreamSupport.stream(userIterable.spliterator(), false).collect(Collectors.toSet());
        Assert.assertEquals(0, set.size());
    }

    @Test
    public void testGetVideosOrderedByViewsThenByLikesThenByDislikesShouldReturnCorrectResult() {
        User user_1 = getRandomUser();
        User user_2 = getRandomUser();
        User user_3 = getRandomUser();

        Video video_1 = new Video("1", "1", 1, 0, 0, 0);
        Video video_2 = new Video("2", "2", 1, 0, 0, 0);
        Video video_3 = new Video("3", "3", 1, 0, 0, 0);
        Video video_4 = new Video("4", "4", 1, 0, 0, 0);
        Video video_5 = new Video("5", "5", 1, 0, 0, 0);
        Video video_6 = new Video("6", "6", 1, 0, 0, 0);

        this.viTubeRepository.registerUser(user_1);
        this.viTubeRepository.registerUser(user_2);
        this.viTubeRepository.registerUser(user_3);
        this.viTubeRepository.postVideo(video_1);
        this.viTubeRepository.postVideo(video_2);
        this.viTubeRepository.postVideo(video_3);
        this.viTubeRepository.postVideo(video_4);
        this.viTubeRepository.postVideo(video_5);
        this.viTubeRepository.postVideo(video_6);

        // watch
        this.viTubeRepository.watchVideo(user_1, video_1);
        this.viTubeRepository.watchVideo(user_2, video_1);
        this.viTubeRepository.watchVideo(user_3, video_1);
        this.viTubeRepository.watchVideo(user_1, video_3);
        this.viTubeRepository.watchVideo(user_2, video_3);
        this.viTubeRepository.watchVideo(user_3, video_3);
        this.viTubeRepository.watchVideo(user_3, video_2);
        this.viTubeRepository.watchVideo(user_1, video_2);

        // like
        this.viTubeRepository.likeVideo(user_3, video_3);
        this.viTubeRepository.likeVideo(user_2, video_3);
        this.viTubeRepository.likeVideo(user_1, video_1);
        this.viTubeRepository.likeVideo(user_3, video_1);

        // dislike
        this.viTubeRepository.dislikeVideo(user_2, video_3);

        Iterable<Video> videoIterable =
                this.viTubeRepository.getVideosOrderedByViewsThenByLikesThenByDislikes();

        String[] expected = {video_1.getId(), video_3.getId(), video_2.getId(), video_4.getId(), video_5.getId(), video_6.getId()};

        List<Video> videoList = StreamSupport
                .stream(videoIterable.spliterator(), false)
                .collect(Collectors.toList());
        int counter = 0;
        for (Video video : videoList) {
            Assert.assertEquals(expected[counter++], video.getId());
        }
    }

    @Test
    public void testGetVideosOrderedByViewsThenByLikesThenByDislikesShouldReturnEmptyCollection() {
        User user_1 = getRandomUser();
        User user_2 = getRandomUser();
        User user_3 = getRandomUser();


        this.viTubeRepository.registerUser(user_1);
        this.viTubeRepository.registerUser(user_2);
        this.viTubeRepository.registerUser(user_3);

        Iterable<Video> videoIterable = this.viTubeRepository.getVideosOrderedByViewsThenByLikesThenByDislikes();

        List<Video> videoList = StreamSupport.stream(videoIterable.spliterator(), false).collect(Collectors.toList());
        Assert.assertEquals(0, videoList.size());
    }

}
