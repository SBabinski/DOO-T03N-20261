import java.util.ArrayList;

public class Database {
    private static ArrayList<Inquilino> listaDeInquilinos = new ArrayList<>();
    private static ArrayList<Imovel> listaDeImoveis = new ArrayList<>();
    private static ArrayList<Contrato> listaDeContratos = new ArrayList<>();
    private static final int LIMITE_CONTRATOS = 10;

    public static void cadastrarInquilino(Inquilino novoInquilino) {
        listaDeInquilinos.add(novoInquilino);
    }

    public static ArrayList<Inquilino> pegarListaInquilinos() {
        return listaDeInquilinos;
    }

    public static void cadastrarImovel(Imovel novoImovel) {
        listaDeImoveis.add(novoImovel);
    }

    public static ArrayList<Imovel> pegarListaImoveis() {
        return listaDeImoveis;
    }

    public static boolean cadastrarContrato(Contrato novoContrato) {
        if (listaDeContratos.size() < LIMITE_CONTRATOS) {
            listaDeContratos.add(novoContrato);
            return true;
        }
        return false;
    }

    public static ArrayList<Contrato> pegarListaContratos() {
        return listaDeContratos;
    }
}
