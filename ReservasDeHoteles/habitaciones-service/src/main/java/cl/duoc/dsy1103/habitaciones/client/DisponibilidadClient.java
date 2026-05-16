package cl.duoc.dsy1103.habitaciones.client;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import cl.duoc.dsy1103.habitaciones.dto.DisponibilidadResponse;
import lombok.extern.slf4j.Slf4j;
 
@Component
@Slf4j
public class DisponibilidadClient {

    @Autowired
    private WebClient webClient;

    public DisponibilidadResponse findDisponibilidadById(Long idDisponibilidad) {
        log.info("Obteniendo disponibilidad con ID -> {}", idDisponibilidad);
        try {
            return webClient.get()
                    .uri("http://localhost:8082/api/disponibilidades/" + idDisponibilidad)
                    .retrieve()
                    .bodyToMono(DisponibilidadResponse.class)
                    .block();
        } catch (WebClientResponseException ex) {
            switch (ex.getStatusCode().value()) {
                case 404 -> throw new NoSuchElementException("No se encontró disponibilidad con ID: " + idDisponibilidad);
                default -> throw new RuntimeException("Error obteniendo disponibilidad con ID -> {}" + idDisponibilidad);
            }
        }
    }
}
