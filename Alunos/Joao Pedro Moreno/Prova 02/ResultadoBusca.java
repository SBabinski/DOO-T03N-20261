package com.seriestv;

//Envelope que a API TVMaze usa para retornar resultados de busca. A API não retorna a série diretamente, ela vem dentro de { "show": { ... } }
public class ResultadoBusca {
    private Serie show;

    public Serie getShow() {
        return show;
    }
}
