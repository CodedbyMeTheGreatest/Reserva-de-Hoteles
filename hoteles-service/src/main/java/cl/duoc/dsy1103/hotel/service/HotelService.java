package cl.duoc.dsy1103.hotel.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.dsy1103.hotel.dto.HotelRequest;
import cl.duoc.dsy1103.hotel.dto.HotelResponse;
import cl.duoc.dsy1103.hotel.dto.HotelUpdateRequest;
import cl.duoc.dsy1103.hotel.exception.ConflictException;
import cl.duoc.dsy1103.hotel.mapper.HotelMapper;
import cl.duoc.dsy1103.hotel.model.Hotel;
import cl.duoc.dsy1103.hotel.repository.HotelRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelMapper hotelMapper;

    public List<HotelResponse> buscarHoteles(){
        log.info("Buscando hoteles...");
        return hotelRepository.findAll().stream().map(hotelMapper::toResponse).collect(java.util.stream.Collectors.toList());
    }

    public HotelResponse buscarHotelPorId(Long idHotel){
        log.info("Buscando hotel por ID: {}",idHotel);
        Hotel hotel = hotelRepository.findById(idHotel)
            .orElseThrow(() -> new NoSuchElementException("Hotel no encontrado."));
        return hotelMapper.toResponse(hotel);
    }


    public HotelResponse buscarHotelPorNombre(String nombre){
        log.info("Buscando hotel por nombre: {}", nombre);
        Hotel hotel = hotelRepository.findByNombre(nombre)
            .orElseThrow(() -> new NoSuchElementException("Hotel no encontrado con nombre: " + nombre));
        return hotelMapper.toResponse(hotel);
    }


    public HotelResponse crearHotel(HotelRequest request){
        log.info("Creando hotel: {}",request.getNombre());
        if(hotelRepository.existsByNombre(request.getNombre())){
            throw new ConflictException("Ese hotel ya existe.");
        }

        Hotel nuevoHotel = hotelRepository.save(hotelMapper.fromRequest(request));
        return hotelMapper.toResponse(nuevoHotel);
    }

    public HotelResponse actualizarHotel(Long idHotel, HotelUpdateRequest request){
        log.info("Actualizando hotel con ID: {}",idHotel);
        Hotel hotel = hotelRepository.findById(idHotel)
            .orElseThrow(() -> new NoSuchElementException("Hotel no encontrado."));
        if(request.getNombre() != null){
            hotel.setNombre(request.getNombre());
        }
        if(request.getDireccion() != null){
            hotel.setDireccion(request.getDireccion());
        }
        if(request.getRut() != null){
            hotel.setRut(request.getRut());
        }   

        hotel = hotelRepository.save(hotel);
        return hotelMapper.toResponse(hotel);
    }

    public void eliminarHotel(Long idHotel){
        log.info("Eliminando hotel con ID: {}",idHotel);
        if(!hotelRepository.existsById(idHotel)){
            throw new NoSuchElementException("Hotel no encontrado.");
        }
        hotelRepository.deleteById(idHotel);
    }
    
}
