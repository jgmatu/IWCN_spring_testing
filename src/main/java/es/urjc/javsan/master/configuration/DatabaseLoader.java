package es.urjc.javsan.master.configuration;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.javsan.master.customers.CustomerRepository;
import es.urjc.javsan.master.entities.Product;

@Service
public class DatabaseLoader {

	@Autowired
	private CustomerRepository customerRepository;
	
	@PostConstruct
	private void initDatabase() {	
		customerRepository.save(new Product(10, "Oswald","Cobblepot", 1.0f));
		customerRepository.save(new Product(11, "Selina","Kyle", 1.1f));
		customerRepository.save(new Product(12, "Bruce","Wayne", 1.0f));
		
		Iterable<Product> products = customerRepository.findAll();
		System.out.println("Búsqueda de todos los elementos: ");
		for (Product p : products) {
			System.out.println(p.toString());
		}
	}
	
	public void add(Product product) {
		customerRepository.save(product);
	}

	public void delete(int code) {
		customerRepository.delete(code);
	}

	public Product get(int code) {
		return customerRepository.findOne(code);
	}
	
	public ArrayList<Product> findAll() {
		ArrayList<Product> products = new ArrayList<>();
	
		for (Product p : customerRepository.findAll()) {
			products.add(p);
		}		
		return products;
	}	
}
