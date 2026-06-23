package cl.duoc.dsy1103.check_out;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CheckOutServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckOutServiceApplication.class, args);
	}

}
