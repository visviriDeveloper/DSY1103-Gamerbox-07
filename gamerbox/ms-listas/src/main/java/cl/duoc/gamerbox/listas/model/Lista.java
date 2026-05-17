package cl.duoc.gamerbox.listas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "listas")
public class Lista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLista;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "nombre_lista", nullable = false, length = 100)
    private String nombreLista;

    @Column(name = "lista_activa", nullable = false)
    private Boolean listaActiva;

    @Column(name = "fecha_creacion_lista", nullable = false)
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
