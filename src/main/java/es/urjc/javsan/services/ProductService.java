package es.urjc.javsan.services;


import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	private ArrayList<Product> products;
	
	public ProductService() {
		products = new ArrayList<>();
	}
	
	public ArrayList<Product> getProducts(){
		return products;
	}
	
	public void add(int code, Product product) {
		boolean added = false;
		int pos = 0;
		
		while (pos < products.size() && !added) {
			if (products.get(pos).getCode() == code) {
				added = true;
			} else {
				pos++;
			}
		}
		if (!added) {
			products.add(product);			
		}
	}
	
	public void delete(int code) {
		boolean delete = false;
		int pos = 0;
		
		while (pos < products.size() && !delete) {
			if (products.get(pos).getCode() == code) {
				products.remove(pos);
				delete = true;
			} else {
				pos++;
			}
		}
	}
	
	public Product getProduct(int code) {
		Product product = null;		
		boolean found = false;
		int pos = 0;

		while (pos < products.size() && !found) {
			if (products.get(pos).getCode() == code) {
				product = products.get(pos);
				found = true;
			} else {
				pos++;
			}
		}
		return product;
	}
	
	@Override
	public String toString() {
		String format = "";
		
		for (int i = 0 ; i < products.size(); i++) {
			format += String.format("Product : %s\n", products.get(i).toString());
		}
		return format;
	}
}

