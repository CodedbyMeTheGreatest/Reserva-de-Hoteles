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

         HuespedResponse existeHuesped = huespedClient.buscarHuespedPorRun(factura.getRunHuesped());
         factura.setNombreHuesped(existeHuesped.getNombreCompleto());

         ReservaResponse existeReserva = reservaClient.buscarReservaPorId(factura.getIdReserva());
         factura.setCantDias(existeReserva.getCantDias());

         CheckInResponse checkIn = checkInClient.obtenerCheckInPorId(existeReserva.getIdCheckIn());
         factura.setFechaIngreso(checkIn.getFechaIngreso());

         CheckOutResponse checkOut = checkOutClient.obtenerCheckOutPorId(existeReserva.getIdCheckOut());
         factura.setFechaSalida(checkOut.getFechaSalida());

         PagoResponse existePago = pagoClient.buscarPagoPorId(factura.getIdPago());
         factura.setSubtotal(existePago.getSubtotal());
         factura.setImpuestos(existePago.getImpuestos());
         factura.setTotal(existePago.getTotal());

         return facturaMapper.toResponse(facturaRepository.save(factura));
     }

     public FacturaResponse actualizarFactura(Long id, FacturaUpdateRequest updateRequest){
        log.info("Actualizando factura con ID -> {}", id);
        Factura factura = facturaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró factura con ID -> " + id));
        if(updateRequest.getFolio() != null){
            factura.setFolio(updateRequest.getFolio());
        }
        if (updateRequest.getIdReserva() != null){
            ReservaResponse existeReserva = reservaClient.buscarReservaPorId(updateRequest.getIdReserva());
            factura.setIdReserva(updateRequest.getIdReserva());

            CheckInResponse checkIn = checkInClient.obtenerCheckInPorId(existeReserva.getIdCheckIn());
            factura.setFechaIngreso(checkIn.getFechaIngreso());

            CheckOutResponse checkOut = checkOutClient.obtenerCheckOutPorId(existeReserva.getIdCheckOut());
            factura.setFechaSalida(checkOut.getFechaSalida());

            factura.setCantDias(existeReserva.getCantDias());
        }
        if (updateRequest.getRunHuesped() != null){
            HuespedResponse existeHuesped = huespedClient.buscarHuespedPorRun(updateRequest.getRunHuesped());
            factura.setRunHuesped(updateRequest.getRunHuesped());
            factura.setNombreHuesped(existeHuesped.getNombreCompleto());
        }
        if (updateRequest.getIdPago() != null){
            PagoResponse existePago = pagoClient.buscarPagoPorId(updateRequest.getIdPago());
            factura.setIdPago(updateRequest.getIdPago());

            factura.setSubtotal(existePago.getSubtotal());
            factura.setImpuestos(existePago.getImpuestos());
            factura.setTotal(existePago.getTotal());
        }
        if (updateRequest.getDescripcionHabitacion() != null){
            factura.setDescripcionHabitacion(updateRequest.getDescripcionHabitacion());
        }
        if (updateRequest.getEstado() != null){
            factura.setEstado(updateRequest.getEstado());
        }
        if (updateRequest.getFechaFactura() != null){
            factura.setFechaFactura(updateRequest.getFechaFactura());
        }
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
