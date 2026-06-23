package cl.duoc.dsy1103.pagos.client;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import cl.duoc.dsy1103.pagos.dto.HabitacionResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HabitacionClient {

    @Autowired
    private WebClient webClient;

    public HabitacionResponse findHabitacionById (Long idHabitacion){
        log.info("Buscando habitacion con ID -> {}",idHabitacion);
        try{
            return webClient.get()
                .uri("lb://habitaciones-service/api/habitaciones/" + idHabitacion)
                .retrieve()
                .bodyToMono(HabitacionResponse.class)
                .block();
        }catch (WebClientResponseException ex){
            switch (ex.getStatusCode().value()) {
                case 404 -> throw new NoSuchElementException("No se encontro habitacion con ID -> "+idHabitacion);            
                default -> throw new RuntimeException("Error obteniendo habitacion con ID -> "+idHabitacion);
            }
        }
    }

}
