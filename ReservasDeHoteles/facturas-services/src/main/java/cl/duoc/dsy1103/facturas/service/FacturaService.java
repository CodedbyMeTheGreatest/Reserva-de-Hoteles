package cl.duoc.dsy1103.facturas.service;

import cl.duoc.dsy1103.facturas.client.HuespedClient;
import cl.duoc.dsy1103.facturas.client.PagoClient;
import cl.duoc.dsy1103.facturas.client.ReservaClient;
import cl.duoc.dsy1103.facturas.dto.*;
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

    public List<FacturaResponse> findAll(){
        log.info("Obteniendo facturas...");
        return facturaRepository.findAll()
                .stream()
                .map(facturaMapper::toResponse)
                .toList();
    }

     public FacturaResponse findById(Long id){
        log.info("Obteniendo factura con ID -> {}", id);
        return facturaMapper.toResponse(facturaRepository.findById(id).orElseThrow(()
        -> new EntityNotFoundException("No se encontró factura con ID "+id)));
     }

     public FacturaResponse findByFolio(String folio){
         log.info("Obteniendo factura con FOLIO -> {}", folio);
         return facturaMapper.toResponse(facturaRepository.findByFolio(folio).orElseThrow(()
                 -> new EntityNotFoundException("No se encontró factura con FOLIO "+folio)));
     }

     public FacturaResponse addFactura(FacturaRequest request){
        log.info("Agregando factura con FOLIO -> {}", request.getFolio());
         ReservaResponse existeReserva = reservaClient.findReservaById(request.getIdReserva());
         HuespedResponse existeHuesped = huespedClient.findHuespedByRun(request.getRunHuesped());
         PagoResponse existePago = pagoClient.findPagoById(request.getIdPago());
         return facturaMapper.toResponse(facturaRepository.save(facturaMapper.fomRequest(request)));
     }

     public FacturaResponse updateFactura(Long id, FacturaUpdateRequest updateRequest){
        log.info("Actualizando factura con ID -> {}", id);
        Factura factura = facturaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró factura con ID " + id));
        if(updateRequest.getFolio() != null){
            factura.setFolio(updateRequest.getFolio());
        }
        if (updateRequest.getIdReserva() != null){
            factura.setIdReserva(updateRequest.getIdReserva());
        }
         if (updateRequest.getRunHuesped() != null){
             factura.setRunHuesped(updateRequest.getRunHuesped());
         }
         if (updateRequest.getFecha() != null){
             factura.setFecha(updateRequest.getFecha());
         }
         if (updateRequest.getIdPago() != null){
             factura.setIdPago(updateRequest.getIdPago());
         }
         if (updateRequest.getDescripcionHabitacion() != null){
             factura.setDescripcionHabitacion(updateRequest.getDescripcionHabitacion());
         }
         if (updateRequest.getEstado() != null){
             factura.setEstado(updateRequest.getEstado());
         }
         return facturaMapper.toResponse(factura);
     }

     public void deleteFactura(Long id){
        log.info("Eliminando factura con ID -> {}", id);
        if(!facturaRepository.existsById(id)){
            throw new EntityNotFoundException("No se encontró factura con ID " +id);
        }
        facturaRepository.deleteById(id);
     }
}
