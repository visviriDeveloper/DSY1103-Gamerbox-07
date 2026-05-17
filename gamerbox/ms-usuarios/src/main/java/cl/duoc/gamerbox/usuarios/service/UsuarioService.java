package cl.duoc.gamerbox.usuarios.service;

import cl.duoc.gamerbox.usuarios.exceptions.BusinessException;
import cl.duoc.gamerbox.usuarios.exceptions.ResourceNotFoundException;
import cl.duoc.gamerbox.usuarios.model.Usuario;
import cl.duoc.gamerbox.usuarios.repository.UsuarioRepository;
import cl.duoc.gamerbox.usuarios.usuarioDTO.UsuarioDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }


    private UsuarioDTO mapToDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getIdUsuario(),
                usuario.getNombreUsuario(),
                usuario.getCorreoUsuario(),
                null, // Ocultamos la contraseña al devolver la respuesta
                usuario.getFechaRegistro(),
                usuario.getActivo()
        );
    }

    private Usuario mapToEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(dto.nombreUsuario());
        usuario.setCorreoUsuario(dto.correoUsuario());
        // El ID, fecha y estado activo se setean en la lógica de negocio
        return usuario;
    }

    // ====================================================================
    // LÓGICA DE NEGOCIO (CRUD)
    // ====================================================================

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::mapToDTO) // Usamos nuestro método manual
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no encontrado"));
    }

    @Transactional
    public UsuarioDTO crear(UsuarioDTO dto) {
        if (usuarioRepository.existsByCorreoUsuario(dto.correoUsuario())) {
            throw new BusinessException("El correo electrónico ya está registrado");
        }

        Usuario usuario = mapToEntity(dto);
        usuario.setPasswordUsuario(dto.passwordUsuario()); // Asignamos la contraseña recibida
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setActivo(true);

        Usuario guardado = usuarioRepository.save(usuario);
        return mapToDTO(guardado);
    }

    @Transactional
    public UsuarioDTO actualizar(Long id, UsuarioDTO dto) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no encontrado"));

        usuarioExistente.setNombreUsuario(dto.nombreUsuario());
        usuarioExistente.setCorreoUsuario(dto.correoUsuario());

        if (dto.passwordUsuario() != null && !dto.passwordUsuario().isBlank()) {
            usuarioExistente.setPasswordUsuario(dto.passwordUsuario());
        }

        Usuario actualizado = usuarioRepository.save(usuarioExistente);
        return mapToDTO(actualizado);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario con ID " + id + " no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}