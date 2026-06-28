package fag;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TelaSistema extends JFrame {

	private Sistema sistema;
	private JTextField txtPesquisa;
	private JButton btnBuscar;
	private JList<Serie> listaResultados;
	private DefaultListModel<Serie> modeloLista;
	private JTextArea txtDetalhes;
	private JButton btnFavorito;
	private JButton btnAssistida;
	private JButton btnDesejo;
	private JButton btnMinhasListas;

	/*
	  aqui e o construtor da classe, que passa o atributo da classe sistema como
	  parametro e coloca titulo e alinha a posicao
	 */

	public TelaSistema(Sistema sistema) {

		this.sistema = sistema;

		setTitle("SISTEMA DE BUSCA DE SERIES - " + sistema.getNomeUsuario());

		setSize(800, 600);

		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		inicializarComponentes();

		setVisible(true);
	}

	private void inicializarComponentes() {

		setLayout(new BorderLayout());

		//cabecalho

		JPanel painelNorte = new JPanel(new BorderLayout());

		painelNorte.setBackground(new Color(45, 62, 80));

		// título
		JLabel lblTitulo = new JLabel("SISTEMA DE SÉRIES");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
		lblTitulo.setForeground(Color.WHITE);

		// painel de busca
		JPanel painelBusca = new JPanel();
		painelBusca.setBackground(new Color(45, 62, 80));

		JLabel lblBusca = new JLabel("Buscar série:");
		lblBusca.setForeground(Color.WHITE);

		txtPesquisa = new JTextField(20);

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(e -> buscarSeries());

		painelBusca.add(lblBusca);
		painelBusca.add(txtPesquisa);
		painelBusca.add(btnBuscar);

		painelNorte.add(lblTitulo, BorderLayout.NORTH);

		painelNorte.add(painelBusca, BorderLayout.SOUTH);

		add(painelNorte, BorderLayout.NORTH);

		//resultados

		modeloLista = new DefaultListModel<>();

		listaResultados = new JList<>(modeloLista);
		listaResultados.addListSelectionListener(e -> {

			if (!e.getValueIsAdjusting()) {
				mostrarDetalhes();
			}
		});

		JScrollPane scrollLista = new JScrollPane(listaResultados);
		scrollLista.setPreferredSize(new Dimension(300, 0));
		add(scrollLista, BorderLayout.WEST);

		//detalhes

		txtDetalhes = new JTextArea();
		txtDetalhes.setEditable(false);
		txtDetalhes.setFont(new Font("Monospaced", Font.PLAIN, 14));
		add(new JScrollPane(txtDetalhes), BorderLayout.CENTER);
		
		JPanel painelAcoes = new JPanel();

		btnFavorito = new JButton("Favoritar");
		btnFavorito.addActionListener(e -> adicionarFavorito());

		btnAssistida = new JButton("Ja Assistido");
		btnAssistida.addActionListener(e -> adicionarAssistida());

		btnDesejo = new JButton("Desejo Assistir");
		btnDesejo.addActionListener(e -> adicionarDesejo());
		
		btnMinhasListas = new JButton("Minhas Listas");
		btnMinhasListas.addActionListener(e -> abrirTelaListas());

		painelAcoes.add(btnFavorito);
		painelAcoes.add(btnAssistida);
		painelAcoes.add(btnDesejo);
		painelAcoes.add(btnMinhasListas);

		add(painelAcoes, BorderLayout.SOUTH);
	}
	

	private void buscarSeries() {

	    try {

			String nome = txtPesquisa.getText();
			
			if( nome==null || nome.isBlank()) {
				
				JOptionPane.showMessageDialog(this, 
						"Insira um nome por favor", 
						"Erro de busca", 
						JOptionPane.ERROR_MESSAGE);
				
				return;
				
			}
			
			modeloLista.clear();

			List<Serie> series = sistema.buscarSeries(nome);

			if(series.isEmpty()) {

			    JOptionPane.showMessageDialog(
			            this,
			            "Nenhuma série encontrada com esse nome.",
			            "Busca",
			            JOptionPane.INFORMATION_MESSAGE);

			    return;
			}

			for(Serie serie : series) {

			    modeloLista.addElement(serie);
			}

		} catch (Exception e) {

			JOptionPane.showMessageDialog(this, 
					"Erro ao buscar séries.", 
					"Erro", 
					JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	
	private void mostrarDetalhes() {

	    Serie serie = listaResultados.getSelectedValue();

	    if (serie == null) {
	        return;
	    }

	    String detalhes =
	            "Nome: " + serie.getNome()
	            + "\n\nIdioma: " + serie.getIdioma()
	            + "\n\nGêneros: " + serie.getGeneros()
	            + "\n\nNota: " + serie.getNotaGeral()
	            + "\n\nStatus: " + serie.getStatus()
	            + "\n\nData de estreia: " + serie.getDataInicio()
	            + "\n\nData de término: " + serie.getDataFim()
	            + "\n\nEmissora: " + serie.getEmissora();

	    txtDetalhes.setText(detalhes);
	}
	
	private void adicionarFavorito() {

		Serie serie = listaResultados.getSelectedValue();

		if (serie == null) {

			JOptionPane.showMessageDialog(this, "Selecione uma série.");

			return;
		}

		sistema.adicionarFavorito(serie);

		JOptionPane.showMessageDialog(this, "Série adicionada aos favoritos!");
	}
	
	private void adicionarAssistida() {

		Serie serie = listaResultados.getSelectedValue();

		if (serie == null) {

			JOptionPane.showMessageDialog(this, "Selecione uma série.");

			return;
		}

		sistema.adicionarAssistida(serie);

		JOptionPane.showMessageDialog(this, "Série adicionada às assistidas!");
	}
	
	private void adicionarDesejo() {

		Serie serie = listaResultados.getSelectedValue();

		if (serie == null) {

			JOptionPane.showMessageDialog(this, "Selecione uma série.");

			return;
		}

		sistema.adicionarDesejaAssistir(serie);

		JOptionPane.showMessageDialog(this, "Série adicionada à lista de desejos!");
	}
	
	private void abrirTelaListas() {
		new TelaListas(sistema);
	}
	
	
	

}
