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
                .idHotel(request.getIdHotel())
                .idDisponibilidad(request.getIdDisponibilidad())
                .build();
    }

    public HabitacionResponse toResponse (Habitacion habitacion) {
        return HabitacionResponse.builder()
                .idHabitacion(habitacion.getIdHabitacion())
                .numero(habitacion.getNumero())
                .descripcion(habitacion.getDescripcion())
                .precioPorNoche(habitacion.getPrecioPorNoche())
                .idHotel(habitacion.getIdHotel())
                .idDisponibilidad(habitacion.getIdDisponibilidad())
                .build();
    }
}
