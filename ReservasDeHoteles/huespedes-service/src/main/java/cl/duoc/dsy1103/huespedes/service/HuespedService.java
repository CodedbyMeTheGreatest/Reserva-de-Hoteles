package cl.duoc.dsy1103.huespedes.service;

import java.util.List;
import java.util.NoSuchElementException;

import cl.duoc.dsy1103.huespedes.dto.HuespedRequest;
import cl.duoc.dsy1103.huespedes.dto.HuespedUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.dsy1103.huespedes.dto.HuespedResponse;
import cl.duoc.dsy1103.huespedes.mapper.HuespedMapper;
import cl.duoc.dsy1103.huespedes.model.Huesped;
import cl.duoc.dsy1103.huespedes.repository.HuespedRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HuespedService {

    @Autowired
    private HuespedRepository huespedRepository;

    @Autowired
    private HuespedMapper huespedMapper;

    //@Autowired
    //private ReservaClient reservaClient;


    public List<HuespedResponse> obtenerHuespedes(){
        log.info("Obteniendo huéspedes...");
        return huespedRepository.findAll()
                .stream()
                .map(huespedMapper::toResponse)
                .toList();
    }

    public HuespedResponse buscarHuespedPorId(Long id){
        log.info("Obteniendo Huésped con ID -> {}", id);
        return huespedMapper.toResponse(huespedRepository.findById(id).orElseThrow(()
        -> new EntityNotFoundException("No se encontró huésped con ID -> "+id)));
    }

    public HuespedResponse buscarHuespedPorRun(String run){
        log.info("Buscando Huésped con RUN -> {}", run);
        Huesped encontrado = huespedRepository.findByRun(run)
        .orElseThrow(() -> new EntityNotFoundException("No se encontró ningún huésped con el RUN -> "+ run));
        return huespedMapper.toResponse(encontrado);
    }

    public HuespedResponse agregarHuesped(HuespedRequest request){
        log.info("Agregando huésped con RUN -> {}", request.getRun());
        return huespedMapper.toResponse(huespedRepository.save(huespedMapper.fromRequest(request)));
    }

    public HuespedResponse actualizarHuesped(Long id, HuespedUpdateRequest updateRequest){
        log.info("Actualizando huésped con ID -> {}", id);
        Huesped existe = huespedRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado huésped con ID -> "+ id));

        if(updateRequest.getRun() != null){
            existe.setRun(updateRequest.getRun());
        }
        if (updateRequest.getNombreCompleto() != null){
            existe.setNombreCompleto(updateRequest.getNombreCompleto());
        }
        if(updateRequest.getEmail() != null){
            existe.setEmail(updateRequest.getEmail());
        }
        if(updateRequest.getTelefono() != null){
            existe.setTelefono(updateRequest.getTelefono());
        }
        if(updateRequest.getNacionalidad() != null){
            existe.setNacionalidad(updateRequest.getNacionalidad());
        }
        return huespedMapper.toResponse(huespedRepository.save(existe));
    }

    public void eliminarHuesped(Long id){
        log.info("Eliminando huésped con ID: {}",id);
        if(!huespedRepository.existsById(id)){
            throw new NoSuchElementException("Huésped no encontrado.");
        }
        huespedRepository.deleteById(id);
    }
}
