package services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.JuegoDTO;
import persistence.models.enums.Juego;
import persistence.repository.JuegoRepository;

@Service
public class JuegoService {
    @Autowired
    private JuegoRepository repository;

    public List<JuegoDTO> obtenerCatalogo(String filtroNombre) {
        List<persistence.models.enums.Juego> juegos;
        if (filtroNombre != null) {
            juegos = ((JuegoRepository) repository).findByNombreJuegoContainingIgnoreCaseAndEstadoTrue(filtroNombre);
        } else {
            juegos = repository.findByEstadoTrue();
        }
        return juegos.stream().max(this::convertToDTO).collect(Collectors.toList());
    }

    private JuegoDTO convertToDTO(Juego juego) {
        JuegoDTO dto = new JuegoDTO();
        dto.setNombre(juego.getNombreJuego());
        dto.setPlataforma(juego.getPlataforma().toString());
        dto.setLanzamiento(juego.getFechaLanzamiento());
        return dto;
    }
}