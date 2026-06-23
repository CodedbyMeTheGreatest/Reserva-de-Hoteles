package cl.duoc.dsy1103.check_out.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${clients.empleados.url}")
    private String empleadosBaseUrl;

    @Value("${clients.reservas.url}")
    private String reservasBaseUrl;

    @Bean
    @LoadBalanced
    public WebClient empleadosWebClient(){
        return WebClient.builder()
                .baseUrl(empleadosBaseUrl)
                .build();
    }

    @Bean
    @LoadBalanced
    public WebClient reservasWebClient(){
        return WebClient.builder()
                .baseUrl(reservasBaseUrl)
                .build();
    }
}
