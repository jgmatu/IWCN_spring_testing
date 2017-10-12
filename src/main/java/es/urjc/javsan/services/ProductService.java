package es.urjc.javsan.services;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	private HashMap<Integer, Product> products;
	
	public ProductService() {
		products = new HashMap<>();
	}
	
	public ArrayList<Product> findAll(){
		ArrayList<Product> products = new ArrayList<>();
		
		for (Entry<Integer, Product> entry : this.products.entrySet()) {
			products.add(entry.getValue());
		}		
		return products;
	}
	
	public void add(int code, Product product) {
		this.products.put(code, product);
	}
	
	public void delete(int code) {
		this.products.remove(code);
	}
	
	public Product getProduct(int code) {
		return this.products.get(code);
	}
	
	@Override
	public String toString() {
		String format = "";

		for (Entry<Integer, Product> entry : this.products.entrySet()) {
			format += String.format("Product : %s\n", entry.getValue().toString());
		}		
		return format;
	}
}

