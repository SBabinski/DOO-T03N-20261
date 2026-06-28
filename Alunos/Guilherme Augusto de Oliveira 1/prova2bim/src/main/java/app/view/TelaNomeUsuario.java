package app.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.service.UsuarioService;

public class TelaNomeUsuario extends JFrame {

    private JTextField txtNome;

    public TelaNomeUsuario() {

        setTitle("Primeiro Acesso");
        setSize(350,150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painel = new JPanel(new BorderLayout(10,10));

        painel.add(new JLabel("Digite seu nome ou apelido:"), BorderLayout.NORTH);

        txtNome = new JTextField();

        painel.add(txtNome, BorderLayout.CENTER);

        JButton btnSalvar = new JButton("Salvar");

        JPanel painelBotao = new JPanel(new FlowLayout());

        painelBotao.add(btnSalvar);

        painel.add(painelBotao, BorderLayout.SOUTH);

        add(painel);

        btnSalvar.addActionListener(e -> salvar());

        setVisible(true);

    }

    private void salvar() {

        String nome = txtNome.getText().trim();

        if(nome.isEmpty()){

            JOptionPane.showMessageDialog(this,
                    "Digite um nome.");

            return;

        }

        UsuarioService.getInstance().getUsuario().setNome(nome);

        UsuarioService.getInstance().setUsuario(
                UsuarioService.getInstance().getUsuario());

        new TelaInicial();

        dispose();

    }

}