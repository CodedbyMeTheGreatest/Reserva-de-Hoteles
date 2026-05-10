package cl.duoc.dsy1103.habitaciones.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.dsy1103.habitaciones.dto.HabitacionRequest;
import cl.duoc.dsy1103.habitaciones.dto.HabitacionResponse;
import cl.duoc.dsy1103.habitaciones.dto.HabitacionUpdateRequest;
import cl.duoc.dsy1103.habitaciones.exception.ConflictException;
import cl.duoc.dsy1103.habitaciones.mapper.HabitacionMapper;
import cl.duoc.dsy1103.habitaciones.model.Habitacion;
import cl.duoc.dsy1103.habitaciones.repository.HabitacionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private HabitacionMapper habitacionMapper;
    
    public List<Habitacion> buscarHabitaciones(){
        log.info("Buscando habitaciones...");
        return habitacionRepository.findAll();
    }

    public HabitacionResponse buscarHabitacionPorId(Long idHabitacion){
        log.info("Buscando habitacion por ID: {}",idHabitacion);
        Habitacion habitacion = habitacionRepository.findById(idHabitacion)
            .orElseThrow(() -> new NoSuchElementException("Habitacion no encontrada."));
        return habitacionMapper.toResponse(habitacion);
    }

    public HabitacionResponse buscarHabitacionPorNumero(String numero){
        log.info("Buscando habitacion por numero: {}",numero);
        Habitacion habitacion = habitacionRepository.findByNumero(numero)
            .orElseThrow(() -> new NoSuchElementException("Habitacion no encontrada."));
        return habitacionMapper.toResponse(habitacion);
    }

    public HabitacionResponse crearHabitacion (HabitacionRequest request) {
        log.info("Creando habitacion con numero: {}",request.getNumero());
        if(habitacionRepository.existsByNumero(request.getNumero())){
            throw new ConflictException("Esa habitacion ya existe.");
        }
        Habitacion habitacion = habitacionRepository.save(habitacionMapper.fromRequest(request));
        return habitacionMapper.toResponse(habitacion);
    }

    public HabitacionResponse actualizarHabitacion (Long idHabitacion, HabitacionUpdateRequest request){
        log.info("Actualizando habitacion con ID: {}",idHabitacion);
        Habitacion habitacion = habitacionRepository.findById(idHabitacion)
            .orElseThrow(() -> new NoSuchElementException("Habitacion no encontrada."));
        
        if(request.getNumero()!=null){
            habitacion.setNumero(request.getNumero());
        }
        if(request.getDescripcion()!=null){
            habitacion.setDescripcion(request.getDescripcion());
        }

        if(request.getPrecioPorNoche()!=null){
            habitacion.setPrecioPorNoche(request.getPrecioPorNoche());
        }

        if(request.getIdHotel()!=null){
            habitacion.setIdHotel(request.getIdHotel());
        }

        if(request.getIdDisponibilidad()!=null){
            habitacion.setIdDisponibilidad(request.getIdDisponibilidad());
        }
        
        habitacion=habitacionRepository.save(habitacion);
        return habitacionMapper.toResponse(habitacion);
    }

    public void eliminarHabitacion (Long idHabitacion){
        log.info("Eliminando habitacion con ID: {}",idHabitacion);
        if(!habitacionRepository.existsById(idHabitacion)){
            throw new NoSuchElementException("Habitacion no encontrada.");
        }
        habitacionRepository.deleteById(idHabitacion);
    }

}
