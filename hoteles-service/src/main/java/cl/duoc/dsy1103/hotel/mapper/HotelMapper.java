package cl.duoc.dsy1103.hotel.mapper;

import org.springframework.stereotype.Component;

import cl.duoc.dsy1103.hotel.dto.HotelRequest;
import cl.duoc.dsy1103.hotel.dto.HotelResponse;
import cl.duoc.dsy1103.hotel.model.Hotel;

@Component
public class HotelMapper {

    public Hotel fromRequest (HotelRequest request) {
        return Hotel.builder()
                .rut(request.getRut())
                .direccion(request.getDireccion())
                .nombre(request.getNombre())
                .build();
    }

    public HotelResponse toResponse (Hotel hotel) {
        return HotelResponse.builder()
                .idHotel(hotel.getIdHotel())
                .rut(hotel.getRut())
                .direccion(hotel.getDireccion())
                .nombre(hotel.getNombre())
                .build();
    }

}
