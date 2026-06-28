package view;

import exception.ExcecaoPersistencia;
import exception.ExcecaoUsuario;
import model.Usuario;
import persistence.RepositorioUsuario;
import service.ServicoUsuario;
import util.UtilitarioExcecao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Tela de perfil do usuário.
 * Exibe estatísticas, permite editar o nome e mostra o status da persistência.
 */
public class TelaPerfil extends JPanel {

    private final Usuario usuario;
    private final ServicoUsuario servicoUsuario;
    private final RepositorioUsuario repositorio;

    private JLabel rotuloNome;
    private JLabel rotuloTotalFavoritos;
    private JLabel rotuloTotalAssistidas;
    private JLabel rotuloTotalDeseja;
    private JLabel rotuloStatusSalvagem;
    private JLabel rotuloCaminhoArquivo;

    public TelaPerfil(Usuario usuario, RepositorioUsuario repositorio) {
        this.usuario = usuario;
        this.repositorio = repositorio;
        this.servicoUsuario = new ServicoUsuario();
        try { servicoUsuario.criarUsuario(usuario.obterNomeOuApelido()); }
        catch (ExcecaoUsuario ignored) {}
        construirInterface();
    }

    private void construirInterface() {
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(242, 243, 250));
        setBorder(new EmptyBorder(24, 32, 24, 32));

        JLabel rotuloTitulo = new JLabel("👤 Perfil");
        rotuloTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        rotuloTitulo.setForeground(new Color(25, 25, 112));
        rotuloTitulo.setBorder(new EmptyBorder(0, 0, 18, 0));

        // ── Cartão: identidade ───────────────────────────────────────────────
        JPanel cartaoIdentidade = criarCartao();
        cartaoIdentidade.setLayout(new BorderLayout(0, 10));

        JLabel lblIdentTitulo = new JLabel("Identificação");
        lblIdentTitulo.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblIdentTitulo.setForeground(new Color(25, 25, 112));

        JPanel linhaIdentidade = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        linhaIdentidade.setOpaque(false);
        JLabel lblDesc = new JLabel("Nome / Apelido:");
        lblDesc.setFont(new Font("SansSerif", Font.BOLD, 14));
        rotuloNome = new JLabel(usuario.obterNomeOuApelido());
        rotuloNome.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JButton btnEditar = criarBotao("✏  Editar", new Color(70, 130, 180));
        btnEditar.addActionListener(e -> editarNome());
        linhaIdentidade.add(lblDesc);
        linhaIdentidade.add(rotuloNome);
        linhaIdentidade.add(btnEditar);

        cartaoIdentidade.add(lblIdentTitulo, BorderLayout.NORTH);
        cartaoIdentidade.add(linhaIdentidade, BorderLayout.CENTER);

        // ── Cartão: estatísticas ─────────────────────────────────────────────
        JPanel cartaoEstat = criarCartao();
        cartaoEstat.setLayout(new BorderLayout(0, 10));

        JLabel lblEstatTitulo = new JLabel("Minhas Listas");
        lblEstatTitulo.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblEstatTitulo.setForeground(new Color(25, 25, 112));

        JPanel gridEstat = new JPanel(new GridLayout(1, 3, 12, 0));
        gridEstat.setOpaque(false);

        rotuloTotalFavoritos  = criarBadgeEstatistica("⭐", "Favoritos",      "0", new Color(255, 193,  7));
        rotuloTotalAssistidas = criarBadgeEstatistica("✅", "Já Assistidas",  "0", new Color( 34, 139, 34));
        rotuloTotalDeseja     = criarBadgeEstatistica("🕐", "Deseja Assistir","0", new Color( 50, 110, 190));

        gridEstat.add(rotuloTotalFavoritos);
        gridEstat.add(rotuloTotalAssistidas);
        gridEstat.add(rotuloTotalDeseja);

        cartaoEstat.add(lblEstatTitulo, BorderLayout.NORTH);
        cartaoEstat.add(gridEstat,      BorderLayout.CENTER);

        // ── Cartão: persistência ─────────────────────────────────────────────
        JPanel cartaoPersistencia = criarCartao();
        cartaoPersistencia.setLayout(new BorderLayout(0, 10));

        JLabel lblPersistTitulo = new JLabel("Dados Salvos");
        lblPersistTitulo.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblPersistTitulo.setForeground(new Color(25, 25, 112));

        JPanel painelStatusSalv = new JPanel(new BorderLayout(0, 4));
        painelStatusSalv.setOpaque(false);

        rotuloStatusSalvagem = new JLabel("Nenhuma gravação nesta sessão.");
        rotuloStatusSalvagem.setFont(new Font("SansSerif", Font.PLAIN, 13));
        rotuloStatusSalvagem.setForeground(new Color(130, 130, 160));

        rotuloCaminhoArquivo = new JLabel(repositorio.obterCaminhoArquivo());
        rotuloCaminhoArquivo.setFont(new Font("Monospaced", Font.PLAIN, 11));
        rotuloCaminhoArquivo.setForeground(new Color(100, 100, 130));

        JButton btnSalvarAgora = criarBotao("💾  Salvar agora", new Color(25, 25, 112));
        btnSalvarAgora.addActionListener(e -> salvarManualmente());

        painelStatusSalv.add(rotuloStatusSalvagem,   BorderLayout.NORTH);
        painelStatusSalv.add(rotuloCaminhoArquivo,   BorderLayout.CENTER);
        painelStatusSalv.add(btnSalvarAgora,         BorderLayout.SOUTH);

        cartaoPersistencia.add(lblPersistTitulo, BorderLayout.NORTH);
        cartaoPersistencia.add(painelStatusSalv, BorderLayout.CENTER);

        // ── Layout geral ─────────────────────────────────────────────────────
        JPanel coluna = new JPanel();
        coluna.setLayout(new BoxLayout(coluna, BoxLayout.Y_AXIS));
        coluna.setOpaque(false);
        coluna.add(cartaoIdentidade);
        coluna.add(Box.createVerticalStrut(14));
        coluna.add(cartaoEstat);
        coluna.add(Box.createVerticalStrut(14));
        coluna.add(cartaoPersistencia);
        coluna.add(Box.createVerticalGlue());

        add(rotuloTitulo, BorderLayout.NORTH);
        add(coluna,       BorderLayout.CENTER);

        atualizarInformacoes();
    }

    /**
     * Atualiza todos os rótulos dinâmicos com os valores atuais.
     */
    public void atualizarInformacoes() {
        rotuloNome.setText(usuario.obterNomeOuApelido());
        atualizarContadorBadge(rotuloTotalFavoritos,  usuario.obterFavoritos().size());
        atualizarContadorBadge(rotuloTotalAssistidas, usuario.obterJaAssistidas().size());
        atualizarContadorBadge(rotuloTotalDeseja,     usuario.obterDesejaAssistir().size());
        atualizarStatusSalvagem();
    }

    /**
     * Atualiza o rótulo de status da última salvagem.
     */
    private void atualizarStatusSalvagem() {
        String ultima = repositorio.obterTextoUltimaSalvagem();
        if (ultima != null) {
            rotuloStatusSalvagem.setText("✅  Última gravação: " + ultima);
            rotuloStatusSalvagem.setForeground(new Color(34, 120, 34));
        } else {
            rotuloStatusSalvagem.setText("Os dados são salvos automaticamente ao alterar as listas.");
            rotuloStatusSalvagem.setForeground(new Color(130, 130, 160));
        }
    }

    /**
     * Salva os dados manualmente e atualiza o status.
     */
    private void salvarManualmente() {
        try {
            repositorio.salvar(usuario);
            atualizarStatusSalvagem();
            UtilitarioExcecao.exibirInformacao(this, "✅  Dados salvos com sucesso!");
        } catch (ExcecaoPersistencia e) {
            UtilitarioExcecao.exibirErro(this, "Falha ao salvar: " + e.getMessage());
        }
    }

    /**
     * Abre diálogo para editar o nome/apelido do usuário.
     */
    private void editarNome() {
        String novoNome = JOptionPane.showInputDialog(
                this,
                "Informe seu novo nome ou apelido:",
                usuario.obterNomeOuApelido()
        );
        if (novoNome == null) return;
        try {
            servicoUsuario.atualizarNome(novoNome);
            usuario.definirNomeOuApelido(novoNome.trim());
            atualizarInformacoes();
            // Salva imediatamente após trocar o nome
            try { repositorio.salvar(usuario); atualizarStatusSalvagem(); }
            catch (ExcecaoPersistencia ignored) {}
            UtilitarioExcecao.exibirInformacao(this, "Nome atualizado com sucesso!");
        } catch (ExcecaoUsuario e) {
            UtilitarioExcecao.exibirExcecao(this, e);
        }
    }

    // ── Helpers de construção visual ─────────────────────────────────────────

    /** Cria um painel "cartão" com fundo branco e borda suave. */
    private JPanel criarCartao() {
        JPanel painel = new JPanel();
        painel.setBackground(Color.WHITE);
        painel.setAlignmentX(LEFT_ALIGNMENT);
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 215, 230), 1),
                new EmptyBorder(14, 16, 14, 16)
        ));
        return painel;
    }

    /**
     * Cria um painel de badge de estatística (ícone + rótulo + número grande).
     * Retorna o próprio painel — o número é atualizado via {@link #atualizarContadorBadge}.
     */
    private JLabel criarBadgeEstatistica(String icone, String descricao,
                                          String quantidade, Color cor) {
        // Empacotamos tudo num HTML para o JLabel renderizar
        JLabel label = new JLabel(
                "<html><div style='text-align:center'>"
                + "<div style='font-size:24pt;'>" + icone + "</div>"
                + "<div style='font-size:18pt; font-weight:bold; color:#"
                + Integer.toHexString(cor.getRGB()).substring(2) + ";'>" + quantidade + "</div>"
                + "<div style='font-size:9pt; color:#606080;'>" + descricao + "</div>"
                + "</div></html>"
        );
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBackground(new Color(245, 246, 252));
        label.setOpaque(true);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 220, 240), 1),
                new EmptyBorder(10, 8, 10, 8)
        ));
        // Guardamos cor e descrição via name para reconstruir no update
        label.setName(icone + "|" + descricao + "|" + cor.getRGB());
        return label;
    }

    /** Atualiza o número exibido no badge de estatística. */
    private void atualizarContadorBadge(JLabel badge, int quantidade) {
        String[] partes = badge.getName().split("\\|");
        String icone      = partes[0];
        String descricao  = partes[1];
        Color  cor        = new Color(Integer.parseInt(partes[2]));
        badge.setText(
                "<html><div style='text-align:center'>"
                + "<div style='font-size:24pt;'>" + icone + "</div>"
                + "<div style='font-size:18pt; font-weight:bold; color:#"
                + Integer.toHexString(cor.getRGB()).substring(2) + ";'>" + quantidade + "</div>"
                + "<div style='font-size:9pt; color:#606080;'>" + descricao + "</div>"
                + "</div></html>"
        );
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton b = new JButton(texto);
        b.setBackground(cor);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
}
