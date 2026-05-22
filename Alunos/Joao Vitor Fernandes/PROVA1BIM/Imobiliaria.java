import java.util.ArrayList;
import java.util.List;

public class Imobiliaria {
    private List<Inquilino> inquilinos;
    private List<Imovel> imoveis;
    private Contrato[] contratos;
    private int numContratos;

    public Imobiliaria() {
        inquilinos = new ArrayList<>();
        imoveis = new ArrayList<>();
        contratos = new Contrato[10];
        numContratos = 0;
    }

    public void adicionarInquilino(Inquilino inquilino) {
        inquilinos.add(inquilino);
    }

    public void adicionarImovel(Imovel imovel) {
        imoveis.add(imovel);
    }

    public boolean adicionarContrato(Contrato contrato) {
        if (numContratos < 10) {
            contratos[numContratos] = contrato;
            numContratos++;
            return true;
        }
        return false;
    }

    public void encerrarContrato(int indice) {
        if (indice >= 0 && indice < numContratos) {
            contratos[indice].encerrar();
        }
    }

    public void listarContratosAtivos() {
        for (int i = 0; i < numContratos; i++) {
            if (contratos[i].isAtivo()) {
                contratos[i].exibirDados();
                System.out.println("---");
            }
        }
    }

    public List<Inquilino> getInquilinos() {
        return inquilinos;
    }

    public List<Imovel> getImoveis() {
        return imoveis;
    }

    public Contrato[] getContratos() {
        return contratos;
    }

    public int getNumContratos() {
        return numContratos;
    }
}