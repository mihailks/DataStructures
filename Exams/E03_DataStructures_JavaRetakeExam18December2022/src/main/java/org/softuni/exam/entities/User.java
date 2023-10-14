package org.softuni.exam.entities;

import java.util.Objects;

public class User {
    private String id;

    private String username;

    private int views;
    private int likes;
    private int dislikes;

    public User(String id, String username) {
        this.id = id;
        this.username = username;
        this.views = 0;
        this.likes = 0;
        this.dislikes = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getViews() {
        return views;
    }

    public User setViews(int views) {
        this.views = views;
        return this;
    }

    public int getLikes() {
        return likes;
    }

    public User setLikes(int likes) {
        this.likes = likes;
        return this;
    }

    public int getDislikes() {
        return dislikes;
    }

    public User setDislikes(int dislikes) {
        this.dislikes = dislikes;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
