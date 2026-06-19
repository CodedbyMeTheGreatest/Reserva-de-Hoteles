package cl.duoc.dsy1103.check_in.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.dsy1103.check_in.client.EmpleadoClient;
import cl.duoc.dsy1103.check_in.client.ReservaClient;
import cl.duoc.dsy1103.check_in.dto.CheckInRequest;
import cl.duoc.dsy1103.check_in.dto.CheckInResponse;
import cl.duoc.dsy1103.check_in.dto.EmpleadoResponse;
import cl.duoc.dsy1103.check_in.dto.ReservaResponse;
import cl.duoc.dsy1103.check_in.mapper.CheckInMapper;
import cl.duoc.dsy1103.check_in.model.CheckIn;
import cl.duoc.dsy1103.check_in.repository.CheckInRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CheckInServiceTest {
    @Mock
    private CheckInRepository repository;
    @Mock
    private ReservaClient reservaClient;
    @Mock 
    private EmpleadoClient empleadoClient;
    @Mock
    private CheckInMapper mapper;

    @InjectMocks
    private CheckInService service;

    private CheckIn checkInMock;
    private CheckInResponse responseMock;
    private CheckInRequest requestMock;
    private ReservaResponse reservaMock;
    private EmpleadoResponse empleadoMock;

    @BeforeEach
    public void setUp(){
        LocalDateTime fechaMock = LocalDateTime.now();

        checkInMock = CheckIn.builder()
                .id(1L)
                .idReserva(10L)
                .idEmpleado(20L)
                .fechaIngreso(fechaMock)
                .build();

        responseMock = CheckInResponse.builder()
                .id(1L)
                .idReserva(10L)
                .idEmpleado(20L)
                .fechaIngreso(fechaMock)
                .build();

        requestMock = CheckInRequest.builder()
                .idReserva(10L)
                .idEmpleado(20L)
                .build();

        empleadoMock = EmpleadoResponse.builder()
                .idEmpleado(20L)
                .run("12345678-9")
                .nombreCompleto("Juan Pérez")
                .cargo("Recepcionista")
                .idHotel(1L)
                .nombreHotel("Hotel Central")
                .build();

        reservaMock = ReservaResponse.builder()
                .id(10L)
                .idHabitacion(100L)
                .idHuesped(200L)
                .idEmpleado(20L)
                .cantDias(3)
                .idCheckIn(1L)
                .idCheckOut(null)
                .build();
        reservaMock = ReservaResponse.builder()
            .id(20L)
            .idHabitacion(100L)
            .idHuesped(100L)
            .idEmpleado(20L)
            .cantDias(3)
            .idCheckIn(2L)
            .idCheckOut(null)
            .build();
    }

    @Test
    @DisplayName("buscarCheckInPorId() debe retornar excepcion cuando no existe")
    void buscarCheckInPorIdDebeRetornarExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarCheckInPorId(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
        
        verify(repository).findById(99L);
        verify(mapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("agregarCheckIn() debe retornar excepcion cuando no exista la reserva")
    void agregarCheckInDebeRetornarExcepcionSiNoExisteReserva() {

    }

    @Test
    @DisplayName("agregarCheckIn() debe retornar excepcion cuando no exista la reserva")
    void agregarCheckInDebeRetornarExcepcionSiNoExisteEmpleado() {

    }

    @Test
    @DisplayName("actualizarCheckIn() debe retornar excepcion cuando no exista")
    void actualizarCheckInDebeRetornarExcepcion() {

    }
    
    @Test
    @DisplayName("actualizarCheckIn() debe retornar excepcion cuando no exista la reserva")
    void actualizarCheckInDebeRetornarExcepcionSiNoExisteReserva() {

    }

    @Test
    @DisplayName("actualizarCheckIn() debe retornar excepcion cuando no exista la reserva")
    void actualizarCheckInDebeRetornarExcepcionSiNoExisteEmpleado() {

    }

    @Test
    @DisplayName("eliminarCheckIn() debe retornar excepcion cuando no exista")
    void eliminarCheckInDebeRetornarExcepcion() {

    }
}
