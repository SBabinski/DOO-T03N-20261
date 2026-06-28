package view;

import javax.swing.*;

/// Pede o nome do usuário em seu primeiro acesso
public class NicknameDialog {

    public static String askNickname() {

        return JOptionPane.showInputDialog(
                null,
                "Digite seu apelido:",
                "Primeiro acesso",
                JOptionPane.QUESTION_MESSAGE
        );
    }
}
