package cl.duoc.gamerbox.usuarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Schema(description = "Entidad que representa un usuario registrado en el ecosistema Gamerbox")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único autoincrementable del usuario", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idUsuario;

    @Column(name = "nombre_usuario", nullable = false, length = 50)
    @Schema(description = "Nombre de usuario o nickname público", example = "Solaire_Astora28")
    private String nombreUsuario;

    @Column(name = "correo_usuario", nullable = false, unique = true, length = 100)
    @Schema(description = "Correo electrónico único vinculado a la cuenta", example = "matisoftware@gamerbox.cl")
    private String correoUsuario;

    @Column(name = "password_usuario", nullable = false)
    @Schema(description = "Contraseña del usuario (almacenada de forma segura)", example = "KagurabachibestAnime4ever")
    private String passwordUsuario;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    @Schema(description = "Fecha y hora exacta de la creación de la cuenta", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaRegistro;

    @Column(name = "activo", nullable = false)
    @Schema(description = "Estado de la cuenta (true = activa, false = suspendida/eliminada)", example = "true", accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean activo;

    // CONSTRUCTORES

    public Usuario() {
    }

    public Usuario(Long idUsuario, String nombreUsuario, String correoUsuario, String passwordUsuario, LocalDateTime fechaRegistro, Boolean activo) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.correoUsuario = correoUsuario;
        this.passwordUsuario = passwordUsuario;
        this.fechaRegistro = fechaRegistro;
        this.activo = activo;
    }

    // --- LÓGICA DE PERSISTENCIA ---

    @PrePersist
    public void onPrePersist() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDateTime.now();
        }
        if (this.activo == null) {
            this.activo = true;
        }
    }

    //GETTERS Y SETTERS

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getPasswordUsuario() {
        return passwordUsuario;
    }

    public void setPasswordUsuario(String passwordUsuario) {
        this.passwordUsuario = passwordUsuario;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}