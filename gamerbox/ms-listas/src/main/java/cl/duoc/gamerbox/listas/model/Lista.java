package cl.duoc.gamerbox.listas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "listas")
@Schema(description = "Entidad que representa una lista personalizada de videojuegos creada por un usuario (ej. 'Mis RPGs favoritos')")
public class Lista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincrementable de la lista", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idLista;

    @Column(name = "id_usuario", nullable = false)
    @Schema(description = "Identificador del usuario creador de la lista (Referencia a ms-usuarios)", example = "42")
    private Long idUsuario;

    @Column(name = "nombre_lista", nullable = false, length = 100)
    @Schema(description = "Nombre visible de la lista", example = "Juegos por terminar en 2026")
    private String nombreLista;

    @Column(name = "lista_activa", nullable = false)
    @Schema(description = "Estado lógico de la lista (true = visible, false = eliminada)", example = "true", accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean listaActiva;

    @Column(name = "fecha_creacion_lista", nullable = false)
    @Schema(description = "Fecha y hora exacta en la que se creó la lista", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaCreacionLista;

    public Lista() {}

    @PrePersist
    public void onPrePersist() {
        if (fechaCreacionLista == null) fechaCreacionLista = LocalDateTime.now();
        if (listaActiva == null) listaActiva = true;
    }

    public Long getIdLista() { return idLista; } public void setIdLista(Long idLista) { this.idLista = idLista; }
    public Long getIdUsuario() { return idUsuario; } public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getNombreLista() { return nombreLista; } public void setNombreLista(String nombreLista) { this.nombreLista = nombreLista; }
    public Boolean getListaActiva() { return listaActiva; } public void setListaActiva(Boolean listaActiva) { this.listaActiva = listaActiva; }
    public LocalDateTime getFechaCreacionLista() { return fechaCreacionLista; } public void setFechaCreacionLista(LocalDateTime fechaCreacionLista) { this.fechaCreacionLista = fechaCreacionLista; }
}
