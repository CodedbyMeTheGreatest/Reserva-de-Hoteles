package cl.duoc.dsy1103.empleados.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.dsy1103.empleados.client.HotelClient;
import cl.duoc.dsy1103.empleados.client.ReservaClient;
import cl.duoc.dsy1103.empleados.dto.EmpleadoRequest;
import cl.duoc.dsy1103.empleados.dto.EmpleadoResponse;
import cl.duoc.dsy1103.empleados.dto.EmpleadoUpdateRequest;
import cl.duoc.dsy1103.empleados.dto.HotelResponse;
import cl.duoc.dsy1103.empleados.dto.ReservaResponse;
import cl.duoc.dsy1103.empleados.exception.BadRequestException;
import cl.duoc.dsy1103.empleados.mapper.EmpleadoMapper;
import cl.duoc.dsy1103.empleados.model.Empleado;
import cl.duoc.dsy1103.empleados.repository.EmpleadoRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;
    @Mock
    private EmpleadoMapper empleadoMapper;
    @Mock
    private HotelClient hotelClient;
    @Mock
    private ReservaClient reservaClient;
    @InjectMocks
    private EmpleadoService empleadoService;

    private Empleado empleadoEntity;
    private EmpleadoRequest empleadoRequest;
    private EmpleadoResponse empleadoResponse;

    @BeforeEach
    void setUp() {
        empleadoEntity = Empleado.builder()
                .id(1L)
                .run("12345678-9")
                .nombreCompleto("John Doe")
                .cargo("Recepcionista")
                .idHotel(1L)
                .nombreCompleto("Hotel A")
                .build();
        
        empleadoResponse = EmpleadoResponse.builder()
                .idEmpleado(1L)
                .run("12345678-9")
                .nombreCompleto("John Doe")
                .cargo("Recepcionista")
                .idHotel(1L)
                .nombreCompleto("Hotel A")
                .build();
        
        empleadoRequest = EmpleadoRequest.builder()
                .run("12345678-9")
                .nombreCompleto("John Doe")
                .cargo("Recepcionista")
                .idHotel(1L)
                .build();
    }

    @Test
    void obtenerEmpleadosDebeRetornarListaResponse() {
        when(empleadoRepository.findAll()).thenReturn(List.of(empleadoEntity));
        when(empleadoMapper.toResponse(empleadoEntity)).thenReturn(empleadoResponse);
        
        List<EmpleadoResponse> resultado = empleadoService.obtenerEmpleados();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(empleadoRepository).findAll();
    }

    @Test
    void buscarEmpleadoPorIdDebeRetornarSiExista() {
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleadoEntity));
        when(empleadoMapper.toResponse(empleadoEntity)).thenReturn(empleadoResponse);

        EmpleadoResponse resultado = empleadoService.buscarEmpleadoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdEmpleado());
        verify(empleadoRepository).findById(1L);
    }

    @Test
    @DisplayName("buscarEmpleadoPorId() debe retornar excepcion cuando no exista")
    void buscarEmpleadoPorIdDebeRetornarExcepcion() {
        when(empleadoRepository.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> empleadoService.buscarEmpleadoPorId(99L));

        verify(empleadoMapper, never()).toResponse(any());
    }

    @Test
    void buscarEmpleadoPorRunDebeRetornarSiExiste() {
        when(empleadoRepository.findByRun("12345678-9")).thenReturn(Optional.of(empleadoEntity));
        when(empleadoMapper.toResponse(empleadoEntity)).thenReturn(empleadoResponse);

        EmpleadoResponse resultado = empleadoService.buscarEmpleadoPorRun("12345678-9");

        assertNotNull(resultado);
        assertEquals("12345678-9", resultado.getRun());
        verify(empleadoRepository).findByRun("12345678-9");
    }

    @Test
    @DisplayName("buscarEmpleadoPorRun() debe retornar excepcion cuando no exista")
    void buscarEmpleadoPorRunDebeRetornarExcepcion() {
        when(empleadoRepository.findByRun("11111111-1")).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> empleadoService.buscarEmpleadoPorRun("11111111-1"));

        verify(empleadoMapper, never()).toResponse(any());
    }

    @Test
    void obtenerReservasPorRunEmpleadoDebeRetornarListaReservasSiExiste() {
        ReservaResponse reservaResponse = ReservaResponse.builder()
                .id(1L)
                .idHabitacion(1L)
                .idEmpleado(1L)
                .cantDias(1)
                .build();


        when(empleadoRepository.existsByRun("12345678-9")).thenReturn(true);
        when(reservaClient.obtenerReservasPorRunEmpleado("12345678-9")).thenReturn(List.of(reservaResponse));
        

        List<ReservaResponse> resultado = empleadoService.obtenerReservasPorRunEmpleado("12345678-9");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(empleadoRepository).existsByRun("12345678-9");
        verify(reservaClient).obtenerReservasPorRunEmpleado("12345678-9");
    }

    @Test
    void agregarEmpleadoDebeAgregarYRetornarSiNoExiste() {
        HotelResponse hotelResponse = new HotelResponse();
        hotelResponse.setId(1L);
        hotelResponse.setNombre("Hotel Paraíso");
        
        when(hotelClient.buscarHotelPorId(1L)).thenReturn(hotelResponse);
        when(empleadoMapper.fromRequest(empleadoRequest)).thenReturn(empleadoEntity);
        when(empleadoRepository.existsByRun("12345678-9")).thenReturn(false);
        when(empleadoRepository.save(empleadoEntity)).thenReturn(empleadoEntity);
        when(empleadoMapper.toResponse(empleadoEntity)).thenReturn(empleadoResponse);

        EmpleadoResponse resultado = empleadoService.agregarEmpleado(empleadoRequest);

        assertNotNull(resultado);
        assertEquals("12345678-9", resultado.getRun());
        verify(hotelClient).buscarHotelPorId(1L);
        verify(empleadoRepository).save(empleadoEntity);
    }

    @Test
    void agregarEmpleadoDebeLanzarExcepcionCuandoCargoEsInvalido() {
        HotelResponse hotel = new HotelResponse();
        hotel.setNombre("Hotel Test");
        when(empleadoMapper.fromRequest(empleadoRequest)).thenReturn(empleadoEntity);

        when(hotelClient.buscarHotelPorId(1L)).thenReturn(hotel);
        empleadoRequest.setCargo("Conserje");

        assertThrows(BadRequestException.class, () -> empleadoService.agregarEmpleado(empleadoRequest));
        verify(empleadoRepository, never()).save(any());
    }    

    @Test
    @DisplayName("agregarEmpleado() debe retornar excepcion cuando ya exista un empleado con ese RUN")
    void agregarEmpleadoDebeRetornarExcepcionSiExisteRun() {
        HotelResponse hotel = new HotelResponse();
        hotel.setNombre("Hotel Test");

        when(empleadoRepository.existsByRun("12345678-9")).thenReturn(true);
        assertThrows(BadRequestException.class, () -> empleadoService.agregarEmpleado(empleadoRequest));

        verify(empleadoMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("agregarEmpleado() debe retornar excepcion cuando no exista el hotel")
    void agregarEmpleadoDebeRetornarExcepcionSiNoExisteHotel() {
        empleadoRequest.setIdHotel(99L);
        when(hotelClient.buscarHotelPorId(99L)).thenThrow(new EntityNotFoundException("No se encontro hotel con ID -> 99"));

        assertThrows(EntityNotFoundException.class, () -> empleadoService.agregarEmpleado(empleadoRequest));

        verify(empleadoMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("actualizarEmpleado() debe actualizar y retornar si existe")
    void actualizarEmpleadoDebeActualizarYRetornarSiExiste() {
        EmpleadoUpdateRequest updateRequest = EmpleadoUpdateRequest.builder()
                .nombreCompleto("Jane Doe")
                .build();
        
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleadoEntity));
        when(empleadoRepository.save(empleadoEntity)).thenReturn(empleadoEntity);
        when(empleadoMapper.toResponse(empleadoEntity)).thenReturn(empleadoResponse);
        
        empleadoService.actualizarEmpleado(1L, updateRequest);

        assertEquals("Jane Doe", empleadoEntity.getNombreCompleto());
        verify(empleadoRepository).save(empleadoEntity);
    
    }

    @Test
    @DisplayName("actualizarEmpleado() debe retornar excepcion cuando no exista")
    void actualizarEmpleadoDebeRetornarExcepcion() {
        EmpleadoUpdateRequest updateRequest = EmpleadoUpdateRequest.builder()
                .nombreCompleto("Jane Doe")
                .build();
        assertThrows(EntityNotFoundException.class, () -> empleadoService.actualizarEmpleado(99L, updateRequest));

        verify(empleadoMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("actualizarEmpleado() debe retornar excepcion cuando no exista el hotel")
    void actualizarEmpleadoDebeRetornarExcepcionSiNoExisteHotel() {
        EmpleadoUpdateRequest updateRequest = EmpleadoUpdateRequest.builder()
                .idHotel(99L)
                .build();

        assertThrows(EntityNotFoundException.class, () -> empleadoService.actualizarEmpleado(1L, updateRequest));

        verify(empleadoMapper, never()).toResponse(any());
    }

    @Test
    void eliminarEmpleadoDebeEliminarSiExiste() {
        when(empleadoRepository.existsById(1L)).thenReturn(true);

        empleadoService.eliminarEmpleado(1L);

        verify(empleadoRepository).deleteById(1L);
    }
    @Test
    @DisplayName("eliminarEmpleado() debe retornar excepcion cuando no exista")
    void eliminarEmpleadoDebeRetornarExcepcion() {
        when(empleadoRepository.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> empleadoService.eliminarEmpleado(99L));

        verify(empleadoRepository, never()).deleteById(any());
    }
}
