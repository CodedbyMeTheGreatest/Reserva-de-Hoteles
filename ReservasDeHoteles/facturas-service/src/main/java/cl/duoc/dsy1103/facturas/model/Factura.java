package cl.duoc.dsy1103.facturas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
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

    @Column(nullable = false, unique = true)
    private String folio;

    @Column(nullable = false, unique = true,  name = "id_reserva")
    private Long idReserva;

    @Column(nullable = false, unique = true, name = "id_pago")
    private Long idPago;

    @Column(nullable = false, name = "run_huesped")
    private String runHuesped;

    @Column(nullable = false, name = "nombre_huesped")
    private String nombreHuesped;

    @Column(nullable = false, name = "fecha_check_in")
    private LocalDateTime fechaIngreso;

    @Column(name = "fecha_check_out")
    private LocalDateTime fechaSalida;

    @Column(nullable = false, name = "descripcion_habitacion")
    private String descripcionHabitacion;

    @Column(nullable = false, name = "cant_dias")
    private Integer cantDias;

    @Column(nullable = false)
    private Integer subtotal;

    @Column(nullable = false)
    private Integer impuestos;

    @Column(nullable = false)
    private Integer total;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false, name = "fecha_factura")
    private LocalDateTime fechaFactura;



}
