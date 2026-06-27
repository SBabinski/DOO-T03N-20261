package fag;

import javax.swing.JOptionPane;

public class Principal {

	public static void main(String[] args) {

		Sistema sistema = new Sistema();

		if (sistema.getNomeUsuario() == null || sistema.getNomeUsuario().isBlank()) {

			String nome;

			do {

				nome = JOptionPane.showInputDialog(null, "Digite seu nome ou apelido:");

				if (nome == null || nome.isBlank()) {

					JOptionPane.showMessageDialog(null, 
							"Digite um nome válido!",
							"Erro de digitacao de nome",
							JOptionPane.WARNING_MESSAGE);
				}

			} while (nome == null || nome.isBlank());

			sistema.setNomeUsuario(nome);

		} else {

			JOptionPane.showMessageDialog(null, "Bem-vinda, " + sistema.getNomeUsuario() + "!");
		}

		new TelaSistema(sistema);

	}

}
