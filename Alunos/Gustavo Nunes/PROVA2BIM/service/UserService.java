package service;

import model.entities.Serie;
import model.entities.User;
import model.entities.UserData;
import repository.UserRepository;

import java.io.IOException;
import java.util.List;

public class UserService {

    private final UserRepository repository;
    private final UserData userData;

    public UserService(UserRepository repository) throws IOException {
        this.repository = repository;
        this.userData = repository.load();
    }

    /// Set the nickname
    public void setNickname(String nickname)
            throws IOException {

        User user = new User(nickname);

        this.userData.setUser(user);

        this.persist();
    }

    /// Save user data in JSON
    private void persist() throws IOException {
        repository.save(userData);
    }

    /// Add Serie to List
    public void addFavorite(Serie serie) throws IOException {

        if (!userData.getFavorites().contains(serie)) {

            this.userData.addFavorite(serie);
            this.persist();

        }

    }

    public void addWatched(Serie serie) throws IOException {

        if (!userData.getWatched().contains(serie)) {

            this.userData.addWatched(serie);
            this.persist();

        }

    }

    public void addWantToWatch(Serie serie) throws IOException {

        if (!userData.getWantToWatch().contains(serie)) {

            this.userData.addWantToWatch(serie);
            this.persist();

        }

    }

    /// Remove serie from list
    public void removeFavorite(Serie serie) throws IOException {
        this.userData.removeFavorite(serie);
        this.persist();
    }

    public void removeWatched(Serie serie) throws IOException {
        this.userData.removeWatched(serie);
        this.persist();
    }

    public void removeWantToWatch(Serie serie) throws IOException {
        this.userData.removeWantToWatch(serie);
        this.persist();
    }

    /// Show user data
    public UserData getUserData() {
        return this.userData;
    }

    public List<Serie> getFavorites() {
        return this.userData.getFavorites();
    }

    public List<Serie> getWatched() {
        return this.userData.getWatched();
    }

    public List<Serie> getWantToWatch() {
        return this.userData.getWantToWatch();
    }

}
