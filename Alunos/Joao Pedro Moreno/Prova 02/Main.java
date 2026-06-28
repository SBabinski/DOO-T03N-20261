package com.seriestv;

import javax.swing.*;

/**
 * Ponto de entrada do programa.
 * Carrega os dados salvos ou pede o nome do usuário na primeira vez.
 */
public class Main {

    public static void main(String[] args) {
        // Garante que o Swing rode na thread certa
        SwingUtilities.invokeLater(() -> {
            try {
                // Visual mais moderno
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Continua com o visual padrão se falhar
            }

            iniciarPrograma();
        });
    }

    private static void iniciarPrograma() {
        PersistenciaJSON persistencia = new PersistenciaJSON();
        Usuario usuario;

        try {
            // Tenta carregar dados salvos
            usuario = persistencia.carregar();

            if (usuario == null) {
                // Primeira vez usando — pede o nome
                usuario = pedirNomeUsuario();
                if (usuario == null) {
                    // Usuário cancelou
                    System.exit(0);
                    return;
                }
            } else {
                // Bem-vindo de volta!
                JOptionPane.showMessageDialog(null,
                    "Bem-vindo de volta, " + usuario.getNome() + "! 🎬\n" +
                    "Seus dados foram carregados com sucesso.",
                    "Olá!", JOptionPane.INFORMATION_MESSAGE);
            }

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

        // Abre a janela principal
        MainFrame frame = new MainFrame(usuario, persistencia);
        frame.setVisible(true);
    }

    private static Usuario pedirNomeUsuario() {
        String nome = null;

        while (nome == null || nome.trim().isEmpty()) {
            nome = JOptionPane.showInputDialog(null,
                "Bem-vindo ao SeriesTV! 🎬\n\nQual é o seu nome ou apelido?",
                "Primeiro acesso", JOptionPane.QUESTION_MESSAGE);

            if (nome == null) {
                // Usuário clicou em Cancelar
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
