import controller.Controller;
import io.IO;
import repository.LojaRepository;
import repository.VendaRepository;
import repository.VendedorRepository;
import service.Service;

public class Main {
    
    public static IO io = new IO();
    public static Service service = new Service();
    public static VendaRepository vendas = new VendaRepository();
    public static LojaRepository lojas = new LojaRepository();
    public static Controller controller = new Controller(io, service, vendas, lojas);
    
    // inicia instancia
    public static void main(String[] args) {
        controller.iniciar();
    }
}