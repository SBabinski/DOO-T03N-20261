import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

public class TelaBoasVindas extends JDialog {
    private static final Color COR_FUNDO = new Color(18, 18, 18);
    private static final Color COR_CAMPO = new Color(35, 35, 35);
    private static final Color COR_TEXTO = new Color(245, 245, 245);
    private static final Color COR_TEXTO_SECUNDARIO = new Color(200, 200, 200);
    private static final Color COR_DESTAQUE = new Color(229, 9, 20);

    private JTextField campoNome;
    private String nomeUsuario;

    private TelaBoasVindas() {
        this.nomeUsuario = "Gabriel";

        configurarJanela();
        criarComponentes();
    }

    public static String solicitarNomeUsuario() {
        TelaBoasVindas telaBoasVindas = new TelaBoasVindas();
        telaBoasVindas.setVisible(true);
        return telaBoasVindas.getNomeUsuario();
    }

    private void configurarJanela() {
        setTitle("Bem-vindo");
        setModal(true);
        setSize(390, 260);
        setMinimumSize(new Dimension(390, 260));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(COR_FUNDO);
    }

    private void criarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(0, 18));
        painelPrincipal.setBackground(COR_FUNDO);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(26, 28, 24, 28));

        JLabel labelTitulo = new JLabel("Bem-vindo ao TV Series", SwingConstants.CENTER);
        labelTitulo.setForeground(COR_TEXTO);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel labelPergunta = new JLabel("Como você gostaria de ser chamado?", SwingConstants.CENTER);
        labelPergunta.setForeground(COR_TEXTO_SECUNDARIO);
        labelPergunta.setFont(new Font("Arial", Font.PLAIN, 14));

        campoNome = new JTextField("Gabriel", 20);
        campoNome.setBackground(COR_CAMPO);
        campoNome.setForeground(COR_TEXTO);
        campoNome.setCaretColor(COR_TEXTO);
        campoNome.setFont(new Font("Arial", Font.PLAIN, 14));
        campoNome.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        JButton botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBackground(COR_DESTAQUE);
        botaoEntrar.setForeground(Color.WHITE);
        botaoEntrar.setFocusPainted(false);
        botaoEntrar.setFont(new Font("Arial", Font.BOLD, 13));
        botaoEntrar.setBorder(BorderFactory.createEmptyBorder(9, 24, 9, 24));
        botaoEntrar.addActionListener(evento -> entrar());
        getRootPane().setDefaultButton(botaoEntrar);

        JPanel painelCentro = new JPanel(new BorderLayout(0, 14));
        painelCentro.setBackground(COR_FUNDO);
        painelCentro.add(labelPergunta, BorderLayout.NORTH);
        painelCentro.add(campoNome, BorderLayout.CENTER);

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotao.setBackground(COR_FUNDO);
        painelBotao.add(botaoEntrar);

        painelPrincipal.add(labelTitulo, BorderLayout.NORTH);
        painelPrincipal.add(painelCentro, BorderLayout.CENTER);
        painelPrincipal.add(painelBotao, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private void entrar() {
        String nomeDigitado = campoNome.getText();

        if (nomeDigitado != null && !nomeDigitado.trim().isEmpty()) {
            nomeUsuario = nomeDigitado.trim();
        }

        dispose();
    }

    private String getNomeUsuario() {
        return nomeUsuario;
    }
}
