package es.urjc.javsan.master.controllers;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import es.urjc.javsan.master.entities.Product;
import es.urjc.javsan.master.services.ProductsService;

@Controller
public class RestClientController {
			
	@Autowired
	private ProductsService productSrv;

	private final String RESPONSE = "response";
	private final String PRODUCT = "product";
	private final String PRODUCTSERVICE = "productService";
	
	@RequestMapping("/")
	public ModelAndView root() {	
		ModelAndView model;
		
		if (isAuthenticathed()) {
			model = new ModelAndView("home");		
		}else {
			model = new ModelAndView("index"); 		
		}
		return model;
	}
	
	@RequestMapping("/login")
	public ModelAndView login() {				
		return new ModelAndView("login");
	}

	private boolean isAuthenticathed() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.filter(r-> !r.getAuthority().equals("ROLE_ANONYMOUS")).collect(Collectors.toList()).size() > 0;
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping("/home")
	public ModelAndView home() {				
		return new ModelAndView("home");
	}
	
	@RequestMapping("/denied")
	public ModelAndView denied() {				
		return new ModelAndView("denied");
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/add") 
	public ModelAndView add(Product product) {
		return new ModelAndView("form_product").addObject(RESPONSE, product);
	}

	@Secured({"ROLE_ADMIN"})
	@PostMapping("/add")
    public ModelAndView add(@Valid Product product, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("form_product");
		}	
		String response = productSrv.insert(product);
		return new ModelAndView("home").addObject(RESPONSE, response);
    }

	@Secured({"ROLE_ADMIN"})
	@GetMapping("/edit") 
	public ModelAndView edit(@RequestParam int code) {
		return new ModelAndView("form_edit").addObject(PRODUCT, productSrv.get(code));
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/edit")
    public ModelAndView editSubmit(@Valid Product product, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("form_edit");
		}	
		String response = productSrv.edit(product);
		return new ModelAndView("home").addObject(RESPONSE, response);
    }
		
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping("/list")
	public ModelAndView list() {		
		return new ModelAndView("list_products").addObject(PRODUCTSERVICE, productSrv.findAll());		
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam int code) {
		String response = productSrv.delete(code);
		return new ModelAndView("home").addObject(RESPONSE, response);
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping("/product")
	public ModelAndView product(@RequestParam int code) {		
		return new ModelAndView("product").addObject(PRODUCT, productSrv.get(code));		
	}
}