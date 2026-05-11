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
                .runHuesped(request.getRunHuesped())
                .fecha(request.getFecha())
                .idPago(request.getIdPago())
                .descripcionHabitacion(request.getDescripcionHabitacion())
                .estado(request.getEstado())
                .build();
    }

    public FacturaResponse toResponse(Factura factura){
        return FacturaResponse.builder()
                .id(factura.getId())
                .folio(factura.getFolio())
                .idReserva(factura.getIdReserva())
                .runHuesped(factura.getRunHuesped())
                .fecha(factura.getFecha())
                .idPago(factura.getIdPago())
                .descripcionHabitacion(factura.getDescripcionHabitacion())
                .estado(factura.getEstado())
                .build();
    }

}
