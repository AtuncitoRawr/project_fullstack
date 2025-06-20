package com.usuarios.usuarios_vm;

import com.usuarios.usuarios_vm.model.Usuarios;
import com.usuarios.usuarios_vm.repository.UsuariosRepository;
import com.usuarios.usuarios_vm.service.UsuariosService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuariosServiceTest {

	@Mock
	private UsuariosRepository usuariosRepository;

	@InjectMocks
	private UsuariosService usuariosService;

	@Test
	void testFindById() {
		// Arrange
		Usuarios usuario = new Usuarios();
		usuario.setId_usuario(2);
		usuario.setNombre_usuario("Carlos");

		when(usuariosRepository.findById(2)).thenReturn(Optional.of(usuario));

		// Act
		Usuarios resultado = usuariosService.findById(2);

		// Assert
		assertNotNull(resultado);
		assertEquals("Carlos", resultado.getNombre_usuario());
	}
}