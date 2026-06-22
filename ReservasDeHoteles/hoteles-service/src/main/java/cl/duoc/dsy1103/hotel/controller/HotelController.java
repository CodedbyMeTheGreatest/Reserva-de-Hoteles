package cl.duoc.dsy1103.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@Slf4j
@RequestMapping("/api/hoteles")
@Tag(name = "Hotel", description = "Gestion de hoteles")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    private void agregarLinks(HotelResponse hotel){
        hotel.add(linkTo(methodOn(HotelController.class).buscarHotelPorId(hotel.getIdHotel())).withSelfRel());
        hotel.add(linkTo(methodOn(HotelController.class).buscarHoteles()).withRel("todos-los-hoteles"));
        hotel.add(linkTo(methodOn(HotelController.class).eliminarHotel(hotel.getIdHotel())).withRel("eliminar"));
    }
    
    @GetMapping
    @Operation(summary = "Obtener todos los hoteles", description = "Obtiene todos los pagos existentes con su informacion")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200" ,description = "Operacion exitosa",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = HotelResponse.class)
                )
        )
    })
    public ResponseEntity<CollectionModel<HotelResponse>> buscarHoteles(){
        log.info("GET /api/hoteles");
        List<HotelResponse> hoteles = hotelService.buscarHoteles();
        hoteles.forEach(this::agregarLinks);
        CollectionModel<HotelResponse> collection = CollectionModel.of(hoteles,
                linkTo(methodOn(HotelController.class).buscarHoteles()).withSelfRel());
        return ResponseEntity.ok(collection);
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
        HotelResponse hotel = hotelService.buscarHotelPorId(idHotel);
        agregarLinks(hotel);
        return ResponseEntity.ok(hotel);
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
        HotelResponse hotel = hotelService.crearHotel(request);
        agregarLinks(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotel);
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
        HotelResponse hotel = hotelService.actualizarHotel(idHotel, request);
        agregarLinks(hotel);
        return ResponseEntity.ok(hotel);
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
        HotelResponse hotel = hotelService.buscarHotelPorNombre(nombre);
        agregarLinks(hotel);
        return ResponseEntity.ok(hotel);
    }

    

}
