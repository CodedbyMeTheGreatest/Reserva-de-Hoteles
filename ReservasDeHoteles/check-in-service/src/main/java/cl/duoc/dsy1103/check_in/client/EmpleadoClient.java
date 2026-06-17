package cl.duoc.dsy1103.check_in.client;

import cl.duoc.dsy1103.check_in.dto.EmpleadoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
public class EmpleadoClient {
    private final WebClient empleadosWebClient;

    EmpleadoClient(WebClient empleadosWebClient) {
        this.empleadosWebClient = empleadosWebClient;
    }

    public EmpleadoResponse buscarEmpleadoPorId(Long id){
        try{
            return empleadosWebClient.get()
                    .uri("api/empleados/{id}", id)
                    .retrieve()
                    .bodyToMono(EmpleadoResponse.class)
                    .block();
        }catch (WebClientResponseException ex){
            switch (ex.getStatusCode().value()){
                case 404 -> throw new EntityNotFoundException("No se ha encontrado al empleado con ID -> " + id);
                default -> throw new RuntimeException("Error obteniendo empleado con ID -> " + id, ex);
            }
        }
    }

}
