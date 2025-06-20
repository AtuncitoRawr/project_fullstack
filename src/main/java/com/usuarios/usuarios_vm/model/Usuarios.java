package com.usuarios.usuarios_vm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name= "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;

    @Column(nullable = false)
    private String nombre_usuario;

    @Column(nullable = false)
    private String apellido_usuario;

    @Column(nullable = false)
    private String correo_usuario;

    @Column(nullable = false)
    private String telefono_usuario;

    @Column(nullable = false)
    private String contraseña_usuario;

    @Column(nullable = false)
    private String direccion_usuario;
}
