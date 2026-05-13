package cl.duoc.dsy1103.reservas.model;


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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservas")
@Entity
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;

    @Column(nullable = false, name = "id_habitacion")
    private Long idHabitacion;

    @Column(nullable = false, name = "id_huesped")
    private Long idHuesped;

    @Column(nullable = false, name = "id_empleado")
    private Long idEmpleado;

    @Column(nullable = false, length = 15, name = "cant_dias")
    private String cantDias;

    @Column(nullable = false, name = "id_checkin")
    private Long idCheckIn;

    @Column(nullable = true, name = "id_checkout")
    private Long idCheckOut;

}
