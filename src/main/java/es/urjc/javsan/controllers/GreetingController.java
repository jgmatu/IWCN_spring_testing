package es.urjc.javsan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.urjc.javsan.services.Product;
import es.urjc.javsan.services.ProductService;

@Controller("/")
public class GreetingController {
	@Autowired
	private ProductService productService;

	@PostMapping("/add")
    public ModelAndView greetingSubmit(@ModelAttribute Product product) {
		productService.add(product.getCode(), product);
		return new ModelAndView("greeting_template");
    }
	
	@RequestMapping("/add") 
	public ModelAndView add() {
		return new ModelAndView("form_product").addObject("product", new Product());
	}
	
	@RequestMapping("/")
	public ModelAndView greeting() {				
		return new ModelAndView("greeting_template");
	}
	
	@RequestMapping("/list")
	public ModelAndView list() {
		return new ModelAndView("list_products").addObject("productService", productService.getProducts());		
	}
	
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam int code) {
		productService.delete(code);
		return new ModelAndView("greeting_template");
	}
	
	@RequestMapping("/product")
	public ModelAndView product(@RequestParam int code) {
		return new ModelAndView("product").addObject("product", productService.getProduct(code));		
	}
}
