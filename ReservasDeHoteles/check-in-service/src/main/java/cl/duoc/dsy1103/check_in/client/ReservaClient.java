package cl.duoc.dsy1103.check_in.client;

import cl.duoc.dsy1103.check_in.dto.ReservaResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
public class ReservaClient {
    @Autowired
    private WebClient webClient;

    public ReservaResponse findReservaById(Long id){
        try{
            return webClient.get()
                    .uri("{id}", id)
                    .retrieve()
                    .bodyToMono(ReservaResponse.class)
                    .block();
        }catch (WebClientResponseException ex){
            switch (ex.getStatusCode().value()){
                case 404 -> throw new EntityNotFoundException("No se ha encontrado reserva con ID " + id);
                default -> throw new RuntimeException("Error al obtener reserva con ID " + id + ex);
            }
        }
    }
}
