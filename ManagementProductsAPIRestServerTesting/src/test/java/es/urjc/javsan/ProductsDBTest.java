package es.urjc.javsan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import es.urjc.javsan.master.configuration.DBLoader;
import es.urjc.javsan.master.customers.ProductRepository;
import es.urjc.javsan.master.entities.Product;

@RunWith(SpringRunner.class)
public class ProductsDBTest {

	
	@Mock
	private ProductRepository productRepo;
	
	@InjectMocks
	private DBLoader productsDB;
	
	private static int NUM_PRODUCTS = 5;
	private static int PRICE = 10;
	
	
	@Before
	public void initBefore() {
		productsDB = new DBLoader();
		
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void ProductDBTest() {
		ArrayList<Product> products = getProductsTest();
		
	    when(productRepo.findAll()).then(answer ->{
        	return products;
        });
	    
	    when(productRepo.findOne(1)).then(answer ->{
        	return products.get(1);
        });

	    assertEquals(productsDB.findAll().size(), NUM_PRODUCTS);
	    assertNull(productsDB.get(-1));
	    
	    assertNotNull(productsDB.get(1));	   
	}
	
	private ArrayList<Product> getProductsTest() {
		ArrayList<Product> products = new ArrayList<>();
		
		for (int i = 0; i < NUM_PRODUCTS; i++) {
			products.add(new Product(i, "test", "test", i*PRICE));			
		}		
		return products;
	}
	
}
