package cl.duoc.gamerbox.comentarios.exceptions;

public class BusinessException extends RuntimeException {
    public BusinessException(String mensaje) {
        super(mensaje);
    }
}
