package cl.duoc.dsy1103.facturas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${clients.huespedes.url}")
    private String huespedesBaseUrl;

    @Value("${clients.pagos.url}")
    private String pagosBaseUrl;

    @Value("${clients.reservas.url}")
    private String reservasBaseUrl;

    @Value("${clients.check_in.url}")
    private String checkInBaseUrl;

    @Value("${clients.check_out.url}")
    private String checkOutBaseUrl;

    @Bean
    @LoadBalanced
    public WebClient huespedesWebClient() {
        return WebClient.builder()
                .baseUrl(huespedesBaseUrl)
                .build();
    }

    @Bean
    @LoadBalanced
    public WebClient pagosWebClient() {
        return WebClient.builder()
                .baseUrl(pagosBaseUrl)
                .build();
    }

    @Bean
    @LoadBalanced
    public WebClient reservasWebClient() {
        return WebClient.builder()
                .baseUrl(reservasBaseUrl)
                .build();
    }

    @Bean
    @LoadBalanced
    public WebClient checkInWebClient() {
        return WebClient.builder()
                .baseUrl(checkInBaseUrl)
                .build();
    }

    @Bean
    @LoadBalanced
    public WebClient checkOutWebClient() {
        return WebClient.builder()
                .baseUrl(checkOutBaseUrl)
                .build();
    }
}
