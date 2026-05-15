package cl.duoc.dsy1103.habitaciones.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import cl.duoc.dsy1103.habitaciones.dto.HotelResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
 
@Component
@Slf4j
public class HotelClient {
    @Autowired
    private WebClient webClient;

    public HotelResponse findHotelById(Long id){
        log.info("Obteniendo hotel con ID -> {}", id);
        try {
            return webClient.get()
                    .uri("http://localhost:8080/api/hoteles/" + id)
                    .retrieve()
                    .bodyToMono(HotelResponse.class)
                    .block();
        }catch (WebClientResponseException ex){
            switch (ex.getStatusCode().value()){
                case 404 -> throw new EntityNotFoundException("No se encontró hotel con ID "+ id);
                default -> throw new RuntimeException("Error obteniendo hotel con ID "+ id, ex);
            }
        }
    }
}
