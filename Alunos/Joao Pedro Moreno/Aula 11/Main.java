import java.awt.*;
import javax.swing.*;

public class Main extends JFrame{

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		criarTela();
	}

	private JTextField txtCidade;
	private JTextField txtEstado;
	private JPasswordField txtApiKey;

	private JLabel lblTemp;
	private JLabel lblMax;
	private JLabel lblMin;
	private JLabel lblUmidade;
	private JLabel lblCondicao;
	private JLabel lblPrecipitacao;
	private JLabel lblVento;
	private JLabel lblDirecao;

	public void criarTela() {
		setTitle("Sistema de Consulta de Clima");
		setSize(600, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// cabeçalho
		JPanel painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout(10, 10));
		painelPrincipal.setBackground(Color.WHITE);
		
		// painel central
		JPanel painelCentro = new JPanel();
		painelCentro.setBackground(Color.WHITE);
		painelCentro.setLayout(new GridLayout(11, 2, 5, 5));

		txtCidade = new JTextField();
		txtEstado = new JTextField();
		txtApiKey = new JPasswordField();

		lblTemp = new JLabel("Temperatura:");
		lblMax = new JLabel("Máxima:");
		lblMin = new JLabel("Mínima:");
		lblUmidade = new JLabel("Umidade:");
		lblCondicao = new JLabel("Condição:");
		lblPrecipitacao = new JLabel("Precipitação:");
		lblVento = new JLabel("Velocidade do vento:");
		lblDirecao = new JLabel("Direção do vento:");

		painelCentro.add(new JLabel("Cidade:"));
		painelCentro.add(txtCidade);

		painelCentro.add(new JLabel("Estado (PR, SP...):"));
		painelCentro.add(txtEstado);

		painelCentro.add(new JLabel("Chave da API:"));
		painelCentro.add(txtApiKey);

		painelCentro.add(lblTemp);
		painelCentro.add(new JLabel());

		painelCentro.add(lblMax);
		painelCentro.add(new JLabel());

		painelCentro.add(lblMin);
		painelCentro.add(new JLabel());

		painelCentro.add(lblUmidade);
		painelCentro.add(new JLabel());

		painelCentro.add(lblCondicao);
		painelCentro.add(new JLabel());

		painelCentro.add(lblPrecipitacao);
		painelCentro.add(new JLabel());

		painelCentro.add(lblVento);
		painelCentro.add(new JLabel());

		painelCentro.add(lblDirecao);
		painelCentro.add(new JLabel());

		painelPrincipal.add(painelCentro, BorderLayout.CENTER);

		// botão centralizado
		JButton btnBuscar = new JButton("Buscar");

		JPanel painelBotao = new JPanel();

		painelBotao.setBackground(Color.WHITE);
		painelBotao.add(btnBuscar);

		painelPrincipal.add(painelBotao, BorderLayout.SOUTH);

		add(painelPrincipal);

		btnBuscar.addActionListener(e -> {
			try {

				String cidade = txtCidade.getText().trim(); //LEMBRAR: o trim serve para
				String estado = txtEstado.getText().trim(); //excluir espaços em brancos
				String chaveApi = new String(txtApiKey.getPassword()).trim();

				ApiServico climApiServico = new ApiServico();
				Clima clima = climApiServico.buscarClima(cidade, estado, chaveApi);

				lblTemp.setText("Temperatura: " + clima.getTemperatura() + " °C");
				lblMax.setText("Máxima: " + clima.getTempMaxima() + " °C");
				lblMin.setText("Mínima: " + clima.getTempMinima() + " °C");
				lblUmidade.setText("Umidade: " + clima.getumidade() + "%");
				lblCondicao.setText("Condição: " + clima.getCondicao());
				lblPrecipitacao.setText("Precipitação: " + clima.getQtdChuva() + " mm");
				lblVento.setText("Velocidade do vento: " + clima.getVelVento() + " km/h");
				lblDirecao.setText("Direção do vento: " + clima.getDirVento());

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, 
						ex.getMessage(), 
						"Erro encontrado", 
						JOptionPane.ERROR_MESSAGE);
			}
		});
		setVisible(true);
	}
}