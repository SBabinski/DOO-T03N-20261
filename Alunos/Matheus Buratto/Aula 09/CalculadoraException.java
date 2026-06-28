import javax.swing.*;

// Superclasse abstrata para exceções da calculadora
abstract class CalculadoraException extends Exception {
    public abstract void exibirMensagem(JFrame janela, JTextField campo);
}