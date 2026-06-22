package cl.duoc.dsy1103.pagos.client;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import cl.duoc.dsy1103.pagos.dto.HuespedResponse;
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
        } catch (WebClientResponseException ex) {
            switch (ex.getStatusCode().value()) {
                case 404 -> throw new NoSuchElementException("No se encontro huesped con ID -> " + id);
                default -> throw new RuntimeException("Error obteniendo huesped con ID -> " + id);
            }
        }
    }

}
