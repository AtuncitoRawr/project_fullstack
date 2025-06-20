package com.usuarios.usuarios_vm.controller;

import com.usuarios.usuarios_vm.model.Usuarios;
import com.usuarios.usuarios_vm.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")

public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    @GetMapping
    public ResponseEntity<List<Usuarios>> listar() {
        List<Usuarios> usuarios = usuariosService.findAll();
        if (usuarios.isEmpty()) {
            //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }
    @PostMapping
    public ResponseEntity<Usuarios> guardar(@RequestBody Usuarios usuario) {
        Usuarios usuarios = usuariosService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> buscarPorId(@PathVariable Integer id) {
        try {
            Usuarios usuario = usuariosService.findById(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuarios> actualizar(@PathVariable Integer id, @RequestBody Usuarios usuario) {
        try {
            Usuarios usu = usuariosService.findById(id);
            usu.setId_usuario(id);
            usu.setNombre_usuario(usuario.getNombre_usuario());
            usu.setApellido_usuario(usuario.getApellido_usuario());
            usu.setCorreo_usuario(usuario.getCorreo_usuario());
            usu.setDireccion_usuario(usuario.getDireccion_usuario());
            usu.setTelefono_usuario(usuario.getTelefono_usuario());
            usu.setContraseña_usuario(usuario.getContraseña_usuario());

            usuariosService.save(usu);
            return ResponseEntity.ok(usu);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuarios> eliminar(@PathVariable Integer id) {
        try {
            usuariosService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
