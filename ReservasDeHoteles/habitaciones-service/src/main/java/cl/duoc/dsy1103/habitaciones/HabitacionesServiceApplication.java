package cl.duoc.dsy1103.habitaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HabitacionesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitacionesServiceApplication.class, args);
	}

}
