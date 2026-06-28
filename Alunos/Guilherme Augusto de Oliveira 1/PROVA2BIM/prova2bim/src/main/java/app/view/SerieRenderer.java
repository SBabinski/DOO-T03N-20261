package app.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import app.model.Serie;

public class SerieRenderer extends JPanel implements ListCellRenderer<Serie> {

    private final JLabel lblNome;
    private final JLabel lblInfo;
    private final JLabel lblNota;

    public SerieRenderer() {

        setLayout(new BorderLayout(5, 5));

        setOpaque(true);

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        lblNome = new JLabel();
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 18));

        lblInfo = new JLabel();
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblInfo.setForeground(new Color(110, 110, 110));

        lblNota = new JLabel();
        lblNota.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNota.setForeground(new Color(25, 118, 210));

        JPanel inferior = new JPanel(new BorderLayout());
        inferior.setOpaque(false);

        inferior.add(lblInfo, BorderLayout.WEST);
        inferior.add(lblNota, BorderLayout.EAST);

        add(lblNome, BorderLayout.NORTH);
        add(inferior, BorderLayout.SOUTH);

    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Serie> list,
            Serie serie,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        lblNome.setText(serie.getNome());

        String generos = "";

        if (serie.getGeneros() != null &&
                !serie.getGeneros().isEmpty()) {

            generos = String.join(" • ", serie.getGeneros());

        }

        String ano = "";

        if (serie.getDataEstreia() != null &&
                !serie.getDataEstreia().isBlank() &&
                serie.getDataEstreia().length() >= 4) {

            ano = serie.getDataEstreia().substring(0, 4);

        }

        if (!ano.isBlank()) {

            if (!generos.isBlank()) {

                generos += "  |  ";

            }

            generos += ano;

        }

        lblInfo.setText(generos);

        lblNota.setText("" + serie.getNota());

        if (isSelected) {

            setBackground(new Color(225, 238, 255));

        } else {

            setBackground(Color.WHITE);

        }

        return this;

    }

}