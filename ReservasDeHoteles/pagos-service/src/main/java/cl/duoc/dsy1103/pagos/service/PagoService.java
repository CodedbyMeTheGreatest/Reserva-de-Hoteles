package cl.duoc.dsy1103.pagos.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.dsy1103.pagos.dto.PagoRequest;
import cl.duoc.dsy1103.pagos.dto.PagoResponse;
import cl.duoc.dsy1103.pagos.dto.PagoUpdateRequest;
import cl.duoc.dsy1103.pagos.enums.EstadoPago;
import cl.duoc.dsy1103.pagos.mapper.PagoMapper;
import cl.duoc.dsy1103.pagos.model.Pago;
import cl.duoc.dsy1103.pagos.repository.PagoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PagoMapper pagoMapper;

    public List<PagoResponse> buscarPagos(){
        log.info("Buscando pagos...");
        return pagoRepository.findAll().stream()
                .map(pagoMapper::toResponse)
                .toList();
    }

    public PagoResponse buscarPagoPorId(Long idPago){
        log.info("Buscando pago por ID: {}",idPago);
        Pago pago = pagoRepository.findById(idPago)
            .orElseThrow(() -> new NoSuchElementException("Pago no encontrado."));
        return pagoMapper.toResponse(pago);
    }

    public PagoResponse crearPago (PagoRequest request) {
        log.info("Creando pago para la habitacion: {}", request.getIdHabitacion());

        Integer subtotal = request.getPrecioPorNoche() * request.getCantDias();
        Integer impuestos = (int) (subtotal * 0.19);
        Integer total = subtotal + impuestos;

        Pago pago = pagoMapper.fromRequest(request);
        pago.setSubtotal(subtotal);
        pago.setImpuestos(impuestos);
        pago.setTotal(total);
        pago.setEstadoPago(EstadoPago.PENDIENTE);
        pago.setFechaPago(null);

        Pago pagoCreado = pagoRepository.save(pago);
        log.info("Pago creado con ID: {}", pagoCreado.getIdPago());
        return pagoMapper.toResponse(pagoCreado);
    }

    public PagoResponse actualizarPago(Long idPago, PagoUpdateRequest request) {
        log.info("Actualizando pago por ID: {}",idPago);
        Pago pago = pagoRepository.findById(idPago)
            .orElseThrow(() -> new NoSuchElementException("Pago no encontrado."));
        
            if(request.getMetodoPago() != null) {
                pago.setMetodoPago(request.getMetodoPago());
            }
            if(request.getEstadoPago() != null) {
                if(request.getEstadoPago() == EstadoPago.PAGADO) {
                    pago.setFechaPago(LocalDateTime.now());
                }
                pago.setEstadoPago(request.getEstadoPago());
            }

            Pago pagoActualizado = pagoRepository.save(pago);
            return pagoMapper.toResponse(pagoActualizado);
    }

    public void eliminarPago (Long idPago){
        log.info("Eliminando pago con ID: {}",idPago);
        if(!pagoRepository.existsById(idPago)){
            throw new NoSuchElementException("Pago no encontrado.");
        }
        pagoRepository.deleteById(idPago);
    }
}
