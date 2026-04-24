public class Imobiliaria {

    private Inquilino[] inquilinos = new Inquilino[10];
    private Imovel[] imoveis = new Imovel[10];
    private Contrato[] contratos = new Contrato[10];

    private int qntInq = 0;
    private int qntImov = 0;
    private int qntCont = 0;

    // INQUILINO
    public void addInquilino(Inquilino i) {
        if (qntInq < 10) {
            inquilinos[qntInq++] = i;
        }
    }

    public Inquilino getInquilino(int i) {
        return inquilinos[i];
    }

    public void listarInquilinos() {
        for (int i = 0; i < qntInq; i++) {
            System.out.println(i + " - " + inquilinos[i]);
        }
    }

    // IMOVEL
    public void addImovel(Imovel i) {
        if (qntImov < 10) {
            imoveis[qntImov++] = i;
        }
    }

    public Imovel getImovel(int i) {
        return imoveis[i];
    }

    public void listarImoveis() {
        for (int i = 0; i < qntImov; i++) {
            System.out.println(i + " - " + imoveis[i]);
        }
    }

    // CONTRATO
    public void addContrato(Contrato c) {
        if (qntCont < 10) {
            contratos[qntCont++] = c;
        }
    }

    public Contrato getContrato(int i) {
        return contratos[i];
    }

    public void listarAtivos() {
        for (int i = 0; i < qntCont; i++) {
            if (!contratos[i].isEncerrado()) {
                contratos[i].exibirDados();
            }
        }
    }
}