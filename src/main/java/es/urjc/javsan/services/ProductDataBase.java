package es.urjc.javsan.services;

import org.springframework.data.repository.CrudRepository;

public interface ProductDataBase extends CrudRepository<Product, Integer> {
	
}
