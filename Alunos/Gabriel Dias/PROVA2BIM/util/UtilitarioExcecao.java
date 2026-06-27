package util;

import javax.swing.JOptionPane;

/**
 * Classe utilitária para centralizar o tratamento e exibição de mensagens de erro.
 */
public class UtilitarioExcecao {

    private UtilitarioExcecao() {
        // Construtor privado — classe utilitária, não deve ser instanciada
    }

    /**
     * Exibe uma mensagem de erro em um diálogo Swing.
     *
     * @param componente componente pai (pode ser null)
     * @param mensagem   mensagem de erro a ser exibida
     */
    public static void exibirErro(java.awt.Component componente, String mensagem) {
        JOptionPane.showMessageDialog(
                componente,
                mensagem,
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Exibe uma mensagem de aviso em um diálogo Swing.
     *
     * @param componente componente pai (pode ser null)
     * @param mensagem   mensagem de aviso a ser exibida
     */
    public static void exibirAviso(java.awt.Component componente, String mensagem) {
        JOptionPane.showMessageDialog(
                componente,
                mensagem,
                "Atenção",
                JOptionPane.WARNING_MESSAGE
        );
    }

    /**
     * Exibe uma mensagem informativa em um diálogo Swing.
     *
     * @param componente componente pai (pode ser null)
     * @param mensagem   mensagem informativa a ser exibida
     */
    public static void exibirInformacao(java.awt.Component componente, String mensagem) {
        JOptionPane.showMessageDialog(
                componente,
                mensagem,
                "Informação",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Exibe uma exceção formatada ao usuário.
     *
     * @param componente componente pai (pode ser null)
     * @param excecao    exceção a ser exibida
     */
    public static void exibirExcecao(java.awt.Component componente, Exception excecao) {
        exibirErro(componente, excecao.getMessage());
    }
}
