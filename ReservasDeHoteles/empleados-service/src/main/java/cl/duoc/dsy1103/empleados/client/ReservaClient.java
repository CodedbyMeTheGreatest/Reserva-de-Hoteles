package cl.duoc.dsy1103.empleados.client;

import cl.duoc.dsy1103.empleados.dto.ReservaResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Component
@Slf4j
public class ReservaClient {

    private final WebClient reservasWebClient;

    ReservaClient(WebClient reservasWebClient) {
        this.reservasWebClient = reservasWebClient;
    }

    public List<ReservaResponse> obtenerReservasPorRunEmpleado(String run){
        log.info("Obteniendo reservas por el empleado con RUN -> {}", run);
        try {
            return reservasWebClient.get()
                    .uri("/api/reservas/empleado/{run}", run)
                    .retrieve()
                    .bodyToFlux(ReservaResponse.class)
                    .collectList()
                    .block();
        }catch (WebClientResponseException ex){
            switch (ex.getStatusCode().value()){
                case 404 -> throw new EntityNotFoundException("No se encontraron reservas por el empleado con RUN "+ run);
                default -> throw new RuntimeException("Error buscando reservas por el empleado con RUN "+ run, ex);
            }
        }
    }
}
