import java.io.File;
import java.io.Writer;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

// Salva e carrega os dados do usuario num arquivo JSON (dados.json).
// E isso que faz "guardar entre abrir e fechar o programa".
//
// formato do dados.json:
// { "nome":"luiz", "favoritos":[...], "assistidas":[...], "desejaAssistir":[...] }
//
// 1a vez (arquivo nao existe) = crio um usuario com series ja prontas.

public class Persistencia {

    // o arquivo fica na pasta onde o programa roda
    private static final String NOME_ARQUIVO = "dados.json";

    private final File arquivo;

    public Persistencia() {
        this.arquivo = new File(NOME_ARQUIVO);
    }

    // construtor extra pra escolher o caminho (ajuda em teste)
    public Persistencia(String caminho) {
        this.arquivo = new File(caminho);
    }

    // pega o usuario todo e grava no arquivo
    public void salvar(Usuario usuario) throws PersistenciaException {
        Map<String, Object> raiz = new LinkedHashMap<String, Object>();
        raiz.put("nome", usuario.getNome());
        raiz.put("favoritos", listaParaJson(usuario.getFavoritos()));
        raiz.put("assistidas", listaParaJson(usuario.getAssistidas()));
        raiz.put("desejaAssistir", listaParaJson(usuario.getDesejaAssistir()));

        String json = Json.escrever(raiz);

        // try-with-resources = fecha o arquivo sozinho, mesmo se der erro
        try (Writer escritor = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(arquivo), StandardCharsets.UTF_8))) {
            escritor.write(json);
        } catch (IOException e) {
            throw new PersistenciaException("Nao foi possivel salvar os dados: " + e.getMessage(), e);
        }
    }

    // le o usuario do arquivo. nao existe? crio com dados prontos e ja salvo
    public Usuario carregar() throws PersistenciaException {
        if (!arquivo.exists()) {
            Usuario novo = criarUsuarioPreCarregado();
            salvar(novo); // ja deixo gravado pra proxima
            return novo;
        }

        String conteudo = lerArquivo();

        // arquivo vazio = trato como 1a vez
        if (conteudo.trim().isEmpty()) {
            Usuario novo = criarUsuarioPreCarregado();
            salvar(novo);
            return novo;
        }

        try {
            Object raizObj = Json.parse(conteudo);
            if (!(raizObj instanceof Map)) {
                throw new PersistenciaException("Formato do arquivo de dados invalido.");
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> raiz = (Map<String, Object>) raizObj;

            String nome = "luiz";
            Object nomeObj = raiz.get("nome");
            if (nomeObj != null) {
                nome = nomeObj.toString();
            }

            Usuario usuario = new Usuario(nome);
            usuario.setFavoritos(jsonParaLista(raiz.get("favoritos")));
            usuario.setAssistidas(jsonParaLista(raiz.get("assistidas")));
            usuario.setDesejaAssistir(jsonParaLista(raiz.get("desejaAssistir")));
            return usuario;

        } catch (JsonException e) {
            // arquivo corrompido: aviso pela excecao (a tela mostra) em vez de quebrar
            throw new PersistenciaException("Arquivo de dados corrompido. "
                    + "Sera necessario recomecar. Detalhe: " + e.getMessage(), e);
        }
    }

    // ---- ajudantes ----

    // transforma a lista de Serie em lista de Map (pronta pro Json)
    private List<Object> listaParaJson(List<Serie> series) {
        List<Object> lista = new ArrayList<Object>();
        for (Serie s : series) {
            lista.add(ConversorSerie.paraMapa(s));
        }
        return lista;
    }

    // caminho contrario: do que veio do arquivo, remonta a lista de Serie
    private List<Serie> jsonParaLista(Object valor) {
        List<Serie> series = new ArrayList<Serie>();
        if (valor instanceof List) {
            for (Object item : (List<?>) valor) {
                if (item instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Serie s = ConversorSerie.deMapaLocal((Map<String, Object>) item);
                    if (s != null) {
                        series.add(s);
                    }
                }
            }
        }
        return series;
    }

    // le o arquivo inteiro e devolve como texto
    private String lerArquivo() throws PersistenciaException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(arquivo), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                sb.append(linha);
            }
        } catch (IOException e) {
            throw new PersistenciaException("Nao foi possivel ler o arquivo de dados: " + e.getMessage(), e);
        }
        return sb.toString();
    }

    // monta o usuario inicial com series ja nas listas (dados pre-carregados que o trabalho pede).
    // os dados sao reais dessas series no TVMaze.
    private Usuario criarUsuarioPreCarregado() {
        Usuario usuario = new Usuario("luiz");

        Serie breakingBad = new Serie(
                169, "Breaking Bad", "English",
                criarGeneros("Drama", "Crime", "Thriller"),
                9.2, EstadoSerie.CONCLUIDA,
                "2008-01-20", "2013-09-29", "AMC");

        Serie got = new Serie(
                82, "Game of Thrones", "English",
                criarGeneros("Drama", "Adventure", "Fantasy"),
                8.9, EstadoSerie.CONCLUIDA,
                "2011-04-17", "2019-05-19", "HBO");

        Serie theLastOfUs = new Serie(
                52595, "The Last of Us", "English",
                criarGeneros("Drama", "Action", "Science-Fiction"),
                8.5, EstadoSerie.TRANSMITINDO,
                "2023-01-15", "", "HBO");

        Serie strangerThings = new Serie(
                2993, "Stranger Things", "English",
                criarGeneros("Drama", "Fantasy", "Science-Fiction"),
                8.6, EstadoSerie.TRANSMITINDO,
                "2016-07-15", "", "Netflix");

        Serie theOffice = new Serie(
                526, "The Office", "English",
                criarGeneros("Comedy"),
                8.6, EstadoSerie.CONCLUIDA,
                "2005-03-24", "2013-05-16", "NBC");

        // espalho as series nas 3 listas como exemplo
        usuario.adicionarFavorito(breakingBad);
        usuario.adicionarFavorito(got);

        usuario.adicionarAssistida(breakingBad);
        usuario.adicionarAssistida(theOffice);

        usuario.adicionarDesejaAssistir(theLastOfUs);
        usuario.adicionarDesejaAssistir(strangerThings);

        return usuario;
    }

    // atalho pra montar a lista de generos sem escrever muito
    private List<String> criarGeneros(String... generos) {
        List<String> lista = new ArrayList<String>();
        for (String g : generos) {
            lista.add(g);
        }
        return lista;
    }
}
