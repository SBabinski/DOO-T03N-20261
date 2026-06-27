package view;

import exception.ExcecaoPersistencia;
import model.Usuario;
import persistence.RepositorioUsuario;
import service.ServicoSerie;
import util.UtilitarioExcecao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Janela principal do sistema.
 * Além de gerenciar a navegação, recebe o {@link RepositorioUsuario} para:
 * <ul>
 *   <li>Salvar automaticamente ao fechar a janela.</li>
 *   <li>Passar o repositório para a {@link TelaPerfil} (salvar manual + status).</li>
 * </ul>
 */
public class TelaPrincipal extends JFrame {

    private final Usuario usuario;
    private final ServicoSerie servicoSerie;
    private final RepositorioUsuario repositorio;

    private JPanel painelConteudo;
    private JLabel rotuloBoasVindas;

    private TelaPesquisa telaPesquisa;
    private TelaFavoritos telaFavoritos;
    private TelaAssistidas telaAssistidas;
    private TelaDesejaAssistir telaDesejaAssistir;
    private TelaPerfil telaPerfil;

    public TelaPrincipal(Usuario usuario, ServicoSerie servicoSerie, RepositorioUsuario repositorio) {
        this.usuario = usuario;
        this.servicoSerie = servicoSerie;
        this.repositorio = repositorio;
        inicializarTelas();
        configurarJanela();
        construirInterface();
    }

    private void inicializarTelas() {
        telaPesquisa      = new TelaPesquisa(usuario, servicoSerie, this);
        telaFavoritos     = new TelaFavoritos(usuario, servicoSerie);
        telaAssistidas    = new TelaAssistidas(usuario, servicoSerie);
        telaDesejaAssistir = new TelaDesejaAssistir(usuario, servicoSerie);
        telaPerfil        = new TelaPerfil(usuario, repositorio);
    }

    private void configurarJanela() {
        setTitle("SeriesTV — Acompanhamento de Séries");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // tratamos no listener
        setSize(1000, 680);
        setMinimumSize(new Dimension(800, 550));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Salva ao fechar e confirma saída
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salvarAoFechar();
            }
        });
    }

    /**
     * Salva os dados e encerra a aplicação.
     * Pergunta ao usuário em caso de falha na gravação.
     */
    private void salvarAoFechar() {
        try {
            repositorio.salvar(usuario);
        } catch (ExcecaoPersistencia e) {
            int opcao = JOptionPane.showConfirmDialog(
                    this,
                    "Não foi possível salvar os dados antes de fechar:\n" + e.getMessage()
                    + "\n\nDeseja sair mesmo assim (os dados desta sessão serão perdidos)?",
                    "Erro ao salvar",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (opcao != JOptionPane.YES_OPTION) return; // aborta o fechamento
        }
        dispose();
        System.exit(0);
    }

    private void construirInterface() {
        // Cabeçalho
        JPanel painelCabecalho = new JPanel(new BorderLayout());
        painelCabecalho.setBackground(new Color(25, 25, 112));
        painelCabecalho.setPreferredSize(new Dimension(0, 56));

        JLabel rotuloTitulo = new JLabel("  📺  SeriesTV");
        rotuloTitulo.setForeground(Color.WHITE);
        rotuloTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));

        rotuloBoasVindas = new JLabel("Olá, " + usuario.obterNomeOuApelido() + "!   ");
        rotuloBoasVindas.setForeground(new Color(200, 220, 255));
        rotuloBoasVindas.setFont(new Font("SansSerif", Font.PLAIN, 14));

        painelCabecalho.add(rotuloTitulo, BorderLayout.WEST);
        painelCabecalho.add(rotuloBoasVindas, BorderLayout.EAST);

        // Menu lateral
        JPanel painelMenu = new JPanel();
        painelMenu.setLayout(new BoxLayout(painelMenu, BoxLayout.Y_AXIS));
        painelMenu.setBackground(new Color(35, 35, 80));
        painelMenu.setPreferredSize(new Dimension(180, 0));
        painelMenu.setBorder(BorderFactory.createEmptyBorder(16, 8, 16, 8));

        String[] rotulosMenu = {"🔍 Pesquisar", "⭐ Favoritos", "✅ Já Assistidas", "🕐 Deseja Assistir", "👤 Perfil"};
        for (String rotulo : rotulosMenu) {
            JButton botao = criarBotaoMenu(rotulo);
            painelMenu.add(botao);
            painelMenu.add(Box.createRigidArea(new Dimension(0, 6)));
        }

        // CardLayout
        painelConteudo = new JPanel(new CardLayout());
        painelConteudo.add(telaPesquisa,       "pesquisa");
        painelConteudo.add(telaFavoritos,      "favoritos");
        painelConteudo.add(telaAssistidas,     "assistidas");
        painelConteudo.add(telaDesejaAssistir, "desejaAssistir");
        painelConteudo.add(telaPerfil,         "perfil");

        add(painelCabecalho,  BorderLayout.NORTH);
        add(painelMenu,       BorderLayout.WEST);
        add(painelConteudo,   BorderLayout.CENTER);

        mostrarTela("pesquisa");
    }

    private JButton criarBotaoMenu(String rotulo) {
        JButton botao = new JButton(rotulo);
        botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        botao.setAlignmentX(Component.LEFT_ALIGNMENT);
        botao.setBackground(new Color(55, 55, 120));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setFont(new Font("SansSerif", Font.PLAIN, 13));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        String chave = obterChaveTela(rotulo);
        botao.addActionListener(e -> {
            mostrarTela(chave);
            atualizarTelaSeNecessario(chave);
        });
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { botao.setBackground(new Color(80, 80, 160)); }
            public void mouseExited(java.awt.event.MouseEvent evt)  { botao.setBackground(new Color(55, 55, 120)); }
        });
        return botao;
    }

    private String obterChaveTela(String rotulo) {
        if (rotulo.contains("Pesquisar"))     return "pesquisa";
        if (rotulo.contains("Favoritos"))     return "favoritos";
        if (rotulo.contains("Já Assistidas")) return "assistidas";
        if (rotulo.contains("Deseja"))        return "desejaAssistir";
        if (rotulo.contains("Perfil"))        return "perfil";
        return "pesquisa";
    }

    public void mostrarTela(String chave) {
        ((CardLayout) painelConteudo.getLayout()).show(painelConteudo, chave);
    }

    private void atualizarTelaSeNecessario(String chave) {
        switch (chave) {
            case "favoritos":      telaFavoritos.atualizarTabela();      break;
            case "assistidas":     telaAssistidas.atualizarTabela();     break;
            case "desejaAssistir": telaDesejaAssistir.atualizarTabela(); break;
            case "perfil":         telaPerfil.atualizarInformacoes();    break;
        }
    }

    public void atualizarBoasVindas() {
        rotuloBoasVindas.setText("Olá, " + usuario.obterNomeOuApelido() + "!   ");
    }
}
