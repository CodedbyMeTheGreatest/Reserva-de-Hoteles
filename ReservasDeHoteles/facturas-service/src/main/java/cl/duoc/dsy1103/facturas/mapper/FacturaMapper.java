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
                .descripcionHabitacion(request.getDescripcionHabitacion())
                .estado(request.getEstado())
                .fechaFactura(request.getFechaFactura())
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
                .fechaIngreso(factura.getFechaIngreso())
                .fechaSalida(factura.getFechaSalida())
                .descripcionHabitacion(factura.getDescripcionHabitacion())
                .cantDias(factura.getCantDias())
                .subtotal(factura.getSubtotal())
                .impuestos(factura.getImpuestos())
                .total(factura.getTotal())
                .estado(factura.getEstado())
                .fechaFactura(factura.getFechaFactura())
                .build();
    }

}
