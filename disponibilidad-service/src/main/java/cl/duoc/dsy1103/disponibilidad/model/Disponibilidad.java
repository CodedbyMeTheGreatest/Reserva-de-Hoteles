package cl.duoc.dsy1103.disponibilidad.model;

import java.time.LocalDateTime;

import cl.duoc.dsy1103.disponibilidad.enums.EstadoDisponibilidad;
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
@Table(name = "disponibilidades")
@Builder
public class Disponibilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDisponibilidad;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "estado")
    private EstadoDisponibilidad estado;

    @Column(nullable = false, name = "fecha_desde")
    private LocalDateTime fechaDesde;

    @Column(name = "fecha_hasta")
    private LocalDateTime fechaHasta;

}
