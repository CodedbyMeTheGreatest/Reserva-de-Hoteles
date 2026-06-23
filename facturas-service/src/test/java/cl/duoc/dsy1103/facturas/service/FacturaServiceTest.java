package cl.duoc.dsy1103.facturas.service;

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

import cl.duoc.dsy1103.facturas.client.CheckInClient;
import cl.duoc.dsy1103.facturas.client.CheckOutClient;
import cl.duoc.dsy1103.facturas.client.HuespedClient;
import cl.duoc.dsy1103.facturas.client.PagoClient;
import cl.duoc.dsy1103.facturas.client.ReservaClient;
import cl.duoc.dsy1103.facturas.dto.CheckInResponse;
import cl.duoc.dsy1103.facturas.dto.CheckOutResponse;
import cl.duoc.dsy1103.facturas.dto.FacturaRequest;
import cl.duoc.dsy1103.facturas.dto.FacturaResponse;
import cl.duoc.dsy1103.facturas.dto.FacturaUpdateRequest;
import cl.duoc.dsy1103.facturas.dto.HuespedResponse;
import cl.duoc.dsy1103.facturas.dto.PagoResponse;
import cl.duoc.dsy1103.facturas.dto.ReservaResponse;
import cl.duoc.dsy1103.facturas.enums.EstadoPago;
import cl.duoc.dsy1103.facturas.enums.MetodoPago;
import cl.duoc.dsy1103.facturas.exception.BadRequestException;
import cl.duoc.dsy1103.facturas.mapper.FacturaMapper;
import cl.duoc.dsy1103.facturas.model.Factura;
import cl.duoc.dsy1103.facturas.repository.FacturaRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class FacturaServiceTest {
   @Mock
   private FacturaRepository facturaRepository;
   @Mock
   private FacturaMapper facturaMapper;
   @Mock
   private ReservaClient reservaClient;
   @Mock
   private PagoClient pagoClient;
   @Mock
   private HuespedClient huespedClient;
   @Mock
   private CheckInClient checkInClient;
   @Mock
   private CheckOutClient checkOutClient;

   @InjectMocks
   private FacturaService facturaService;

   private Factura facturaEntity;
   private FacturaRequest facturaRequest;
   private FacturaResponse facturaResponse;
   private FacturaUpdateRequest facturaUpdateRequest;

    @BeforeEach
    void setUp() {
        facturaEntity = Factura.builder()
            .id(1L)
            .folio("F1")
            .idReserva(1L)
            .idPago(1L)
            .runHuesped("123-4")
            .nombreHuesped("John Doe")
            .idCheckIn(1L)
            .idCheckOut(1L)
            .fechaIngreso(LocalDateTime.now())
            .fechaSalida(LocalDateTime.now())
            .descripcionHabitacion("Suite")
            .cantDias(1)
            .subtotal(1)
            .impuestos(1)
            .total(2)
            .metodoPago(MetodoPago.TARJETA)
            .estadoPago(EstadoPago.PENDIENTE)
            .build();

        facturaResponse = FacturaResponse.builder()
            .id(1L)
            .folio("F1")
            .idReserva(1L)
            .idPago(1L)
            .runHuesped("123-4")
            .nombreHuesped("John Doe")
            .idCheckIn(1L)
            .idCheckOut(1L)
            .fechaIngreso(LocalDateTime.now())
            .fechaSalida(LocalDateTime.now())
            .descripcionHabitacion("Suite")
            .cantDias(1)
            .subtotal(1)
            .impuestos(1)
            .total(2)
            .metodoPago(MetodoPago.TARJETA)
            .estadoPago(EstadoPago.PENDIENTE)
            .build();

        facturaRequest = FacturaRequest.builder()
            .folio("F1")
            .idReserva(1L)
            .idPago(1L)
            .runHuesped("123-4")
            .idCheckIn(1L)
            .idCheckOut(1L)
            .descripcionHabitacion("Suite")
            .build();

        facturaUpdateRequest = FacturaUpdateRequest.builder()
            .folio("F2")
            .idReserva(1L)
            .runHuesped("456-7")
            .idCheckIn(1L)
            .idCheckOut(1L)
            .idPago(1L)
            .descripcionHabitacion("Suite Deluxe")
            .build();
    }

    @Test
    @DisplayName("obtenerFacturas() debe retornar lista de FacturaResponse")
    void obtenerFacturasDebeRetornarListaResponse() {
        when(facturaRepository.findAll()).thenReturn(List.of(facturaEntity));
        when(facturaMapper.toResponse(facturaEntity)).thenReturn(facturaResponse);
        
        List<FacturaResponse> resultado = facturaService.obtenerFacturas();
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(facturaRepository).findAll();
        verify(facturaMapper).toResponse(facturaEntity);
    }
    
    @Test
    @DisplayName("obtenerFacturas() debe retornar lista vacía cuando no hay facturas")
    void obtenerFacturasDebeRetornarListaVacia() {
        when(facturaRepository.findAll()).thenReturn(List.of());
        
        List<FacturaResponse> resultado = facturaService.obtenerFacturas();
        
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(facturaRepository).findAll();
        verify(facturaMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("buscarFacturaPorId() debe retornar FacturaResponse cuando existe")
    void buscarFacturaPorIdDebeRetornarSiExiste() {
        when(facturaRepository.findById(1L)).thenReturn(Optional.of(facturaEntity));
        when(facturaMapper.toResponse(facturaEntity)).thenReturn(facturaResponse);
        
        FacturaResponse resultado = facturaService.buscarFacturaPorId(1L);
        
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("F1", resultado.getFolio());
        verify(facturaRepository).findById(1L);
        verify(facturaMapper).toResponse(facturaEntity);
    }

    @Test
    @DisplayName("buscarFacturaPorId() debe lanzar excepción cuando no existe")
    void buscarFacturaPorIdDebeLanzarExcepcion() {
        when(facturaRepository.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> facturaService.buscarFacturaPorId(99L));
        
        verify(facturaRepository).findById(99L);
        verify(facturaMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("buscarFacturaPorFolio() debe retornar FacturaResponse cuando existe")
    void buscarFacturaPorFolioDebeRetornarSiExiste() {
        when(facturaRepository.findByFolio("F1")).thenReturn(Optional.of(facturaEntity));
        when(facturaMapper.toResponse(facturaEntity)).thenReturn(facturaResponse);
        
        FacturaResponse resultado = facturaService.buscarFacturaPorFolio("F1");
        
        assertNotNull(resultado);
        assertEquals("F1", resultado.getFolio());
        verify(facturaRepository).findByFolio("F1");
        verify(facturaMapper).toResponse(facturaEntity);
    }

    @Test
    @DisplayName("buscarFacturaPorFolio() debe lanzar excepción cuando no existe")
    void buscarFacturaPorFolioDebeLanzarExcepcion() {
        when(facturaRepository.findByFolio("F99")).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> facturaService.buscarFacturaPorFolio("F99"));
        
        verify(facturaRepository).findByFolio("F99");
        verify(facturaMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("agregarFactura() debe crear y retornar FacturaResponse cuando todo es válido")
    void agregarFacturaDebeAgregarYRetornarSiTodoValido() {
        ReservaResponse reservaResponse = ReservaResponse.builder()
                .idReserva(1L)
                .idHuesped(1L)
                .cantDias(1)
                .build();
                
        HuespedResponse huespedResponse = HuespedResponse.builder()
                .id(1L)
                .run("123-4")
                .nombreCompleto("John Doe")
                .build();
                
        PagoResponse pagoResponse = PagoResponse.builder()
                .idPago(1L)
                .idHuesped(1L)
                .subtotal(1)
                .impuestos(1)
                .total(2)
                .metodoPago(MetodoPago.TARJETA)
                .estadoPago(EstadoPago.PENDIENTE)
                .build();
                
        CheckInResponse checkInResponse = CheckInResponse.builder()
                .id(1L)
                .idReserva(1L)
                .fechaIngreso(LocalDateTime.now())
                .build();
                
        CheckOutResponse checkOutResponse = CheckOutResponse.builder()
                .id(1L)
                .idReserva(1L)
                .fechaSalida(LocalDateTime.now())
                .build();

        when(facturaRepository.existsByIdReserva(1L)).thenReturn(false);
        when(facturaRepository.existsByIdPago(1L)).thenReturn(false);
        when(facturaMapper.fomRequest(facturaRequest)).thenReturn(facturaEntity);
        when(reservaClient.buscarReservaPorId(1L)).thenReturn(reservaResponse);
        when(huespedClient.buscarHuespedPorRun("123-4")).thenReturn(huespedResponse);
        when(checkInClient.obtenerCheckInPorId(1L)).thenReturn(checkInResponse);
        when(checkOutClient.obtenerCheckOutPorId(1L)).thenReturn(checkOutResponse);
        when(pagoClient.buscarPagoPorId(1L)).thenReturn(pagoResponse);
        when(facturaRepository.save(facturaEntity)).thenReturn(facturaEntity);
        when(facturaMapper.toResponse(facturaEntity)).thenReturn(facturaResponse);
        
        FacturaResponse resultado = facturaService.agregarFactura(facturaRequest);

        assertNotNull(resultado);
        assertEquals("F1", resultado.getFolio());
        assertEquals("John Doe", resultado.getNombreHuesped());
        verify(facturaRepository).save(facturaEntity);
    }

    @Test
    @DisplayName("agregarFactura() debe lanzar excepción cuando ya existe factura para la reserva")
    void agregarFacturaDebeLanzarExcepcionSiYaExisteReserva() {
        when(facturaRepository.existsByIdReserva(1L)).thenReturn(true);
        
        assertThrows(BadRequestException.class, () -> facturaService.agregarFactura(facturaRequest));
        
        verify(facturaRepository, never()).save(any());
        verify(facturaMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("agregarFactura() debe lanzar excepción cuando ya existe factura con ese pago")
    void agregarFacturaDebeLanzarExcepcionSiYaExistePago() {
        when(facturaRepository.existsByIdReserva(1L)).thenReturn(false);
        when(facturaRepository.existsByIdPago(1L)).thenReturn(true);
        
        assertThrows(BadRequestException.class, () -> facturaService.agregarFactura(facturaRequest));
        
        verify(facturaRepository, never()).save(any());
        verify(facturaMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("agregarFactura() debe lanzar excepción cuando reserva no pertenece al huésped")
    void agregarFacturaDebeLanzarExcepcionSiReservaNoPerteneceAHuesped() {
        ReservaResponse reservaResponse = ReservaResponse.builder()
                .idReserva(1L)
                .idHuesped(99L)
                .cantDias(1)
                .build();
                
        HuespedResponse huespedResponse = HuespedResponse.builder()
                .id(1L)
                .run("123-4")
                .nombreCompleto("John Doe")
                .build();

        when(facturaRepository.existsByIdReserva(1L)).thenReturn(false);
        when(facturaRepository.existsByIdPago(1L)).thenReturn(false);
        when(facturaMapper.fomRequest(facturaRequest)).thenReturn(facturaEntity);
        when(reservaClient.buscarReservaPorId(1L)).thenReturn(reservaResponse);
        when(huespedClient.buscarHuespedPorRun("123-4")).thenReturn(huespedResponse);
        
        assertThrows(BadRequestException.class, () -> facturaService.agregarFactura(facturaRequest));
        
        verify(facturaRepository, never()).save(any());
    }

    @Test
    @DisplayName("agregarFactura() debe lanzar excepción cuando check-in no pertenece a la reserva")
    void agregarFacturaDebeLanzarExcepcionSiCheckInNoPerteneceAReserva() {
        ReservaResponse reservaResponse = ReservaResponse.builder()
                .idReserva(1L)
                .idHuesped(1L)
                .cantDias(1)
                .build();
                
        HuespedResponse huespedResponse = HuespedResponse.builder()
                .id(1L)
                .run("123-4")
                .nombreCompleto("John Doe")
                .build();
                
        CheckInResponse checkInResponse = CheckInResponse.builder()
                .id(1L)
                .idReserva(99L)
                .fechaIngreso(LocalDateTime.now())
                .build();

        when(facturaRepository.existsByIdReserva(1L)).thenReturn(false);
        when(facturaRepository.existsByIdPago(1L)).thenReturn(false);
        when(facturaMapper.fomRequest(facturaRequest)).thenReturn(facturaEntity);
        when(reservaClient.buscarReservaPorId(1L)).thenReturn(reservaResponse);
        when(huespedClient.buscarHuespedPorRun("123-4")).thenReturn(huespedResponse);
        when(checkInClient.obtenerCheckInPorId(1L)).thenReturn(checkInResponse);
        
        assertThrows(BadRequestException.class, () -> facturaService.agregarFactura(facturaRequest));
        
        verify(facturaRepository, never()).save(any());
    }

    @Test
    @DisplayName("agregarFactura() debe lanzar excepción cuando check-out no pertenece a la reserva")
    void agregarFacturaDebeLanzarExcepcionSiCheckOutNoPerteneceAReserva() {
        ReservaResponse reservaResponse = ReservaResponse.builder()
                .idReserva(1L)
                .idHuesped(1L)
                .cantDias(1)
                .build();
                
        HuespedResponse huespedResponse = HuespedResponse.builder()
                .id(1L)
                .run("123-4")
                .nombreCompleto("John Doe")
                .build();
                
        CheckInResponse checkInResponse = CheckInResponse.builder()
                .id(1L)
                .idReserva(1L)
                .fechaIngreso(LocalDateTime.now())
                .build();
                
        CheckOutResponse checkOutResponse = CheckOutResponse.builder()
                .id(1L)
                .idReserva(99L)
                .fechaSalida(LocalDateTime.now())
                .build();

        when(facturaRepository.existsByIdReserva(1L)).thenReturn(false);
        when(facturaRepository.existsByIdPago(1L)).thenReturn(false);
        when(facturaMapper.fomRequest(facturaRequest)).thenReturn(facturaEntity);
        when(reservaClient.buscarReservaPorId(1L)).thenReturn(reservaResponse);
        when(huespedClient.buscarHuespedPorRun("123-4")).thenReturn(huespedResponse);
        when(checkInClient.obtenerCheckInPorId(1L)).thenReturn(checkInResponse);
        when(checkOutClient.obtenerCheckOutPorId(1L)).thenReturn(checkOutResponse);
        
        assertThrows(BadRequestException.class, () -> facturaService.agregarFactura(facturaRequest));
        
        verify(facturaRepository, never()).save(any());
    }

    @Test
    @DisplayName("agregarFactura() debe lanzar excepción cuando pago no pertenece al huésped")
    void agregarFacturaDebeLanzarExcepcionSiPagoNoPerteneceAHuesped() {
        ReservaResponse reservaResponse = ReservaResponse.builder()
                .idReserva(1L)
                .idHuesped(1L)
                .cantDias(1)
                .build();
                
        HuespedResponse huespedResponse = HuespedResponse.builder()
                .id(1L)
                .run("123-4")
                .nombreCompleto("John Doe")
                .build();
                
        PagoResponse pagoResponse = PagoResponse.builder()
                .idPago(1L)
                .idHuesped(99L)
                .subtotal(1)
                .impuestos(1)
                .total(2)
                .metodoPago(MetodoPago.TARJETA)
                .estadoPago(EstadoPago.PENDIENTE)
                .build();
                
        CheckInResponse checkInResponse = CheckInResponse.builder()
                .id(1L)
                .idReserva(1L)
                .fechaIngreso(LocalDateTime.now())
                .build();
                
        CheckOutResponse checkOutResponse = CheckOutResponse.builder()
                .id(1L)
                .idReserva(1L)
                .fechaSalida(LocalDateTime.now())
                .build();

        when(facturaRepository.existsByIdReserva(1L)).thenReturn(false);
        when(facturaRepository.existsByIdPago(1L)).thenReturn(false);
        when(facturaMapper.fomRequest(facturaRequest)).thenReturn(facturaEntity);
        when(reservaClient.buscarReservaPorId(1L)).thenReturn(reservaResponse);
        when(huespedClient.buscarHuespedPorRun("123-4")).thenReturn(huespedResponse);
        when(checkInClient.obtenerCheckInPorId(1L)).thenReturn(checkInResponse);
        when(checkOutClient.obtenerCheckOutPorId(1L)).thenReturn(checkOutResponse);
        when(pagoClient.buscarPagoPorId(1L)).thenReturn(pagoResponse);
        
        assertThrows(BadRequestException.class, () -> facturaService.agregarFactura(facturaRequest));
        
        verify(facturaRepository, never()).save(any());
    }

    @Test
    @DisplayName("actualizarFactura() debe actualizar y retornar FacturaResponse cuando existe")
    void actualizarFacturaDebeActualizarYRetornarSiExiste() {
        ReservaResponse reservaResponse = ReservaResponse.builder()
                .idReserva(1L)
                .idHuesped(1L)
                .cantDias(1)
                .build();
                
        HuespedResponse huespedResponse = HuespedResponse.builder()
                .id(1L)
                .run("456-7")
                .nombreCompleto("John Doe")
                .build();
                
        PagoResponse pagoResponse = PagoResponse.builder()
                .idPago(1L)
                .idHuesped(1L)
                .subtotal(1)
                .impuestos(1)
                .total(2)
                .metodoPago(MetodoPago.TARJETA)
                .estadoPago(EstadoPago.PENDIENTE)
                .build();
                
        CheckInResponse checkInResponse = CheckInResponse.builder()
                .id(1L)
                .idReserva(1L)
                .fechaIngreso(LocalDateTime.now())
                .build();
                
        CheckOutResponse checkOutResponse = CheckOutResponse.builder()
                .id(1L)
                .idReserva(1L)
                .fechaSalida(LocalDateTime.now())
                .build();

        when(facturaRepository.findById(1L)).thenReturn(Optional.of(facturaEntity));
        when(reservaClient.buscarReservaPorId(1L)).thenReturn(reservaResponse);
        when(huespedClient.buscarHuespedPorRun("456-7")).thenReturn(huespedResponse);
        when(checkInClient.obtenerCheckInPorId(1L)).thenReturn(checkInResponse);
        when(checkOutClient.obtenerCheckOutPorId(1L)).thenReturn(checkOutResponse);
        when(pagoClient.buscarPagoPorId(1L)).thenReturn(pagoResponse);
        when(facturaRepository.save(facturaEntity)).thenReturn(facturaEntity);
        when(facturaMapper.toResponse(facturaEntity)).thenReturn(facturaResponse);

        
        FacturaResponse resultado = facturaService.actualizarFactura(1L, facturaUpdateRequest);

        
        assertNotNull(resultado);
        assertEquals("F1", resultado.getFolio());
        verify(facturaRepository).save(facturaEntity);
    }

    @Test
    @DisplayName("actualizarFactura() debe lanzar excepción cuando no existe la factura")
    void actualizarFacturaDebeLanzarExcepcionSiNoExiste() {
        when(facturaRepository.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> facturaService.actualizarFactura(99L, facturaUpdateRequest));
        
        verify(facturaRepository, never()).save(any());
    }

    @Test
    @DisplayName("actualizarFactura() debe lanzar excepción cuando reserva no pertenece al huésped")
    void actualizarFacturaDebeLanzarExcepcionSiReservaNoPerteneceAHuesped() {
        ReservaResponse reservaResponse = ReservaResponse.builder()
                .idReserva(1L)
                .idHuesped(99L)
                .cantDias(1)
                .build();
                
        HuespedResponse huespedResponse = HuespedResponse.builder()
                .id(1L)
                .run("456-7")
                .nombreCompleto("John Doe")
                .build();

        when(facturaRepository.findById(1L)).thenReturn(Optional.of(facturaEntity));
        when(reservaClient.buscarReservaPorId(1L)).thenReturn(reservaResponse);
        when(huespedClient.buscarHuespedPorRun("456-7")).thenReturn(huespedResponse);
        
        assertThrows(BadRequestException.class, () -> facturaService.actualizarFactura(1L, facturaUpdateRequest));
        
        verify(facturaRepository, never()).save(any());
    }

    @Test
    @DisplayName("eliminarFactura() debe eliminar cuando existe")
    void eliminarFacturaDebeEliminarSiExiste() {
        when(facturaRepository.existsById(1L)).thenReturn(true);
        
        facturaService.eliminarFactura(1L);
        
        verify(facturaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("eliminarFactura() debe lanzar excepción cuando no existe")
    void eliminarFacturaDebeLanzarExcepcionSiNoExiste() {
        when(facturaRepository.existsById(99L)).thenReturn(false);
        
        assertThrows(EntityNotFoundException.class, () -> facturaService.eliminarFactura(99L));
        
        verify(facturaRepository, never()).deleteById(any());
    }
}
