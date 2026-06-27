import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorClima {
    private final JanelaClima janela;
    private final ServicoClima servico;

    public ControladorClima(JanelaClima janela, ServicoClima servico) {
        this.janela = janela;
        this.servico = servico;

        this.janela.aoPesquisar(new AcaoPesquisarClima());
    }

    private class AcaoPesquisarClima implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evento) {
            String cidadeInformada = janela.obterCidadeDigitada();

            if (!cidadeTemTamanhoMinimo(cidadeInformada)) {
                janela.limparResultado();
                janela.mostrarErro("Digite pelo menos 2 caracteres para pesquisar.");
                return;
            }

            janela.prepararNovaBusca();

            try {
                ClimaAtual climaEncontrado = servico.consultarPorCidade(cidadeInformada);
                janela.mostrarResultado(climaEncontrado);
            } catch (Exception erro) {
                janela.limparResultado();
                janela.mostrarErro("Nao foi possivel buscar o clima. Verifique a cidade e tente novamente.");
                erro.printStackTrace();
            } finally {
                janela.finalizarBusca();
            }
        }
    }

    private boolean cidadeTemTamanhoMinimo(String cidadeInformada) {
        return cidadeInformada != null && cidadeInformada.length() >= 2;
    }
}
