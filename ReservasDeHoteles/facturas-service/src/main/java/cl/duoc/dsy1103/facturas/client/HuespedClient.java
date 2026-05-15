package cl.duoc.dsy1103.facturas.client;

import cl.duoc.dsy1103.facturas.dto.HuespedResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
public class HuespedClient {
    @Autowired
    private WebClient huespedesWebClient;

    public HuespedResponse buscarHuespedPorRun(String run){
        log.info("Obteniendo huésped con RUN -> {}", run);
        try {
            return huespedesWebClient.get()
                    .uri("/api/huespedes/run/{run}", run)
                    .retrieve()
                    .bodyToMono(HuespedResponse.class)
                    .block();
        }catch (WebClientResponseException ex){
            switch (ex.getStatusCode().value()){
                case 404 -> throw new EntityNotFoundException("No se encontró huésped con RUN -> "+ run);
                default -> throw new RuntimeException("Error buscando huésped con RUN -> "+ run, ex);
            }
        }
    }
}
