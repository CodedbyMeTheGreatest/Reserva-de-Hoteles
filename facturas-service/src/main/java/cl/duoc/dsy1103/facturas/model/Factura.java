package cl.duoc.dsy1103.facturas.model;

import cl.duoc.dsy1103.facturas.enums.EstadoPago;
import cl.duoc.dsy1103.facturas.enums.MetodoPago;
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

    @Column(name = "folio", length = 5, nullable = false, unique = true)
    private String folio;

    @Column(name = "id_reserva", nullable = false, unique = true)
    private Long idReserva;

    @Column(name = "id_pago", nullable = false, unique = true)
    private Long idPago;

    @Column(name = "run_huesped", length = 10, nullable = false)
    private String runHuesped;

    @Column(name = "nombre_huesped", length = 150, nullable = false)
    private String nombreHuesped;

    @Column(name = "id_check_in", nullable = false, unique = true)
    private Long idCheckIn;

    @Column(name = "id_check_out", unique = true)
    private Long idCheckOut;

    @Column(name = "fecha_check_in", nullable = false)
    private LocalDateTime fechaIngreso;

    @Column(name = "fecha_check_out")
    private LocalDateTime fechaSalida;

    @Column(name = "descripcion_habitacion", length = 50, nullable = false)
    private String descripcionHabitacion;

    @Column(name = "cantidad_dias", nullable = false)
    private Integer cantDias;

    @Column(name = "subtotal", nullable = false)
    private Integer subtotal;

    @Column(name = "impuestos", nullable = false)
    private Integer impuestos;

    @Column(name = "total", nullable = false)
    private Integer total;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago",nullable = false)
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago", nullable = false)
    private EstadoPago estadoPago;

    @Column(name = "fecha_factura", nullable = false)
    private LocalDateTime fechaFactura;
    @PrePersist
    protected void onCreate(){
        this.fechaFactura = LocalDateTime.now();
    }
}
