package es.urjc.javsan.master.customers;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.repository.CrudRepository;

import es.urjc.javsan.master.entities.Product;

@EntityScan("es.urjc.javsan.entity")
public interface ProductRepository extends CrudRepository<Product, Integer> {
	
}
