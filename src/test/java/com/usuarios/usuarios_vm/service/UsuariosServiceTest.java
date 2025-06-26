package com.usuarios.usuarios_vm.service;

import com.usuarios.usuarios_vm.model.Usuarios;
import com.usuarios.usuarios_vm.repository.UsuariosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UsuariosServiceTest {

    @Mock
    private UsuariosRepository usuariosRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuariosService usuariosService;

    private Usuarios usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuarios();
        usuario.setId_usuario(1);
        usuario.setNombre_usuario("Juan");
        usuario.setApellido_usuario("Pérez");
        usuario.setCorreo_usuario("juan@example.com");
        usuario.setTelefono_usuario("123456789");
        usuario.setDireccion_usuario("Calle Falsa 123");
        usuario.setContraseña_usuario("secreta");
    }

    @Test
    void testFindAll() {
        List<Usuarios> lista = Arrays.asList(usuario);
        when(usuariosRepository.findAll()).thenReturn(lista);

        List<Usuarios> resultado = usuariosService.findAll();

        assertEquals(1, resultado.size());
        verify(usuariosRepository).findAll();
    }

    @Test
    void testFindById() {
        when(usuariosRepository.findById(1)).thenReturn(Optional.of(usuario));

        Usuarios resultado = usuariosService.findById(1);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre_usuario());
        verify(usuariosRepository).findById(1);
    }

    @Test
    void testSave() {
        // Simular que el PasswordEncoder devuelve una contraseña cifrada
        when(passwordEncoder.encode(anyString())).thenReturn("encrypted-password");

        // Simular que el repositorio guarda el usuario
        when(usuariosRepository.save(any(Usuarios.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuarios resultado = usuariosService.save(usuario);

        // Verificar que se cifró la contraseña antes de guardar
        verify(passwordEncoder).encode("secreta");
        verify(usuariosRepository).save(usuario);

        assertEquals("encrypted-password", resultado.getContraseña_usuario());
    }

    @Test
    void testDelete() {
        usuariosService.delete(1);
        verify(usuariosRepository).deleteById(1);
    }
}