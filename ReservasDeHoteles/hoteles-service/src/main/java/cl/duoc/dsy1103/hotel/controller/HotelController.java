package cl.duoc.dsy1103.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.dsy1103.hotel.dto.HotelRequest;
import cl.duoc.dsy1103.hotel.dto.HotelResponse;
import cl.duoc.dsy1103.hotel.dto.HotelUpdateRequest;
import cl.duoc.dsy1103.hotel.service.HotelService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/hoteles")

public class HotelController {

    @Autowired
    private HotelService hotelService;
    
    @GetMapping
    public ResponseEntity<List<HotelResponse>> buscarHoteles(){
        log.info("GET /api/hoteles");
        return ResponseEntity.ok().body(hotelService.buscarHoteles());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> buscarHotelPorId(@PathVariable("id")Long idHotel){
        log.info("GET /api/hoteles/{}", idHotel);
        return ResponseEntity.ok().body(hotelService.buscarHotelPorId(idHotel));
    }

    @PostMapping
    public ResponseEntity<HotelResponse> crearHotel(@Valid @RequestBody HotelRequest request){
        log.info("POST /api/hoteles/crearHotel");
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.crearHotel(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> actualizarHotel(@PathVariable("id") Long idHotel, @Valid @RequestBody HotelUpdateRequest request){
        log.info("PUT /api/hoteles/actualizarHotel/{}", idHotel);
        return ResponseEntity.ok().body(hotelService.actualizarHotel(idHotel, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHotel(@PathVariable("id") Long idHotel){
        log.info("DELETE /api/hoteles/eliminarHotel/{}",idHotel);
        hotelService.eliminarHotel(idHotel);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<HotelResponse> buscarHotelPorNombre(@PathVariable("nombre") String nombre) {
        log.info("GET /api/hoteles/nombre/{}", nombre);
        return ResponseEntity.ok().body(hotelService.buscarHotelPorNombre(nombre));
    }

    

}
