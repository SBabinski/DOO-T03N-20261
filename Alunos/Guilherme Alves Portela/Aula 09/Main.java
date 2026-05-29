import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Executa a interface gráfica de forma segura dentro da Thread de Eventos do Java
        SwingUtilities.invokeLater(() -> {
            new CalculatorUI().setVisible(true);
        });
    }
}
