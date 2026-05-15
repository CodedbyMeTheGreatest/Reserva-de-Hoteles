package cl.duoc.dsy1103.pagos.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import cl.duoc.dsy1103.pagos.enums.EstadoPago;
import cl.duoc.dsy1103.pagos.enums.MetodoPago;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Entity
@Table(name = "pagos")
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    @Column(nullable = false, name = "id_habitacion")
    private Long idHabitacion;

    @Column(nullable = false, name = "id_huesped")
    private Long idHuesped;

    @Column(nullable = false, length = 15)
    private BigInteger precioPorNoche;

    @Column(nullable = false, length = 15)
    private Integer cantDias;

    @Column(nullable = false, length = 15)
    private BigInteger subtotal;

    @Column(nullable = false, length = 15)
    private BigInteger impuestos;

    @Column(nullable = false, length = 20)
    private BigInteger total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "metodo_pago")
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "estado_pago")
    private EstadoPago estadoPago;

    private LocalDateTime fechaPago;



}
