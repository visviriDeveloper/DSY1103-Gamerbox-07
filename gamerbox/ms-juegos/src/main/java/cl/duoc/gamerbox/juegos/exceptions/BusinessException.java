package cl.duoc.gamerbox.juegos.exceptions;

public class BusinessException extends RuntimeException {
    public BusinessException(String mensaje) {
        super(mensaje);
    }
}
