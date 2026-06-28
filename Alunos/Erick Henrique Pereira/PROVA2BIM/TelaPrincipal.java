package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import servicos.UsuarioServicos;

public class TelaPrincipal extends JFrame {

    private final UsuarioServicos usuarioService;

    public TelaPrincipal(UsuarioServicos usuarioService) {
        this.usuarioService = usuarioService;
        configurarJanela();
        construirLayout();
    }

    private void configurarJanela() {
        setTitle("TV Tracker");
        setSize(480, 420);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // abre caixa de confirmação ao clicar no X
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                confirmarSaida();
            }
        });
    }

    private void construirLayout() {

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(28, 28, 40));

        //cabeçalho
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(new Color(40, 40, 60));
        cabecalho.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel lblTitulo = new JLabel("TV Tracker", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(220, 220, 255));

        JLabel lblUsuario = new JLabel("Olá, " + usuarioService.getUsuario().getNome() + "!", SwingConstants.CENTER);
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 15));
        lblUsuario.setForeground(new Color(160, 160, 200));
        lblUsuario.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        cabecalho.add(lblTitulo, BorderLayout.CENTER);
        cabecalho.add(lblUsuario, BorderLayout.SOUTH);

        //painel de botões
        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 0, 14));
        painelBotoes.setBackground(new Color(28, 28, 40));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JButton btnBuscar = criarBotao("Buscar Séries", new Color(88, 86, 214));
        JButton btnListas = criarBotao("Minhas Listas", new Color(52, 120, 180));
        JButton btnSair   = criarBotao("Salvar e Sair",  new Color(180, 60, 60));

        btnBuscar.addActionListener(e -> new TelaBusca(usuarioService).setVisible(true));
        btnListas.addActionListener(e -> new TelaListas(usuarioService).setVisible(true));
        btnSair.addActionListener(e -> confirmarSaida());

        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnListas);
        painelBotoes.add(btnSair);

        painel.add(cabecalho, BorderLayout.NORTH);
        painel.add(painelBotoes, BorderLayout.CENTER);
        add(painel);
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        // efeito hover: escurece o botão ao passar o mouse
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            final Color corOriginal = cor;
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(corOriginal.darker());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(corOriginal);
            }
        });

        return btn;
    }

    // exibe caixa de confirmação antes de sair
    private void confirmarSaida() {
        int opcao = JOptionPane.showConfirmDialog(
                this,
                "Deseja salvar e sair do TV Tracker?",
                "Confirmar saída",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcao == JOptionPane.YES_OPTION) {
            try {
                usuarioService.salvar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Erro ao salvar dados: " + ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0);
        }
    }
}