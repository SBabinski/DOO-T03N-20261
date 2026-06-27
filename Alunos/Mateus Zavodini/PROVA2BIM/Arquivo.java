

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

// Essa classe salva e le o arquivo dados.json.
// Usa apenas classes antigas de java para funcionar em qualquer versao do Java.
// Estava dando erro, versão divergente do java
public class Arquivo {

    private static final String NOME_ARQUIVO = "dados.json";

    public void salvar(Usuario usuario) throws ErroArquivo {
        try {
            Map<String, Object> mapa = usuario.paraMapa();
            String texto = Json.escrever(mapa);

            Writer escritor = new OutputStreamWriter(new FileOutputStream(NOME_ARQUIVO), "UTF-8");
            try {
                escritor.write(texto);
            } finally {
                escritor.close();
            }
        } catch (IOException e) {
            throw new ErroArquivo("Nao foi possivel salvar o arquivo " + NOME_ARQUIVO, e);
        }
    }

    @SuppressWarnings("unchecked")
    public Usuario carregar() throws ErroArquivo {
        File arquivo = new File(NOME_ARQUIVO);

        if (!arquivo.exists()) {
            return new Usuario();
        }

        try {
            String texto = lerConteudoDoArquivo(arquivo);
            if (texto.trim().isEmpty()) {
                return new Usuario();
            }
            Map<String, Object> mapa = (Map<String, Object>) Json.ler(texto);
            return Usuario.deMapa(mapa);
        } catch (IOException e) {
            throw new ErroArquivo("Nao foi possivel ler o arquivo " + NOME_ARQUIVO, e);
        } catch (Exception e) {
            throw new ErroArquivo("O arquivo " + NOME_ARQUIVO + " esta com formato invalido.", e);
        }
    }

    // Le todo o conteudo de um arquivo de texto e devolve como uma unica String.
    private String lerConteudoDoArquivo(File arquivo) throws IOException {
        StringBuilder conteudo = new StringBuilder();
        BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(arquivo), "UTF-8"));
        try {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                conteudo.append(linha).append("\n");
            }
        } finally {
            leitor.close();
        }
        return conteudo.toString();
    }
}