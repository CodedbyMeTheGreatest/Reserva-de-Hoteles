package cl.duoc.dsy1103.facturas.client;

import cl.duoc.dsy1103.facturas.dto.PagoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
public class PagoClient {

    @Autowired
    private WebClient webClient;

    public PagoResponse findPagoById(Long id){
        log.info("Obteniendo pago con ID -> {}", id);
        try {
            return webClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .bodyToMono(PagoResponse.class)
                    .block();
        }catch (WebClientResponseException ex){
            switch (ex.getStatusCode().value()){
                case 404 -> throw new EntityNotFoundException("No se obtuvó pago con ID "+ id);
                default -> throw new RuntimeException("Error obteniendo pago con ID "+ id, ex);
            }
        }
    }
}
