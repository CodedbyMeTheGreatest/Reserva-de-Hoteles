package cl.duoc.dsy1103.empleados.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${clients.reservas.url}")
    private String reservasBaseUrl;

    @Value("${clients.hoteles.url}")
    private String hotelesBaseUrl;

    @Bean
    @LoadBalanced
    public WebClient reservasWebClient(){
        return WebClient.builder().baseUrl(reservasBaseUrl).build();
    }

    @Bean
    @LoadBalanced
    public WebClient hotelesWebClient(){
        return WebClient.builder().baseUrl(hotelesBaseUrl).build();
    }

}
