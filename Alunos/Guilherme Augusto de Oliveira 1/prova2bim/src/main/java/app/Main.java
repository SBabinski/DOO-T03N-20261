package app;

import javax.swing.SwingUtilities;

import app.service.UsuarioService;
import app.view.TelaInicial;
import app.view.TelaNomeUsuario;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            UsuarioService usuarioService = UsuarioService.getInstance();

            if (usuarioService.primeiroAcesso()) {

                new TelaNomeUsuario();

            } else {

                new TelaInicial();

            }

        });

    }

}