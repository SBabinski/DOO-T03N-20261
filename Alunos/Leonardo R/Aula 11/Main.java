public class Main {
    public static void main(String[] args) {
        ServicoClima servicoClima = new ServicoVisualCrossingClima();
        JanelaClima janelaClima = new JanelaClima();

        new ControladorClima(janelaClima, servicoClima);

        System.out.println("Aplicacao de clima aberta.");
    }
}
