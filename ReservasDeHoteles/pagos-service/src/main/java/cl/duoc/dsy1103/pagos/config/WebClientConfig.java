package cl.duoc.dsy1103.pagos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class WebClientConfig {
    @Value("${services.pagos.baseUrl}")
    private String baseUrl;

    @Bean
    public WebClient webClient(){
        return WebClient.builder().baseUrl(baseUrl).build();
    }
}
