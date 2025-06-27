package com.usuarios.usuarios_vm;

import com.usuarios.usuarios_vm.model.Usuarios;
import com.usuarios.usuarios_vm.repository.UsuariosRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Inyecta el codificador de contraseñas

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        for (int i = 0; i < 3; i++) {
            Usuarios usuario = new Usuarios();
            usuario.setNombre_usuario(faker.name().firstName());
            usuario.setApellido_usuario(faker.name().lastName());
            usuario.setCorreo_usuario(faker.internet().emailAddress());
            usuario.setDireccion_usuario(faker.address().fullAddress());
            usuario.setTelefono_usuario(faker.phoneNumber().cellPhone());

            // Generar y codificar la contraseña
            String contraseniaPlana = faker.internet().password();
            String contraseniaCodificada = passwordEncoder.encode(contraseniaPlana);
            usuario.setContrasenia_usuario(contraseniaCodificada);

            usuariosRepository.save(usuario);
        }
    }
}