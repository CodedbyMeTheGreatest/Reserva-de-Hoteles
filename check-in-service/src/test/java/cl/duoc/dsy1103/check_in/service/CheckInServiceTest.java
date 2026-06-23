package cl.duoc.dsy1103.check_in.service;

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

import cl.duoc.dsy1103.check_in.client.EmpleadoClient;
import cl.duoc.dsy1103.check_in.client.ReservaClient;
import cl.duoc.dsy1103.check_in.dto.CheckInRequest;
import cl.duoc.dsy1103.check_in.dto.CheckInResponse;
import cl.duoc.dsy1103.check_in.dto.CheckInUpdateRequest;
import cl.duoc.dsy1103.check_in.exception.BadRequestException;
import cl.duoc.dsy1103.check_in.mapper.CheckInMapper;
import cl.duoc.dsy1103.check_in.model.CheckIn;
import cl.duoc.dsy1103.check_in.repository.CheckInRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CheckInServiceTest {
    @Mock
    private CheckInRepository checkInRepository;
    @Mock
    private CheckInMapper checkInMapper;
    @Mock
    private ReservaClient reservaClient;
    @Mock 
    private EmpleadoClient empleadoClient;

    @InjectMocks
    private CheckInService checkInService;

    private CheckIn checkInEntity;
    private CheckInResponse checkInResponse;
    private CheckInRequest checkInRequest;

    @BeforeEach
    public void setUp(){
        
        checkInEntity = CheckIn.builder()
                .id(1L)
                .idReserva(1L)
                .idEmpleado(1L)
                .observaciones("Sin observaciones")
                .build();

        checkInResponse = CheckInResponse.builder()
                .id(1L)
                .idReserva(1L)
                .idEmpleado(1L)
                .fechaIngreso(checkInEntity.getFechaIngreso())
                .observaciones("Sin observaciones")
                .build();
        
        checkInRequest = CheckInRequest.builder()
                .idReserva(1L)
                .idEmpleado(1L)
                .observaciones("Sin observaciones")
                .build();
    }

    @Test
    @DisplayName("obtenerCheckIns() debe retornar una lista de CheckInResponse")
    void obtenerCheckInsDebeRetornarListaDeResponse() {
        when(checkInRepository.findAll()).thenReturn(List.of(checkInEntity));
        when(checkInMapper.toResponse(checkInEntity)).thenReturn(checkInResponse);

        List<CheckInResponse> resultado = checkInService.obtenerCheckIns();
 
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(checkInRepository).findAll();
    }

    @Test
    @DisplayName("buscarCheckInPorId() debe retornar CheckInResponse si existe")
    void buscarCheckInPorIdDebeRetornarResponseSiExiste() {
        when(checkInRepository.findById(1L)).thenReturn(Optional.of(checkInEntity));
        when(checkInMapper.toResponse(checkInEntity)).thenReturn(checkInResponse);

        CheckInResponse resultado = checkInService.buscarCheckInPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdReserva());
        verify(checkInRepository).findById(1L);
    }

    @Test
    @DisplayName("buscarCheckInPorId() debe lanzar una excepcion EntityNotFound si no existe")
    void buscarCheckInPorIdDebeLanzarExcepcionSiNoExiste() {
        when(checkInRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, 
                () -> checkInService.buscarCheckInPorId(99L));
        verify(checkInMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("agregarCheckIn() debe guardar y retornar un CheckInResponse si no existe duplicado")
    void agregarCheckInDebeGuardarYRetornarSiNoExisteDuplicado() {

        when(checkInMapper.fromRequest(checkInRequest)).thenReturn(checkInEntity);
        when(checkInRepository.save(checkInEntity)).thenReturn(checkInEntity);
        when(checkInMapper.toResponse(checkInEntity)).thenReturn(checkInResponse);

        CheckInResponse resultado = checkInService.agregarCheckIn(checkInRequest);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdReserva());
        verify(reservaClient).buscarReservaPorId(1L);
        verify(empleadoClient).buscarEmpleadoPorId(1L);
        verify(checkInRepository).save(checkInEntity);
    }

    @Test
    @DisplayName("agregarCheckIn() debe lanzar una excepcion BadRequest si existe otro Check In con la misma Id Reserva")
    void agregarCheckInDebeLanzarExcepcionSiExisteCheckInParaReserva() {
        when(checkInRepository.existsByIdReserva(1L)).thenThrow(new BadRequestException("Ya existe check in para reserva con ID -> 1"));

        assertThrows(BadRequestException.class, () -> checkInService.agregarCheckIn(checkInRequest));
        verify(checkInRepository, never()).save(any());
    }

    @Test
    @DisplayName("agregarCheckIn() debe lanzar una excepcion EntityNotFound si no existe la reserva")
    void agregarCheckInDebeLanzarExcepcionSiNoExisteReserva() {
        when(checkInRepository.existsByIdReserva(1L)).thenThrow(new EntityNotFoundException("No se encontro reserva reserva con ID -> 99"));

        assertThrows(EntityNotFoundException.class, () -> checkInService.agregarCheckIn(checkInRequest));
        verify(checkInRepository, never()).save(any());
    }

    @Test
    @DisplayName("actualizarCheckIn() debe actualizar y retornar CheckInResponse si existe")
    void actualizarCheckInDebeActualizarYRetornarResponseSiExiste() {
        
        CheckInUpdateRequest updateRequest = CheckInUpdateRequest.builder()
                .idReserva(1L)
                .idEmpleado(1L)
                .observaciones("Actualizado")
                .build();
        
        when(checkInRepository.findById(1L)).thenReturn(Optional.of(checkInEntity));
        when(checkInRepository.save(checkInEntity)).thenReturn(checkInEntity);
        when(checkInMapper.toResponse(checkInEntity)).thenReturn(checkInResponse);

        checkInService.actualizarCheckIn(1L, updateRequest);

        assertEquals("Actualizado", checkInEntity.getObservaciones());
        verify(checkInRepository).save(checkInEntity);
    }

    @Test
    @DisplayName("actualizarCheckIn() debe lanzar una excepcion EntityNotFound si no existe")
    void actualizarCheckInDebeLanzarExcepcionSiNoExiste() {
        when(checkInRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, 
                () -> checkInService.actualizarCheckIn(99L, new CheckInUpdateRequest()));
        verify(checkInRepository, never()).save(any());
    }

    @Test
    @DisplayName("eliminarCheckIn() debe eliminar si existe")
    void eliminarCheckInDebeEliminarSiExiste() {
        when(checkInRepository.existsById(1L)).thenReturn(true);

        checkInService.eliminarCheckIn(1L);

        verify(checkInRepository).deleteById(1L);
    }

    @Test
    @DisplayName("eliminarCheckIn() debe lanzar una excepcion EntityNotFound si no existe")
    void eliminarCheckInDebeLanzarExcepcionSiNoExiste() {
        when(checkInRepository.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, 
                () -> checkInService.eliminarCheckIn(99L));

        verify(checkInRepository, never()).deleteById(any());
    }


}