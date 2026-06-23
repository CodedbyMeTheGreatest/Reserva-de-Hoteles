package cl.duoc.dsy1103.empleados.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Component
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info()
                    .title("API 2026 Empleados")
                    .version("1.1")
                    .description("Documentación de la API para el microservicio de empleados del hotel"))
                    .servers(
                        List.of(
                            new Server().url("http://localhost:8084").description("Servidor local"),
                            new Server().url("http://localhost:8080").description("Vía API Gateway")));
    }
}
