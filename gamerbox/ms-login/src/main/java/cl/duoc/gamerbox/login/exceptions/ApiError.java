package cl.duoc.gamerbox.login.exceptions;

import java.time.LocalDateTime;
import java.util.List;

public class ApiError {
    private int status; private String error; private String message; private String path;
    private LocalDateTime timestamp; private List<String> detalles;

    public ApiError(int s, String e, String m, String p, List<String> d) {
        this.status=s; this.error=e; this.message=m; this.path=p;
        this.timestamp=LocalDateTime.now(); this.detalles=d;
    }
    public int getStatus() { return status; } public String getError() { return error; }
    public String getMessage() { return message; } public String getPath() { return path; }
    public LocalDateTime getTimestamp() { return timestamp; } public List<String> getDetalles() { return detalles; }
}

