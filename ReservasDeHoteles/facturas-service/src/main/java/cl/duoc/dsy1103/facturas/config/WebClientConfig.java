package cl.duoc.dsy1103.facturas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${services.huespedes.url}")
    private String huespedesBaseUrl;

    @Value("${services.pagos.url}")
    private String pagosBaseUrl;

    @Value("${services.reservas.url}")
    private String reservasBaseUrl;

    @Value("${services.check_in.url}")
    private String checkInBaseUrl;

    @Value("${services.check_out.url}")
    private String checkOutBaseUrl;

    @Bean
    public WebClient huespedesWebClient() {
        return WebClient.builder()
                .baseUrl(huespedesBaseUrl)
                .build();
    }

    @Bean
    public WebClient pagosWebClient() {
        return WebClient.builder()
                .baseUrl(pagosBaseUrl)
                .build();
    }

    @Bean
    public WebClient reservasWebClient() {
        return WebClient.builder()
                .baseUrl(reservasBaseUrl)
                .build();
    }

    @Bean
    public WebClient checkInWebClient() {
        return WebClient.builder()
                .baseUrl(checkInBaseUrl)
                .build();
    }

    @Bean
    public WebClient checkOutWebClient() {
        return WebClient.builder()
                .baseUrl(checkOutBaseUrl)
                .build();
    }
}
