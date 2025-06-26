package com.usuarios.usuarios_vm.assemblers;

import com.usuarios.usuarios_vm.controller.UsuariosController;
import com.usuarios.usuarios_vm.model.Usuarios;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component // Permite que Spring detecte esta clase como un componente y la inyecte automáticamente
public class UsuariosAssembler implements RepresentationModelAssembler<Usuarios, EntityModel<Usuarios>> {

    @Override
    public EntityModel<Usuarios> toModel(Usuarios usuario) {
        // Envuelve un objeto Usuarios con sus enlaces HATEOAS
        return EntityModel.of(usuario,
                // Enlace al recurso actual (self)
                linkTo(methodOn(UsuariosController.class).buscarPorId(usuario.getId_usuario())).withSelfRel(),

                // Enlace a la lista completa de usuarios
                linkTo(methodOn(UsuariosController.class).listar()).withRel("usuarios"),

                // Enlace a eliminar este usuario
                linkTo(methodOn(UsuariosController.class).eliminar(usuario.getId_usuario())).withRel("eliminar"),

                // Enlace a actualizar este usuario (misma URI con PUT)
                linkTo(methodOn(UsuariosController.class).actualizar(usuario.getId_usuario(), usuario)).withRel("actualizar")
        );
    }
}