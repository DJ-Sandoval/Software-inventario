package com.software.api_Inventario.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "proveedores")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;
    private String telefono;
    private String correo;
    private String direccion;
    private String contacto;
    private boolean estado;
}
