package com.melisoft.ms_usuarios.service;

import com.melisoft.ms_usuarios.model.Usuario;
import com.melisoft.ms_usuarios.repository.UsuarioRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    /**
     * Contiene toda la logica del negocio para la gestion de los usuarios.
     * Se encarga de coordinar las operaciones entre el Controller y el Repository.
     */
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    //Listar todos los usuarios
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    //Busqueda de usuario por PK (id)
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorId(Long id){
        return usuarioRepository.findById(id);
    }

    //Registro de nuevo usuario
    @Transactional
    public Usuario guardar(Usuario usuario) {

        //Se define la fecha de registro al crear el usuario.
        usuario.setFechaRegistro(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> actualizarUsuario(Long id, Usuario usuarioActualizado){
        return usuarioRepository.findById(id).map(usuario ->{
            usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
            usuario.setCorreoUsuario(usuarioActualizado.getCorreoUsuario());
            usuario.setPasswordUsuario(usuarioActualizado.getPasswordUsuario());
            return usuarioRepository.save(usuario);
        });
    }
    @Transactional
    public boolean eliminarUsuario(Long id) {
        boolean encontrado = usuarioRepository.existsById(id);

        if(!encontrado){
            return false;
        }
        usuarioRepository.deleteById(id);
        return true;
    }

}
