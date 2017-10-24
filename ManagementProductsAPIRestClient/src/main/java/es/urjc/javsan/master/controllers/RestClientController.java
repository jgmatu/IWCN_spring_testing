package es.urjc.javsan.master.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import es.urjc.javsan.master.entities.Product;

@Controller
public class RestClientController {
		
	private static final String REST = "http://localhost:8080";
	
	@RequestMapping("/")
	public ModelAndView greeting() {				
		return new ModelAndView("greeting_template");
	}

	@GetMapping("/add") 
	public ModelAndView add(Product product) {
		return new ModelAndView("form_product");
	}
	
	@PostMapping("/add")
    public ModelAndView add(@Valid Product product, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("form_product");
		}
		
		RestTemplate restTemplate = new RestTemplate();
		String url = REST + "/add";
		String response = restTemplate.postForObject(url, product, String.class);
	
		return new ModelAndView("greeting_template").addObject("response", response);
    }

	@GetMapping("/edit") 
	public ModelAndView edit(@RequestParam int code) {
		RestTemplate restTemplate = new RestTemplate();
		String url = REST + "/product?code="+ String.valueOf(code);
		Product response = restTemplate.getForObject(url, Product.class);

		return new ModelAndView("form_edit").addObject("product", response);
	}
	
	@PostMapping("/edit")
    public ModelAndView editSubmit(@Valid Product product, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("form_edit");
		}	
		String url = REST + "/edit";
		
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.postForObject(url, product, String.class);
	
		return new ModelAndView("greeting_template").addObject("response", response);
    }
		
	@RequestMapping("/list")
	public ModelAndView list() {
		String url = REST + "/list";
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Product[]> responseEntity = restTemplate.getForEntity(url, Product[].class);
		Product[] products = responseEntity.getBody();
		
		return new ModelAndView("list_products").addObject("productService", products);		
	}
	
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam int code) {
		String url = REST + "/delete?code=" + String.valueOf(code);
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(url, String.class);

		return new ModelAndView("greeting_template").addObject("response", response);
	}
	
	@RequestMapping("/product")
	public ModelAndView product(@RequestParam int code) {
		RestTemplate restTemplate = new RestTemplate();
		String url = REST + "/product?code=" + String.valueOf(code);
		Product product = restTemplate.getForObject(url, Product.class);
		
		return new ModelAndView("product").addObject("product", product);		
	}
}