import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

class DivisaoPorZeroException extends CalculadoraException {
    @Override
    public void exibirMensagem(JFrame janela, JTextField campo) {
        JOptionPane.showMessageDialog(
                janela,
                "Você tentou dividir por zero!",
                "Erro de Divisão",
                JOptionPane.ERROR_MESSAGE
        );
        campo.setText("0 / 0 = ERRO!!!");
    }
}