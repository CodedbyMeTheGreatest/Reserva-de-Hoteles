package cl.duoc.dsy1103.huespedes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HuespedesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuespedesServiceApplication.class, args);
	}

}
