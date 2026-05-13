package cl.duoc.dsy1103.usuarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Huespedes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Huesped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "run_huesped", nullable = false)
    private String run;

    @Column(name = "nombre_huesped", nullable = false)
    private String nombreCompleto;

    @Column(name = "email_huesped", nullable = false)
    private String email;

    @Column(name = "telefono_huesped", nullable = false)
    private int telefono;

    @Column(name = "nacionalidad_huesped", nullable = false)
    private String nacionalidad;

}
