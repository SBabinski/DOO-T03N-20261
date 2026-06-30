
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Repositório local que persiste os usuários e suas listas em um arquivo JSON. */
public final class PersistenciaJson {
    private static final int VERSAO = 2;
    private final Path arquivo;

    public PersistenciaJson() {
        this(Path.of(System.getProperty("user.home"), ".seriefinder", "dados.json"));
    }

    public PersistenciaJson(Path arquivo) {
        if (arquivo == null) {
            throw new IllegalArgumentException("O caminho do arquivo deve ser informado.");
        }
        this.arquivo = arquivo.toAbsolutePath().normalize();
    }

    public Path getArquivo() {
        return arquivo;
    }

    /**
     * Carrega todos os usuários salvos. Aceita tanto o formato novo (lista de
     * usuários) quanto o formato antigo de usuário único, migrando-o.
     */
    public synchronized List<Usuario> carregar() throws PersistenciaException {
        if (!Files.exists(arquivo)) {
            return new ArrayList<>();
        }
        try {
            String conteudo = Files.readString(arquivo, StandardCharsets.UTF_8);
            if (conteudo.isBlank()) {
                return new ArrayList<>();
            }
            return converterUsuarios(JsonUtil.parse(conteudo));
        } catch (IOException | RuntimeException erro) {
            throw new PersistenciaException(
                    "Não foi possível ler os dados salvos em " + arquivo + ".", erro);
        }
    }

    public synchronized void salvar(List<Usuario> usuarios) throws PersistenciaException {
        if (usuarios == null) {
            throw new IllegalArgumentException("A lista de usuários deve ser informada.");
        }
        Path temporario = arquivo.resolveSibling(arquivo.getFileName() + ".tmp");
        try {
            Path pasta = arquivo.getParent();
            if (pasta != null) {
                Files.createDirectories(pasta);
            }
            Files.writeString(temporario, JsonUtil.stringify(converterParaJson(usuarios)),
                    StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
            try {
                Files.move(temporario, arquivo, StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException ignorado) {
                Files.move(temporario, arquivo, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException | RuntimeException erro) {
            try {
                Files.deleteIfExists(temporario);
            } catch (IOException ignorado) {
                // O erro original é o que interessa ao usuário.
            }
            throw new PersistenciaException(
                    "Não foi possível salvar os dados em " + arquivo + ".", erro);
        }
    }

    private Map<String, Object> converterParaJson(List<Usuario> usuarios) {
        Map<String, Object> raiz = new LinkedHashMap<>();
        raiz.put("versao", VERSAO);
        List<Object> usuariosJson = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuariosJson.add(converterUsuarioParaJson(usuario));
        }
        raiz.put("usuarios", usuariosJson);
        return raiz;
    }

    private Map<String, Object> converterUsuarioParaJson(Usuario usuario) {
        Map<String, Object> usuarioJson = new LinkedHashMap<>();
        usuarioJson.put("apelido", usuario.getApelido());
        Map<String, Object> listas = new LinkedHashMap<>();
        for (TipoLista tipo : TipoLista.values()) {
            List<Object> itens = new ArrayList<>();
            for (Serie serie : usuario.getSeries(tipo)) {
                itens.add(converterSerieParaJson(serie));
            }
            listas.put(tipo.getChaveJson(), itens);
        }
        usuarioJson.put("listas", listas);
        return usuarioJson;
    }

    private Map<String, Object> converterSerieParaJson(Serie serie) {
        Map<String, Object> json = new LinkedHashMap<>();
        json.put("id", serie.getId());
        json.put("nome", serie.getNome());
        json.put("idioma", serie.getIdioma());
        json.put("generos", serie.getGeneros());
        json.put("nota", serie.getNota());
        json.put("status", serie.getStatusTvMaze());
        json.put("estreia", dataParaTexto(serie.getDataEstreia()));
        json.put("termino", dataParaTexto(serie.getDataTermino()));
        json.put("emissora", serie.getEmissora());
        json.put("resumo", serie.getResumo());
        json.put("imagem", serie.getUrlImagem());
        return json;
    }

    private List<Usuario> converterUsuarios(Object valor) {
        Map<String, Object> raiz = comoMapa(valor, "raiz");
        List<Usuario> usuarios = new ArrayList<>();
        Object listaUsuarios = raiz.get("usuarios");
        if (listaUsuarios instanceof List<?>) {
            // Formato novo: lista de usuários.
            for (Object item : (List<?>) listaUsuarios) {
                usuarios.add(converterUsuario(comoMapa(item, "usuário")));
            }
        } else if (raiz.get("usuario") != null) {
            // Formato antigo (versão 1): um único usuário. Migrado ao próximo salvar.
            usuarios.add(converterUsuario(comoMapa(raiz.get("usuario"), "usuario")));
        }
        return usuarios;
    }

    private Usuario converterUsuario(Map<String, Object> usuarioJson) {
        Usuario usuario = new Usuario(comoTexto(usuarioJson.get("apelido"), "Visitante"));
        Map<String, Object> listas = comoMapa(usuarioJson.get("listas"), "listas");
        for (TipoLista tipo : TipoLista.values()) {
            Object valorLista = listas.get(tipo.getChaveJson());
            if (valorLista == null) {
                continue;
            }
            if (!(valorLista instanceof List<?>)) {
                throw new IllegalArgumentException("A lista " + tipo.getChaveJson() + " é inválida.");
            }
            List<?> itens = (List<?>) valorLista;
            for (Object item : itens) {
                usuario.adicionar(tipo, converterSerie(comoMapa(item, "série")));
            }
        }
        return usuario;
    }

    private Serie converterSerie(Map<String, Object> json) {
        long id = comoLong(json.get("id"));
        String nome = comoTexto(json.get("nome"), "Sem nome");
        String idioma = comoTexto(json.get("idioma"), "Não informado");
        List<String> generos = comoListaTextos(json.get("generos"));
        double nota = comoDouble(json.get("nota"), -1);
        String status = comoTexto(json.get("status"), "Não informado");
        LocalDate estreia = comoData(json.get("estreia"));
        LocalDate termino = comoData(json.get("termino"));
        String emissora = comoTexto(json.get("emissora"), "Não informada");
        String resumo = comoTexto(json.get("resumo"), "Resumo não disponível.");
        String imagem = comoTexto(json.get("imagem"), "");
        return new Serie(id, nome, idioma, generos, nota, status, estreia, termino,
                emissora, resumo, imagem);
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> comoMapa(Object valor, String campo) {
        if (!(valor instanceof Map<?, ?>)) {
            throw new IllegalArgumentException("O campo " + campo + " é inválido.");
        }
        return (Map<String, Object>) valor;
    }

    private static List<String> comoListaTextos(Object valor) {
        if (!(valor instanceof List<?>)) {
            return List.of();
        }
        List<?> lista = (List<?>) valor;
        List<String> textos = new ArrayList<>();
        for (Object item : lista) {
            if (item != null) {
                textos.add(String.valueOf(item));
            }
        }
        return textos;
    }

    private static String comoTexto(Object valor, String padrao) {
        return valor == null ? padrao : String.valueOf(valor);
    }

    private static long comoLong(Object valor) {
        if (valor instanceof Number) {
            Number numero = (Number) valor;
            return numero.longValue();
        }
        throw new IllegalArgumentException("Identificador de série inválido.");
    }

    private static double comoDouble(Object valor, double padrao) {
        return valor instanceof Number ? ((Number) valor).doubleValue() : padrao;
    }

    private static String dataParaTexto(LocalDate data) {
        return data == null ? null : data.toString();
    }

    private static LocalDate comoData(Object valor) {
        if (valor == null || String.valueOf(valor).isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(String.valueOf(valor));
        } catch (DateTimeParseException erro) {
            throw new IllegalArgumentException("Data inválida no arquivo JSON.", erro);
        }
    }
}
