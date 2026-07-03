import view.TelaPrincipal;

public class Main {

    public static void main(String[] args) {

        try {
            TelaPrincipal tela =
                    new TelaPrincipal();

            tela.setVisible(true);

        } catch(Exception e){

            System.out.println(
                    "Erro ao iniciar: "
                    + e.getMessage());

        }
    }
}