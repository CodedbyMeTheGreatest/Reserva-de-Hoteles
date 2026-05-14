package cl.duoc.dsy1103.empleados.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${services.reservas.url}")
    private String reservasBaseUrl;

    @Value("${services.hoteles.url}")
    private String hotelesBaseUrl;

    @Bean
    public WebClient reservasWebClient(){
        return WebClient.builder().baseUrl(reservasBaseUrl).build();
    }

    @Bean
    public WebClient hotelesWebClient(){
        return WebClient.builder().baseUrl(hotelesBaseUrl).build();
    }

}
