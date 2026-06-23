package cl.duoc.dsy1103.facturas.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FacturaServiceTest {
    /**
     * - - buscarFacturaPorId
        - - - Retorna excepción EntityNotFound
        - - buscarFacturaPorFolio
        - - - Retorna excepción EntityNotFound
        - - agregarFactura
        - - - Retorna excepción BadRequest si ya existe una factura con ID Reserva
        - - - Retorna excepción BadRequest cuando la reserva no pertenezca al huésped
        - - - Retorna excepción BadRequest cuando el check in no pertenezca a la reserva
        - - - Retorna excepción BadRequest cuando el pago no pertenezca a la reserva
        - - actualizarFactura
        - - - Retorna EntityNotFound si no existe factura
        - - - Retorna excepción BadRequest cuando la reserva no pertenezca al huésped
        - - - Retorna excepción BadRequest cuando el check in no pertenezca a la reserva
        - - - Retorna excepción BadRequest cuando el pago no pertenezca a la reserva
        - - eliminarFactura
        - - - Retorna excepción EntityNotFound
     */
}
