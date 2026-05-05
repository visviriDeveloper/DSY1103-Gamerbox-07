package com.melisoft.ms_resenias.Service;

import com.melisoft.ms_resenias.client.JuegoClient;
import com.melisoft.ms_resenias.Model.Resenia;
import com.melisoft.ms_resenias.Repository.ReseniaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReseniaService {

    @Autowired
    private ReseniaRepository reseniaRepository;

    @Autowired
    private JuegoClient juegoClient;

    public Resenia crearResenia(Resenia resenia) {
        // Validación cruzada con ms-juegos vía Feign
        try {
            juegoClient.obtenerJuegoPorId(resenia.getIdJuego());
        } catch (Exception e) {
            throw new IllegalArgumentException("El juego especificado no existe en el catálogo.");
        }

        // La fecha y el estado se asignan automáticamente por el @PrePersist en el Model
        return reseniaRepository.save(resenia);
    }

    public List<Resenia> obtenerTodasActivas() {
        return reseniaRepository.findByEstadoTrue();
    }

    public List<Resenia> obtenerPorJuego(Long idJuego) {
        return reseniaRepository.findByIdJuegoAndEstadoTrue(idJuego);
    }
    
    public void eliminarLogica(Long idResenia) {
        Resenia resenia = reseniaRepository.findById(idResenia)
                .orElseThrow(() -> new IllegalArgumentException("Reseña no encontrada"));
        resenia.setEstado(false);
        reseniaRepository.save(resenia);
    }
}