package cl.duoc.dsy1103.reservas.client;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import cl.duoc.dsy1103.reservas.dto.CheckInResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CheckinClient {

    @Autowired
    private WebClient webClient;

    public CheckInResponse buscarCheckInPorId (Long idCheckIn){
        log.info("Buscando check-in con ID -> {}",idCheckIn);
        try {
            return webClient.get()
                .uri("http://localhost:8086/api/checkins/{id}",idCheckIn)
                .retrieve()
                .bodyToMono(CheckInResponse.class)
                .block();
        } catch (WebClientResponseException ex) {
            switch (ex.getStatusCode().value()) {
                case 404 -> throw new NoSuchElementException("No se encontro check-in con ID -> " + idCheckIn);
                default -> throw new RuntimeException("Error al buscar check-in con ID -> " + idCheckIn, ex);
            }
        }
    }




}
