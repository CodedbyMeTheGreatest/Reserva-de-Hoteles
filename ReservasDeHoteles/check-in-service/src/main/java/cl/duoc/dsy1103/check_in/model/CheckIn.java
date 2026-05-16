package cl.duoc.dsy1103.check_in.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "check_in")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_reserva", nullable = false, unique = true)
    private Long idReserva;

    @Column(name = "id_empleado", nullable = false)
    private Long idEmpleado;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDateTime fechaIngreso;

    @Column(name = "observaciones", length = 250)
    private String observaciones;
}
