package cl.duoc.dsy1103.empleados.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "run", length = 10, nullable = false, unique = true)
    private String run;

    @Column(name = "nombre_completo", length = 150, nullable = false)
    private String nombreCompleto;

    @Column(name = "cargo", length = 20)
    private String cargo;

    @Column(name = "id_hotel", nullable = false)
    private Long idHotel;

    @Column(name = "nombre_hotel", length = 50)
    private String nombreHotel;

}
