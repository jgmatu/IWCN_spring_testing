package es.urjc.javsan.master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import es.urjc.javsan.services.ProductService;

@SpringBootApplication
@ComponentScan("es.urjc.javsan.controllers")
public class Application {

	@Bean
	public ProductService productService() {
		return new ProductService();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

