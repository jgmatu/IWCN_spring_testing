package es.urjc.javsan.master.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import es.urjc.javsan.master.configuration.DBLoader;
import es.urjc.javsan.master.entities.Product;

@RestController
public class ProductsController {
		
	@Autowired
	private DBLoader productDB;
		
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addSubmit(@Valid @RequestBody Product product, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "Error bad product!";
		}
		productDB.add(product);
		return "Added!!";
    }

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editSubmit(@Valid @RequestBody Product product, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "Error bad edit!!";
		}	
		productDB.edit(product);
		return "Product Edited!!";
    }
		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> list() {
		return new ResponseEntity<List<Product>>(productDB.findAll(), HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam int code) {
		productDB.delete(code);
		return "Protuct Deleted!!";
	}
	
	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public ResponseEntity<Product> product(@RequestParam int code) {
		return new ResponseEntity<Product>(productDB.get(code), HttpStatus.ACCEPTED);
	}
}