package cl.duoc.dsy1103.pagos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
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

import cl.duoc.dsy1103.pagos.client.HabitacionClient;
import cl.duoc.dsy1103.pagos.client.HuespedClient;
import cl.duoc.dsy1103.pagos.dto.PagoRequest;
import cl.duoc.dsy1103.pagos.dto.PagoResponse;
import cl.duoc.dsy1103.pagos.dto.PagoUpdateRequest;
import cl.duoc.dsy1103.pagos.enums.EstadoPago;
import cl.duoc.dsy1103.pagos.enums.MetodoPago;
import cl.duoc.dsy1103.pagos.mapper.PagoMapper;
import cl.duoc.dsy1103.pagos.model.Pago;
import cl.duoc.dsy1103.pagos.repository.PagoRepository;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private PagoMapper pagoMapper;

    @Mock
    private HabitacionClient habitacionClient;

    @Mock
    private HuespedClient huespedClient;

    @InjectMocks
    private PagoService pagoService;

    private Pago pagoMock;
    private PagoResponse responseMock;
    private PagoRequest requestMock;

    @BeforeEach
    void setUp() {
        pagoMock = Pago.builder()
                .idPago(1L)
                .idHabitacion(1L)
                .idHuesped(1L)
                .precioPorNoche(BigInteger.valueOf(50000))
                .cantDias(3)
                .subtotal(BigInteger.valueOf(150000))
                .impuestos(BigInteger.valueOf(28500))
                .total(BigInteger.valueOf(178500))
                .metodoPago(MetodoPago.TARJETA)
                .estadoPago(EstadoPago.PENDIENTE)
                .fechaPago(null)
                .build();

        responseMock = PagoResponse.builder()
                .idPago(1L)
                .idHabitacion(1L)
                .idHuesped(1L)
                .precioPorNoche(BigInteger.valueOf(50000))
                .cantDias(3)
                .subtotal(BigInteger.valueOf(150000))
                .impuestos(BigInteger.valueOf(28500))
                .total(BigInteger.valueOf(178500))
                .metodoPago(MetodoPago.TARJETA)
                .estadoPago(EstadoPago.PENDIENTE)
                .build();

        requestMock = new PagoRequest();
        requestMock.setIdHabitacion(1L);
        requestMock.setIdHuesped(1L);
        requestMock.setPrecioPorNoche(BigInteger.valueOf(50000));
        requestMock.setCantDias(3);
        requestMock.setMetodoPago(MetodoPago.TARJETA);
    }

    @Test
    @DisplayName("buscarPagos() debe retornar lista de pagos")
    void buscarPagos_debeRetornarLista() {
        when(pagoRepository.findAll()).thenReturn(List.of(pagoMock));
        when(pagoMapper.toResponse(pagoMock)).thenReturn(responseMock);

        List<PagoResponse> resultado = pagoService.buscarPagos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(pagoRepository).findAll();
    }

    @Test
    @DisplayName("buscarPagoPorId() debe retornar pago cuando existe")
    void buscarPagoPorId_cuandoExiste_retornaPago() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoMock));
        when(pagoMapper.toResponse(pagoMock)).thenReturn(responseMock);

        PagoResponse resultado = pagoService.buscarPagoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdPago());
        verify(pagoRepository).findById(1L);
    }

    @Test
    @DisplayName("buscarPagoPorId() debe lanzar excepcion cuando no existe")
    void buscarPagoPorId_cuandoNoExiste_lanzaExcepcion() {
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> pagoService.buscarPagoPorId(99L));
        verify(pagoRepository).findById(99L);
        verify(pagoMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("crearPago() debe calcular correctamente subtotal, impuestos y total")
    void crearPago_debeCalcularMontosCorrectamente() {
        Pago pagoSinMontos = Pago.builder()
                .idHabitacion(1L)
                .idHuesped(1L)
                .precioPorNoche(BigInteger.valueOf(50000))
                .cantDias(3)
                .metodoPago(MetodoPago.TARJETA)
                .build();

        when(pagoMapper.fromRequest(requestMock)).thenReturn(pagoSinMontos);
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoMock);
        when(pagoMapper.toResponse(pagoMock)).thenReturn(responseMock);

        PagoResponse resultado = pagoService.crearPago(requestMock);

        assertNotNull(resultado);
        assertEquals(BigInteger.valueOf(150000), resultado.getSubtotal());
        assertEquals(BigInteger.valueOf(28500), resultado.getImpuestos());
        assertEquals(BigInteger.valueOf(178500), resultado.getTotal());
        assertEquals(EstadoPago.PENDIENTE, resultado.getEstadoPago());
        verify(pagoRepository).save(any(Pago.class));
    }

    @Test
    @DisplayName("crearPago() debe iniciar con estado PENDIENTE")
    void crearPago_debeIniciarConEstadoPendiente() {
        Pago pagoSinMontos = Pago.builder()
                .idHabitacion(1L)
                .idHuesped(1L)
                .precioPorNoche(BigInteger.valueOf(50000))
                .cantDias(3)
                .metodoPago(MetodoPago.TARJETA)
                .build();

        when(pagoMapper.fromRequest(requestMock)).thenReturn(pagoSinMontos);
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoMock);
        when(pagoMapper.toResponse(pagoMock)).thenReturn(responseMock);

        PagoResponse resultado = pagoService.crearPago(requestMock);

        assertEquals(EstadoPago.PENDIENTE, resultado.getEstadoPago());
        assertNull(resultado.getFechaPago());
    }

    @Test
    @DisplayName("actualizarPago() debe asignar fechaPago cuando estado cambia a PAGADO")
    void actualizarPago_cuandoCambiaAPagado_asignaFecha() {
        PagoUpdateRequest updateRequest = new PagoUpdateRequest();
        updateRequest.setEstadoPago(EstadoPago.PAGADO);

        PagoResponse responsePagado = PagoResponse.builder()
                .idPago(1L)
                .estadoPago(EstadoPago.PAGADO)
                .build();

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoMock));
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoMock);
        when(pagoMapper.toResponse(pagoMock)).thenReturn(responsePagado);

        PagoResponse resultado = pagoService.actualizarPago(1L, updateRequest);

        assertEquals(EstadoPago.PAGADO, resultado.getEstadoPago());
        assertNotNull(pagoMock.getFechaPago());
        verify(pagoRepository).save(pagoMock);
    }

    @Test
    @DisplayName("actualizarPago() debe lanzar excepcion cuando no existe")
    void actualizarPago_cuandoNoExiste_lanzaExcepcion() {
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> pagoService.actualizarPago(99L, new PagoUpdateRequest()));
        verify(pagoRepository, never()).save(any());
    }

    @Test
    @DisplayName("eliminarPago() debe eliminar cuando existe")
    void eliminarPago_cuandoExiste_eliminaCorrectamente() {
        when(pagoRepository.existsById(1L)).thenReturn(true);

        pagoService.eliminarPago(1L);

        verify(pagoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("eliminarPago() debe lanzar excepcion cuando no existe")
    void eliminarPago_cuandoNoExiste_lanzaExcepcion() {
        when(pagoRepository.existsById(99L)).thenReturn(false);

        assertThrows(NoSuchElementException.class,
                () -> pagoService.eliminarPago(99L));
        verify(pagoRepository, never()).deleteById(any());
    }

}
