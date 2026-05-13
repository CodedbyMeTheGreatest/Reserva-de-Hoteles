package cl.duoc.dsy1103.check_in.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "check_ins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_ingreso", nullable = false)
    private Date fechaIngreso;

    @Column(name = "id_reserva", nullable = false)
    private Long idReserva;

    @Column(name = "id_empleado", nullable = false)
    private Long idEmpleado;

    @Column(length = 250)
    private String observaciones;
}
