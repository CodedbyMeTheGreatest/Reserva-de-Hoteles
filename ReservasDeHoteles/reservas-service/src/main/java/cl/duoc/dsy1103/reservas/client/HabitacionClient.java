package cl.duoc.dsy1103.reservas.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import cl.duoc.dsy1103.reservas.dto.HabitacionResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HabitacionClient {

    @Autowired
    private WebClient webClient;

    public HabitacionResponse buscarHabitacionPorId (Long idHabitacion){
        log.info("Buscando habitacion con ID -> {}",idHabitacion);
        try{
            return webClient.get()
                .uri("http://localhost:8081/api/habitaciones/{id}",idHabitacion)
                .retrieve()
                .bodyToMono(HabitacionResponse.class)
                .block();
        }catch (WebClientResponseException ex){
            switch (ex.getStatusCode().value()) {
                case 404 -> throw new EntityNotFoundException("No se encontro habitacion con ID -> "+idHabitacion);            
                default -> throw new RuntimeException("Error obteniendo habitacion con ID -> "+idHabitacion);
            }
        }
    }

}
