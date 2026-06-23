package cl.duoc.dsy1103.disponibilidad.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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

import cl.duoc.dsy1103.disponibilidad.dto.DisponibilidadRequest;
import cl.duoc.dsy1103.disponibilidad.dto.DisponibilidadResponse;
import cl.duoc.dsy1103.disponibilidad.dto.DisponibilidadUpdateRequest;
import cl.duoc.dsy1103.disponibilidad.enums.EstadoDisponibilidad;
import cl.duoc.dsy1103.disponibilidad.mapper.DisponibilidadMapper;
import cl.duoc.dsy1103.disponibilidad.model.Disponibilidad;
import cl.duoc.dsy1103.disponibilidad.repository.DisponibilidadRepository;

@ExtendWith(MockitoExtension.class)
class DisponibilidadServiceTest {

    @Mock
    private DisponibilidadRepository disponibilidadRepository;

    @Mock
    private DisponibilidadMapper disponibilidadMapper;

    @InjectMocks
    private DisponibilidadService disponibilidadService;

    private Disponibilidad disponibilidadMock;
    private DisponibilidadResponse responseMock;
    private DisponibilidadRequest requestMock;
    private LocalDateTime fechaDesdeMock;

    @BeforeEach
    void setUp() {
        fechaDesdeMock = LocalDateTime.of(2026, 5, 16, 0, 0);

        disponibilidadMock = Disponibilidad.builder()
                .idDisponibilidad(1L)
                .estado(EstadoDisponibilidad.DISPONIBLE)
                .fechaDesde(fechaDesdeMock)
                .fechaHasta(null)
                .build();

        responseMock = DisponibilidadResponse.builder()
                .idDisponibilidad(1L)
                .estado(EstadoDisponibilidad.DISPONIBLE)
                .fechaDesde(fechaDesdeMock)
                .fechaHasta(null)
                .build();

        requestMock = new DisponibilidadRequest();
        requestMock.setEstado(EstadoDisponibilidad.DISPONIBLE);
        requestMock.setFechaDesde(fechaDesdeMock);
    }

    @Test
    @DisplayName("buscarDisponibilidades() debe retornar lista de disponibilidades")
    void buscarDisponibilidades_debeRetornarLista() {
        when(disponibilidadRepository.findAll()).thenReturn(List.of(disponibilidadMock));
        when(disponibilidadMapper.toResponse(disponibilidadMock)).thenReturn(responseMock);

        List<DisponibilidadResponse> resultado = disponibilidadService.buscarDisponibilidades();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(disponibilidadRepository).findAll();
    }

    @Test
    @DisplayName("buscarDisponibilidadPorId() debe retornar disponibilidad cuando existe")
    void buscarDisponibilidadPorId_cuandoExiste_retornaDisponibilidad() {

        when(disponibilidadRepository.findById(1L)).thenReturn(Optional.of(disponibilidadMock));
        when(disponibilidadMapper.toResponse(disponibilidadMock)).thenReturn(responseMock);

        DisponibilidadResponse resultado = disponibilidadService.buscarDisponibilidadPorId(1L);

        assertNotNull(resultado);
        assertEquals(EstadoDisponibilidad.DISPONIBLE, resultado.getEstado());
        verify(disponibilidadRepository).findById(1L);
    }

    @Test
    @DisplayName("buscarDisponibilidadPorId() debe lanzar excepcion cuando no existe")
    void buscarDisponibilidadPorId_cuandoNoExiste_lanzaExcepcion() {
        when(disponibilidadRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> disponibilidadService.buscarDisponibilidadPorId(99L));
        verify(disponibilidadMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("crearDisponibilidad() debe crear correctamente")
    void crearDisponibilidad_debeCrearCorrectamente() {
        when(disponibilidadMapper.fromRequest(requestMock)).thenReturn(disponibilidadMock);
        when(disponibilidadRepository.save(disponibilidadMock)).thenReturn(disponibilidadMock);
        when(disponibilidadMapper.toResponse(disponibilidadMock)).thenReturn(responseMock);

        DisponibilidadResponse resultado = disponibilidadService.crearDisponibilidad(requestMock);

        assertNotNull(resultado);
        assertEquals(EstadoDisponibilidad.DISPONIBLE, resultado.getEstado());
        verify(disponibilidadRepository).save(disponibilidadMock);
    }

    @Test
    @DisplayName("actualizarDisponibilidad() debe actualizar solo los campos enviados")
    void actualizarDisponibilidad_debeActualizarCamposEnviados() {
        DisponibilidadUpdateRequest updateRequest = new DisponibilidadUpdateRequest();
        updateRequest.setEstado(EstadoDisponibilidad.OCUPADA);

        when(disponibilidadRepository.findById(1L)).thenReturn(Optional.of(disponibilidadMock));
        when(disponibilidadRepository.save(disponibilidadMock)).thenReturn(disponibilidadMock);
        when(disponibilidadMapper.toResponse(disponibilidadMock)).thenReturn(responseMock);

        disponibilidadService.actualizarDisponibilidad(1L, updateRequest);

        assertEquals(EstadoDisponibilidad.OCUPADA, disponibilidadMock.getEstado());
        assertEquals(fechaDesdeMock, disponibilidadMock.getFechaDesde()); // no cambió, no se envió
        verify(disponibilidadRepository).save(disponibilidadMock);
    }

    @Test
    @DisplayName("actualizarDisponibilidad() debe lanzar excepcion cuando no existe")
    void actualizarDisponibilidad_cuandoNoExiste_lanzaExcepcion() {
        when(disponibilidadRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> disponibilidadService.actualizarDisponibilidad(99L, new DisponibilidadUpdateRequest()));
        verify(disponibilidadRepository, never()).save(any());
    }

    @Test
    @DisplayName("eliminarDisponibilidad() debe eliminar cuando existe")
    void eliminarDisponibilidad_cuandoExiste_eliminaCorrectamente() {
        when(disponibilidadRepository.findById(1L)).thenReturn(Optional.of(disponibilidadMock));

        disponibilidadService.eliminarDisponibilidad(1L);

        verify(disponibilidadRepository).delete(disponibilidadMock);
    }

    @Test
    @DisplayName("eliminarDisponibilidad() debe lanzar excepcion cuando no existe")
    void eliminarDisponibilidad_cuandoNoExiste_lanzaExcepcion() {
        when(disponibilidadRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> disponibilidadService.eliminarDisponibilidad(99L));
        verify(disponibilidadRepository, never()).delete(any());
    }
}
