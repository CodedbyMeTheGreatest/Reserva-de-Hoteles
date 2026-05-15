package cl.duoc.dsy1103.facturas.client;

import cl.duoc.dsy1103.facturas.dto.CheckInResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
public class CheckInClient {
    @Autowired
    private WebClient checkInWebClient;

    public CheckInResponse obtenerCheckInPorId (Long id){
        log.info("Obteniendo check in con ID -> {}", id);
        try {
            return checkInWebClient.get()
                    .uri("/api/check_in/{id}", id)
                    .retrieve()
                    .bodyToMono(CheckInResponse.class)
                    .block();
        }catch (WebClientResponseException ex){
            switch (ex.getStatusCode().value()){
                case 404 -> throw new EntityNotFoundException("No se encontró check in con ID -> "+ id);
                default -> throw new RuntimeException("Error buscando check in con ID -> "+ id, ex);
            }
        }
    }
}
