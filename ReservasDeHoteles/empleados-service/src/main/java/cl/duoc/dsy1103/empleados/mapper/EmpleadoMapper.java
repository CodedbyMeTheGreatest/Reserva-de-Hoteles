package cl.duoc.dsy1103.empleados.mapper;

import cl.duoc.dsy1103.empleados.dto.EmpleadoRequest;
import cl.duoc.dsy1103.empleados.dto.EmpleadoResponse;
import cl.duoc.dsy1103.empleados.model.Empleado;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoMapper {

    public Empleado fromRequest(EmpleadoRequest request){
        return Empleado.builder()
                .run(request.getRun())
                .nombreCompleto(request.getNombreCompleto())
                .cargo(request.getCargo())
                .idHotel(request.getIdHotel())
                .build();
    }

    public EmpleadoResponse toResponse(Empleado empleado){
        return EmpleadoResponse.builder()
                .idEmpleado(empleado.getId())
                .run(empleado.getRun())
                .nombreCompleto(empleado.getNombreCompleto())
                .cargo(empleado.getCargo())
                .idHotel(empleado.getIdHotel())
                .nombreHotel(empleado.getNombreHotel())
                .build();
    }
}
