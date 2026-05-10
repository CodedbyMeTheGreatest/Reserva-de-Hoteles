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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String folio;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private int nroHabitacion;

    @Column(nullable = false)
    private String runHuesped;

    @Column(nullable = false)
    private Long idHotel;

    @Column(nullable = false)
    private int duracionNoches;

    @Column(nullable = false)
    private String descripcionHabitacion;

    @Column(nullable = false)
    private Long idPago;

}
