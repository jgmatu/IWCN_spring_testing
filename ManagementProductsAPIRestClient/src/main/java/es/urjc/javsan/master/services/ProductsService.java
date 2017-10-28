package es.urjc.javsan.master.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.urjc.javsan.master.entities.Product;

@Service
public class ProductsService {

	private static final String REST = "http://localhost:8080";

	public Product get(int code) {
		String url = REST + "/product?code="+ String.valueOf(code);
		
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(url, Product.class);
	}
	
	public String insert(Product p) {
		String url = REST + "/add";
		RestTemplate restTemplate = new RestTemplate();

		return restTemplate.postForObject(url, p, String.class);
	}
	
	public String edit(Product p) {
		String url = REST + "/edit?code="+p.getCode();
		RestTemplate restTemplate = new RestTemplate();

		return restTemplate.postForObject(url, p, String.class);		
	}
	
	public String delete(int code) {
		String url = REST + "/delete?code=" + String.valueOf(code);		
		RestTemplate restTemplate = new RestTemplate();
		
		return restTemplate.getForObject(url, String.class);
	}
	
	public Product[] findAll() {
		String url = REST + "/list";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Product[]> responseEntity = restTemplate.getForEntity(url, Product[].class);

		return responseEntity.getBody();
	}
}
