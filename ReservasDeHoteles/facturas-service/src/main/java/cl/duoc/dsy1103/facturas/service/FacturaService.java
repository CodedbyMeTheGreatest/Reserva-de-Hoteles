package cl.duoc.dsy1103.facturas.service;

import cl.duoc.dsy1103.facturas.client.*;
import cl.duoc.dsy1103.facturas.dto.*;
import cl.duoc.dsy1103.facturas.exception.BadRequestException;
import cl.duoc.dsy1103.facturas.mapper.FacturaMapper;
import cl.duoc.dsy1103.facturas.model.Factura;
import cl.duoc.dsy1103.facturas.repository.FacturaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private FacturaMapper facturaMapper;

    @Autowired
    private HuespedClient huespedClient;

    @Autowired
    private PagoClient pagoClient;

    @Autowired
    private ReservaClient reservaClient;

    @Autowired
    private CheckInClient checkInClient;

    @Autowired
    private CheckOutClient checkOutClient;

    public List<FacturaResponse> obtenerFacturas(){
        log.info("Obteniendo facturas...");
        return facturaRepository.findAll()
                .stream()
                .map(facturaMapper::toResponse)
                .toList();
    }

    public FacturaResponse buscarFacturaPorId(Long id){
        log.info("Buscando factura con ID -> {}", id);
        return facturaMapper.toResponse(facturaRepository.findById(id).orElseThrow(()
        -> new EntityNotFoundException("No se encontró factura con ID -> "+id)));
    }

    public FacturaResponse buscarFacturaPorFolio(String folio){
         log.info("Buscando factura con FOLIO -> {}", folio);
         return facturaMapper.toResponse(facturaRepository.findByFolio(folio).orElseThrow(()
                 -> new EntityNotFoundException("No se encontró factura con FOLIO -> "+folio)));
    }

    public FacturaResponse agregarFactura(FacturaRequest request){
        log.info("Agregando factura con FOLIO -> {}", request.getFolio());
        if(facturaRepository.existsByIdReserva(request.getIdReserva())){
             throw new BadRequestException("Ya existe una factura para la reserva con ID -> "+ request.getIdReserva());
        }
        if(facturaRepository.existsByIdPago(request.getIdPago())){
            throw new BadRequestException("Ya existe una factura con el pago de ID -> " + request.getIdPago());
        }
        Factura factura = facturaMapper.fomRequest(request);

        ReservaResponse existeReserva = reservaClient.buscarReservaPorId(factura.getIdReserva());
        HuespedResponse existeHuesped = huespedClient.buscarHuespedPorRun(factura.getRunHuesped());
        if(!existeReserva.getIdHuesped().equals(existeHuesped.getId())){
            throw new BadRequestException("La reserva con ID -> " + existeReserva.getIdReserva() + " no pertenece al huésped con ID -> " + existeHuesped.getId());
        }
        factura.setNombreHuesped(existeHuesped.getNombreCompleto());
        factura.setCantDias(existeReserva.getCantDias());

        CheckInResponse existeCheckIn = checkInClient.obtenerCheckInPorId(factura.getIdCheckIn());
        if(!existeCheckIn.getIdReserva().equals(existeReserva.getIdReserva())){
            throw new BadRequestException("El check-in con ID -> " + existeCheckIn.getId() + " no pertenece a la reserva con ID -> " + existeReserva.getIdReserva());
        }
        factura.setFechaIngreso(existeCheckIn.getFechaIngreso());

        CheckOutResponse existeCheckOut = checkOutClient.obtenerCheckOutPorId(factura.getIdCheckOut());
        if(!existeCheckOut.getIdReserva().equals(existeReserva.getIdReserva())){
            throw new BadRequestException("El check-out con ID -> " + existeCheckOut.getId() + " no pertenece a la reserva con ID -> " + existeReserva.getIdReserva());
        }
        factura.setFechaSalida(existeCheckOut.getFechaSalida());

        PagoResponse existePago = pagoClient.buscarPagoPorId(factura.getIdPago());
        if(!existeHuesped.getId().equals(existePago.getIdHuesped())){
            throw new BadRequestException("El pago con ID -> " + existePago.getIdPago() + " no pertenece al huésped con ID -> " + existeHuesped.getId());
        }
        factura.setSubtotal(existePago.getSubtotal());
        factura.setImpuestos(existePago.getImpuestos());
        factura.setTotal(existePago.getTotal());
        factura.setMetodoPago(existePago.getMetodoPago());
        factura.setEstadoPago(existePago.getEstadoPago());

        return facturaMapper.toResponse(facturaRepository.save(factura));
    }

    public FacturaResponse actualizarFactura(Long id, FacturaUpdateRequest updateRequest){
        log.info("Actualizando factura con ID -> {}", id);
        Factura factura = facturaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró factura con ID -> " + id));
        if(updateRequest.getFolio() != null){
            factura.setFolio(updateRequest.getFolio());
        }
        if (updateRequest.getIdReserva() != null){
            if(facturaRepository.existsByIdReserva(updateRequest.getIdReserva())){
                throw new BadRequestException("Ya existe una factura para la reserva con ID -> "+ updateRequest.getIdReserva());
            }
            factura.setIdReserva(updateRequest.getIdReserva());
        }

        if (updateRequest.getRunHuesped() != null){
            factura.setRunHuesped(updateRequest.getRunHuesped());
        }

        if(updateRequest.getIdCheckIn() != null){
            factura.setIdCheckIn(updateRequest.getIdCheckIn());
        }
        if(updateRequest.getIdCheckOut() != null){
            factura.setIdCheckOut(updateRequest.getIdCheckOut());
        }
        if (updateRequest.getIdPago() != null){
            if(facturaRepository.existsByIdPago(updateRequest.getIdPago())){
                throw new BadRequestException("Ya existe una factura con el pago de ID -> " + updateRequest.getIdPago());
            }
            factura.setIdPago(updateRequest.getIdPago());
        }
        if (updateRequest.getDescripcionHabitacion() != null){
            factura.setDescripcionHabitacion(updateRequest.getDescripcionHabitacion());
        }

        ReservaResponse existeReserva = reservaClient.buscarReservaPorId(factura.getIdReserva());
        factura.setCantDias(existeReserva.getCantDias());

        HuespedResponse existeHuesped = huespedClient.buscarHuespedPorRun(factura.getRunHuesped());
        if(!existeReserva.getIdHuesped().equals(existeHuesped.getId())){
            throw new BadRequestException("La reserva con ID -> " + existeReserva.getIdReserva() + " no pertenece al huésped con ID -> " + existeHuesped.getId());
        }
        factura.setNombreHuesped(existeHuesped.getNombreCompleto());

        CheckInResponse existeCheckIn = checkInClient.obtenerCheckInPorId(factura.getIdCheckIn());
        if(!existeCheckIn.getIdReserva().equals(existeReserva.getIdReserva())){
            throw new BadRequestException("El check-in con ID -> " + existeCheckIn.getId() + " no pertenece a la reserva con ID -> " + existeReserva.getIdReserva());
        }
        factura.setFechaIngreso(existeCheckIn.getFechaIngreso());

        CheckOutResponse existeCheckOut = checkOutClient.obtenerCheckOutPorId(factura.getIdCheckOut());
        if(!existeCheckOut.getIdReserva().equals(existeReserva.getIdReserva())){
            throw new BadRequestException("El check-out con ID -> " + existeCheckOut.getId() + " no pertenece a la reserva con ID -> " + existeReserva.getIdReserva());
        }
        factura.setFechaSalida(existeCheckOut.getFechaSalida());

        PagoResponse existePago = pagoClient.buscarPagoPorId(factura.getIdPago());
        if(!existeHuesped.getId().equals(existePago.getIdHuesped())){
            throw new BadRequestException("El pago con ID -> " + existePago.getIdPago() + " no pertenece al huésped con ID -> " + existeHuesped.getId());
        }
        factura.setSubtotal(existePago.getSubtotal());
        factura.setImpuestos(existePago.getImpuestos());
        factura.setTotal(existePago.getTotal());
        factura.setMetodoPago(existePago.getMetodoPago());
        factura.setEstadoPago(existePago.getEstadoPago());
        return facturaMapper.toResponse(facturaRepository.save(factura));
    }

    public void eliminarFactura(Long id){
       log.info("Eliminando factura con ID -> {}", id);
       if(!facturaRepository.existsById(id)){
           throw new EntityNotFoundException("No se encontró factura con ID -> " +id);
       }
       facturaRepository.deleteById(id);
    }
}
