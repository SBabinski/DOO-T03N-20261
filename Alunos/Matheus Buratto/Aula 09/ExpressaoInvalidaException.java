import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

class ExpressaoInvalidaException extends CalculadoraException {
    @Override
    public void exibirMensagem(JFrame janela, JTextField campo) {
        JOptionPane.showMessageDialog(
                janela,
                "Expressão inválida! Use o formato: número operador número",
                "Erro de Expressão",
                JOptionPane.ERROR_MESSAGE
        );
        campo.setText("Erro: expressão inválida!");
    }
}