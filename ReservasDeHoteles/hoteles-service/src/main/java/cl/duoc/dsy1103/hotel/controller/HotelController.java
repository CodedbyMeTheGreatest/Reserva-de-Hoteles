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
import cl.duoc.dsy1103.hotel.model.Hotel;
import cl.duoc.dsy1103.hotel.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/hoteles")

public class HotelController {

    @Autowired
    private HotelService hotelService;
    
    @GetMapping
    @Operation(summary = "Obtener todos los hoteles", description = "Obtiene todos los pagos existentes con su informacion")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200" ,description = "Operacion exitosa",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = HotelResponse.class)
                )
        )
    })
    public ResponseEntity<List<HotelResponse>> buscarHoteles(){
        log.info("GET /api/hoteles");
        return ResponseEntity.ok().body(hotelService.buscarHoteles());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener hotel por ID", description = "Obtiene un hotel especifico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = HotelResponse.class))),
        @ApiResponse(responseCode = "404", description = "Hotel no encontrado"),
        
    })
    public ResponseEntity<HotelResponse> buscarHotelPorId(@PathVariable("id")Long idHotel){
        log.info("GET /api/hoteles/{}", idHotel);
        return ResponseEntity.ok().body(hotelService.buscarHotelPorId(idHotel));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo hotel", description = "Agrega un nuevo hotel al sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = HotelResponse.class))),
        @ApiResponse(responseCode = "404", description = "Hotel no encontrado"),
        @ApiResponse(responseCode = "400", description = "Ingreso de datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<HotelResponse> crearHotel(@Valid @RequestBody HotelRequest request){
        log.info("POST /api/hoteles/crearHotel");
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.crearHotel(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un hotel", description = "Actualiza uno o todos los campos de un hotel existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = HotelResponse.class))),
        @ApiResponse(responseCode = "404", description = "Hotel no encontrado"),
        @ApiResponse(responseCode = "400", description = "Ingreso de datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<HotelResponse> actualizarHotel(@PathVariable("id") Long idHotel, @Valid @RequestBody HotelUpdateRequest request){
        log.info("PUT /api/hoteles/actualizarHotel/{}", idHotel);
        return ResponseEntity.ok().body(hotelService.actualizarHotel(idHotel, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un hotel por ID", description = "Elimina un hotel existente por la ID ingresada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Hotel eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Hotel no encontrado")
    })
    public ResponseEntity<Void> eliminarHotel(@PathVariable("id") Long idHotel){
        log.info("DELETE /api/hoteles/eliminarHotel/{}",idHotel);
        hotelService.eliminarHotel(idHotel);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Obtener un hotel por nombre", description = "Obtiene un hotel mediante el nombre ingresado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = HotelResponse.class))),
        @ApiResponse(responseCode = "404", description = "Hotel no encontrado"),
        
    })    
    public ResponseEntity<HotelResponse> buscarHotelPorNombre(@PathVariable("nombre") String nombre) {
        log.info("GET /api/hoteles/nombre/{}", nombre);
        return ResponseEntity.ok().body(hotelService.buscarHotelPorNombre(nombre));
    }

    

}
