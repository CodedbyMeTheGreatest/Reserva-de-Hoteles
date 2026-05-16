package cl.duoc.dsy1103.reservas.client;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import cl.duoc.dsy1103.reservas.dto.CheckOutResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CheckoutClient {

    @Autowired
    private WebClient webClient;

    public CheckOutResponse buscarCheckOutPorId (Long idCheckOut){
        log.info("Buscando check-out con ID -> {}",idCheckOut);
        try {
            return webClient.get()
                .uri("http://localhost:8087/api/checkouts/{id}",idCheckOut)
                .retrieve()
                .bodyToMono(CheckOutResponse.class)
                .block();
        } catch (WebClientResponseException ex) {
            switch (ex.getStatusCode().value()) {
                case 404 -> throw new NoSuchElementException("No se encontro check-out con ID -> " + idCheckOut);
                default -> throw new RuntimeException("Error al buscar check-out con ID -> " + idCheckOut, ex);
            }
        }
    }

}
