package model.dtos;

import java.time.LocalDate;
import java.util.List;

public class ShowDTO {

    // Attributes
    private int id;
    private String name;
    private String language;
    private List<String> genres;
    private RatingDTO rating;
    private String status;
    private String premiered;
    private String ended;
    private NetworkDTO network;

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public RatingDTO getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }

    public String getPremiered() {
        return premiered;
    }

    public String getEnded() {
        return ended;
    }

    public NetworkDTO getNetwork() {
        return network;
    }

    public String getLanguage() {
        return language;
    }
}
