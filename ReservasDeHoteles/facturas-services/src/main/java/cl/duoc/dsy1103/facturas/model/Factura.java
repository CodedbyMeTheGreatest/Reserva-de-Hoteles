package cl.duoc.dsy1103.facturas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="facturas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String folio;

    @Column(nullable = false, name = "id_reserva")
    private Long idReserva;

    @Column(nullable = false, length = 12, name = "run_huesped")
    private String runHuesped;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false, unique = true, name = "id_pago")
    private Long idPago;

    @Column(nullable = false, length = 250, name = "descripcion_habitacion")
    private String descripcionHabitacion;

    @Column(nullable = false, length = 50)
    private String estado;




}
