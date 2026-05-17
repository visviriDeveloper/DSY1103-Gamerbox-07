package cl.duoc.gamerbox.login.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 404 - No encontrado
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> notFound(ResourceNotFoundException ex, HttpServletRequest req) {
        log.warn("404 {}: {}", req.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(404, "Not Found", ex.getMessage(), req.getRequestURI(), null));
    }

    // 422 - Error de negocio
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> business(BusinessException ex, HttpServletRequest req) {
        log.warn("422 {}: {}", req.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ApiError(422, "Unprocessable Entity", ex.getMessage(), req.getRequestURI(), null));
    }

    // 400 - Error de validación de argumentos (@Valid en Controllers)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<String> errs = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage()).toList();
        log.warn("400 {}: {}", req.getRequestURI(), errs);
        return ResponseEntity.badRequest()
                .body(new ApiError(400, "Bad Request", "Errores de validación", req.getRequestURI(), errs));
    }

    // 400 - Violación de restricciones (JPA/Constraints)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> constraint(ConstraintViolationException ex, HttpServletRequest req) {
        List<String> errs = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage()).toList();
        return ResponseEntity.badRequest()
                .body(new ApiError(400, "Bad Request", "Restricción violada", req.getRequestURI(), errs));
    }

    // 500 - Error genérico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> generic(Exception ex, HttpServletRequest req) {
        log.error("500 {}: {}", req.getRequestURI(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(500, "Internal Server Error", ex.getMessage(), req.getRequestURI(), null));
    }
}