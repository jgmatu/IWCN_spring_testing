package es.urjc.javsan.master;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import es.urjc.javsan.master.services.ProductsService;

@RunWith(SpringRunner.class)
public class ClientTest {
	
	@InjectMocks
	private ProductsService productSrv;
	
	
	@Before
	public void init(){
		productSrv = new ProductsService();
		
		MockitoAnnotations.initMocks(this);
	}
}
