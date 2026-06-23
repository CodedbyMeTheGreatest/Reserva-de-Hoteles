package cl.duoc.dsy1103.facturas.mapper;

import cl.duoc.dsy1103.facturas.dto.FacturaRequest;
import cl.duoc.dsy1103.facturas.dto.FacturaResponse;
import cl.duoc.dsy1103.facturas.model.Factura;
import org.springframework.stereotype.Component;

@Component
public class FacturaMapper {

    public Factura fomRequest(FacturaRequest request){
        return Factura.builder()
                .folio(request.getFolio())
                .idReserva(request.getIdReserva())
                .idPago(request.getIdPago())
                .runHuesped(request.getRunHuesped())
                .idCheckIn(request.getIdCheckIn())
                .idCheckOut(request.getIdCheckOut())
                .descripcionHabitacion(request.getDescripcionHabitacion())
                .build();
    }

    public FacturaResponse toResponse(Factura factura){
        return FacturaResponse.builder()
                .id(factura.getId())
                .folio(factura.getFolio())
                .idReserva(factura.getIdReserva())
                .idPago(factura.getIdPago())
                .runHuesped(factura.getRunHuesped())
                .nombreHuesped(factura.getNombreHuesped())
                .idCheckIn(factura.getIdCheckIn())
                .idCheckOut(factura.getIdCheckOut())
                .fechaIngreso(factura.getFechaIngreso())
                .fechaSalida(factura.getFechaSalida())
                .descripcionHabitacion(factura.getDescripcionHabitacion())
                .cantDias(factura.getCantDias())
                .subtotal(factura.getSubtotal())
                .impuestos(factura.getImpuestos())
                .total(factura.getTotal())
                .metodoPago(factura.getMetodoPago())
                .estadoPago(factura.getEstadoPago())
                .fechaFactura(factura.getFechaFactura())
                .build();
    }

}
