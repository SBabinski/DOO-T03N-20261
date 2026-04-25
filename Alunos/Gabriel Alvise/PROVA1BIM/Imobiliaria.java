public class Imobiliaria {
    private ContratoAluguel[] contratos;
    private int quantidadeContratos;

    public Imobiliaria() {
        contratos = new ContratoAluguel[10];
        quantidadeContratos = 0;
    }

    public void adicionarContrato(ContratoAluguel contrato) {
        if (quantidadeContratos < contratos.length) {
            contratos[quantidadeContratos] = contrato;
            quantidadeContratos++;
            System.out.println("Contrato cadastrado");
        } else {
            System.out.println("Não é possível cadastrar mais contratos");
        }
    }

    public boolean listarContratosAtivos() {
        boolean encontrou = false;

        System.out.println("\nCONTRATOS ATIVOS");

        for (int i = 0; i < quantidadeContratos; i++) {
            if (!contratos[i].isEncerrado()) {
                System.out.println("Contrato número: " + i);
                contratos[i].exibirContrato();
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum contrato ativo encontrado");
        }

        return encontrou;
    }

    public void listarTodosContratos() {
        for (int i = 0; i < quantidadeContratos; i++) {
            System.out.println("Contrato número: " + i);
            contratos[i].exibirContrato();
        }
    }

    public void encerrarContrato(int indice) {
        if (indice >= 0 && indice < quantidadeContratos) {
            if (!contratos[indice].isEncerrado()) {
                contratos[indice].encerrarContrato();
                System.out.println("Contrato encerrado");
            } else {
                System.out.println("Esse contrato já está encerrado");
            }
        } else {
            System.out.println("Contrato não encontrado");
        }
    }
}