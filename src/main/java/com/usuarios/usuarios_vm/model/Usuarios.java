package com.usuarios.usuarios_vm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un usuario del sistema") // Descripción general de la clase
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del usuario", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id_usuario;

    @Column(nullable = false)
    @Schema(description = "Nombre del usuario", example = "Carlos")
    private String nombre_usuario;

    @Column(nullable = false)
    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String apellido_usuario;

    @Column(nullable = false)
    @Schema(description = "Correo electrónico del usuario", example = "carlos.perez@email.com")
    private String correo_usuario;

    @Column(nullable = false)
    @Schema(description = "Número de teléfono del usuario", example = "+56912345678")
    private String telefono_usuario;

    @Column(nullable = false)
    @Schema(description = "Contraseña del usuario", example = "contraseña123")
    private String contraseña_usuario;

    @Column(nullable = false)
    @Schema(description = "Dirección del usuario", example = "Av. Los Leones 123, Santiago")
    private String direccion_usuario;
}