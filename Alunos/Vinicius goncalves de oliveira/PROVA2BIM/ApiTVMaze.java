package com.seriestv;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiTVMaze {

    private static final String BASE_URL = "https://api.tvmaze.com";
    private final Gson gson = new Gson();

    public List<Serie> buscarSeries(String nomeDaSerie) throws Exception {
        String urlFormatada = nomeDaSerie.replace(" ", "+");
        String endereco = BASE_URL + "/search/shows?q=" + urlFormatada;

        String json = fazerRequisicao(endereco);
        ResultadoBusca[] resultados = gson.fromJson(json, ResultadoBusca[].class);

        List<Serie> lista = new ArrayList<>();
        for (ResultadoBusca r : resultados) {
            if (r.getShow() != null) {
                lista.add(r.getShow());
            }
        }
        return lista;
    }

    private String fazerRequisicao(String endereco) throws Exception {
        URL url = new URL(endereco);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");
        conexao.setConnectTimeout(5000); // 5 segundos para conectar
        conexao.setReadTimeout(5000);    // 5 segundos para ler

        int status = conexao.getResponseCode();
        if (status != 200) {
            throw new Exception("Erro na API. Código: " + status);
        }

        BufferedReader leitor = new BufferedReader(
            new InputStreamReader(conexao.getInputStream())
        );

        StringBuilder resposta = new StringBuilder();
        String linha;
        while ((linha = leitor.readLine()) != null) {
            resposta.append(linha);
        }
        leitor.close();

        return resposta.toString();
    }
}
