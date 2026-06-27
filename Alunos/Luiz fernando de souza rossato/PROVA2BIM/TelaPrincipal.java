package tv;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaPrincipal extends JFrame {

    private Usuario usuario;

    private JTextField txtNome;

    private JButton btnSalvar;
    private JButton btnBuscar;
    private JButton btnFavoritos;
    private JButton btnAssistidas;
    private JButton btnDesejo;
    private JButton btnSair;

    public TelaPrincipal() {

        usuario = JsonManager.carregarUsuario();

        configurarJanela();

        criarComponentes();

        setVisible(true);
    }

    private void configurarJanela() {

        setTitle("Sistema de Séries de TV");

        setSize(500, 450);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                int resposta = JOptionPane.showConfirmDialog(
                        TelaPrincipal.this,
                        "Deseja realmente sair do sistema?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_OPTION) {

                    JsonManager.salvarUsuario(usuario);

                    dispose();

                }

            }

        });

    }

    private void criarComponentes() {

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Sistema de Séries de TV", JLabel.CENTER);

        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        add(titulo, BorderLayout.NORTH);

        JPanel painelCentro = new JPanel();

        painelCentro.setLayout(new GridLayout(8, 1, 10, 10));

        painelCentro.add(new JLabel("Nome ou apelido:"));

        txtNome = new JTextField(usuario.getNome());

        painelCentro.add(txtNome);

        btnSalvar = new JButton("Salvar Usuário");

        btnBuscar = new JButton("Buscar Série");

        btnFavoritos = new JButton("Favoritos");

        btnAssistidas = new JButton("Séries Assistidas");

        btnDesejo = new JButton("Desejo Assistir");

        btnSair = new JButton("Sair");

        painelCentro.add(btnSalvar);

        painelCentro.add(btnBuscar);

        painelCentro.add(btnFavoritos);

        painelCentro.add(btnAssistidas);

        painelCentro.add(btnDesejo);

        painelCentro.add(btnSair);

        add(painelCentro, BorderLayout.CENTER);

        eventos();

    }

    private void eventos() {

        btnSalvar.addActionListener(e -> {

            String nome = txtNome.getText().trim();

            if (nome.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Digite seu nome ou apelido.");

                return;

            }

            usuario.setNome(nome);

            JsonManager.salvarUsuario(usuario);

            JOptionPane.showMessageDialog(this,
                    "Usuário salvo com sucesso!");

        });

        btnBuscar.addActionListener(e -> {

            new TelaBusca(usuario);

        });

        btnFavoritos.addActionListener(e -> {

            new TelaFavoritos(usuario);

        });

        btnAssistidas.addActionListener(e -> {

            new TelaAssistidas(usuario);

        });

        btnDesejo.addActionListener(e -> {

            new TelaDesejoAssistir(usuario);

        });

        btnSair.addActionListener(e -> {

            dispatchEvent(new WindowEvent(this,
                    WindowEvent.WINDOW_CLOSING));

        });

    }

}