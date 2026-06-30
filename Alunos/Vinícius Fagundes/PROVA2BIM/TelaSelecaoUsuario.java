
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Tela modal de seleção de usuário: permite escolher um usuário existente,
 * criar um novo ou excluir um já cadastrado. Devolve o usuário escolhido
 * através de {@link #getUsuarioEscolhido()}.
 */
@SuppressWarnings("serial")
public final class TelaSelecaoUsuario extends JDialog {
    private static final Color BG_DARK = new Color(0x181825);
    private static final Color BG_PANEL = new Color(0x24243A);
    private static final Color BG_FIELD = new Color(0x303047);
    private static final Color ACCENT = new Color(0x7C5CFC);
    private static final Color PERIGO = new Color(0xC0566A);
    private static final Color TEXT_LIGHT = new Color(0xF2F2F7);
    private static final Color TEXT_MUTED = new Color(0xB1B1C3);

    private final RepositorioUsuarios repositorio;
    private final DefaultListModel<Usuario> modelo = new DefaultListModel<>();
    private final JList<Usuario> lista = new JList<>(modelo);
    private JButton botaoEntrar;
    private JButton botaoExcluir;
    private Usuario usuarioEscolhido;

    public TelaSelecaoUsuario(Frame dono, RepositorioUsuarios repositorio) {
        super(dono, "SérieFinder — Quem está assistindo?", true);
        this.repositorio = repositorio;

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evento) {
                cancelar();
            }
        });

        JPanel raiz = new JPanel(new BorderLayout(0, 14));
        raiz.setBackground(BG_DARK);
        raiz.setBorder(new EmptyBorder(22, 24, 20, 24));
        setContentPane(raiz);

        raiz.add(criarCabecalho(), BorderLayout.NORTH);
        raiz.add(criarLista(), BorderLayout.CENTER);
        raiz.add(criarAcoes(), BorderLayout.SOUTH);

        recarregarLista();
        lista.addListSelectionListener(evento -> atualizarBotoes());
        atualizarBotoes();

        setSize(420, 460);
        setMinimumSize(new Dimension(380, 380));
        setLocationRelativeTo(dono);
        setAlwaysOnTop(true);
    }

    private JPanel criarCabecalho() {
        JPanel cabecalho = new JPanel();
        cabecalho.setOpaque(false);
        cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.Y_AXIS));
        JLabel logo = new JLabel("🎬  SérieFinder");
        logo.setFont(new Font("SansSerif", Font.BOLD, 22));
        logo.setForeground(TEXT_LIGHT);
        JLabel subtitulo = new JLabel("Escolha um perfil para continuar");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitulo.setForeground(TEXT_MUTED);
        cabecalho.add(logo);
        cabecalho.add(Box.createVerticalStrut(4));
        cabecalho.add(subtitulo);
        return cabecalho;
    }

    private JScrollPane criarLista() {
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setBackground(BG_PANEL);
        lista.setForeground(TEXT_LIGHT);
        lista.setFixedCellHeight(46);
        lista.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lista.setBorder(new EmptyBorder(4, 4, 4, 4));
        lista.setSelectionBackground(ACCENT);
        lista.setSelectionForeground(Color.WHITE);
        lista.setCellRenderer((jlista, usuario, indice, selecionado, focado) -> {
            JLabel rotulo = new JLabel("  " + descreverUsuario(usuario));
            rotulo.setOpaque(true);
            rotulo.setFont(new Font("SansSerif", Font.PLAIN, 15));
            rotulo.setBorder(new EmptyBorder(0, 8, 0, 8));
            rotulo.setBackground(selecionado ? ACCENT : BG_PANEL);
            rotulo.setForeground(selecionado ? Color.WHITE : TEXT_LIGHT);
            return rotulo;
        });
        // Duplo clique entra direto no perfil selecionado.
        lista.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evento) {
                if (evento.getClickCount() == 2 && lista.getSelectedValue() != null) {
                    entrar();
                }
            }
        });

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createLineBorder(BG_FIELD));
        scroll.getViewport().setBackground(BG_PANEL);
        return scroll;
    }

    private JPanel criarAcoes() {
        JPanel acoes = new JPanel(new BorderLayout(8, 0));
        acoes.setOpaque(false);

        JButton botaoNovo = criarBotao("Novo usuário", BG_FIELD);
        botaoNovo.addActionListener(evento -> criarUsuario());

        botaoExcluir = criarBotao("Excluir", PERIGO);
        botaoExcluir.addActionListener(evento -> excluirUsuario());

        botaoEntrar = criarBotao("Entrar", ACCENT);
        botaoEntrar.addActionListener(evento -> entrar());
        getRootPane().setDefaultButton(botaoEntrar);

        JPanel esquerda = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        esquerda.setOpaque(false);
        esquerda.add(botaoNovo);
        esquerda.add(botaoExcluir);

        acoes.add(esquerda, BorderLayout.WEST);
        acoes.add(botaoEntrar, BorderLayout.EAST);
        return acoes;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("SansSerif", Font.BOLD, 13));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(new EmptyBorder(9, 16, 9, 16));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private String descreverUsuario(Usuario usuario) {
        int total = 0;
        for (TipoLista tipo : TipoLista.values()) {
            total += usuario.quantidade(tipo);
        }
        return usuario.getApelido() + "  ·  " + total + " série(s)";
    }

    private void recarregarLista() {
        Usuario selecionado = lista.getSelectedValue();
        modelo.clear();
        for (Usuario usuario : repositorio.listar()) {
            modelo.addElement(usuario);
        }
        if (selecionado != null && modelo.contains(selecionado)) {
            lista.setSelectedValue(selecionado, true);
        } else if (!modelo.isEmpty()) {
            lista.setSelectedIndex(0);
        }
    }

    private void atualizarBotoes() {
        boolean temSelecao = lista.getSelectedValue() != null;
        botaoEntrar.setEnabled(temSelecao);
        botaoExcluir.setEnabled(temSelecao);
    }

    private void criarUsuario() {
        String apelido = JOptionPane.showInputDialog(this,
                "Qual é o nome ou apelido do novo usuário?",
                "Novo usuário", JOptionPane.QUESTION_MESSAGE);
        if (apelido == null) {
            return;
        }
        apelido = apelido.trim();
        if (apelido.isEmpty()) {
            avisar("O nome ou apelido não pode ficar vazio.");
            return;
        }
        if (apelido.length() > 60) {
            avisar("Use um nome ou apelido com no máximo 60 caracteres.");
            return;
        }
        try {
            Usuario novo = repositorio.criar(apelido);
            recarregarLista();
            lista.setSelectedValue(novo, true);
            atualizarBotoes();
        } catch (IllegalArgumentException erro) {
            avisar(erro.getMessage());
        } catch (PersistenciaException erro) {
            mostrarErro("Não foi possível salvar o novo usuário.", erro);
        }
    }

    private void excluirUsuario() {
        Usuario selecionado = lista.getSelectedValue();
        if (selecionado == null) {
            return;
        }
        int escolha = JOptionPane.showConfirmDialog(this,
                "Excluir o usuário \"" + selecionado.getApelido() + "\" e todas as suas listas?\n"
                        + "Esta ação não pode ser desfeita.",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (escolha != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            repositorio.remover(selecionado);
            recarregarLista();
            atualizarBotoes();
        } catch (PersistenciaException erro) {
            mostrarErro("Não foi possível excluir o usuário.", erro);
        }
    }

    private void entrar() {
        Usuario selecionado = lista.getSelectedValue();
        if (selecionado == null) {
            avisar("Selecione um usuário ou crie um novo.");
            return;
        }
        usuarioEscolhido = selecionado;
        dispose();
    }

    private void cancelar() {
        usuarioEscolhido = null;
        dispose();
    }

    /** Usuário escolhido, ou {@code null} se a janela foi fechada sem seleção. */
    public Usuario getUsuarioEscolhido() {
        return usuarioEscolhido;
    }

    private void avisar(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarErro(String mensagem, Throwable erro) {
        if (erro != null) {
            erro.printStackTrace();
        }
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
