package cl.duoc.dsy1103.empleados.client;

import cl.duoc.dsy1103.empleados.dto.HotelResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
public class HotelClient {
    private final WebClient hotelesWebClient;

    HotelClient(WebClient hotelesWebClient) {
        this.hotelesWebClient = hotelesWebClient;
    }

    public HotelResponse buscarHotelPorId(Long id){
        log.info("Obteniendo hotel con ID -> {}", id);
        try {
            return hotelesWebClient.get()
                    .uri("/api/hoteles/{id}", id)
                    .retrieve()
                    .bodyToMono(HotelResponse.class)
                    .block();
        }catch (WebClientResponseException ex){
            switch (ex.getStatusCode().value()){
                case 404 -> throw new EntityNotFoundException("No se encontró hotel con ID "+ id);
                default -> throw new RuntimeException("Error buscando hotel con ID "+ id, ex);
            }
        }
    }
}
