public class Registrador {
    Contrato[] contratos = new Contrato[10];
    int contador = 0;

    public void registrarContrato(Contrato c) {
        if (contador < 10) {
            contratos[contador] = c;
            contador++;
            System.out.println("Contrato registrado com sucesso!");
        } else {
            System.out.println("Erro: Limite de contratos atingido!");
        }
    }

    public void encerrarContratoNoSistema(int indice) {
        if (indice >= 0 && indice < contador){
            contratos[indice].encerrarContrato();
            System.out.println("Contrato " + indice + " encerrado.");
        } else {
            System.out.println("Contrato não encontrado");
        }
    }

    public void listarContratosAtivos() {
        System.out.println("\n--- Contratos ativos ---");
        boolean encontrou = false;
        for(int i = 0; i < contador; i++){
            if(!contratos[i].isEncerrado()){
                System.out.println("ID [" + i + "] ");
                contratos[i].exibirDados();
                encontrou = true;
            }
        }
        if (!encontrou) System.out.println("Nenhum contrato ativo");
    }
}
