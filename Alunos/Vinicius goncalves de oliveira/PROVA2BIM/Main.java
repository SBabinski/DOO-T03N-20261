package com.seriestv;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Mantém o Look & Feel padrão do Swing sem interromper o programa
            }

            iniciarPrograma();
        });
    }

    private static void iniciarPrograma() {
        PersistenciaJSON persistencia = new PersistenciaJSON();
        Usuario usuario;

        try {
            // se tiver dados salvos ele carrega
            usuario = persistencia.carregar();

            if (usuario == null) {
                // caso seja a primeira vez
                usuario = pedirNomeUsuario();

                if (usuario == null) {
                    System.exit(0);
                    return;
                }
            } else {
                // se voltar aparce as boas vindas
                JOptionPane.showMessageDialog(null,
                    "Bem-vindo de volta, " + usuario.getNome() + "! 🎬\n" +
                    "Seus dados foram carregados com sucesso.",
                    "Olá!", JOptionPane.INFORMATION_MESSAGE);
            }

            //pra nao dar erro
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Erro ao carregar dados salvos: " + e.getMessage() + "\n" +
                "O programa iniciará com dados novos.",
                "Aviso", JOptionPane.WARNING_MESSAGE);

            usuario = pedirNomeUsuario();
            if (usuario == null) {
                System.exit(0);
                return;
            }
        }


        MainFrame frame = new MainFrame(usuario, persistencia);
        frame.setVisible(true);
    }

    private static Usuario pedirNomeUsuario() {
        String nome = null;

        while (nome == null || nome.trim().isEmpty()) {
            nome = JOptionPane.showInputDialog(null,
                "Bem-vindo ao SeriesTV! \n\nDigite seu nome?",
                "Primeiro acesso", JOptionPane.QUESTION_MESSAGE);

            if (nome == null) {
                return null;
            }

            if (nome.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "Por favor, digite seu nome para continuar.",
                    "Nome obrigatório", JOptionPane.WARNING_MESSAGE);
            }
        }

        return new Usuario(nome.trim());
    }
}
