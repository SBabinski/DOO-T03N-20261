import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame implements ActionListener {

    private final JTextField visor;

    private double primeiroNumero = 0;
    private String operacao = "";
    private boolean esperandoNovoNumero = false;

    public Main() {

        setTitle("Calculadora");
        setSize(350, 500);

        // ALTERADO PARA CONFIRMAÇÃO DE SAÍDA
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // CONFIRMAÇÃO AO FECHAR
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                int resposta = JOptionPane.showConfirmDialog(
                        Main.this,
                        "Deseja realmente sair?",
                        "Confirmação de saída",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (resposta == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        visor = new JTextField();
        visor.setFont(new Font("Arial", Font.BOLD, 28));
        visor.setHorizontalAlignment(JTextField.RIGHT);
        visor.setEditable(false);

        add(visor, BorderLayout.NORTH);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(4, 4, 10, 10));

        String[] botoes = {
                "7", "8", "9", "÷",
                "4", "5", "6", "×",
                "1", "2", "3", "-",
                "0", "=", "+", "C"
        };

        for (String texto : botoes) {

            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.BOLD, 22));
            botao.addActionListener(this);

            painel.add(botao);
        }

        add(painel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {

        String comando = evento.getActionCommand();

        try {

            if (comando.matches("[0-9]")) {

                if (esperandoNovoNumero) {
                    visor.setText("");
                    esperandoNovoNumero = false;
                }

                visor.setText(visor.getText() + comando);
            }

            else if (comando.matches("[+\\-×÷]")) {

                if (visor.getText().isEmpty()) {
                    throw new EntradaInvalidaException(
                            "Digite um número primeiro."
                    );
                }

                double numeroAtual =
                        Double.parseDouble(visor.getText());

                if (operacao.isEmpty()) {

                    primeiroNumero = numeroAtual;

                } else {

                    switch (operacao) {

                        case "+":
                            primeiroNumero += numeroAtual;
                            break;

                        case "-":
                            primeiroNumero -= numeroAtual;
                            break;

                        case "×":
                            primeiroNumero *= numeroAtual;
                            break;

                        case "÷":

                            if (numeroAtual == 0) {
                                throw new EntradaInvalidaException(
                                        "Divisão por zero não permitida."
                                );
                            }

                            primeiroNumero /= numeroAtual;
                            break;
                    }

                    visor.setText(String.valueOf(primeiroNumero));
                }

                operacao = comando;
                esperandoNovoNumero = true;
            }

            else if (comando.equals("=")) {

                if (operacao.isEmpty()) {
                    return;
                }

                double segundoNumero =
                        Double.parseDouble(visor.getText());

                switch (operacao) {

                    case "+":
                        primeiroNumero += segundoNumero;
                        break;

                    case "-":
                        primeiroNumero -= segundoNumero;
                        break;

                    case "×":
                        primeiroNumero *= segundoNumero;
                        break;

                    case "÷":

                        if (segundoNumero == 0) {
                            throw new EntradaInvalidaException(
                                    "Divisão por zero não permitida."
                            );
                        }

                        primeiroNumero /= segundoNumero;
                        break;
                }

                visor.setText(String.valueOf(primeiroNumero));

                operacao = "";
                esperandoNovoNumero = true;
            }

            else if (comando.equals("C")) {

                visor.setText("");
                primeiroNumero = 0;
                operacao = "";
                esperandoNovoNumero = false;
            }

        } catch (NumberFormatException erro) {

            JOptionPane.showMessageDialog(
                    this,
                    "Digite apenas números válidos.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (EntradaInvalidaException erro) {

            JOptionPane.showMessageDialog(
                    this,
                    erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Main calculadora = new Main();
            calculadora.setVisible(true);

        });
    }
}

class EntradaInvalidaException extends Exception {

    public EntradaInvalidaException(String mensagem) {
        super(mensagem);
    }
}