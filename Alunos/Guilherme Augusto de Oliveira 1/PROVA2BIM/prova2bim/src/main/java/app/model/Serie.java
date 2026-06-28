package app.model;

import java.util.List;

public class Serie {

    private int id;
    private String name;
    private String language;
    private List<String> genres;
    private String status;
    private String premiered;
    private String ended;

    private Rating rating;
    private Network network;
    private WebChannel webChannel;

    public Serie() {
    }

    // GETTERS EM PORTUGUÊS

    public int getId() {
        return id;
    }

    public String getNome() {
        return name;
    }

    public String getIdioma() {
        return language;
    }

    public List<String> getGeneros() {
        return genres;
    }

    public String getStatus() {
        return status;
    }

    public String getDataEstreia() {
        return premiered;
    }

    public String getDataFim() {
        return ended;
    }

    public double getNota() {
        if (rating == null || rating.getAverage() == null)
            return 0;

        return rating.getAverage();
    }

    public String getEmissora() {

        if (network != null)
            return network.getName();

        if (webChannel != null)
            return webChannel.getName();

        return "Não informado";
    }

    @Override
    public String toString() {

        if (premiered != null && premiered.length() >= 4) {

            return name + " (" + premiered.substring(0, 4) + ")";

        }

        return name;

    }
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Serie outra = (Serie) obj;

        return id == outra.id;

    }

    @Override
    public int hashCode() {

        return Integer.hashCode(id);

    }
}