package cl.duoc.dsy1103.check_out.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info().title("API 2026 Check Out")
                        .version("1.1")
                        .description("Documentación de la API para el microservicio de check out de reservas de habitaciones."))
                        .servers(List.of(
                                new Server().url("http://localhost:8088").description("Servidor local"),
                                new Server().url("http://localhost:8080").description("Vía API Gateway")));
    }
}
