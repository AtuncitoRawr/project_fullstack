package com.usuarios.usuarios_vm.service;

import com.usuarios.usuarios_vm.model.Usuarios;
import com.usuarios.usuarios_vm.repository.UsuariosRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuarios> findAll() {
        return usuariosRepository.findAll();
    }

    public Usuarios findById(Integer id) {
        return usuariosRepository.findById(id).get();
    }

    // Guardar un nuevo usuario (o actualizar) con cifrado de contraseña
    public Usuarios save(Usuarios usuario) {
        // Si es un nuevo usuario o si se está actualizando la contraseña
        if (usuario.getContraseña_usuario() != null && !usuario.getContraseña_usuario().startsWith("$2a$")) {
            String contraseñaCodificada = passwordEncoder.encode(usuario.getContraseña_usuario());
            usuario.setContraseña_usuario(contraseñaCodificada);
        }
        return usuariosRepository.save(usuario);
    }

    public void delete(Integer id) {
        usuariosRepository.deleteById(id);
    }
}
