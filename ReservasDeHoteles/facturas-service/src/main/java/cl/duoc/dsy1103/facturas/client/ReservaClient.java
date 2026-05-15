package cl.duoc.dsy1103.facturas.client;

import cl.duoc.dsy1103.facturas.dto.ReservaResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
public class ReservaClient {

    @Autowired
    private WebClient reservasWebClient;

    public ReservaResponse buscarReservaPorId(Long id){
        log.info("Obteniendo reservas con ID-> {}", id);
        try {
            return reservasWebClient.get()
                    .uri("/api/reservas/{id}", id)
                    .retrieve()
                    .bodyToMono(ReservaResponse.class)
                    .block();
        }catch (WebClientResponseException ex){
            switch (ex.getStatusCode().value()){
                case 404 -> throw new EntityNotFoundException("No se encontró reserva con ID -> "+ id);
                default -> throw new RuntimeException("Error buscando reserva con ID -> "+ id, ex);
            }
        }
    }
}
