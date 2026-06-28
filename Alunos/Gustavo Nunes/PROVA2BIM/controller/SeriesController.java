package controller;

import model.entities.Serie;
import model.entities.UserData;
import repository.UserRepository;
import service.SeriesSortingService;
import service.TvMazeAPIService;
import service.UserService;

import java.io.IOException;
import java.util.List;

public class SeriesController {

    // Attributes
    private final TvMazeAPIService apiService;
    private final UserService userService;
    private final SeriesSortingService sortingService;

    // Constructor
    public SeriesController() throws IOException {

        this.apiService = new TvMazeAPIService();

        this.userService = new UserService(new UserRepository()
        );

        this.sortingService = new SeriesSortingService();

    }

    /// Set nickname
    public void setNickname(String nickname)
            throws IOException {

        this.userService.setNickname(nickname);
    }

    /// search serie
    public List<Serie> searchSeries(String name)
            throws IOException, InterruptedException {

        return this.apiService.searchSeries(name);
    }

    /// Get data
    public UserData getUserData() {

        return userService.getUserData();
    }

    public List<Serie> getFavorite() {

        return userService.getFavorites();
    }

    public List<Serie> getWatched() {

        return userService.getWatched();
    }

    public List<Serie> getWantToWatch() {

        return userService.getWantToWatch();
    }

    /// Add serie
    public void addFavorite(Serie serie)
            throws IOException {

        this.userService.addFavorite(serie);
    }

    public void addWatched(Serie serie)
            throws IOException {

        this.userService.addWatched(serie);
    }

    public void addWantToWatch(Serie serie)
            throws IOException {

        this.userService.addWantToWatch(serie);
    }

    /// Remove serie
    public void removeFavorite(Serie serie)
            throws IOException {

        this.userService.removeFavorite(serie);
    }

    public void removeWatched(Serie serie)
            throws IOException {

        this.userService.removeWatched(serie);
    }

    public void removeWantToWatch(Serie serie)
            throws IOException {

        this.userService.removeWantToWatch(serie);
    }

    /// Sort Series
    public List<Serie> sortByName(List<Serie> series) {
        return sortingService.sortByName(series);
    }

    public List<Serie> sortByRating(List<Serie> series) {
        return sortingService.sortByRating(series);
    }

    public List<Serie> sortByStatus(List<Serie> series) {
        return sortingService.sortByStatus(series);
    }

    public List<Serie> sortByPremiered(List<Serie> series) {
        return sortingService.sortByPremiered(series);
    }
}
