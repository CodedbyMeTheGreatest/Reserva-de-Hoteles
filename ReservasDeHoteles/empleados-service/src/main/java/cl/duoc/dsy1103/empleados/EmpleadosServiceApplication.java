package cl.duoc.dsy1103.empleados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmpleadosServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpleadosServiceApplication.class, args);
	}

}
