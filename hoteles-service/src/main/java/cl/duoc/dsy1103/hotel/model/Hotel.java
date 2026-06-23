package cl.duoc.dsy1103.hotel.model;

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
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hoteles")
@Builder
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHotel;

    @Column(unique = true, nullable = false, length = 12, name = "rut_hotel")
    private String rut;

    @Column(nullable = false, length = 200, name = "direccion")
    private String direccion;

    @Column(nullable = false, length = 100, name = "nombre_hotel")
    private String nombre;

}
