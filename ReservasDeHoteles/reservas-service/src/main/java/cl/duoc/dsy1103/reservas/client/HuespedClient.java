package cl.duoc.dsy1103.reservas.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import cl.duoc.dsy1103.reservas.dto.HuespedResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HuespedClient {

    @Autowired
    private WebClient webClient;
    
    public HuespedResponse findHuespedById(Long id) {
        log.info("Buscando huesped con ID -> {}", id);
        try {
            return webClient.get()
                    .uri("http://localhost:8083/api/huespedes/{id}", id)
                    .retrieve()
                    .bodyToMono(HuespedResponse.class)
                    .block();
        } catch (Exception ex) {
            log.error("Error obteniendo huesped con ID -> {}", id, ex);
            throw new RuntimeException("Error obteniendo huesped con ID -> " + id);
        }
    }

}
