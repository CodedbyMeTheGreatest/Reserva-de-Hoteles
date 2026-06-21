package cl.duoc.dsy1103.habitaciones.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig {

    @Value("${clients.disponibilidad.url}")
    private String disponibilidadBaseURL;

    @Value("${clients.hoteles.url}")
    private String hotelesBaseUrl;

    @Bean
    @LoadBalanced
    public WebClient disponibilidadesWebClient() {
        return WebClient.builder().baseUrl(disponibilidadBaseURL).build();
    }

    @Bean
    @LoadBalanced
    public WebClient hotelesWebClient() {
        return WebClient.builder().baseUrl(hotelesBaseUrl).build();
    }
}
