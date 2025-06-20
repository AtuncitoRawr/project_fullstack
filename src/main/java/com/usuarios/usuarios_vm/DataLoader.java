package com.usuarios.usuarios_vm;

import com.usuarios.usuarios_vm.model.*;
import com.usuarios.usuarios_vm.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Profile("dev")
@Component

public class DataLoader implements CommandLineRunner {
    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        for(int i = 0; i < 3; i++){
            Usuarios usuario = new Usuarios();
            usuario.setNombre_usuario(faker.name().firstName());
            usuario.setApellido_usuario(faker.name().lastName());
            usuario.setCorreo_usuario(faker.internet().emailAddress());
            usuario.setDireccion_usuario(faker.address().fullAddress());
            usuario.setTelefono_usuario(faker.phoneNumber().cellPhone());
            usuario.setContraseña_usuario(faker.internet().password());
            usuariosRepository.save(usuario);
        }
    }
}