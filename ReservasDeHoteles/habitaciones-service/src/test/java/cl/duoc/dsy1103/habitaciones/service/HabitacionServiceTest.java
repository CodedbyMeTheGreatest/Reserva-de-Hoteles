package cl.duoc.dsy1103.habitaciones.service;

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

import cl.duoc.dsy1103.habitaciones.client.DisponibilidadClient;
import cl.duoc.dsy1103.habitaciones.client.HotelClient;
import cl.duoc.dsy1103.habitaciones.dto.HabitacionRequest;
import cl.duoc.dsy1103.habitaciones.dto.HabitacionResponse;
import cl.duoc.dsy1103.habitaciones.dto.HabitacionUpdateRequest;
import cl.duoc.dsy1103.habitaciones.exception.ConflictException;
import cl.duoc.dsy1103.habitaciones.mapper.HabitacionMapper;
import cl.duoc.dsy1103.habitaciones.model.Habitacion;
import cl.duoc.dsy1103.habitaciones.repository.HabitacionRepository;

@ExtendWith(MockitoExtension.class)
class HabitacionServiceTest {

    @Mock
    private HabitacionRepository habitacionRepository;

    @Mock
    private HabitacionMapper habitacionMapper;

    @Mock
    private HotelClient hotelClient;

    @Mock
    private DisponibilidadClient disponibilidadClient;

    @InjectMocks
    private HabitacionService habitacionService;

    private Habitacion habitacionMock;
    private HabitacionResponse responseMock;
    private HabitacionRequest requestMock;

    @BeforeEach
    void setUp() {
        habitacionMock = Habitacion.builder()
                .idHabitacion(1L)
                .numero("101")
                .descripcion("Habitación doble con vista al mar")
                .precioPorNoche(50000)
                .idHotel(1L)
                .idDisponibilidad(1L)
                .build();

        responseMock = HabitacionResponse.builder()
                .idHabitacion(1L)
                .numero("101")
                .descripcion("Habitación doble con vista al mar")
                .precioPorNoche(50000)
                .idHotel(1L)
                .idDisponibilidad(1L)
                .build();

        requestMock = new HabitacionRequest();
        requestMock.setNumero("101");
        requestMock.setDescripcion("Habitación doble con vista al mar");
        requestMock.setPrecioPorNoche(50000);
        requestMock.setIdHotel(1L);
        requestMock.setIdDisponibilidad(1L);
    }

    @Test
    @DisplayName("buscarHabitaciones() debe retornar lista de habitaciones")
    void buscarHabitaciones_debeRetornarLista() {
        when(habitacionRepository.findAll()).thenReturn(List.of(habitacionMock));
        when(habitacionMapper.toResponse(habitacionMock)).thenReturn(responseMock);

        List<HabitacionResponse> resultado = habitacionService.buscarHabitaciones();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(habitacionRepository).findAll();
    }

    @Test
    @DisplayName("buscarHabitacionPorId() debe retornar habitacion cuando existe")
    void buscarHabitacionPorId_cuandoExiste_retornaHabitacion() {
        when(habitacionRepository.findById(1L)).thenReturn(Optional.of(habitacionMock));
        when(habitacionMapper.toResponse(habitacionMock)).thenReturn(responseMock);

        HabitacionResponse resultado = habitacionService.buscarHabitacionPorId(1L);

        assertNotNull(resultado);
        assertEquals("101", resultado.getNumero());
        verify(habitacionRepository).findById(1L);
    }

    @Test
    @DisplayName("buscarHabitacionPorId() debe lanzar excepcion cuando no existe")
    void buscarHabitacionPorId_cuandoNoExiste_lanzaExcepcion() {
        when(habitacionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> habitacionService.buscarHabitacionPorId(99L));
        verify(habitacionMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("buscarHabitacionPorNumero() debe retornar habitacion cuando existe")
    void buscarHabitacionPorNumero_cuandoExiste_retornaHabitacion() {
        when(habitacionRepository.findByNumero("101")).thenReturn(Optional.of(habitacionMock));
        when(habitacionMapper.toResponse(habitacionMock)).thenReturn(responseMock);

        HabitacionResponse resultado = habitacionService.buscarHabitacionPorNumero("101");

        assertNotNull(resultado);
        assertEquals("101", resultado.getNumero());
    }

    @Test
    @DisplayName("buscarHabitacionPorNumero() debe lanzar excepcion cuando no existe")
    void buscarHabitacionPorNumero_cuandoNoExiste_lanzaExcepcion() {
        when(habitacionRepository.findByNumero("999")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> habitacionService.buscarHabitacionPorNumero("999"));
    }

    @Test
    @DisplayName("crearHabitacion() debe crear correctamente cuando no existe duplicado")
    void crearHabitacion_cuandoNoExisteDuplicado_creaCorrectamente() {
        when(habitacionRepository.existsByNumero("101")).thenReturn(false);
        when(habitacionMapper.fromRequest(requestMock)).thenReturn(habitacionMock);
        when(habitacionRepository.save(habitacionMock)).thenReturn(habitacionMock);
        when(habitacionMapper.toResponse(habitacionMock)).thenReturn(responseMock);

        HabitacionResponse resultado = habitacionService.crearHabitacion(requestMock);

        assertNotNull(resultado);
        assertEquals("101", resultado.getNumero());
        verify(hotelClient).findHotelById(1L);
        verify(disponibilidadClient).findDisponibilidadById(1L);
        verify(habitacionRepository).save(habitacionMock);
    }

    @Test
    @DisplayName("crearHabitacion() debe lanzar excepcion cuando el numero ya existe")
    void crearHabitacion_cuandoNumeroDuplicado_lanzaExcepcion() {
        when(habitacionRepository.existsByNumero("101")).thenReturn(true);

        assertThrows(ConflictException.class,
                () -> habitacionService.crearHabitacion(requestMock));
        verify(habitacionRepository, never()).save(any());
        verify(hotelClient, never()).findHotelById(any());
    }

    @Test
    @DisplayName("crearHabitacion() debe lanzar excepcion cuando el hotel no existe")
    void crearHabitacion_cuandoHotelNoExiste_lanzaExcepcion() {
        when(habitacionRepository.existsByNumero("101")).thenReturn(false);
        when(hotelClient.findHotelById(1L))
                .thenThrow(new NoSuchElementException("No se encontró hotel con ID 1"));

        assertThrows(NoSuchElementException.class,
                () -> habitacionService.crearHabitacion(requestMock));
        verify(habitacionRepository, never()).save(any());
    }

    @Test
    @DisplayName("actualizarHabitacion() debe actualizar solo los campos enviados")
    void actualizarHabitacion_debeActualizarCamposEnviados() {
        HabitacionUpdateRequest updateRequest = HabitacionUpdateRequest.builder()
                .precioPorNoche(65000)
                .build();

        when(habitacionRepository.findById(1L)).thenReturn(Optional.of(habitacionMock));
        when(habitacionRepository.save(habitacionMock)).thenReturn(habitacionMock);
        when(habitacionMapper.toResponse(habitacionMock)).thenReturn(responseMock);

        habitacionService.actualizarHabitacion(1L, updateRequest);

        assertEquals(65000, habitacionMock.getPrecioPorNoche());
        verify(hotelClient, never()).findHotelById(any()); // no se mandó idHotel, no debe llamarse
        verify(habitacionRepository).save(habitacionMock);
    }

    @Test
    @DisplayName("actualizarHabitacion() debe lanzar excepcion cuando no existe")
    void actualizarHabitacion_cuandoNoExiste_lanzaExcepcion() {
        when(habitacionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> habitacionService.actualizarHabitacion(99L, new HabitacionUpdateRequest()));
        verify(habitacionRepository, never()).save(any());
    }


    @Test
    @DisplayName("eliminarHabitacion() debe eliminar cuando existe")
    void eliminarHabitacion_cuandoExiste_eliminaCorrectamente() {
        when(habitacionRepository.existsById(1L)).thenReturn(true);

        habitacionService.eliminarHabitacion(1L);

        verify(habitacionRepository).deleteById(1L);
    }

    @Test
    @DisplayName("eliminarHabitacion() debe lanzar excepcion cuando no existe")
    void eliminarHabitacion_cuandoNoExiste_lanzaExcepcion() {
        when(habitacionRepository.existsById(99L)).thenReturn(false);

        assertThrows(NoSuchElementException.class,
                () -> habitacionService.eliminarHabitacion(99L));
        verify(habitacionRepository, never()).deleteById(any());
    }
}
