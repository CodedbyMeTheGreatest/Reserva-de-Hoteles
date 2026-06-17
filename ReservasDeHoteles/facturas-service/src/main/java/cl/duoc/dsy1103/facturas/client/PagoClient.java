package cl.duoc.dsy1103.facturas.client;

import cl.duoc.dsy1103.facturas.dto.PagoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
public class PagoClient {

    private final WebClient pagosWebClient;

    PagoClient(WebClient pagosWebClient) {
        this.pagosWebClient = pagosWebClient;
    }

    public PagoResponse buscarPagoPorId(Long id){
        log.info("Obteniendo pago con ID -> {}", id);
        try {
            return pagosWebClient.get()
                    .uri("/api/pagos/{id}", id)
                    .retrieve()
                    .bodyToMono(PagoResponse.class)
                    .block();
        }catch (WebClientResponseException ex){
            switch (ex.getStatusCode().value()){
                case 404 -> throw new EntityNotFoundException("No se obtuvó pago con ID -> "+ id);
                default -> throw new RuntimeException("Error buscando pago con ID -> "+ id, ex);
            }
        }
    }
}
