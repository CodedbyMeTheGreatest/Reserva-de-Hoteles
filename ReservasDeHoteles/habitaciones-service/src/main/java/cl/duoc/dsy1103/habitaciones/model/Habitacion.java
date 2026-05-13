package cl.duoc.dsy1103.habitaciones.model;

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
@Entity
@Table(name = "habitaciones")
@Builder
public class Habitacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHabitacion;

    @Column(nullable = false, unique = true, length = 10)
    private String numero;

    @Column(nullable = false, name = "descripcion", length = 50)
    private String descripcion;

    @Column(nullable = false)
    private Integer precioPorNoche;

    @Column(nullable = false, name = "id_hotel")
    private Long idHotel;

    @Column(nullable = false, name = "id_disponibilidad")
    private Long idDisponibilidad;




}
