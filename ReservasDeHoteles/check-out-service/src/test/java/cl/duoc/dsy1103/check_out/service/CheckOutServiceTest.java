package cl.duoc.dsy1103.check_out.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CheckOutServiceTest {


    @Test
    @DisplayName("buscarCheckOutPorId() debe retornar excepcion cuando no exista")
    void buscarCheckOutPorIdDebeRetornarExcepcion() {

    }

    @Test
    @DisplayName("buscarCheckOutPorIdReserva() debe retornar excepcion cuando no exista")
    void buscarCheckOutPorIdReservaDebeRetornarExcepcion() {
        
    }

    @Test
    @DisplayName("agregarCheckOut() debe retornar excepcion cuando no exista la reserva")
    void agregarCheckOutDebeRetornarExcepcionSiNoExisteReserva() {
        
    }

    @Test
    @DisplayName("agregarCheckOut() debe retornar excepcion cuando no exista el empleado")
    void agregarCheckOutDebeRetornarExcepcionSiNoExisteEmpleado() {
        
    }

    @Test
    @DisplayName("actualizarCheckOut() debe retornar excepcion cuando no exista")
    void actualizarCheckOutDebeRetornarExcepcion() {
        
    }

    @Test
    @DisplayName("actualizarCheckOut() debe retornar excepcion cuando no exista la reserva")
    void actualizarCheckOutDebeRetornarExcepcionSiNoExisteReserva() {
        
    }

    @Test
    @DisplayName("actualizarCheckOut() debe retornar excepcion cuando no exista el empleado")
    void actualizarCheckOutDebeRetornarExcepcionSiNoExisteEmpleado() {
        
    }

    @Test
    @DisplayName("eliminarCheckOut() debe retornar excepcion cuando no exista")
    void eliminarCheckOutDebeRetornarExcepcion() {
        
    }
}
