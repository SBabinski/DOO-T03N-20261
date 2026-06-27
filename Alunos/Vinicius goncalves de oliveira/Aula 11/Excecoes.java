public class Excecoes extends Exception {

    public Excecoes(String mensagem) {
        super(mensagem);
    }

    public static void verificarStatus(int status, String cidade) throws Excecoes {
        switch (status) {
            case 200 -> {
            }
            case 401 -> throw new Excecoes("API Key invalida ou sem autorizacao.");
            case 404 -> throw new Excecoes("Cidade nao encontrada: \"" + cidade + "\".");
            case 429 -> throw new Excecoes("Limite de requisicoes atingido. Tente mais tarde.");
            default -> throw new Excecoes("Erro na API (HTTP " + status + ").");
        }
    }
}
