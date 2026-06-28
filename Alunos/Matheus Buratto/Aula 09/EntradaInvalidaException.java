import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

class EntradaInvalidaException extends CalculadoraException {
    @Override
    public void exibirMensagem(JFrame janela, JTextField campo) {
        JOptionPane.showMessageDialog(
                janela,
                "Entrada inválida! Use apenas números.",
                "Erro de Entrada",
                JOptionPane.ERROR_MESSAGE
        );
        campo.setText("Erro: entrada inválida!");
    }
}