package cl.duoc.dsy1103.check_out.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "check_out")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckOut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_salida", nullable = false)
    private Date fechaSalida;

    @Column(name = "id_reserva", nullable = false)
    private Long idReserva;

    @Column(name = "id_empleado", nullable = false)
    private Long idEmpleado;

    @Column(length = 250)
    private String observaciones;


}
