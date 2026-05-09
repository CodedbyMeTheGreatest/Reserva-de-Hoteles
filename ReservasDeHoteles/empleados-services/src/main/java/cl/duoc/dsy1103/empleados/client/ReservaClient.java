package cl.duoc.dsy1103.empleados.client;

import cl.duoc.dsy1103.empleados.dto.ReservaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@Slf4j
public class ReservaClient {

    @Autowired
    private WebClient webClient;

    @Bean
    public List<ReservaResponse> findReservasByEmployeeRun(String run){
        return webClient.get()
                .uri("empleados/run/{run}", run)
                .retrieve()
                .bodyToFlux(ReservaResponse.class)
                .collectList()
                .block();
    }
}
