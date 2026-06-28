package app.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class Componentes {

    public static JButton criarBotao(String texto) {

        JButton botao = new JButton(texto);

        botao.setFont(Estilo.BOTAO);

        botao.setBackground(new Color(33, 150, 243));

        botao.setForeground(Color.WHITE);

        botao.setFocusPainted(false);

        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botao.setBorder(BorderFactory.createEmptyBorder(12,20,12,20));

        botao.setPreferredSize(new Dimension(320,50));

        return botao;

    }

}