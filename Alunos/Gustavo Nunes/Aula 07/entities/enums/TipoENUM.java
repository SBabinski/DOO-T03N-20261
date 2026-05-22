package entities.enums;

public enum TipoENUM {
    FLOR("FLOR"),
    MUDA("MUDA"),
    SEMENTE("SEMENTE");
    
    private String tipo;
    
    TipoENUM(String tipo) {
        this.tipo = tipo;
    }
}
