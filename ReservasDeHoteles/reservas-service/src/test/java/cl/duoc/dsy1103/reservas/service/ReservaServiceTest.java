package cl.duoc.dsy1103.reservas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.dsy1103.reservas.client.EmpleadoClient;
import cl.duoc.dsy1103.reservas.client.HabitacionClient;
import cl.duoc.dsy1103.reservas.client.HuespedClient;
import cl.duoc.dsy1103.reservas.dto.EmpleadoResponse;
import cl.duoc.dsy1103.reservas.dto.ReservaRequest;
import cl.duoc.dsy1103.reservas.dto.ReservaResponse;
import cl.duoc.dsy1103.reservas.dto.ReservaUpdateRequest;
import cl.duoc.dsy1103.reservas.mapper.ReservaMapper;
import cl.duoc.dsy1103.reservas.model.Reserva;
import cl.duoc.dsy1103.reservas.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ReservaMapper reservaMapper;

    @Mock
    private HabitacionClient habitacionClient;

    @Mock
    private HuespedClient huespedClient;

    @Mock
    private EmpleadoClient empleadoClient;

    @InjectMocks
    private ReservaService reservaService;

    private Reserva reservaMock;
    private ReservaResponse responseMock;
    private ReservaRequest requestMock;

    @BeforeEach
    void setUp() {
        reservaMock = Reserva.builder()
                .idReserva(1L)
                .idHabitacion(1L)
                .idHuesped(1L)
                .idEmpleado(1L)
                .cantDias(3)
                .build();

        responseMock = ReservaResponse.builder()
                .idReserva(1L)
                .idHabitacion(1L)
                .idHuesped(1L)
                .idEmpleado(1L)
                .cantDias(3)
                .build();

        requestMock = new ReservaRequest();
        requestMock.setIdHabitacion(1L);
        requestMock.setIdHuesped(1L);
        requestMock.setIdEmpleado(1L);
        requestMock.setCantDias(3);
    }

    @Test
    @DisplayName("buscarReservas() debe retornar lista de reservas")
    void buscarReservas_debeRetornarLista() {
        when(reservaRepository.findAll()).thenReturn(List.of(reservaMock));
        when(reservaMapper.toResponse(reservaMock)).thenReturn(responseMock);

        List<ReservaResponse> resultado = reservaService.buscarReservas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(reservaRepository).findAll();
    }

    @Test
    @DisplayName("buscarReservaPorId() debe retornar reserva cuando existe")
    void buscarReservaPorId_cuandoExiste_retornaReserva() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reservaMock));
        when(reservaMapper.toResponse(reservaMock)).thenReturn(responseMock);

        ReservaResponse resultado = reservaService.buscarReservaPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdReserva());
        verify(reservaRepository).findById(1L);
    }

    @Test
    @DisplayName("buscarReservaPorId() debe lanzar excepcion cuando no existe")
    void buscarReservaPorId_cuandoNoExiste_lanzaExcepcion() {
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> reservaService.buscarReservaPorId(99L));
        verify(reservaMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("buscarReservasPorEmpleado() debe retornar reservas del empleado")
    void buscarReservasPorEmpleado_debeRetornarReservas() {
        EmpleadoResponse empleadoMock = EmpleadoResponse.builder()
                .idEmpleado(1L)
                .run("12345678-9")
                .build();

        when(empleadoClient.buscarEmpleadoPorRun("12345678-9")).thenReturn(empleadoMock);
        when(reservaRepository.findByIdEmpleado(1L)).thenReturn(List.of(reservaMock));
        when(reservaMapper.toResponse(reservaMock)).thenReturn(responseMock);

        List<ReservaResponse> resultado = reservaService.buscarReservasPorEmpleado("12345678-9");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(empleadoClient).buscarEmpleadoPorRun("12345678-9");
        verify(reservaRepository).findByIdEmpleado(1L);
    }

    @Test
    @DisplayName("buscarReservasPorEmpleado() debe lanzar excepcion cuando el empleado no existe")
    void buscarReservasPorEmpleado_cuandoEmpleadoNoExiste_lanzaExcepcion() {
        when(empleadoClient.buscarEmpleadoPorRun("00000000-0"))
                .thenThrow(new NoSuchElementException("No se ha encontrado al empleado con RUN -> 00000000-0"));

        assertThrows(NoSuchElementException.class,
                () -> reservaService.buscarReservasPorEmpleado("00000000-0"));
        verify(reservaRepository, never()).findByIdEmpleado(any());
    }

    @Test
    @DisplayName("crearReserva() debe crear correctamente validando todos los clients")
    void crearReserva_debeCrearCorrectamente() {

        when(reservaMapper.fromRequest(requestMock)).thenReturn(reservaMock);
        when(reservaRepository.save(reservaMock)).thenReturn(reservaMock);
        when(reservaMapper.toResponse(reservaMock)).thenReturn(responseMock);

        ReservaResponse resultado = reservaService.crearReserva(requestMock);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdReserva());
        verify(huespedClient).buscarHuespedPorId(1L);
        verify(empleadoClient).buscarEmpleadoPorId(1L);
        verify(habitacionClient).buscarHabitacionPorId(1L);
        verify(reservaRepository).save(reservaMock);
    }

    @Test
    @DisplayName("crearReserva() debe lanzar excepcion cuando la habitacion no existe")
    void crearReserva_cuandoHabitacionNoExiste_lanzaExcepcion() {
        when(habitacionClient.buscarHabitacionPorId(1L))
                .thenThrow(new NoSuchElementException("No se encontro habitacion con ID -> 1"));

        assertThrows(NoSuchElementException.class,
                () -> reservaService.crearReserva(requestMock));
        verify(reservaRepository, never()).save(any());
    }

    @Test
    @DisplayName("actualizarReserva() debe actualizar solo los campos enviados")
    void actualizarReserva_debeActualizarCamposEnviados() {
        ReservaUpdateRequest updateRequest = new ReservaUpdateRequest();
        updateRequest.setCantDias(5);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reservaMock));
        when(reservaRepository.save(reservaMock)).thenReturn(reservaMock);
        when(reservaMapper.toResponse(reservaMock)).thenReturn(responseMock);

        reservaService.actualizarReserva(1L, updateRequest);

        assertEquals(5, reservaMock.getCantDias());
        verify(habitacionClient, never()).buscarHabitacionPorId(any()); // no se envió, no debe llamarse
        verify(reservaRepository).save(reservaMock);
    }

    @Test
    @DisplayName("actualizarReserva() debe lanzar excepcion cuando no existe")
    void actualizarReserva_cuandoNoExiste_lanzaExcepcion() {
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> reservaService.actualizarReserva(99L, new ReservaUpdateRequest()));
        verify(reservaRepository, never()).save(any());
    }

    @Test
    @DisplayName("eliminarReserva() debe eliminar cuando existe")
    void eliminarReserva_cuandoExiste_eliminaCorrectamente() {
        when(reservaRepository.existsById(1L)).thenReturn(true);

        reservaService.eliminarReserva(1L);

        verify(reservaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("eliminarReserva() debe lanzar excepcion cuando no existe")
    void eliminarReserva_cuandoNoExiste_lanzaExcepcion() {
        when(reservaRepository.existsById(99L)).thenReturn(false);

        assertThrows(NoSuchElementException.class,
                () -> reservaService.eliminarReserva(99L));
        verify(reservaRepository, never()).deleteById(any());
    }
}
