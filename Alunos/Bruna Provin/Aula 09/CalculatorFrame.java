import javax.swing.*; // para criar a interface gráfica
import java.awt.*; // para usar layouts e cores
import java.awt.event.*; // para lidar com eventos de clique e teclado

public class CalculatorFrame extends JFrame { 
    private final JTextField visor = new JTextField(); // campo de texto para mostrar os números e resultados
    private final JLabel labelOperacao = new JLabel(" "); // rótulo para mostrar o operador atual (ex: +, -, etc.)
    private final Calculator calculadora = new Calculator();// instância da classe de lógica de cálculo

    private Double valorArmazenado = null;
    private String operador = null;

    public CalculatorFrame() {
        super("Calculadora Swing <3");
        initUI(); // método para configurar a interface gráfica
    }

public static void main(String[] args) {
    
        CalculatorFrame frame = new CalculatorFrame();
        frame.setVisible(true);
    
}
    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // fecha a aplicação ao fechar a janela
        setSize(360, 480); // tamanho inicial da janela
        setLocationRelativeTo(null); // centraliza a janela na tela

        Color bg = new Color(255, 192, 203);  // rosa claro  
        Color btnPink = new Color(255, 105, 180);
        Color btnDark = new Color(219, 112, 147);

        JPanel root = new JPanel(new BorderLayout(8,8));
        root.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        root.setBackground(bg);

        visor.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 28));
        visor.setHorizontalAlignment(JTextField.RIGHT);
        visor.setEditable(true);
        visor.setBackground(Color.WHITE);

        labelOperacao.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        labelOperacao.setForeground(btnDark);

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(labelOperacao, BorderLayout.WEST);
        top.add(visor, BorderLayout.CENTER);

        root.add(top, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(5,4,6,6));
        buttons.setOpaque(false);

        String[] btnOrder = {
            "C", "⌫", "÷", "×",
            "7","8","9","-",
            "4","5","6", "+",
            "1","2","3","=",
            "0", ".", "", ""
        };

        for (String key : btnOrder) {
            if (key.equals("")) { buttons.add(new JLabel()); continue; }
            JButton b = new JButton(key);
            b.setFocusPainted(false);
            b.setBackground(key.matches("[0-9]|") ? Color.PINK : btnPink);
            b.setOpaque(true);
            b.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
            if (key.equals("C")) b.setBackground(new Color(255,182,193));
            if (key.equals("=")) b.setBackground(btnDark);
            buttons.add(b);
            b.addActionListener(e -> botaoPressionado(key));
        }

        root.add(buttons, BorderLayout.CENTER);

        add(root);

        bindKeys();

        setVisible(true);
    }

    private void botaoPressionado(String key) {
        switch (key) {
            case "C":
                visor.setText("");
                labelOperacao.setText(" ");
                valorArmazenado = null;
                operador = null;
                calculadora.limpar();
                break;
            case "⌫":
                String t = visor.getText();
                if (!t.isEmpty()) visor.setText(t.substring(0, t.length()-1));
                break;
            case "+": case "-": case "×": case "÷":
                escolherOperador(key);
                break;
            case "=":
                calcularResultado();
                break;
            default:
                visor.setText(visor.getText() + key);
        }
    }

    private void escolherOperador(String op) {
        try {
            String txt = visor.getText().trim();
            if (txt.isEmpty()) {
               
                this.operador = op;
                labelOperacao.setText(op);
                return;
            }
            double val = Double.parseDouble(txt);
            if (valorArmazenado == null) {
                valorArmazenado = val;
            } else if (operador != null) {
                valorArmazenado = calculadora.calcular(valorArmazenado, val, operador);
            }
            operador = op;
            labelOperacao.setText(operador);
            visor.setText("");
        } catch (NumberFormatException e) {
            mostrarErro(new CalculatorException("Entrada inválida: digite apenas números."));
        } catch (CalculatorException ce) {
            mostrarErro(ce);
        }
    }

    private void calcularResultado() {
        try {
            String txt = visor.getText().trim();
            double right = txt.isEmpty() ? 0 : Double.parseDouble(txt);
            if (valorArmazenado == null && operador == null) {
                return;
            }
            if (operador == null) {
                // apenas exibir o valor atual
                visor.setText(Calculator.formatarDouble(right));
                return;
            }
            double result = calculadora.calcular(valorArmazenado == null ? 0 : valorArmazenado, right, operador);
            visor.setText(Calculator.formatarDouble(result));
            labelOperacao.setText(" ");
            valorArmazenado = null;
            operador = null;
        } catch (NumberFormatException e) {
            mostrarErro(new CalculatorException("Entrada inválida: digite apenas números."));
        } catch (CalculatorException ce) {
            mostrarErro(ce);
        }
    }

    private void mostrarErro(CalculatorException ce) {
        JOptionPane.showMessageDialog(this, ce.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        visor.setText("");
        labelOperacao.setText(" ");
        valorArmazenado = null;
        operador = null;
    }

    private void bindKeys() {
        JRootPane root = getRootPane();
        InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = root.getActionMap();

        for (int i = 0; i <= 9; i++) {
            final String key = String.valueOf(i);
            im.put(KeyStroke.getKeyStroke(key), "num" + key);
            am.put("num" + key, new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    if (visor.isFocusOwner()) return; // evita duplicar quando o campo já recebe a tecla
                    visor.setText(visor.getText() + key);
                }
            });
        }
        im.put(KeyStroke.getKeyStroke('.'), "dot");
        am.put("dot", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (visor.isFocusOwner()) return;
                visor.setText(visor.getText() + ".");
            }
        });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "back");
        am.put("back", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (visor.isFocusOwner()) return; // quando focado, JTextField já trata Backspace
                String t = visor.getText(); if (!t.isEmpty()) visor.setText(t.substring(0,t.length()-1));
            }
        });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "enter");
        am.put("enter", new AbstractAction() { public void actionPerformed(ActionEvent e) { calcularResultado(); } });

        im.put(KeyStroke.getKeyStroke('/'), "div");
        am.put("div", new AbstractAction() { public void actionPerformed(ActionEvent e) { escolherOperador("÷"); } });
        im.put(KeyStroke.getKeyStroke('*'), "mul");
        am.put("mul", new AbstractAction() { public void actionPerformed(ActionEvent e) { escolherOperador("×"); } });
        im.put(KeyStroke.getKeyStroke('-'), "sub");
        am.put("sub", new AbstractAction() { public void actionPerformed(ActionEvent e) { escolherOperador("-"); } });
        im.put(KeyStroke.getKeyStroke('+'), "add");
        am.put("add", new AbstractAction() { public void actionPerformed(ActionEvent e) { escolherOperador("+"); } });

        // Interceptar operadores quando o `display` estiver focado, evitando inserir o caractere no campo
        InputMap dim = visor.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap dam = visor.getActionMap();
        dim.put(KeyStroke.getKeyStroke('/'), "div_focus");
        dam.put("div_focus", new AbstractAction() { public void actionPerformed(ActionEvent e) { escolherOperador("÷"); } });
        dim.put(KeyStroke.getKeyStroke('*'), "mul_focus");
        dam.put("mul_focus", new AbstractAction() { public void actionPerformed(ActionEvent e) { escolherOperador("×"); } });
        dim.put(KeyStroke.getKeyStroke('-'), "sub_focus");
        dam.put("sub_focus", new AbstractAction() { public void actionPerformed(ActionEvent e) { escolherOperador("-"); } });
        dim.put(KeyStroke.getKeyStroke('+'), "add_focus");
        dam.put("add_focus", new AbstractAction() { public void actionPerformed(ActionEvent e) { escolherOperador("+"); } });
    }
}
