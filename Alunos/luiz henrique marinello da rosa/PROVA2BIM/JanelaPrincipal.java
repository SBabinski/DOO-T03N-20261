import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.util.List;

// A janela principal (a tela que aparece). Feita em Java Swing.
//
// como ela e organizada:
//   - topo: titulo + saudacao + botao de trocar usuario
//   - 4 abas: Buscar (usa a API de verdade), Favoritos, Ja assistidas, Deseja assistir
//   - cada aba de lista tem: a lista em cartoes, ordenar, ver detalhes e remover
//
// o visual vem do Tema (cores/botoes) e do CartaoSerieRenderer (os cartoes).
// e aqui que fica o try/catch de tudo, pra mostrar aviso e o programa nao fechar.

public class JanelaPrincipal extends JFrame {

    private final Usuario usuario;
    private final Persistencia persistencia;
    private final TVMazeAPI api;

    // o texto da saudacao (com o nome)
    private JLabel rotuloUsuario;

    // ---- coisas da aba de busca ----
    private JTextField campoBusca;
    private DefaultListModel<Serie> modeloResultados;
    private JList<Serie> listaResultados;

    // ---- os "modelos" (dados) das 3 listas ----
    private DefaultListModel<Serie> modeloFavoritos;
    private DefaultListModel<Serie> modeloAssistidas;
    private DefaultListModel<Serie> modeloDeseja;

    private JList<Serie> listaFavoritos;
    private JList<Serie> listaAssistidas;
    private JList<Serie> listaDeseja;

    public JanelaPrincipal(Usuario usuario, Persistencia persistencia) {
        this.usuario = usuario;
        this.persistencia = persistencia;
        this.api = new TVMazeAPI();

        configurarJanela();
        adicionarCabecalho();
        adicionarAbas();

        // ao fechar a janela, salvo os dados antes de sair
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                salvarDados();
                dispose();
                System.exit(0);
            }
        });
    }

    // ajustes basicos da janela
    private void configurarJanela() {
        setTitle("Acompanhador de Series de TV");
        setSize(820, 600);
        setMinimumSize(new Dimension(680, 480));
        setLocationRelativeTo(null); // abre no meio da tela
        // eu mesmo trato o fechar (pra salvar antes)
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(Tema.FUNDO);
    }

    // ---- topo: titulo + saudacao + botao trocar ----
    private void adicionarCabecalho() {
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(Tema.FUNDO);
        topo.setBorder(Tema.margem(18, 22, 12, 22));

        // esquerda: titulo e saudacao, um embaixo do outro
        JPanel textoEsq = new JPanel();
        textoEsq.setOpaque(false);
        textoEsq.setLayout(new BoxLayout(textoEsq, BoxLayout.Y_AXIS));

        JLabel titulo = Tema.criarTitulo("Minhas Series");
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        rotuloUsuario = Tema.criarSubtitulo("");
        rotuloUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        atualizarRotuloUsuario();

        textoEsq.add(titulo);
        textoEsq.add(Box.createVerticalStrut(4));
        textoEsq.add(rotuloUsuario);

        // direita: botao de trocar usuario
        JPanel dir = new JPanel(new GridBagLayout());
        dir.setOpaque(false);
        JButton botaoTrocar = Tema.botaoSecundario("Trocar usuario");
        botaoTrocar.addActionListener(e -> trocarUsuario());
        dir.add(botaoTrocar);

        topo.add(textoEsq, BorderLayout.WEST);
        topo.add(dir, BorderLayout.EAST);

        add(topo, BorderLayout.NORTH);
    }

    // atualiza o texto "Ola, fulano"
    private void atualizarRotuloUsuario() {
        rotuloUsuario.setText("Ola, " + usuario.getNome() + " - organize o que voce assiste");
    }

    // abre a caixinha pra trocar o nome
    private void trocarUsuario() {
        String novo = Dialogos.pedirTexto(
                this, "Trocar usuario", "Digite seu nome ou apelido:", usuario.getNome());
        // cancelou? novo vem null e nao faco nada
        if (novo != null && !novo.trim().isEmpty()) {
            usuario.setNome(novo);
            atualizarRotuloUsuario();
            salvarDados();
        }
    }

    // ---- monta as 4 abas ----
    private void adicionarAbas() {
        JTabbedPane abas = new JTabbedPane();
        abas.setBackground(Tema.FUNDO);
        abas.setForeground(Tema.TEXTO);
        abas.setFont(Tema.FONTE_NEGRITO);
        estilizarAbas(abas);

        abas.addTab("  Buscar  ", criarAbaBusca());
        abas.addTab("  Favoritos  ", criarAbaFavoritos());
        abas.addTab("  Ja assistidas  ", criarAbaAssistidas());
        abas.addTab("  Deseja assistir  ", criarAbaDeseja());

        // uma margem em volta das abas
        JPanel envolto = new JPanel(new BorderLayout());
        envolto.setBackground(Tema.FUNDO);
        envolto.setBorder(Tema.margem(0, 22, 18, 22));
        envolto.add(abas, BorderLayout.CENTER);

        add(envolto, BorderLayout.CENTER);

        // ja preenche as 3 listas
        recarregarListas();
    }

    // pinta as abas com as cores do tema (o padrao do Swing e cinza feio)
    private void estilizarAbas(JTabbedPane abas) {
        abas.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                lightHighlight = Tema.BORDA;
                shadow = Tema.FUNDO;
                darkShadow = Tema.FUNDO;
                focus = Tema.DESTAQUE;
            }

            @Override
            protected void paintTabBackground(Graphics g, int placa, int indice,
                                              int x, int y, int w, int h, boolean selecionada) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(selecionada ? Tema.DESTAQUE : Tema.FUNDO_PAINEL);
                g2.fillRoundRect(x + 2, y + 4, w - 4, h - 4, 14, 14);
                g2.dispose();
            }

            @Override
            protected void paintTabBorder(Graphics g, int placa, int indice,
                                          int x, int y, int w, int h, boolean selecionada) {
                // sem borda
            }

            @Override
            protected void paintContentBorder(Graphics g, int placa, int indiceSel) {
                // sem borda em volta
            }

            @Override
            protected void paintText(Graphics g, int placa, Font fonte, FontMetrics metrica,
                                     int indice, String titulo, Rectangle ret, boolean selecionada) {
                g.setColor(selecionada ? Tema.TEXTO : Tema.TEXTO_FRACO);
                g.setFont(fonte);
                int tx = ret.x + (ret.width - metrica.stringWidth(titulo)) / 2;
                int ty = ret.y + ((ret.height + metrica.getAscent() - metrica.getDescent()) / 2);
                g.drawString(titulo, tx, ty);
            }

            @Override
            protected Insets getTabInsets(int placa, int indice) {
                return new Insets(8, 6, 8, 6);
            }
        });
    }

    // ---- ABA 1: busca ----
    private JPanel criarAbaBusca() {
        JPanel painel = criarPainelBase();

        // linha de cima: campo + botao buscar
        JPanel linhaBusca = new JPanel(new BorderLayout(10, 0));
        linhaBusca.setOpaque(false);
        linhaBusca.setBorder(Tema.margem(0, 0, 14, 0));

        campoBusca = Tema.criarCampo();
        JButton botaoBuscar = Tema.botaoPrimario("Buscar");

        // busca no clique e no Enter
        botaoBuscar.addActionListener(e -> realizarBusca());
        campoBusca.addActionListener(e -> realizarBusca());

        JLabel rotulo = Tema.criarRotulo("Nome da serie: ");
        linhaBusca.add(rotulo, BorderLayout.WEST);
        linhaBusca.add(campoBusca, BorderLayout.CENTER);
        linhaBusca.add(botaoBuscar, BorderLayout.EAST);

        // a lista de resultados no meio
        modeloResultados = new DefaultListModel<Serie>();
        listaResultados = criarListaEstilizada(modeloResultados);
        JScrollPane rolagem = criarRolagem(listaResultados);

        // botoes de baixo
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        botoes.setOpaque(false);
        botoes.setBorder(Tema.margem(14, 0, 0, 0));

        JButton verDetalhes = Tema.botaoSecundario("Ver detalhes");
        verDetalhes.addActionListener(e -> verDetalhesDe(listaResultados));

        JButton addFav = Tema.botaoPrimario("+ Favoritos");
        addFav.addActionListener(e -> adicionarSelecionada(listaResultados, Lista.FAVORITOS));

        JButton addAssistida = Tema.botaoPrimario("+ Assistidas");
        addAssistida.addActionListener(e -> adicionarSelecionada(listaResultados, Lista.ASSISTIDAS));

        JButton addDeseja = Tema.botaoPrimario("+ Deseja assistir");
        addDeseja.addActionListener(e -> adicionarSelecionada(listaResultados, Lista.DESEJA));

        botoes.add(verDetalhes);
        botoes.add(addFav);
        botoes.add(addAssistida);
        botoes.add(addDeseja);

        painel.add(linhaBusca, BorderLayout.NORTH);
        painel.add(rolagem, BorderLayout.CENTER);
        painel.add(botoes, BorderLayout.SOUTH);

        return painel;
    }

    // ---- abas 2, 3 e 4 (favoritos / assistidas / deseja) ----
    // sao quase iguais, entao uso um metodo generico (criarPainelLista)

    private JPanel criarAbaFavoritos() {
        modeloFavoritos = new DefaultListModel<Serie>();
        listaFavoritos = criarListaEstilizada(modeloFavoritos);
        return criarPainelLista(listaFavoritos, Lista.FAVORITOS);
    }

    private JPanel criarAbaAssistidas() {
        modeloAssistidas = new DefaultListModel<Serie>();
        listaAssistidas = criarListaEstilizada(modeloAssistidas);
        return criarPainelLista(listaAssistidas, Lista.ASSISTIDAS);
    }

    private JPanel criarAbaDeseja() {
        modeloDeseja = new DefaultListModel<Serie>();
        listaDeseja = criarListaEstilizada(modeloDeseja);
        return criarPainelLista(listaDeseja, Lista.DESEJA);
    }

    // monta o painel de uma aba de lista (ordenar em cima, lista no meio, botoes embaixo)
    private JPanel criarPainelLista(JList<Serie> lista, Lista qual) {
        JPanel painel = criarPainelBase();

        JScrollPane rolagem = criarRolagem(lista);

        // cima: ordenar por...
        JPanel linhaOrdenar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        linhaOrdenar.setOpaque(false);
        linhaOrdenar.setBorder(Tema.margem(0, 0, 14, 0));
        linhaOrdenar.add(Tema.criarRotulo("Ordenar por: "));

        JComboBox<OrdenadorSeries.Criterio> combo =
                new JComboBox<OrdenadorSeries.Criterio>(OrdenadorSeries.Criterio.values());
        estilizarCombo(combo);

        JButton botaoOrdenar = Tema.botaoSecundario("Ordenar");
        botaoOrdenar.addActionListener(e -> {
            OrdenadorSeries.Criterio criterio =
                    (OrdenadorSeries.Criterio) combo.getSelectedItem();
            ordenarLista(qual, criterio);
        });

        linhaOrdenar.add(combo);
        linhaOrdenar.add(botaoOrdenar);

        // baixo: ver detalhes e remover
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        botoes.setOpaque(false);
        botoes.setBorder(Tema.margem(14, 0, 0, 0));

        JButton verDetalhes = Tema.botaoSecundario("Ver detalhes");
        verDetalhes.addActionListener(e -> verDetalhesDe(lista));

        JButton remover = Tema.botaoPerigo("Remover da lista");
        remover.addActionListener(e -> removerSelecionada(lista, qual));

        botoes.add(verDetalhes);
        botoes.add(remover);

        painel.add(linhaOrdenar, BorderLayout.NORTH);
        painel.add(rolagem, BorderLayout.CENTER);
        painel.add(botoes, BorderLayout.SOUTH);

        return painel;
    }

    // ---- ajudantes de visual ----

    // o painel de fundo de cada aba
    private JPanel criarPainelBase() {
        JPanel painel = new JPanel(new BorderLayout(0, 0));
        painel.setBackground(Tema.FUNDO_PAINEL);
        painel.setBorder(Tema.margem(18));
        return painel;
    }

    // cria a JList ja com os cartoes (CartaoSerieRenderer) e as cores
    private JList<Serie> criarListaEstilizada(DefaultListModel<Serie> modelo) {
        JList<Serie> lista = new JList<Serie>(modelo);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setCellRenderer(new CartaoSerieRenderer());
        lista.setBackground(Tema.FUNDO_PAINEL);
        lista.setFixedCellHeight(64);          // altura de cada cartao
        lista.setFixedCellWidth(-1);
        lista.setBorder(Tema.margem(0));
        return lista;
    }

    // poe a lista numa barra de rolagem com as cores do tema
    private JScrollPane criarRolagem(JList<Serie> lista) {
        JScrollPane rolagem = new JScrollPane(lista);
        rolagem.setBorder(BorderFactory.createLineBorder(Tema.BORDA, 1, true));
        rolagem.getViewport().setBackground(Tema.FUNDO_PAINEL);
        rolagem.setBackground(Tema.FUNDO_PAINEL);
        return rolagem;
    }

    // poe as cores do tema no combo de ordenar
    private void estilizarCombo(JComboBox<?> combo) {
        combo.setBackground(Tema.CARTAO);
        combo.setForeground(Tema.TEXTO);
        combo.setFont(Tema.FONTE_NORMAL);
    }

    // ---- acoes (o que os botoes fazem) ----

    // pra saber em qual lista estou mexendo
    private enum Lista { FAVORITOS, ASSISTIDAS, DESEJA }

    // busca na API quando clico Buscar
    private void realizarBusca() {
        String termo = campoBusca.getText();

        if (termo == null || termo.trim().isEmpty()) {
            Dialogos.aviso(this, "Aviso",
                    "Digite o nome de uma serie para buscar.");
            return;
        }

        // cursor de "carregando" enquanto busca
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            List<Serie> encontradas = api.buscarPorNome(termo);

            modeloResultados.clear();
            if (encontradas.isEmpty()) {
                Dialogos.info(this, "Busca",
                        "Nenhuma serie encontrada para \"" + termo + "\".");
            } else {
                for (Serie s : encontradas) {
                    modeloResultados.addElement(s);
                }
            }

        } catch (ApiException e) {
            // erro de internet/API: avisa e segue de boa
            Dialogos.erro(this, "Erro ao acessar a API", e.getMessage());
        } catch (JsonException e) {
            Dialogos.erro(this, "Erro de dados",
                    "A resposta da API veio em um formato inesperado.\n" + e.getMessage());
        } catch (Exception e) {
            // qualquer outro erro inesperado tambem nao derruba o app
            Dialogos.erro(this, "Erro",
                    "Ocorreu um erro inesperado durante a busca:\n" + e.getMessage());
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    // mostra os detalhes da serie selecionada
    private void verDetalhesDe(JList<Serie> lista) {
        Serie s = lista.getSelectedValue();
        if (s == null) {
            Dialogos.aviso(this, "Aviso",
                    "Selecione uma serie na lista primeiro.");
            return;
        }
        DetalhesSerie.mostrar(this, s);
    }

    // adiciona a serie selecionada (da busca) numa das 3 listas
    private void adicionarSelecionada(JList<Serie> origem, Lista destino) {
        Serie s = origem.getSelectedValue();
        if (s == null) {
            Dialogos.aviso(this, "Aviso",
                    "Selecione uma serie na lista primeiro.");
            return;
        }

        boolean adicionou;
        String nomeLista;

        switch (destino) {
            case FAVORITOS:
                adicionou = usuario.adicionarFavorito(s);
                nomeLista = "Favoritos";
                break;
            case ASSISTIDAS:
                adicionou = usuario.adicionarAssistida(s);
                nomeLista = "Ja assistidas";
                break;
            case DESEJA:
                adicionou = usuario.adicionarDesejaAssistir(s);
                nomeLista = "Deseja assistir";
                break;
            default:
                return;
        }

        if (adicionou) {
            recarregarListas();
            salvarDados();
            Dialogos.info(this, "Pronto",
                    "\"" + s.getNome() + "\" adicionada a " + nomeLista + ".");
        } else {
            Dialogos.aviso(this, "Aviso",
                    "\"" + s.getNome() + "\" ja esta em " + nomeLista + ".");
        }
    }

    // remove a serie selecionada de uma lista (pedindo confirmacao)
    private void removerSelecionada(JList<Serie> lista, Lista qual) {
        Serie s = lista.getSelectedValue();
        if (s == null) {
            Dialogos.aviso(this, "Aviso",
                    "Selecione uma serie para remover.");
            return;
        }

        boolean confirmou = Dialogos.confirmar(this, "Confirmar",
                "Remover \"" + s.getNome() + "\" desta lista?");

        if (!confirmou) {
            return;
        }

        switch (qual) {
            case FAVORITOS:  usuario.removerFavorito(s);      break;
            case ASSISTIDAS: usuario.removerAssistida(s);     break;
            case DESEJA:     usuario.removerDesejaAssistir(s); break;
        }

        recarregarListas();
        salvarDados();
    }

    // ordena a lista escolhida e atualiza a tela
    private void ordenarLista(Lista qual, OrdenadorSeries.Criterio criterio) {
        switch (qual) {
            case FAVORITOS:
                OrdenadorSeries.ordenar(usuario.getFavoritos(), criterio);
                break;
            case ASSISTIDAS:
                OrdenadorSeries.ordenar(usuario.getAssistidas(), criterio);
                break;
            case DESEJA:
                OrdenadorSeries.ordenar(usuario.getDesejaAssistir(), criterio);
                break;
        }
        recarregarListas();
        salvarDados();
    }

    // joga os dados do Usuario de volta nas 3 listas da tela
    private void recarregarListas() {
        preencherModelo(modeloFavoritos, usuario.getFavoritos());
        preencherModelo(modeloAssistidas, usuario.getAssistidas());
        preencherModelo(modeloDeseja, usuario.getDesejaAssistir());
    }

    // limpa e preenche um modelo de lista
    private void preencherModelo(DefaultListModel<Serie> modelo, List<Serie> series) {
        if (modelo == null) {
            return;
        }
        modelo.clear();
        for (Serie s : series) {
            modelo.addElement(s);
        }
    }

    // salva no disco. se der erro, avisa em vez de quebrar
    private void salvarDados() {
        try {
            persistencia.salvar(usuario);
        } catch (PersistenciaException e) {
            Dialogos.erro(this, "Erro ao salvar",
                    "Nao foi possivel salvar seus dados:\n" + e.getMessage());
        }
    }
}
