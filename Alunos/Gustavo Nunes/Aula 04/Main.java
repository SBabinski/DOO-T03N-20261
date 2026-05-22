import controller.VendaController;
import io.VendaIO;
import service.VendaService;

public class Main {
    
    public static VendaIO io = new VendaIO();
    public static VendaService service = new VendaService();
    public static VendaController vendaController = new VendaController(io, service);
    
    // inicia instancia
    public static void main(String[] args) {
        vendaController.iniciar();
    }
}