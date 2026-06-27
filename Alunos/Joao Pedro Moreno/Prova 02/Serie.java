package com.seriestv;

//Representa uma série de TV com todos os dados vindos da API TVMaze.
//Cada campo bate exatamente com o nome no JSON da API.

public class Serie {
    private int id;
    private String name;
    private String language;
    private String[] genres;
    private String status;
    private String premiered;
    private String ended;
    private Rating rating;
    private Image image;
    private String summary;
    private Network network;


    //Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name != null ? name : "Sem nome";
    }

    public String getLanguage() {
        return language != null ? language : "Desconhecido";
    }

    public String[] getGenres() {
        return genres != null ? genres : new String[]{};
    }

    public String getGenresFormatted() {
        if (genres == null || genres.length == 0) return "Sem gênero";
        return String.join(", ", genres);
    }

    public String getStatus() {
        if (status == null) return "Desconhecido";
        return switch (status) {
            case "Ended" -> "Encerrada";
            case "Running" -> "Em exibição";
            case "To Be Determined" -> "Indefinido";
            case "In Development" -> "Em desenvolvimento";
            default -> status;
        };
    }

    public String getStatusOriginal() {
        return status != null ? status : "";
    }

    public String getPremiered() {
        return premiered != null ? premiered : "Desconhecida";
    }

    public String getEnded() {
        return ended != null ? ended : "—";
    }

    public double getRating() {
        return rating != null && rating.average != null ? rating.average : 0.0;
    }

    public String getImageUrl() {
        if (image != null && image.medium != null) return image.medium;
        return null;
    }

    public String getSummary() {
        if (summary == null) return "Sem sinopse disponível.";
        // Remove tags HTML que vêm da API
        return summary.replaceAll("<[^>]*>", "").trim();
    }

    public String getNetwork() {
        if (network != null && network.name != null) return network.name;
        return "Desconhecida";
    }

    
    //Classes internas para mapear o JSON
    private static class Rating {
        Double average;
    }

    private static class Image {
        String medium;
        String original;
    }

    private static class Network {
        String name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
