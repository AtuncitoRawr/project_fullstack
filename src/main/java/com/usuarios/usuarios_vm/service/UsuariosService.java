package com.usuarios.usuarios_vm.service;

import com.usuarios.usuarios_vm.model.Usuarios;
import com.usuarios.usuarios_vm.repository.UsuariosRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional

public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    public List<Usuarios> findAll() {
        return usuariosRepository.findAll();
    }

    public Usuarios findById(Integer id) {
        return usuariosRepository.findById(id).get();
    }

    public Usuarios save(Usuarios usuarios) {
        return usuariosRepository.save(usuarios);
    }

    public void delete(Integer id) {
        usuariosRepository.deleteById(id);
    }
}
