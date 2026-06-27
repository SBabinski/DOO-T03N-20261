import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaJson {
    private static final String ARQUIVO = "usuario.json";

    public boolean arquivoExiste() {
        return new File(ARQUIVO).exists();
    }

    public void salvarUsuario(Usuario usuario) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(ARQUIVO))) {
            escritor.println("{");
            escritor.println("  \"nome\": \"" + escapar(usuario.getNome()) + "\",");
            escritor.println("  \"favoritas\": " + converterListaParaJson(usuario.getFavoritas()) + ",");
            escritor.println("  \"assistidas\": " + converterListaParaJson(usuario.getAssistidas()) + ",");
            escritor.println("  \"desejaAssistir\": " + converterListaParaJson(usuario.getDesejaAssistir()));
            escritor.println("}");
        } catch (IOException e) {
            System.out.println("Erro ao salvar JSON: " + e.getMessage());
        }
    }

    public Usuario carregarUsuario() {
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return null;
        }

        try {
            StringBuilder conteudo = new StringBuilder();
            BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
            String linha;

            while ((linha = leitor.readLine()) != null) {
                conteudo.append(linha);
            }

            leitor.close();

            String json = conteudo.toString();
            Usuario usuario = new Usuario(extrairCampo(json, "nome"));

            carregarLista(json, "favoritas", usuario.getFavoritas());
            carregarLista(json, "assistidas", usuario.getAssistidas());
            carregarLista(json, "desejaAssistir", usuario.getDesejaAssistir());

            return usuario;

        } catch (Exception e) {
            System.out.println("Erro ao carregar JSON: " + e.getMessage());
            return null;
        }
    }

    private String converterListaParaJson(List<Serie> lista) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < lista.size(); i++) {
            Serie s = lista.get(i);

            json.append("{");
            json.append("\"nome\":\"").append(escapar(s.getNome())).append("\",");
            json.append("\"idioma\":\"").append(escapar(s.getIdioma())).append("\",");
            json.append("\"generos\":\"").append(escapar(s.getGeneros())).append("\",");
            json.append("\"nota\":").append(s.getNota()).append(",");
            json.append("\"status\":\"").append(escapar(s.getStatus())).append("\",");
            json.append("\"estreia\":\"").append(escapar(s.getEstreia())).append("\",");
            json.append("\"termino\":\"").append(escapar(s.getTermino())).append("\",");
            json.append("\"emissora\":\"").append(escapar(s.getEmissora())).append("\"");
            json.append("}");

            if (i < lista.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");
        return json.toString();
    }

    private void carregarLista(String json, String nomeLista, List<Serie> lista) {
        String bloco = extrairBlocoLista(json, nomeLista);

        if (bloco.isEmpty()) return;

        List<String> objetos = separarObjetos(bloco);

        for (String obj : objetos) {
            String nome = extrairCampo(obj, "nome");
            String idioma = extrairCampo(obj, "idioma");
            String generos = extrairCampo(obj, "generos");
            String status = extrairCampo(obj, "status");
            String estreia = extrairCampo(obj, "estreia");
            String termino = extrairCampo(obj, "termino");
            String emissora = extrairCampo(obj, "emissora");

            double nota = 0;

            try {
                nota = Double.parseDouble(extrairNumero(obj, "nota"));
            } catch (Exception e) {
                nota = 0;
            }

            lista.add(new Serie(nome, idioma, generos, nota, status, estreia, termino, emissora));
        }
    }

    private String extrairBlocoLista(String json, String nomeLista) {
        String chave = "\"" + nomeLista + "\"";
        int inicio = json.indexOf(chave);

        if (inicio == -1) return "";

        inicio = json.indexOf("[", inicio);

        if (inicio == -1) return "";

        inicio++;

        int contador = 1;
        int fim = inicio;

        while (fim < json.length() && contador > 0) {
            char c = json.charAt(fim);

            if (c == '[') contador++;
            if (c == ']') contador--;

            fim++;
        }

        return json.substring(inicio, fim - 1);
    }

    private List<String> separarObjetos(String bloco) {
        List<String> objetos = new ArrayList<>();

        int inicio = -1;
        int contador = 0;

        for (int i = 0; i < bloco.length(); i++) {
            char c = bloco.charAt(i);

            if (c == '{') {
                if (contador == 0) inicio = i;
                contador++;
            }

            if (c == '}') {
                contador--;

                if (contador == 0 && inicio != -1) {
                    objetos.add(bloco.substring(inicio, i + 1));
                }
            }
        }

        return objetos;
    }

    private String extrairCampo(String json, String campo) {
        try {
            String chave = "\"" + campo + "\":\"";
            int inicio = json.indexOf(chave);

            if (inicio == -1) return "Não informado";

            inicio += chave.length();
            int fim = json.indexOf("\"", inicio);

            if (fim == -1) return "Não informado";

            return json.substring(inicio, fim).replace("\\\"", "\"");
        } catch (Exception e) {
            return "Não informado";
        }
    }

    private String extrairNumero(String json, String campo) {
        try {
            String chave = "\"" + campo + "\":";
            int inicio = json.indexOf(chave);

            if (inicio == -1) return "0";

            inicio += chave.length();

            int fim = json.indexOf(",", inicio);

            if (fim == -1) {
                fim = json.indexOf("}", inicio);
            }

            return json.substring(inicio, fim);
        } catch (Exception e) {
            return "0";
        }
    }

    private String escapar(String texto) {
        if (texto == null) return "";
        return texto.replace("\"", "\\\"");
    }
}