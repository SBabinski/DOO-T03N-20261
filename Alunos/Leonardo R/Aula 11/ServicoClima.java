public interface ServicoClima {
    /*
     * Define a operacao principal do sistema: receber uma cidade e devolver
     * os dados de clima ja organizados para a tela.
     */
    ClimaAtual consultarPorCidade(String nomeCidade) throws Exception;
}
