package cl.duoc.dsy1103.huespedes.model;

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
@Table(name = "huespedes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Huesped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "run", length = 10, nullable = false, unique = true)
    private String run;

    @Column(name = "nombre_completo", length = 150, nullable = false)
    private String nombreCompleto;

    @Column(name = "email", length = 150, nullable = false)
    private String email;

    @Column(name = "telefono", length = 20, nullable = false)
    private String telefono;

    @Column(name = "nacionalidad", length = 50, nullable = false)
    private String nacionalidad;
}
