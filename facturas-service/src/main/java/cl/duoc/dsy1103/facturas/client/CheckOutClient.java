package cl.duoc.dsy1103.facturas.client;

import cl.duoc.dsy1103.facturas.dto.CheckOutResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
public class CheckOutClient {
    @Autowired
    private WebClient checkOutWebClient;

    public CheckOutResponse obtenerCheckOutPorId (Long id){
        log.info("Obteniendo check in con ID -> {}", id);
        try {
            return checkOutWebClient.get()
                    .uri("/api/check_out/{id}", id)
                    .retrieve()
                    .bodyToMono(CheckOutResponse.class)
                    .block();
        }catch (WebClientResponseException ex){
            switch (ex.getStatusCode().value()){
                case 404 -> throw new EntityNotFoundException("No se encontró check out con ID -> "+ id);
                default -> throw new RuntimeException("Error buscando check out con ID -> "+ id, ex);
            }
        }
    }
}
