package cl.duoc.dsy1103.reservas.client;

import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import cl.duoc.dsy1103.reservas.dto.EmpleadoResponse;

@Component
@Slf4j
public class EmpleadoClient {
    private final WebClient webClient;

    EmpleadoClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public EmpleadoResponse buscarEmpleadoPorId(Long id){
        try{
            return webClient.get()
                    .uri("http://localhost:8085/api/empleados/{id}", id)
                    .retrieve()
                    .bodyToMono(EmpleadoResponse.class)
                    .block();
        }catch (WebClientResponseException ex){
            switch (ex.getStatusCode().value()){
                case 404 -> throw new NoSuchElementException("No se ha encontrado al empleado con ID -> " + id);
                default -> throw new RuntimeException("Error obteniendo empleado con ID -> " + id, ex);
            }
        }
    }

    public EmpleadoResponse buscarEmpleadoPorRun(String run){
        try{
            return webClient.get()
                    .uri("/empleado/{run}", run)
                    .retrieve()
                    .bodyToMono(EmpleadoResponse.class)
                    .block();
        }catch (WebClientResponseException ex){
            switch (ex.getStatusCode().value()){
                case 404 -> throw new NoSuchElementException("No se ha encontrado al empleado con RUN -> " + run);
                default -> throw new RuntimeException("Error obteniendo empleado con RUN -> " + run, ex);
            }
        }
    }

}
