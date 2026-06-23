package cl.duoc.dsy1103.check_out.service;

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

import cl.duoc.dsy1103.check_out.client.EmpleadoClient;
import cl.duoc.dsy1103.check_out.client.ReservaClient;
import cl.duoc.dsy1103.check_out.dto.CheckOutRequest;
import cl.duoc.dsy1103.check_out.dto.CheckOutResponse;
import cl.duoc.dsy1103.check_out.dto.CheckOutUpdateRequest;
import cl.duoc.dsy1103.check_out.mapper.CheckOutMapper;
import cl.duoc.dsy1103.check_out.model.CheckOut;
import cl.duoc.dsy1103.check_out.repository.CheckOutRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CheckOutServiceTest {

    @Mock
    private CheckOutRepository checkOutRepository;

    @Mock
    private CheckOutMapper checkOutMapper;

    @Mock
    private ReservaClient reservaClient;

    @Mock
    private EmpleadoClient empleadoClient;

    @InjectMocks
    private CheckOutService checkOutService;

    private CheckOut checkOutEntity;
    private CheckOutResponse checkOutResponse;
    private CheckOutRequest checkOutRequest;

    @BeforeEach
    void setUp() {
        checkOutEntity = CheckOut.builder()
                .id(1L)
                .idReserva(1L)
                .idEmpleado(1L)
                .observaciones("Sin observaciones")
                .build();

        checkOutResponse = CheckOutResponse.builder()
                .id(1L)
                .idReserva(1L)
                .idEmpleado(1L)
                .fechaSalida(checkOutEntity.getFechaSalida())
                .observaciones("Sin observaciones")
                .build();
        
        checkOutRequest = CheckOutRequest.builder()
                .idReserva(1L)
                .idEmpleado(1L)
                .observaciones("Sin observaciones")
                .build();
    }

    @Test
    @DisplayName("obtenerCheckOut() debe retornar una lista de CheckOutResponse")
    void obtenerCheckOutDebeRetornarListaResponse() {
        when(checkOutRepository.findAll()).thenReturn(List.of(checkOutEntity));
        when(checkOutMapper.toResponse(checkOutEntity)).thenReturn(checkOutResponse);

        List<CheckOutResponse> resultado = checkOutService.obtenerCheckOut();
 
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(checkOutRepository).findAll();
    }

    @Test
    @DisplayName("buscarCheckOutPorId() debe retornar CheckOutResponse si existe")
    void buscarCheckOutPorIdDebeRetornarResponseSiExiste() {
        when(checkOutRepository.findById(1L)).thenReturn(Optional.of(checkOutEntity));
        when(checkOutMapper.toResponse(checkOutEntity)).thenReturn(checkOutResponse);

        CheckOutResponse resultado = checkOutService.buscarCheckOutPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdReserva());
        verify(checkOutRepository).findById(1L);
    }

    @Test
    @DisplayName("buscarCheckOutPorId() debe retornar excepcion cuando no exista")
    void buscarCheckOutPorIdDebeRetornarExcepcion() {
        when(checkOutRepository.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> checkOutService.buscarCheckOutPorId(99L));
        verify(checkOutMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("buscarCheckOutPorIdReserva() debe retornar CheckOutResponse si existe")
    void buscarCheckOutPorIdReservaDebeRetornarResponseSiExiste() {
        when(checkOutRepository.findByIdReserva(1L)).thenReturn(Optional.of(checkOutEntity));
        when(checkOutMapper.toResponse(checkOutEntity)).thenReturn(checkOutResponse);

        CheckOutResponse resultado = checkOutService.buscarCheckOutPorIdReserva(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(checkOutRepository).findByIdReserva(1L);
    }

    @Test
    @DisplayName("buscarCheckOutPorIdReserva() debe retornar excepcion cuando no exista")
    void buscarCheckOutPorIdReservaDebeRetornarExcepcion() {
        when(checkOutRepository.findByIdReserva(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> checkOutService.buscarCheckOutPorIdReserva(99L));
        verify(checkOutMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("agregarCheckOut() debe guardar y retornar si no existe duplicado")
    void agregarCheckOutDebeGuardarYRetornarSiNoExisteDuplicado() {
        when(checkOutMapper.fromRequest(checkOutRequest)).thenReturn(checkOutEntity);
        when(checkOutRepository.save(checkOutEntity)).thenReturn(checkOutEntity);
        when(checkOutMapper.toResponse(checkOutEntity)).thenReturn(checkOutResponse);

        CheckOutResponse resultado = checkOutService.agregarCheckOut(checkOutRequest);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdReserva());
        verify(empleadoClient).buscarEmpleadoPorId(1L);
        verify(reservaClient).buscarReservaPorId(1L);
        verify(checkOutRepository).save(checkOutEntity);
    }

    @Test
    @DisplayName("agregarCheckOut() debe retornar excepcion cuando no exista la reserva")
    void agregarCheckOutDebeRetornarExcepcionSiNoExisteReserva() {
        when(reservaClient.buscarReservaPorId(99L)).thenThrow(new EntityNotFoundException("No se encontro reserva con ID -> 99"));

        assertThrows(EntityNotFoundException.class, () -> checkOutService.buscarCheckOutPorIdReserva(99L));
        verify(checkOutMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("actualizarCheckOut() debe actualizar y retornar check out actualizado")
    void actualizarCheckOutDebeActualizarRetornarResponse() {
        CheckOutUpdateRequest updateRequest = CheckOutUpdateRequest.builder()
                .idReserva(1L)
                .idEmpleado(1L)
                .observaciones("Actualizado")
                .build();
        when(checkOutRepository.findById(1L)).thenReturn(Optional.of(checkOutEntity));
        when(checkOutRepository.save(checkOutEntity)).thenReturn(checkOutEntity);
        when(checkOutMapper.toResponse(checkOutEntity)).thenReturn(checkOutResponse);

        checkOutService.actualizarCheckOut(1L, updateRequest);

        assertEquals("Actualizado", updateRequest.getObservaciones());
        verify(checkOutRepository).save(checkOutEntity);
    }

    @Test
    @DisplayName("actualizarCheckOut() debe retornar excepcion cuando no exista")
    void actualizarCheckOutDebeRetornarExcepcion() {
        when(checkOutRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> checkOutService.actualizarCheckOut(99L, new CheckOutUpdateRequest()));
        verify(checkOutRepository, never()).save(any());
    }

    @Test
    @DisplayName("eliminarCheckOut() debe eliminar si existe")
    void eliminarCheckOutDebeEliminar() {
        when(checkOutRepository.existsById(1L)).thenReturn(true);

        checkOutService.eliminarCheckOut(1L);

        verify(checkOutRepository).deleteById(1L);
    }

    @Test
    @DisplayName("eliminarCheckOut() debe retornar excepcion cuando no exista")
    void eliminarCheckOutDebeRetornarExcepcion() {
        when(checkOutRepository.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> checkOutService.eliminarCheckOut(99L));

        verify(checkOutRepository, never()).deleteAllById(any());
    }
}
