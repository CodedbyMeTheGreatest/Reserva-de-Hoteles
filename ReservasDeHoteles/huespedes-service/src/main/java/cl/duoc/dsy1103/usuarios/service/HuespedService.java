package cl.duoc.dsy1103.usuarios.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.Client.ReservaClient;
import cl.duoc.dsy1103.usuarios.dto.HuespedResponse;
import cl.duoc.dsy1103.usuarios.mapper.HuespedMapper;
import cl.duoc.dsy1103.usuarios.model.Huesped;
import cl.duoc.dsy1103.usuarios.repository.HuespedRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HuespedService {

    @Autowired
    private HuespedRepository huespedRepository;

    @Autowired
    private HuespedMapper huespedMapper;

    @Autowired
    private ReservaClient reservaClient;


    public List<HuespedResponse> findAll(){
        log.info("Obteniendo huespedes...");
        return huespedRepository.findAll()
                .stream()
                .map(huespedMapper::toResponse)
                .toList();
    }

    public HuespedResponse findById(Long id){
        log.info("Obteniendo Huesped con ID -> {}", id);
        return huespedMapper.toResponse(huespedRepository.findById(id).orElseThrow(()
        -> new EntityNotFoundException("No se encontró Huesped con ID "+id)));
    }

    public HuespedResponse buscarHuespedPorRun(String run){
        log.info("Buscando Huesped con RUN -> {}", run);
        Huesped encontrado = huespedRepository.findByRun(run)
        .orElseThrow(() -> new EntityNotFoundException("No se encontró ningún Huesped con el RUN "+ run));
        return huespedMapper.toResponse(encontrado);
    }




    public void eliminarHuesped (Long id){
        log.info("Eliminando Huesped con ID: {}",id);
        if(!huespedRepository.existsById(id)){
            throw new NoSuchElementException("Huesped no encontrado.");
        }
        huespedRepository.deleteById(id);
    }

    
}
