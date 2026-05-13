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

    @Column(length = 12, unique = true, nullable = false)
    private String run;

    @Column(name = "nombre_completo", length = 100, nullable = false)
    private String nombreCompleto;

    @Column(length = 50)
    private String cargo;

    @Column(name = "id_hotel", nullable = false)
    private Long idHotel;

    @Column(name = "nombre_hotel")
    private String nombreHotel;

}
