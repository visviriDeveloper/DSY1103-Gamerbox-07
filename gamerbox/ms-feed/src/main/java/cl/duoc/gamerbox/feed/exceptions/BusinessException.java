package cl.duoc.gamerbox.feed.exceptions;

public class BusinessException extends RuntimeException {
    public BusinessException(String mensaje) {
        super(mensaje);
    }
}
