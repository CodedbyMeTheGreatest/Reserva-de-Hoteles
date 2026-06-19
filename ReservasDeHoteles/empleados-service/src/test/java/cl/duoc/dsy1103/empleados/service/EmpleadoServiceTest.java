package cl.duoc.dsy1103.empleados.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTest {

    @Test
    @DisplayName("buscarEmpleadoPorId() debe retornar excepcion cuando no exista")
    void buscarEmpleadoPorIdDebeRetornarExcepcion() {

    }

    @Test
    @DisplayName("buscarEmpleadoPorRun() debe retornar excepcion cuando no exista")
    void buscarEmpleadoPorRunDebeRetornarExcepcion() {
        
    }

    @Test
    @DisplayName("agregarEmpleado() debe retornar excepcion cuando ya exista un empleado con ese RUN")
    void agregarEmpleadoDebeRetornarExcepcionSiExisteRun() {
        
    }

    @Test
    @DisplayName("agregarEmpleado() debe retornar excepcion cuando no exista el hotel")
    void agregarEmpleadoDebeRetornarExcepcionSiNoExisteHotel() {
        
    }

    @Test
    @DisplayName("agregarEmpleado() debe retornar excepcion cuando no los cargos asignados sean invalidos")
    void agregarEmpleadoDebeRetornarExcepcionSiCargosSonInvalidos() {
        
    }

    @Test
    @DisplayName("actualizarEmpleado() debe retornar excepcion cuando no exista")
    void actualizarEmpleadoDebeRetornarExcepcion() {
        
    }

    @Test
    @DisplayName("actualizarEmpleado() debe retornar excepcion cuando no exista el hotel")
    void actualizarEmpleadoDebeRetornarExcepcionSiNoExisteHotel() {
        
    }

    @Test
    @DisplayName("eliminarEmpleado() debe retornar excepcion cuando no exista")
    void eliminarEmpleadoDebeRetornarExcepcion() {
        
    }
}
