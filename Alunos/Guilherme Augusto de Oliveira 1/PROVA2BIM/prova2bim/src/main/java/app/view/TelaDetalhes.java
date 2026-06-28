package app.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import app.model.Serie;
import app.service.UsuarioService;

public class TelaDetalhes extends JFrame {

    private final Serie serie;
    private final UsuarioService usuarioService;

    public TelaDetalhes(Serie serie) {

        this.serie = serie;
        this.usuarioService = UsuarioService.getInstance();

        setTitle("Detalhes da Série");
        setSize(600, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarComponentes();

        setVisible(true);

    }

    private void inicializarComponentes() {

        Color fundo = new Color(240,242,245);
        Color azul = new Color(33,150,243);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(fundo);
        principal.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(25,30,25,30));

        JLabel titulo = new JLabel(serie.getNome().toUpperCase());

        titulo.setAlignmentX(CENTER_ALIGNMENT);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JLabel linha = new JLabel("────────────────────────────────────────");

        linha.setAlignmentX(CENTER_ALIGNMENT);
        linha.setForeground(Color.GRAY);

        card.add(titulo);
        card.add(Box.createVerticalStrut(10));
        card.add(linha);
        card.add(Box.createVerticalStrut(20));

        card.add(criarCampo("Nome", serie.getNome()));
        card.add(criarCampo("Idioma", serie.getIdioma()));
        card.add(criarCampo("Gêneros", String.join(" • ", serie.getGeneros())));
        card.add(criarCampo("Nota", String.valueOf(serie.getNota())));
        card.add(criarCampo("Status", serie.getStatus()));
        card.add(criarCampo("Estreia", serie.getDataEstreia()));
        card.add(criarCampo("Fim", serie.getDataFim()));
        card.add(criarCampo("Emissora", serie.getEmissora()));

        card.add(Box.createVerticalStrut(25));

        JPanel painelBotoes = new JPanel(new GridLayout(1,3,10,0));
        painelBotoes.setOpaque(false);

        JButton btnFavoritos = criarBotao("Favoritos", azul);
        JButton btnAssistida = criarBotao("Já Assisti", azul);
        JButton btnDesejo = criarBotao("Desejo Assistir", azul);

        painelBotoes.add(btnFavoritos);
        painelBotoes.add(btnAssistida);
        painelBotoes.add(btnDesejo);

        card.add(painelBotoes);

        principal.add(card);

        add(principal);

        btnFavoritos.addActionListener(e -> adicionarFavorito());

        btnAssistida.addActionListener(e -> adicionarAssistida());

        btnDesejo.addActionListener(e -> adicionarDesejo());

    }

    private JPanel criarCampo(String titulo, String valor){

        JPanel painel = new JPanel(new BorderLayout());

        painel.setOpaque(false);

        painel.setBorder(BorderFactory.createEmptyBorder(8,0,8,0));

        JLabel lblTitulo = new JLabel(titulo + ":");

        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD,15));

        lblTitulo.setPreferredSize(new Dimension(120,25));

        JLabel lblValor = new JLabel(valor == null || valor.isBlank() ? "-" : valor);

        lblValor.setFont(new Font("Segoe UI", Font.PLAIN,15));

        painel.add(lblTitulo, BorderLayout.WEST);

        painel.add(lblValor, BorderLayout.CENTER);

        return painel;

    }

    private JButton criarBotao(String texto, Color cor){

        JButton botao = new JButton(texto);

        botao.setBackground(cor);

        botao.setForeground(Color.WHITE);

        botao.setFocusPainted(false);

        botao.setFont(new Font("Segoe UI", Font.BOLD,15));

        botao.setPreferredSize(new Dimension(150,45));

        return botao;

    }
        private void adicionarFavorito() {

        if (usuarioService.getUsuario().getFavoritos().contains(serie)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Esta série já está nos Favoritos.");

            return;

        }

        usuarioService.adicionarFavorito(serie);

        JOptionPane.showMessageDialog(
                this,
                "Série adicionada aos Favoritos!");

    }

    private void adicionarAssistida() {

        if (usuarioService.getUsuario().getAssistidas().contains(serie)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Esta série já está na lista de Assistidas.");

            return;

        }

        usuarioService.adicionarAssistida(serie);

        JOptionPane.showMessageDialog(
                this,
                "Série adicionada à lista de Assistidas!");

    }

    private void adicionarDesejo() {

        if (usuarioService.getUsuario().getDesejoAssistir().contains(serie)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Esta série já está na lista de Desejo.");

            return;

        }

        usuarioService.adicionarDesejo(serie);

        JOptionPane.showMessageDialog(
                this,
                "Série adicionada à lista de Desejo!");

    }

}