package es.urjc.javsan.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import es.urjc.javsan.master.configuration.DatabaseLoader;
import es.urjc.javsan.master.entities.Product;

@Controller("/")
public class GreetingController {
		
	@Autowired
	private DatabaseLoader productDatabase;
	
	@PostMapping("/add")
    public ModelAndView greetingSubmit(@Valid Product product, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("form_product");
		}
		productDatabase.add(product.getCode(), product);
		return new ModelAndView("greeting_template");
    }
	
	@GetMapping("/add") 
	public ModelAndView add(Product product) {
		return new ModelAndView("form_product");
	}
	
	@RequestMapping("/")
	public ModelAndView greeting() {				
		return new ModelAndView("greeting_template");
	}
	
	@RequestMapping("/list")
	public ModelAndView list() {
		return new ModelAndView("list_products").addObject("productService", productDatabase.findAll());		
	}
	
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam int code) {
		productDatabase.delete(code);
		return new ModelAndView("greeting_template");
	}
	
	@RequestMapping("/product")
	public ModelAndView product(@RequestParam int code) {
		return new ModelAndView("product").addObject("product", productDatabase.get(code));		
	}
}
