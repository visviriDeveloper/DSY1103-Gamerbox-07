package cl.duoc.gamerbox.usuarios.exceptions;

public class BusinessException extends RuntimeException {
    public BusinessException(String mensaje) {
        super(mensaje);
    }
}
