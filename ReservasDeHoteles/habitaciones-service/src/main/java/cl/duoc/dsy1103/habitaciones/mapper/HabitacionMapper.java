package cl.duoc.dsy1103.habitaciones.mapper;

import org.springframework.stereotype.Component;

import cl.duoc.dsy1103.habitaciones.dto.HabitacionRequest;
import cl.duoc.dsy1103.habitaciones.dto.HabitacionResponse;
import cl.duoc.dsy1103.habitaciones.model.Habitacion;

@Component
public class HabitacionMapper {

    public Habitacion fromRequest (HabitacionRequest request) {
        return Habitacion.builder()
                .numero(request.getNumero())
                .descripcion(request.getDescripcion())
                .precioPorNoche(request.getPrecioPorNoche())
                .id_hotel(request.getId_hotel())
                .id_disponibilidad(request.getId_disponibilidad())
                .build();
    }

    public HabitacionResponse toResponse (Habitacion habitacion) {
        return HabitacionResponse.builder()
                .id(habitacion.getId())
                .numero(habitacion.getNumero())
                .descripcion(habitacion.getDescripcion())
                .precioPorNoche(habitacion.getPrecioPorNoche())
                .id_hotel(habitacion.getId_hotel())
                .id_disponibilidad(habitacion.getId_disponibilidad())
                .build();
    }
}
