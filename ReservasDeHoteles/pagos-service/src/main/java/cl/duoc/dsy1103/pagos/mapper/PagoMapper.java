package cl.duoc.dsy1103.pagos.mapper;

import org.springframework.stereotype.Component;

import cl.duoc.dsy1103.pagos.dto.PagoRequest;
import cl.duoc.dsy1103.pagos.dto.PagoResponse;
import cl.duoc.dsy1103.pagos.model.Pago;

@Component
public class PagoMapper {

    public Pago fromRequest (PagoRequest request) {
        return Pago.builder()
                .idHuesped(request.getIdHuesped())
                .precioPorNoche(request.getPrecioPorNoche())
                .cantDias(request.getCantDias())
                .metodoPago(request.getMetodoPago())
                .build();
    }

    public PagoResponse toResponse (Pago pago) {
        return PagoResponse.builder()
                .idPago(pago.getIdPago())
                .idHuesped(pago.getIdHuesped())
                .precioPorNoche(pago.getPrecioPorNoche())
                .cantDias(pago.getCantDias())
                .subtotal(pago.getSubtotal())
                .impuestos(pago.getImpuestos())
                .total(pago.getTotal())
                .metodoPago(pago.getMetodoPago())
                .estadoPago(pago.getEstadoPago())
                .fechaPago(pago.getFechaPago())
                .build();
    }

}