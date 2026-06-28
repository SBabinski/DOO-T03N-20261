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
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import app.service.UsuarioService;

public class TelaInicial extends JFrame {

    private final UsuarioService usuarioService;

    public TelaInicial() {

        usuarioService = UsuarioService.getInstance();

        setTitle("TV Series Manager");
        setSize(550, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        criarTela();

        setVisible(true);
    }

    private void criarTela() {

        Color fundo = new Color(240, 242, 245);
        Color painel = Color.WHITE;
        Color azul = new Color(33, 150, 243);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(fundo);
        principal.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JPanel centro = new JPanel();
        centro.setBackground(painel);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBorder(BorderFactory.createEmptyBorder(35,40,35,40));

        JLabel titulo = new JLabel("TV SERIES");
        titulo.setAlignmentX(CENTER_ALIGNMENT);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 34));

        JLabel subtitulo = new JLabel("Organize suas séries favoritas");
        subtitulo.setAlignmentX(CENTER_ALIGNMENT);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN,16));
        subtitulo.setForeground(Color.GRAY);

        JLabel usuario = new JLabel(
                "Bem-vindo, " + usuarioService.getUsuario().getNome());
        usuario.setAlignmentX(CENTER_ALIGNMENT);
        usuario.setFont(new Font("Segoe UI", Font.BOLD,18));

        centro.add(titulo);
        centro.add(Box.createVerticalStrut(8));
        centro.add(subtitulo);
        centro.add(Box.createVerticalStrut(30));
        centro.add(usuario);
        centro.add(Box.createVerticalStrut(35));

        JPanel painelBotoes = new JPanel(new GridLayout(5,1,0,15));
        painelBotoes.setOpaque(false);

        JButton btnBuscar = criarBotao("Buscar Série", azul);
        JButton btnFavoritos = criarBotao("Favoritos", azul);
        JButton btnAssistidas = criarBotao("Já Assistidas", azul);
        JButton btnDesejo = criarBotao("Desejo Assistir", azul);
        JButton btnSair = criarBotao("Sair", new Color(220,53,69));

        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnFavoritos);
        painelBotoes.add(btnAssistidas);
        painelBotoes.add(btnDesejo);
        painelBotoes.add(btnSair);

        centro.add(painelBotoes);

        centro.add(Box.createVerticalGlue());

        JLabel versao = new JLabel("Versão 1.0");
        versao.setAlignmentX(CENTER_ALIGNMENT);
        versao.setForeground(Color.GRAY);
        versao.setFont(new Font("Segoe UI", Font.PLAIN,13));

        centro.add(Box.createVerticalStrut(25));
        centro.add(versao);

        principal.add(centro);

        add(principal);

        btnBuscar.addActionListener(e -> new TelaBusca());

        btnFavoritos.addActionListener(e ->
                new TelaLista("Favoritos",
                        usuarioService.getUsuario().getFavoritos()));

        btnAssistidas.addActionListener(e ->
                new TelaLista("Já Assistidas",
                        usuarioService.getUsuario().getAssistidas()));

        btnDesejo.addActionListener(e ->
                new TelaLista("Desejo Assistir",
                        usuarioService.getUsuario().getDesejoAssistir()));

        btnSair.addActionListener(e -> System.exit(0));

    }

    private JButton criarBotao(String texto, Color cor){

        JButton botao = new JButton(texto);

        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);

        botao.setFont(new Font("Segoe UI",Font.BOLD,16));

        botao.setPreferredSize(new Dimension(320,55));

        botao.setMaximumSize(new Dimension(Integer.MAX_VALUE,55));

        botao.setBorder(BorderFactory.createEmptyBorder(12,20,12,20));

        return botao;

    }

}