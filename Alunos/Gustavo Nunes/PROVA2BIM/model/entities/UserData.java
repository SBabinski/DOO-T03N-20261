package model.entities;

import java.util.ArrayList;
import java.util.List;

public class UserData {

    // Attributes
    private User user;
    private List<Serie> favorites;
    private List<Serie> wantToWatch;
    private List<Serie> watched;

    // Constructor
    public UserData() {
        this.favorites = new ArrayList<>();
        this.wantToWatch = new ArrayList<>();
        this.watched = new ArrayList<>();
    }

    // User setter
    public void setUser(User user) {
        this.user = user;
    }

    // Getters
    public User getUser() {
        return user;
    }

    public List<Serie> getFavorites() {
        return favorites;
    }

    public List<Serie> getWantToWatch() {
        return wantToWatch;
    }

    public List<Serie> getWatched() {
        return watched;
    }

    // Adders
    public void addFavorite(Serie serie) {
        this.favorites.add(serie);
    }

    public void addWantToWatch(Serie serie) {
        this.wantToWatch.add(serie);
    }

    public void addWatched(Serie serie) {
        this.watched.add(serie);
    }

    // Removers
    public void removeFavorite(Serie serie) {
        this.favorites.remove(serie);
    }

    public void removeWantToWatch(Serie serie) {
        this.wantToWatch.remove(serie);
    }

    public void removeWatched(Serie serie) {
        this.watched.remove(serie);
    }

    // Util
    @Override
    public String toString() {

        return "UserData{" +
                "user=" + user +
                ", favorites=" + favorites.size() +
                ", watched=" + watched.size() +
                ", wantToWatch=" + wantToWatch.size() +
                '}';
    }
}
