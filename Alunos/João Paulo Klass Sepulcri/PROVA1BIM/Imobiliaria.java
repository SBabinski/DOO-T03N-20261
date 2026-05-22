import java.util.ArrayList;

public class Imobiliaria {
    ArrayList<Contrato> contratos;

    public Imobiliaria() {
        contratos = new ArrayList<Contrato>();
    }

    public void adicionarContrato(Contrato contrato) {
        if (contratos.size() < 10) {
            contratos.add(contrato);
            System.out.println("Contrato adicionado com sucesso.");
        } else {
            System.out.println("Limite maximo de 10 contratos atingido.");
        }
    }

    public void listarContratosAtivos() {
        boolean encontrouAtivo = false;

        for (int i = 0; i < contratos.size(); i++) {
            Contrato contrato = contratos.get(i);

            if (!contrato.encerrado) {
                System.out.println("Contrato " + i + ":");
                contrato.exibirInformacoes();
                System.out.println("------------------------------");
                encontrouAtivo = true;
            }
        }

        if (!encontrouAtivo) {
            System.out.println("Nao ha contratos ativos.");
        }
    }
}
