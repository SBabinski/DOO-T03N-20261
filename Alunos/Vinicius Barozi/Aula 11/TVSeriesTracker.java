import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TVSeriesTracker extends JFrame {
    private static final String DATA_FILE = "dados_series.json";
    private static final String API_URL = "https://api.tvmaze.com/search/shows?q=%s";
    private static final String[] LIST_TYPES = {"Favoritos", "Já assistidas", "Desejo assistir"};
    private static final String[] SORT_OPTIONS = {"Nome", "Nota", "Estado", "Data de estreia"};

    private JTextField campoBusca;
    private JButton botaoBuscar;
    private DefaultListModel<Show> modeloBusca;
    private JList<Show> listaBusca;
    private JTextArea detalhesBusca;
    private JComboBox<String> comboTipoLista;
    private JComboBox<String> comboOrdenacao;
    private DefaultListModel<Show> modeloLista;
    private JList<Show> listaMinhas;
    private JLabel labelUsuario;
    private AppData dados;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TVSeriesTracker());
    }

    public TVSeriesTracker() {
        setTitle("Rastreador de Séries de TV");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(920, 640);
        setLocationRelativeTo(null);

        dados = carregarDados();
        if (dados.currentUser == null || dados.currentUser.isBlank()) {
            dados.currentUser = "Usuário";
        }
        if (dados.users.isEmpty()) {
            UserData usuario = new UserData();
            usuario.name = dados.currentUser;
            dados.users.add(usuario);
        }

        criarInterface();
        setVisible(true);
    }

    private void criarInterface() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        painelPrincipal.add(criarPainelUsuario(), BorderLayout.NORTH);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Buscar Séries", criarAbaBusca());
        abas.addTab("Minhas Listas", criarAbaListas());
        painelPrincipal.add(abas, BorderLayout.CENTER);

        add(painelPrincipal);
    }

    private JPanel criarPainelUsuario() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painel.setBackground(new Color(70, 130, 180));

        labelUsuario = new JLabel("Usuário: " + dados.currentUser);
        labelUsuario.setForeground(Color.WHITE);
        labelUsuario.setFont(new Font("Arial", Font.BOLD, 14));

        JButton botaoTrocar = new JButton("Trocar Usuário");
        botaoTrocar.addActionListener(e -> trocarUsuario());

        painel.add(labelUsuario);
        painel.add(botaoTrocar);

        return painel;
    }

    private JPanel criarAbaBusca() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topo.add(new JLabel("Nome da série:"));

        campoBusca = new JTextField(26);
        topo.add(campoBusca);

        botaoBuscar = new JButton("Buscar");
        botaoBuscar.addActionListener(e -> executarBusca());
        topo.add(botaoBuscar);

        painel.add(topo, BorderLayout.NORTH);

        modeloBusca = new DefaultListModel<>();
        listaBusca = new JList<>(modeloBusca);
        listaBusca.setCellRenderer(new RenderizadorSerie());
        listaBusca.addListSelectionListener(e -> atualizarDetalhesBusca());

        detalhesBusca = new JTextArea();
        detalhesBusca.setEditable(false);
        detalhesBusca.setLineWrap(true);
        detalhesBusca.setWrapStyleWord(true);
        detalhesBusca.setText("Selecione uma série para ver os detalhes.");

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(listaBusca), new JScrollPane(detalhesBusca));
        split.setDividerLocation(360);
        painel.add(split, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        botoes.add(criarBotaoAdicionar("Favoritos", 0));
        botoes.add(criarBotaoAdicionar("Já assistidas", 1));
        botoes.add(criarBotaoAdicionar("Desejo assistir", 2));
        painel.add(botoes, BorderLayout.SOUTH);

        return painel;
    }

    private JButton criarBotaoAdicionar(String nome, int tipo) {
        JButton botao = new JButton(nome);
        botao.addActionListener(e -> adicionarSelecionada(tipo));
        return botao;
    }

    private JPanel criarAbaListas() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controles.add(new JLabel("Lista:"));

        comboTipoLista = new JComboBox<>(LIST_TYPES);
        comboTipoLista.addActionListener(e -> atualizarLista());
        controles.add(comboTipoLista);

        controles.add(new JLabel("Ordenar por:"));
        comboOrdenacao = new JComboBox<>(SORT_OPTIONS);
        comboOrdenacao.addActionListener(e -> atualizarLista());
        controles.add(comboOrdenacao);

        JButton botaoRemover = new JButton("Remover Série");
        botaoRemover.addActionListener(e -> removerSelecionada());
        controles.add(botaoRemover);

        painel.add(controles, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();
        listaMinhas = new JList<>(modeloLista);
        listaMinhas.setCellRenderer(new RenderizadorSerie());
        listaMinhas.addListSelectionListener(e -> atualizarDetalhesLista());

        JTextArea detalhesLista = new JTextArea();
        detalhesLista.setEditable(false);
        detalhesLista.setLineWrap(true);
        detalhesLista.setWrapStyleWord(true);
        detalhesLista.setText("Selecione uma série da lista para ver mais informações.");

        listaMinhas.putClientProperty("detalhesArea", detalhesLista);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(listaMinhas), new JScrollPane(detalhesLista));
        split.setDividerLocation(360);
        painel.add(split, BorderLayout.CENTER);

        atualizarLista();
        return painel;
    }

    private void executarBusca() {
        String termo = campoBusca.getText().trim();
        if (termo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um nome de série para buscar.");
            return;
        }

        botaoBuscar.setEnabled(false);
        modeloBusca.clear();
        detalhesBusca.setText("Buscando séries...");

        new Thread(() -> {
            List<Show> resultados;
            try {
                resultados = buscarSeries(termo);
            } catch (IOException ex) {
                SwingUtilities.invokeLater(() -> {
                    detalhesBusca.setText("Erro ao buscar séries: " + ex.getMessage());
                    botaoBuscar.setEnabled(true);
                });
                return;
            }

            SwingUtilities.invokeLater(() -> {
                if (resultados.isEmpty()) {
                    detalhesBusca.setText("Nenhuma série encontrada.");
                } else {
                    for (Show s : resultados) {
                        modeloBusca.addElement(s);
                    }
                }
                botaoBuscar.setEnabled(true);
            });
        }).start();
    }

    private void atualizarDetalhesBusca() {
        Show selecionado = listaBusca.getSelectedValue();
        if (selecionado == null) {
            detalhesBusca.setText("Selecione uma série para ver os detalhes.");
        } else {
            detalhesBusca.setText(selecionado.getFullDescription());
        }
    }

    private void adicionarSelecionada(int tipoLista) {
        Show selecionada = listaBusca.getSelectedValue();
        if (selecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma série nos resultados de busca.");
            return;
        }

        UserData usuario = getCurrentUser();
        List<Show> lista = getListByType(usuario, tipoLista);

        for (Show s : lista) {
            if (s.id == selecionada.id) {
                JOptionPane.showMessageDialog(this, "A série já está nessa lista.");
                return;
            }
        }

        lista.add(selecionada);
        salvarDados();
        atualizarLista();
        JOptionPane.showMessageDialog(this, "Série adicionada com sucesso.");
    }

    private void atualizarLista() {
        modeloLista.clear();
        UserData usuario = getCurrentUser();
        int tipo = comboTipoLista.getSelectedIndex();
        if (tipo < 0) tipo = 0;
        List<Show> lista = getListByType(usuario, tipo);

        ordenarShows(lista, (String) comboOrdenacao.getSelectedItem());
        for (Show s : lista) {
            modeloLista.addElement(s);
        }
    }

    private void atualizarDetalhesLista() {
        Show selecionado = listaMinhas.getSelectedValue();
        JTextArea area = (JTextArea) listaMinhas.getClientProperty("detalhesArea");
        if (area != null) {
            if (selecionado == null) {
                area.setText("Selecione uma série da lista para ver mais informações.");
            } else {
                area.setText(selecionado.getFullDescription());
            }
        }
    }

    private void removerSelecionada() {
        Show selecionada = listaMinhas.getSelectedValue();
        if (selecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma série para remover.");
            return;
        }

        UserData usuario = getCurrentUser();
        int tipo = comboTipoLista.getSelectedIndex();
        if (tipo < 0) tipo = 0;
        List<Show> lista = getListByType(usuario, tipo);

        lista.removeIf(s -> s.id == selecionada.id);
        salvarDados();
        atualizarLista();
    }

    private UserData getCurrentUser() {
        for (UserData u : dados.users) {
            if (u.name.equals(dados.currentUser)) {
                return u;
            }
        }
        UserData novo = new UserData();
        novo.name = dados.currentUser;
        dados.users.add(novo);
        return novo;
    }

    private List<Show> getListByType(UserData user, int tipo) {
        if (tipo == 0) {
            return user.favorites;
        }
        if (tipo == 1) {
            return user.watched;
        }
        return user.wantToWatch;
    }

    private void ordenarShows(List<Show> shows, String criterio) {
        if (criterio == null) {
            criterio = "Nome";
        }
        switch (criterio) {
            case "Nome":
                shows.sort(Comparator.comparing(s -> s.name == null ? "" : s.name.toLowerCase()));
                break;
            case "Nota":
                shows.sort(Comparator.comparingDouble((Show s) -> s.rating).reversed());
                break;
            case "Estado":
                shows.sort(Comparator.comparingInt((Show s) -> statusOrder(s.status)).thenComparing(s -> s.name == null ? "" : s.name));
                break;
            case "Data de estreia":
                shows.sort(Comparator.comparing(s -> s.premiered == null ? "" : s.premiered));
                break;
        }
    }

    private int statusOrder(String status) {
        if (status == null) {
            return 10;
        }
        String normalized = status.trim().toLowerCase();
        if (normalized.equals("ended") || normalized.equals("concluded") || normalized.equals("finalizado")) {
            return 0;
        }
        if (normalized.equals("running") || normalized.equals("em exibição") || normalized.equals("ainda transmitindo")) {
            return 1;
        }
        if (normalized.equals("cancelled") || normalized.equals("canceled") || normalized.equals("cancelada")) {
            return 2;
        }
        return 3;
    }

    private void trocarUsuario() {
        String novo = JOptionPane.showInputDialog(this, "Digite o nome do usuário:", dados.currentUser);
        if (novo != null && !novo.trim().isEmpty()) {
            dados.currentUser = novo.trim();
            if (getCurrentUser() == null) {
                UserData novoUsuario = new UserData();
                novoUsuario.name = dados.currentUser;
                dados.users.add(novoUsuario);
            }
            labelUsuario.setText("Usuário: " + dados.currentUser);
            salvarDados();
            atualizarLista();
        }
    }

    private List<Show> buscarSeries(String termo) throws IOException {
        String termoCodificado = URLEncoder.encode(termo, StandardCharsets.UTF_8);
        URL url = new URL(String.format(API_URL, termoCodificado));
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");
        conexao.setConnectTimeout(10000);
        conexao.setReadTimeout(10000);

        int codigo = conexao.getResponseCode();
        if (codigo != 200) {
            throw new IOException("Erro HTTP: " + codigo);
        }

        BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder resposta = new StringBuilder();
        String linha;
        while ((linha = leitor.readLine()) != null) {
            resposta.append(linha);
        }
        leitor.close();
        conexao.disconnect();

        return parseSearchResponse(resposta.toString());
    }

    private List<Show> parseSearchResponse(String json) {
        List<Show> results = new ArrayList<>();
        int pos = 0;
        while (true) {
            int inicio = json.indexOf("\"show\":", pos);
            if (inicio == -1) {
                break;
            }
            inicio = json.indexOf('{', inicio);
            if (inicio == -1) {
                break;
            }
            int fim = findMatchingBrace(json, inicio);
            if (fim == -1) {
                break;
            }
            String showJson = json.substring(inicio, fim + 1);
            Show show = parseShow(showJson);
            if (show != null) {
                results.add(show);
            }
            pos = fim + 1;
        }
        return results;
    }

    private int findMatchingBrace(String text, int start) {
        int depth = 0;
        boolean inString = false;
        for (int i = start; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '"' && (i == 0 || text.charAt(i - 1) != '\\')) {
                inString = !inString;
            }
            if (!inString) {
                if (c == '{') {
                    depth++;
                } else if (c == '}') {
                    depth--;
                    if (depth == 0) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    private Show parseShow(String json) {
        if (json == null || json.isBlank()) {
            return null;
        }
        Show show = new Show();
        show.id = extractInt(json, "id");
        show.name = extractString(json, "name");
        show.language = extractString(json, "language");
        show.status = extractString(json, "status");
        show.premiered = extractString(json, "premiered");
        show.ended = extractString(json, "ended");
        show.summary = stripHtml(extractString(json, "summary"));
        show.network = extractString(extractJsonObject(json, "network"), "name");
        if (show.network.isBlank()) {
            show.network = extractString(extractJsonObject(json, "webChannel"), "name");
        }
        show.rating = extractDouble(extractJsonObject(json, "rating"), "average");
        show.genres = extractStringArray(json, "genres");
        return show;
    }

    private String extractString(String json, String key) {
        if (json == null || key == null) {
            return "";
        }
        String busca = "\"" + key + "\":";
        int pos = json.indexOf(busca);
        if (pos == -1) {
            return "";
        }
        pos += busca.length();
        while (pos < json.length() && Character.isWhitespace(json.charAt(pos))) {
            pos++;
        }
        if (pos >= json.length()) {
            return "";
        }
        if (json.charAt(pos) == '"') {
            int fim = pos + 1;
            while (fim < json.length()) {
                char c = json.charAt(fim);
                if (c == '"' && json.charAt(fim - 1) != '\\') {
                    return json.substring(pos + 1, fim);
                }
                fim++;
            }
        }
        return "";
    }

    private int extractInt(String json, String key) {
        String valor = extractPrimitive(json, key);
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private double extractDouble(String json, String key) {
        if (json == null || key == null) {
            return 0.0;
        }
        String busca = "\"" + key + "\":";
        int pos = json.indexOf(busca);
        if (pos == -1) {
            return 0.0;
        }
        pos += busca.length();
        while (pos < json.length() && Character.isWhitespace(json.charAt(pos))) {
            pos++;
        }
        int fim = pos;
        while (fim < json.length() && (Character.isDigit(json.charAt(fim)) || json.charAt(fim) == '.')) {
            fim++;
        }
        try {
            return Double.parseDouble(json.substring(pos, fim));
        } catch (Exception e) {
            return 0.0;
        }
    }

    private String extractPrimitive(String json, String key) {
        if (json == null || key == null) {
            return "";
        }
        String busca = "\"" + key + "\":";
        int pos = json.indexOf(busca);
        if (pos == -1) {
            return "";
        }
        pos += busca.length();
        while (pos < json.length() && Character.isWhitespace(json.charAt(pos))) {
            pos++;
        }
        int fim = pos;
        while (fim < json.length() && json.charAt(fim) != ',' && json.charAt(fim) != '}' && json.charAt(fim) != ']') {
            fim++;
        }
        return json.substring(pos, fim).trim();
    }

    private String extractJsonObject(String json, String key) {
        if (json == null || key == null) {
            return null;
        }
        String busca = "\"" + key + "\":";
        int pos = json.indexOf(busca);
        if (pos == -1) {
            return null;
        }
        pos = json.indexOf('{', pos);
        if (pos == -1) {
            return null;
        }
        int fim = findMatchingBrace(json, pos);
        if (fim == -1) {
            return null;
        }
        return json.substring(pos, fim + 1);
    }

    private List<String> extractStringArray(String json, String key) {
        List<String> values = new ArrayList<>();
        if (json == null || key == null) {
            return values;
        }
        String busca = "\"" + key + "\":";
        int pos = json.indexOf(busca);
        if (pos == -1) {
            return values;
        }
        pos = json.indexOf('[', pos);
        if (pos == -1) {
            return values;
        }
        int fim = json.indexOf(']', pos);
        if (fim == -1) {
            return values;
        }
        String array = json.substring(pos + 1, fim);
        String[] partes = array.split(",");
        for (String parte : partes) {
            parte = parte.trim();
            if (parte.startsWith("\"") && parte.endsWith("\"") && parte.length() > 1) {
                values.add(parte.substring(1, parte.length() - 1));
            }
        }
        return values;
    }

    private String stripHtml(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replaceAll("<[^>]*>", "").trim();
    }

    private void salvarDados() {
        Path arquivo = Paths.get(DATA_FILE);
        try {
            Files.writeString(arquivo, toJson(dados), StandardCharsets.UTF_8);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar dados: " + e.getMessage());
        }
    }

    private AppData carregarDados() {
        Path arquivo = Paths.get(DATA_FILE);
        if (!Files.exists(arquivo)) {
            return new AppData();
        }
        try {
            String conteudo = Files.readString(arquivo, StandardCharsets.UTF_8);
            return parseAppData(conteudo);
        } catch (IOException e) {
            return new AppData();
        }
    }

    private AppData parseAppData(String json) {
        AppData app = new AppData();
        String nome = extractString(json, "currentUser");
        if (!nome.isBlank()) {
            app.currentUser = nome;
        }
        int pos = json.indexOf("\"users\":[");
        if (pos != -1) {
            pos = json.indexOf('[', pos);
            int fim = json.indexOf(']', pos);
            if (pos != -1 && fim != -1) {
                String array = json.substring(pos + 1, fim);
                int i = 0;
                while (i < array.length()) {
                    int inicio = array.indexOf('{', i);
                    if (inicio == -1) break;
                    int fimObj = findMatchingBrace(array, inicio);
                    if (fimObj == -1) break;
                    String obj = array.substring(inicio, fimObj + 1);
                    UserData usuario = parseUsuario(obj);
                    if (usuario != null) {
                        app.users.add(usuario);
                    }
                    i = fimObj + 1;
                }
            }
        }
        if (app.users.isEmpty()) {
            UserData usuario = new UserData();
            usuario.name = app.currentUser.isBlank() ? "Usuário" : app.currentUser;
            app.users.add(usuario);
        }
        if (app.currentUser.isBlank()) {
            app.currentUser = app.users.get(0).name;
        }
        return app;
    }

    private UserData parseUsuario(String json) {
        UserData usuario = new UserData();
        usuario.name = extractString(json, "name");
        usuario.favorites = parseShowList(json, "favorites");
        usuario.watched = parseShowList(json, "watched");
        usuario.wantToWatch = parseShowList(json, "wantToWatch");
        return usuario;
    }

    private List<Show> parseShowList(String json, String key) {
        List<Show> lista = new ArrayList<>();
        int pos = json.indexOf("\"" + key + "\":[");
        if (pos == -1) {
            return lista;
        }
        pos = json.indexOf('[', pos);
        int fim = json.indexOf(']', pos);
        if (pos == -1 || fim == -1) {
            return lista;
        }
        String array = json.substring(pos + 1, fim);
        int i = 0;
        while (i < array.length()) {
            int inicio = array.indexOf('{', i);
            if (inicio == -1) break;
            int fimObj = findMatchingBrace(array, inicio);
            if (fimObj == -1) break;
            String obj = array.substring(inicio, fimObj + 1);
            Show show = parseSavedShow(obj);
            if (show != null) {
                lista.add(show);
            }
            i = fimObj + 1;
        }
        return lista;
    }

    private Show parseSavedShow(String json) {
        Show show = new Show();
        show.id = extractInt(json, "id");
        show.name = extractString(json, "name");
        show.language = extractString(json, "language");
        show.status = extractString(json, "status");
        show.premiered = extractString(json, "premiered");
        show.ended = extractString(json, "ended");
        show.network = extractString(json, "network");
        show.summary = extractString(json, "summary");
        show.rating = extractDoubleSimples(json, "rating");
        show.genres = extractShowGenres(json);
        return show;
    }

    private double extractDoubleSimples(String json, String key) {
        String busca = "\"" + key + "\":";
        int pos = json.indexOf(busca);
        if (pos == -1) {
            return 0.0;
        }
        int inicio = json.indexOf(':', pos) + 1;
        int fim = inicio;
        while (fim < json.length() && (Character.isDigit(json.charAt(fim)) || json.charAt(fim) == '.')) {
            fim++;
        }
        try {
            return Double.parseDouble(json.substring(inicio, fim));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private List<String> extractShowGenres(String json) {
        List<String> generos = new ArrayList<>();
        int pos = json.indexOf("\"genres\":[");
        if (pos == -1) {
            return generos;
        }
        int inicio = json.indexOf('[', pos);
        int fim = json.indexOf(']', inicio);
        if (inicio == -1 || fim == -1) {
            return generos;
        }
        String array = json.substring(inicio + 1, fim);
        String[] partes = array.split(",");
        for (String parte : partes) {
            parte = parte.trim();
            if (parte.startsWith("\"") && parte.endsWith("\"") && parte.length() > 1) {
                generos.add(parte.substring(1, parte.length() - 1));
            }
        }
        return generos;
    }

    private String toJson(AppData app) {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("\"currentUser\":").append(toJsonString(app.currentUser)).append(',');
        sb.append("\"users\":");
        sb.append('[');
        for (int i = 0; i < app.users.size(); i++) {
            sb.append(userToJson(app.users.get(i)));
            if (i < app.users.size() - 1) {
                sb.append(',');
            }
        }
        sb.append(']');
        sb.append('}');
        return sb.toString();
    }

    private String userToJson(UserData user) {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("\"name\":").append(toJsonString(user.name)).append(',');
        sb.append("\"favorites\":").append(showsToJson(user.favorites)).append(',');
        sb.append("\"watched\":").append(showsToJson(user.watched)).append(',');
        sb.append("\"wantToWatch\":").append(showsToJson(user.wantToWatch));
        sb.append('}');
        return sb.toString();
    }

    private String showsToJson(List<Show> shows) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < shows.size(); i++) {
            sb.append(showToJson(shows.get(i)));
            if (i < shows.size() - 1) {
                sb.append(',');
            }
        }
        sb.append(']');
        return sb.toString();
    }

    private String showToJson(Show show) {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("\"id\":").append(show.id).append(',');
        sb.append("\"name\":").append(toJsonString(show.name)).append(',');
        sb.append("\"language\":").append(toJsonString(show.language)).append(',');
        sb.append("\"genres\":").append(stringListToJson(show.genres)).append(',');
        sb.append("\"rating\":").append(show.rating).append(',');
        sb.append("\"status\":").append(toJsonString(show.status)).append(',');
        sb.append("\"premiered\":").append(toJsonString(show.premiered)).append(',');
        sb.append("\"ended\":").append(toJsonString(show.ended)).append(',');
        sb.append("\"network\":").append(toJsonString(show.network)).append(',');
        sb.append("\"summary\":").append(toJsonString(show.summary));
        sb.append('}');
        return sb.toString();
    }

    private String stringListToJson(List<String> list) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < list.size(); i++) {
            sb.append(toJsonString(list.get(i)));
            if (i < list.size() - 1) {
                sb.append(',');
            }
        }
        sb.append(']');
        return sb.toString();
    }

    private String toJsonString(String value) {
        if (value == null) {
            return "\"\"";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('"');
        for (char c : value.toCharArray()) {
            switch (c) {
                case '"': sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\t': sb.append("\\t"); break;
                default: sb.append(c);
            }
        }
        sb.append('"');
        return sb.toString();
    }

    private static class AppData {
        String currentUser = "";
        List<UserData> users = new ArrayList<>();
    }

    private static class UserData {
        String name = "";
        List<Show> favorites = new ArrayList<>();
        List<Show> watched = new ArrayList<>();
        List<Show> wantToWatch = new ArrayList<>();
    }

    private static class Show {
        int id;
        String name = "";
        String language = "";
        List<String> genres = new ArrayList<>();
        double rating;
        String status = "";
        String premiered = "";
        String ended = "";
        String network = "";
        String summary = "";

        String getDisplayLabel() {
            String estado = "";
            if (status != null && !status.isBlank() && !status.equalsIgnoreCase("ended")) {
                estado = " [" + status + "]";
            }
            return name + estado + " - Nota: " + String.format("%.1f", rating);
        }

        String getFullDescription() {
            StringBuilder sb = new StringBuilder();
            sb.append("Nome: ").append(name).append("\n");
            sb.append("Idioma: ").append(language.isBlank() ? "-" : language).append("\n");
            sb.append("Gêneros: ").append(genres.isEmpty() ? "-" : String.join(", ", genres)).append("\n");
            sb.append("Nota: ").append(rating > 0 ? String.format("%.1f", rating) : "-").append("\n");
            sb.append("Estado: ").append(status.isBlank() ? "-" : status).append("\n");
            sb.append("Estreia: ").append(premiered.isBlank() ? "-" : premiered).append("\n");
            sb.append("Término: ").append(ended.isBlank() ? "-" : ended).append("\n");
            sb.append("Rede: ").append(network.isBlank() ? "-" : network).append("\n\n");
            sb.append("Resumo:\n");
            sb.append(summary.isBlank() ? "Sem resumo disponível." : summary).append("\n");
            return sb.toString();
        }
    }

    private static class RenderizadorSerie extends JLabel implements ListCellRenderer<Show> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Show> list, Show value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(value == null ? "" : value.getDisplayLabel());
            setOpaque(true);
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            return this;
        }
    }
}