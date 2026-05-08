package cl.duoc.dsy1103.empleados.mapper;

import cl.duoc.dsy1103.empleados.dto.EmpleadoRequest;
import cl.duoc.dsy1103.empleados.dto.EmpleadoResponse;
import cl.duoc.dsy1103.empleados.model.Empleado;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoMapper {
    /**
     * Transformacion de Clase EmpleadoRequest a Clase Empleado
     * @param request
     * @return
     */
    public Empleado fromRequest(EmpleadoRequest request){
        return Empleado.builder()
                .run(request.getRun())
                .nombreCompleto(request.getNombreCompleto())
                .cargo(request.getCargo())
                .idHotel(request.getIdHotel())
                .build();
    }

    /**
     * Transformacion de Clase Empleado a Clase EmpleadoResponse
     * @param empleado
     * @return
     */
    public EmpleadoResponse toResponse(Empleado empleado){
        return EmpleadoResponse.builder()
                .idEmpleado(empleado.getIdEmpleado())
                .run(empleado.getRun())
                .nombreCompleto(empleado.getNombreCompleto())
                .cargo(empleado.getCargo())
                .idHotel(empleado.getIdHotel())
                .build();
    }
}
