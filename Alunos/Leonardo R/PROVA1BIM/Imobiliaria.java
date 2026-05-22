public class Imobiliaria {

    private Contrato[] contratos = new Contrato[10];
    private int quantidade = 0;

    public void adicionarContrato(Contrato contrato) {
        if (quantidade < contratos.length) {
            contratos[quantidade] = contrato;
            quantidade++;
        } else {
            System.out.println("Limite de contratos atingido.");
        }
    }

    public void encerrarContrato(int index) {
        if (index >= 0 && index < quantidade) {
            contratos[index].encerrarContrato();
        } else {
            System.out.println("Numero inválido.");
        }
    }

    public void listarContratosAtivos() {
        System.out.println("Contratos Ativos:");
        for (int i = 0; i < quantidade; i++) {
            if (!contratos[i].isEncerrou()) {
                System.out.println("Numero: " + i);
                contratos[i].exibirContrato();
                System.out.println("-------------------");
            }
        }
    }

    public void listarTodos() {
        System.out.println("Todos os Contratos:");

        for (int i = 0; i < quantidade; i++) {
            System.out.println("Numero: " + i);
            contratos[i].exibirContrato();
            System.out.println("----------------------");
        }
    }
}