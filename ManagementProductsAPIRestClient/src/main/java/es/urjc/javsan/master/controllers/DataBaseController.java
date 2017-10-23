package es.urjc.javsan.master.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import es.urjc.javsan.master.configuration.DatabaseLoader;
import es.urjc.javsan.master.entities.Product;

@Controller("/")
public class DataBaseController {
		
	private static final String REST = "http://localhost:8080";
	
	@Autowired
	private DatabaseLoader productDatabase;

	@RequestMapping("/")
	public ModelAndView greeting() {				
		return new ModelAndView("greeting_template");
	}

	@GetMapping("/add") 
	public ModelAndView edit(Product product) {
		return new ModelAndView("form_product");
	}
	
	@PostMapping("/add")
    public ModelAndView addSubmit(@Valid Product product, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("form_product");
		}
		
		// API Client add.
		RestTemplate restTemplate = new RestTemplate();
		String url = REST + "/add";
		String response = restTemplate.postForObject(url, product, String.class);
		System.out.println(response);
		return new ModelAndView("greeting_template");
    }

	@GetMapping("/edit") 
	public ModelAndView add(@RequestParam int code) {
		return new ModelAndView("form_edit")
				.addObject("product", productDatabase.get(code));
	}

	
	@PostMapping("/edit")
    public ModelAndView editSubmit(@Valid Product product, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("form_edit");
		}	
		productDatabase.edit(product);
		return new ModelAndView("greeting_template");
    }
		
	@RequestMapping("/list")
	public ModelAndView list() {
		return new ModelAndView("list_products")
				.addObject("productService", productDatabase.findAll());		
	}
	
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam int code) {
		productDatabase.delete(code);
		return new ModelAndView("greeting_template");
	}
	
	@RequestMapping("/product")
	public ModelAndView product(@RequestParam int code) {
		return new ModelAndView("product")
				.addObject("product", productDatabase.get(code));		
	}
}