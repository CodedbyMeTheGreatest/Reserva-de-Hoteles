package cl.duoc.dsy1103.check_out.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${services.empleados.url}")
    private String empleadosBaseUrl;

    @Value("${services.reservas.url}")
    private String reservasBaseUrl;

    @Bean
    public WebClient empleadosWebClient(){
        return WebClient.builder()
                .baseUrl(empleadosBaseUrl)
                .build();
    }

    @Bean
    public WebClient reservasWebClient(){
        return WebClient.builder()
                .baseUrl(reservasBaseUrl)
                .build();
    }
}
