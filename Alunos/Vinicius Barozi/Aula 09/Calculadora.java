import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculadora extends JFrame {

    private JLabel     lblExpressao;
    private JLabel     lblResultado;
    private JTextField txtNumero1;
    private JTextField txtNumero2;
    private JTextField campoAtivo;
    private String     operadorSelecionado = "";

    private final CalculadoraEngine engine = new CalculadoraEngine();

    public Calculadora() {
        configurarJanela();
        JPanel painelPrincipal = criarPainelPrincipal();
        add(painelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private JPanel criarPainelPrincipal() {
        JPanel painel = new JPanel(new BorderLayout(0, 0));
        painel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        painel.add(criarPainelDisplay(), BorderLayout.NORTH);
        painel.add(criarPainelEntrada(), BorderLayout.CENTER);
        painel.add(criarPainelTeclado(), BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelDisplay() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)));

        lblExpressao = new JLabel(" ");
        lblExpressao.setFont(new Font("Arial", Font.PLAIN, 13));
        lblExpressao.setHorizontalAlignment(SwingConstants.RIGHT);

        lblResultado = new JLabel("0");
        lblResultado.setFont(new Font("Arial", Font.BOLD, 36));
        lblResultado.setHorizontalAlignment(SwingConstants.RIGHT);

        painel.add(lblExpressao, BorderLayout.NORTH);
        painel.add(lblResultado, BorderLayout.CENTER);
        painel.setPreferredSize(new Dimension(360, 90));

        return painel;
    }

    private JPanel criarPainelEntrada() {
        JPanel painel = new JPanel(new GridLayout(1, 2, 8, 0));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        txtNumero1 = criarCampoNumerico("Número 1");
        txtNumero2 = criarCampoNumerico("Número 2");

        campoAtivo = txtNumero1;

        txtNumero1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                campoAtivo = txtNumero1;
            }
        });
        txtNumero2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                campoAtivo = txtNumero2;
            }
        });

        painel.add(txtNumero1);
        painel.add(txtNumero2);
        return painel;
    }

    private JTextField criarCampoNumerico(String dica) {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Arial", Font.PLAIN, 18));
        campo.setHorizontalAlignment(JTextField.CENTER);
        campo.setPreferredSize(new Dimension(170, 50));
        campo.setToolTipText(dica);
        return campo;
    }

    private JPanel criarPainelTeclado() {
        JPanel painel = new JPanel(new BorderLayout(0, 8));

        JPanel grade = new JPanel(new GridLayout(4, 4, 6, 6));

        String[][] layout = {
            {"7", "8", "9", "÷"},
            {"4", "5", "6", "×"},
            {"1", "2", "3", "-"},
            {"0", ".", "⌫", "+"}
        };

        for (String[] linha : layout) {
            for (String label : linha) {
                JButton btn = new JButton(label);
                btn.setFont(new Font("Arial", Font.BOLD, 16));
                btn.setPreferredSize(new Dimension(80, 56));
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        processarTeclado(label);
                    }
                });
                grade.add(btn);
            }
        }

        JPanel acoes = new JPanel(new GridLayout(1, 2, 6, 0));

        JButton btnCalcular = new JButton("= CALCULAR");
        btnCalcular.setFont(new Font("Arial", Font.BOLD, 15));
        btnCalcular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calcular();
            }
        });

        JButton btnLimpar = new JButton("LIMPAR");
        btnLimpar.setFont(new Font("Arial", Font.BOLD, 15));
        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpar();
            }
        });

        acoes.add(btnCalcular);
        acoes.add(btnLimpar);

        painel.add(grade, BorderLayout.CENTER);
        painel.add(acoes, BorderLayout.SOUTH);
        return painel;
    }

    private void processarTeclado(String tecla) {
        if (tecla.equals("⌫")) {
  
            String t = campoAtivo.getText();
            if (!t.isEmpty()) {
                campoAtivo.setText(t.substring(0, t.length() - 1));
            }
        } else if (tecla.equals("+") || tecla.equals("-") || tecla.equals("×") || tecla.equals("÷")) {
  
            operadorSelecionado = tecla;
            atualizarExpressao();
            campoAtivo = txtNumero2;
            txtNumero2.requestFocus();
        } else {
   
            campoAtivo.setText(campoAtivo.getText() + tecla);
        }
    }

    private void calcular() {
        try {
            if (operadorSelecionado.isEmpty()) {
                throw new CalculadoraException(
                        CalculadoraException.TipoErro.OPERACAO_INCOMPLETA,
                        "Nenhum operador selecionado.\nClique em +, -, × ou ÷ antes de calcular.");
            }

            double resultado = engine.calcular(
                    txtNumero1.getText(),
                    txtNumero2.getText(),
                    operadorSelecionado);

            String textoResultado = engine.formatarResultado(resultado);
            atualizarExpressao();
            lblResultado.setText(textoResultado);

        } catch (CalculadoraException ex) {
            lblResultado.setText("Erro");
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    ex.getTituloAmigavel(),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpar() {
        txtNumero1.setText("");
        txtNumero2.setText("");
        operadorSelecionado = "";
        campoAtivo = txtNumero1;
        lblExpressao.setText(" ");
        lblResultado.setText("0");
        txtNumero1.requestFocus();
    }

    private void atualizarExpressao() {
        String n1 = txtNumero1.getText().isEmpty() ? "?" : txtNumero1.getText();
        String op = operadorSelecionado.isEmpty() ? "" : " " + operadorSelecionado + " ";
        String n2 = txtNumero2.getText().isEmpty() ? "" : txtNumero2.getText();
        lblExpressao.setText(n1 + op + n2);
    }

    static class CalculadoraException extends Exception {

        enum TipoErro {
            OPERACAO_INCOMPLETA,
            DIVISAO_POR_ZERO,
            NUMERO_INVALIDO
        }

        private final TipoErro tipo;

        CalculadoraException(TipoErro tipo, String mensagem) {
            super(mensagem);
            this.tipo = tipo;
        }

        public String getTituloAmigavel() {
            if (tipo == TipoErro.OPERACAO_INCOMPLETA) return "Operação incompleta";
            if (tipo == TipoErro.DIVISAO_POR_ZERO)    return "Divisão por zero";
            if (tipo == TipoErro.NUMERO_INVALIDO)      return "Número inválido";
            return "Erro";
        }
    }


    static class CalculadoraEngine {

        public double calcular(String numero1, String numero2, String operador) throws CalculadoraException {
            double valor1 = parseNumero(numero1);
            double valor2 = parseNumero(numero2);

            if (operador.equals("+")) return valor1 + valor2;
            if (operador.equals("-")) return valor1 - valor2;
            if (operador.equals("×")) return valor1 * valor2;
            if (operador.equals("÷")) {
                if (valor2 == 0) {
                    throw new CalculadoraException(
                            CalculadoraException.TipoErro.DIVISAO_POR_ZERO,
                            "Não é possível dividir por zero!");
                }
                return valor1 / valor2;
            }

            throw new CalculadoraException(
                    CalculadoraException.TipoErro.OPERACAO_INCOMPLETA,
                    "Operador desconhecido.");
        }

        public String formatarResultado(double valor) {
            if (valor == (long) valor) {
                return String.valueOf((long) valor);
            }
            String texto = String.format("%.8f", valor);
            texto = texto.replaceFirst("\\.?0+$", "");
            return texto;
        }

        private double parseNumero(String texto) throws CalculadoraException {
            if (texto == null || texto.isBlank()) {
                throw new CalculadoraException(
                        CalculadoraException.TipoErro.NUMERO_INVALIDO,
                        "Preencha os dois campos antes de calcular!");
            }
            try {
                return Double.parseDouble(texto);
            } catch (NumberFormatException ex) {
                throw new CalculadoraException(
                        CalculadoraException.TipoErro.NUMERO_INVALIDO,
                        "\"" + texto + "\" não é um número válido!");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Calculadora();
            }
        });
    }
}