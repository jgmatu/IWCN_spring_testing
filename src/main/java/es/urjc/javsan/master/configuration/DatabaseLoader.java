package es.urjc.javsan.master.configuration;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.javsan.master.customers.ProductRepository;
import es.urjc.javsan.master.entities.Product;

@Service
public class DatabaseLoader {

	@Autowired
	private ProductRepository customerRepository;
	
	@PostConstruct
	private void initDatabase() {	
		System.out.println("Data base initialized...");
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
	
	public void edit(Product product) {
		customerRepository.delete(product.getCode());
		customerRepository.save(product);
	}
	
	public ArrayList<Product> findAll() {
		ArrayList<Product> products = new ArrayList<>();
	
		for (Product p : customerRepository.findAll()) {
			products.add(p);
		}		
		return products;
	}	
}
