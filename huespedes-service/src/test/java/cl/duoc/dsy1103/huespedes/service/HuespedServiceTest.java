package cl.duoc.dsy1103.huespedes.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
import org.springframework.dao.DataIntegrityViolationException;

import cl.duoc.dsy1103.huespedes.dto.HuespedRequest;
import cl.duoc.dsy1103.huespedes.dto.HuespedResponse;
import cl.duoc.dsy1103.huespedes.dto.HuespedUpdateRequest;
import cl.duoc.dsy1103.huespedes.mapper.HuespedMapper;
import cl.duoc.dsy1103.huespedes.model.Huesped;
import cl.duoc.dsy1103.huespedes.repository.HuespedRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class HuespedServiceTest {
    @Mock
    private HuespedRepository huespedRepository;
    @Mock
    private HuespedMapper huespedMapper;
    @InjectMocks
    private HuespedService huespedService;

    private Huesped huespedEntity;
    private HuespedRequest huespedRequest;
    private HuespedResponse huespedResponse;
    
    @BeforeEach
    void setUp(){
        huespedEntity = Huesped.builder()
                .id(1L)
                .run("12345678-9")
                .nombreCompleto("John Doe")
                .email("john@gmail.com")
                .telefono("11223344")
                .nacionalidad("Chileno")
                .build();

        huespedResponse = HuespedResponse.builder()
                .id(1L)
                .run("12345678-9")
                .nombreCompleto("John Doe")
                .email("john@gmail.com")
                .telefono("11223344")
                .nacionalidad("Chileno")
                .build();

        huespedRequest = HuespedRequest.builder()
                .run("12345678-9")
                .nombreCompleto("John Doe")
                .email("john@gmail.com")
                .telefono("11223344")
                .nacionalidad("Chileno")
                .build();
    }

    @Test
    @DisplayName("obtenerHuespedes() debe retornar lista de HuespedResponse")
    void obtenerHuespedesDebeRetornarListaResponse() {
        when(huespedRepository.findAll()).thenReturn(List.of(huespedEntity));
        when(huespedMapper.toResponse(huespedEntity)).thenReturn(huespedResponse);

        List<HuespedResponse> resultado = huespedService.obtenerHuespedes();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(huespedRepository).findAll();
    }

    @Test
    @DisplayName("buscarHuespedPorId() debe retornar HuespedResponse si existe")
    void buscarHuespedPorIdDebeRetornarSiExiste() {
        when(huespedRepository.findById(1L)).thenReturn(Optional.of(huespedEntity));
        when(huespedMapper.toResponse(huespedEntity)).thenReturn(huespedResponse);

        HuespedResponse resultado = huespedService.buscarHuespedPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(huespedRepository).findById(1L);
    }

    @Test
    @DisplayName("buscarHuespedPorId() debe lanzar una excepcion EntityNotFound si no existe")
    void buscarHuespedPorIdDebeLanzarExcepcionSiNoExiste() {
        when(huespedRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> huespedService.buscarHuespedPorId(99L));
        verify(huespedMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("buscarHuespedPorRun() debe retornar HuespedResponse si existe")
    void buscarHuespedPorRunDebeRetornarSiExiste() {
        when(huespedRepository.findByRun("12345678-9")).thenReturn(Optional.of(huespedEntity));
        when(huespedMapper.toResponse(huespedEntity)).thenReturn(huespedResponse);

        HuespedResponse resultado = huespedService.buscarHuespedPorRun("12345678-9");

        assertNotNull(resultado);
        assertEquals("12345678-9", resultado.getRun());
        verify(huespedRepository).findByRun("12345678-9");
    }

    @Test
    @DisplayName("buscarHuespedPorRun() debe lanzar una excepcion EntityNotFound si no existe")
    void buscarHuespedPorRunDebeLanzarExcepcionSiNoExiste() {
        when(huespedRepository.findByRun("12345678-9")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> huespedService.buscarHuespedPorRun("12345678-9"));
        verify(huespedMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("agregarHuesped() debe agregar y retornar HuespedResponse")
    void agregarHuespedDebeAgregarYRetornarResponse() {
        when(huespedMapper.fromRequest(huespedRequest)).thenReturn(huespedEntity);
        when(huespedRepository.save(huespedEntity)).thenReturn(huespedEntity);
        when(huespedMapper.toResponse(huespedEntity)).thenReturn(huespedResponse);

        HuespedResponse resultado = huespedService.agregarHuesped(huespedRequest);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(huespedRepository).save(huespedEntity);
    }

    @Test
    @DisplayName("agregarHuesped() debe lanzar excepcion cuando el RUN ya existe")
    void agregarHuespedDebeLanzarExcepcionSiExisteRun() {
        when(huespedRepository.findByRun("12345678-9")).thenThrow(new DataIntegrityViolationException("Ya existe un huesped con el RUN -> 12345678-9"));

        assertThrows(DataIntegrityViolationException.class, () -> huespedService.agregarHuesped(huespedRequest));
        verify(huespedRepository, never()).save(any());
    }

    @Test
    @DisplayName("actualizarHuesped() debe actualizar los campos enviados")
    void actualizarHuespedDebeActualizarCamposEnviados() {
        HuespedUpdateRequest updateRequest = HuespedUpdateRequest.builder()
                .nombreCompleto("Jane Doe")
                .build();

        when(huespedRepository.findById(1L)).thenReturn(Optional.of(huespedEntity));
        when(huespedRepository.save(huespedEntity)).thenReturn(huespedEntity);
        when(huespedMapper.toResponse(huespedEntity)).thenReturn(huespedResponse);

        huespedService.actualizarHuesped(1L, updateRequest);

        assertEquals("Jane Doe", huespedEntity.getNombreCompleto());

        verify(huespedRepository).save(huespedEntity);
    }

    @Test
    @DisplayName("actualizarHuesped() debe lanzar excepcion si no existe")    
    void actualizarHuespedDebeLanzarExcepcionSiNoExiste() {
        HuespedUpdateRequest updateRequest = HuespedUpdateRequest.builder()
                .nombreCompleto("Jane Doe")
                .build();

        when(huespedRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> huespedService.actualizarHuesped(99L, updateRequest));

        verify(huespedRepository, never()).save(any());
        verify(huespedMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("eliminarHuesped() debe eliminar si existe")    
    void eliminarHuespedDebeEliminarSiExiste() {
        when(huespedRepository.existsById(1L)).thenReturn(true);

        huespedService.eliminarHuesped(1L);

        verify(huespedRepository).deleteById(1L);
    }

    @Test
    @DisplayName("eliminarHuesped() debe lanzar excepcion si no existe")
    void eliminarHuespedDebeLanzarExcepcionSiNoExiste() {
        when(huespedRepository.existsById(99L)).thenReturn(false);

        assertThrows(NoSuchElementException.class,
                () -> huespedService.eliminarHuesped(99L));

        verify(huespedRepository, never()).deleteById(anyLong());
    }
}
