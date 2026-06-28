package view;

import model.entities.Serie;

import javax.swing.*;
import java.awt.*;

/// Exibe os detalhes de uma série
public class SerieDetailsDialog extends JDialog {

    public SerieDetailsDialog(
            JFrame parent,
            Serie serie
    ) {

        super(
                parent,
                "Detalhes da Série",
                true
        );

        setSize(500, 400);

        setLocationRelativeTo(parent);

        JTextArea detailsArea =
                new JTextArea();

        detailsArea.setEditable(false);

        detailsArea.setText(
                "Nome: " + serie.getName() + "\n\n" +
                        "Idioma: " + serie.getLanguage() + "\n\n" +
                        "Nota: " + serie.getAverage() + "\n\n" +
                        "Status: " + serie.getStatus() + "\n\n" +
                        "Estreia: " + serie.getPremiered() + "\n\n" +
                        "Fim: " + serie.getEnded() + "\n\n" +
                        "Emissora: " + serie.getBroadcaster() + "\n\n" +
                        "Gêneros: " + serie.getGenres()
        );

        add(
                new JScrollPane(detailsArea),
                BorderLayout.CENTER
        );
    }
}
