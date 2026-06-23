package cl.duoc.dsy1103.hotel.service;

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

import cl.duoc.dsy1103.hotel.dto.HotelRequest;
import cl.duoc.dsy1103.hotel.dto.HotelResponse;
import cl.duoc.dsy1103.hotel.dto.HotelUpdateRequest;
import cl.duoc.dsy1103.hotel.exception.ConflictException;
import cl.duoc.dsy1103.hotel.mapper.HotelMapper;
import cl.duoc.dsy1103.hotel.model.Hotel;
import cl.duoc.dsy1103.hotel.repository.HotelRepository;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelService hotelService;

    private Hotel hotelMock;
    private HotelResponse responseMock;
    private HotelRequest requestMock;

    @BeforeEach
    void setUp() {
        hotelMock = Hotel.builder()
                .idHotel(1L)
                .rut("76123456-7")
                .direccion("Av. Providencia 1234, Santiago")
                .nombre("Hotel Plaza")
                .build();

        responseMock = HotelResponse.builder()
                .idHotel(1L)
                .rut("76123456-7")
                .direccion("Av. Providencia 1234, Santiago")
                .nombre("Hotel Plaza")
                .build();

        requestMock = new HotelRequest();
        requestMock.setRut("76123456-7");
        requestMock.setDireccion("Av. Providencia 1234, Santiago");
        requestMock.setNombre("Hotel Plaza");
    }
    
    @Test
    @DisplayName("buscarHoteles() debe retornar lista de hoteles")
    void buscarHoteles_debeRetornarLista() {
        when(hotelRepository.findAll()).thenReturn(List.of(hotelMock));
        when(hotelMapper.toResponse(hotelMock)).thenReturn(responseMock);

        List<HotelResponse> resultado = hotelService.buscarHoteles();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(hotelRepository).findAll();
    }

    @Test
    @DisplayName("buscarHotelPorId() debe retornar hotel cuando existe")
    void buscarHotelPorId_cuandoExiste_retornaHotel() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotelMock));
        when(hotelMapper.toResponse(hotelMock)).thenReturn(responseMock);

        HotelResponse resultado = hotelService.buscarHotelPorId(1L);

        assertNotNull(resultado);
        assertEquals("Hotel Plaza", resultado.getNombre());
        verify(hotelRepository).findById(1L);
    }

    @Test
    @DisplayName("buscarHotelPorId() debe lanzar excepcion cuando no existe")
    void buscarHotelPorId_cuandoNoExiste_lanzaExcepcion() {
        when(hotelRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> hotelService.buscarHotelPorId(99L));
        verify(hotelMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("buscarHotelPorNombre() debe retornar hotel cuando existe")
    void buscarHotelPorNombre_cuandoExiste_retornaHotel() {
        when(hotelRepository.findByNombre("Hotel Plaza")).thenReturn(Optional.of(hotelMock));
        when(hotelMapper.toResponse(hotelMock)).thenReturn(responseMock);

        HotelResponse resultado = hotelService.buscarHotelPorNombre("Hotel Plaza");

        assertNotNull(resultado);
        assertEquals("Hotel Plaza", resultado.getNombre());
    }

    @Test
    @DisplayName("buscarHotelPorNombre() debe lanzar excepcion cuando no existe")
    void buscarHotelPorNombre_cuandoNoExiste_lanzaExcepcion() {
        when(hotelRepository.findByNombre("Hotel Inexistente")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> hotelService.buscarHotelPorNombre("Hotel Inexistente"));
    }

    @Test
    @DisplayName("crearHotel() debe crear correctamente cuando no existe duplicado")
    void crearHotel_cuandoNoExisteDuplicado_creaCorrectamente() {
        when(hotelRepository.existsByNombre("Hotel Plaza")).thenReturn(false);
        when(hotelMapper.fromRequest(requestMock)).thenReturn(hotelMock);
        when(hotelRepository.save(hotelMock)).thenReturn(hotelMock);
        when(hotelMapper.toResponse(hotelMock)).thenReturn(responseMock);

        HotelResponse resultado = hotelService.crearHotel(requestMock);

        assertNotNull(resultado);
        assertEquals("Hotel Plaza", resultado.getNombre());
        verify(hotelRepository).save(hotelMock);
    }

    @Test
    @DisplayName("crearHotel() debe lanzar excepcion cuando el nombre ya existe")
    void crearHotel_cuandoNombreDuplicado_lanzaExcepcion() {
        when(hotelRepository.existsByNombre("Hotel Plaza")).thenReturn(true);

        assertThrows(ConflictException.class,
                () -> hotelService.crearHotel(requestMock));
        verify(hotelRepository, never()).save(any());
    }

    @Test
    @DisplayName("actualizarHotel() debe actualizar solo los campos enviados")
    void actualizarHotel_debeActualizarCamposEnviados() {
        HotelUpdateRequest updateRequest = new HotelUpdateRequest();
        updateRequest.setDireccion("Nueva Direccion 456");

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotelMock));
        when(hotelRepository.save(hotelMock)).thenReturn(hotelMock);
        when(hotelMapper.toResponse(hotelMock)).thenReturn(responseMock);

        hotelService.actualizarHotel(1L, updateRequest);

        assertEquals("Nueva Direccion 456", hotelMock.getDireccion());
        assertEquals("Hotel Plaza", hotelMock.getNombre()); // no cambió, no se envió
        verify(hotelRepository).save(hotelMock);
    }

    @Test
    @DisplayName("actualizarHotel() debe lanzar excepcion cuando no existe")
    void actualizarHotel_cuandoNoExiste_lanzaExcepcion() {
        when(hotelRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> hotelService.actualizarHotel(99L, new HotelUpdateRequest()));
        verify(hotelRepository, never()).save(any());
    }

    @Test
    @DisplayName("eliminarHotel() debe eliminar cuando existe")
    void eliminarHotel_cuandoExiste_eliminaCorrectamente() {
        when(hotelRepository.existsById(1L)).thenReturn(true);

        hotelService.eliminarHotel(1L);

        verify(hotelRepository).deleteById(1L);
    }

    @Test
    @DisplayName("eliminarHotel() debe lanzar excepcion cuando no existe")
    void eliminarHotel_cuandoNoExiste_lanzaExcepcion() {
        when(hotelRepository.existsById(99L)).thenReturn(false);

        assertThrows(NoSuchElementException.class,
                () -> hotelService.eliminarHotel(99L));
        verify(hotelRepository, never()).deleteById(any());
    }
}
