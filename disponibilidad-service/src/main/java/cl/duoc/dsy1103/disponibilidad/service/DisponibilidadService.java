package cl.duoc.dsy1103.disponibilidad.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.dsy1103.disponibilidad.dto.DisponibilidadRequest;
import cl.duoc.dsy1103.disponibilidad.dto.DisponibilidadResponse;
import cl.duoc.dsy1103.disponibilidad.dto.DisponibilidadUpdateRequest;
import cl.duoc.dsy1103.disponibilidad.mapper.DisponibilidadMapper;
import cl.duoc.dsy1103.disponibilidad.model.Disponibilidad;
import cl.duoc.dsy1103.disponibilidad.repository.DisponibilidadRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class DisponibilidadService {

    @Autowired
    private DisponibilidadRepository disponibilidadRepository;

    @Autowired
    private DisponibilidadMapper disponibilidadMapper;

    public List<DisponibilidadResponse> buscarDisponibilidades(){
        log.info("Buscando disponibilidades...");
        return disponibilidadRepository.findAll().stream().map(disponibilidadMapper::toResponse).collect(java.util.stream.Collectors.toList());
    }

    public DisponibilidadResponse buscarDisponibilidadPorId(Long idDisponibilidad){
        log.info("Buscando disponibilidad por ID: {}",idDisponibilidad);
        Disponibilidad disponibilidad = disponibilidadRepository.findById(idDisponibilidad)
            .orElseThrow(() -> new NoSuchElementException("Disponibilidad no encontrada."));
        return disponibilidadMapper.toResponse(disponibilidad);
    }

    public DisponibilidadResponse crearDisponibilidad (DisponibilidadRequest request) {
        log.info("Creando disponibilidad con estado: {}",request.getEstado());
        Disponibilidad disponibilidad = disponibilidadRepository.save(disponibilidadMapper.fromRequest(request));
        return disponibilidadMapper.toResponse(disponibilidad);
    }

    public DisponibilidadResponse actualizarDisponibilidad (Long idDisponibilidad, DisponibilidadUpdateRequest request) {
        log.info("Actualizando disponibilidad con ID: {}",idDisponibilidad);
        Disponibilidad disponibilidad = disponibilidadRepository.findById(idDisponibilidad)
            .orElseThrow(() -> new NoSuchElementException("Disponibilidad no encontrada."));
        
        if(request.getEstado() != null){
            disponibilidad.setEstado(request.getEstado());
        }
        if(request.getFechaDesde() != null){
            disponibilidad.setFechaDesde(request.getFechaDesde());
        }
        if(request.getFechaHasta() != null){
            disponibilidad.setFechaHasta(request.getFechaHasta());
        }

        disponibilidad = disponibilidadRepository.save(disponibilidad);
        return disponibilidadMapper.toResponse(disponibilidad);
    }

    public void eliminarDisponibilidad (Long idDisponibilidad) {
        log.info("Eliminando disponibilidad con ID: {}",idDisponibilidad);
        Disponibilidad disponibilidad = disponibilidadRepository.findById(idDisponibilidad)
            .orElseThrow(() -> new NoSuchElementException("Disponibilidad no encontrada."));
        disponibilidadRepository.delete(disponibilidad);
    }

}
