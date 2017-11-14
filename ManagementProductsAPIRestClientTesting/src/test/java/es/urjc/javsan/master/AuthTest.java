package es.urjc.javsan.master;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import es.urjc.javsan.master.configuration.AuthProviderProducts;
import es.urjc.javsan.master.customers.UsersRepo;
import es.urjc.javsan.master.entities.User;

@RunWith(SpringRunner.class)
public class AuthTest {
	
	@Mock
	private UsersRepo usersDB;
	
	@InjectMocks
	private AuthProviderProducts authProducts;
	
	@Before
	public void init() {
		authProducts = new AuthProviderProducts();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void authTest() {
		User user = new User("user", "user1", null); 
		
		when(usersDB.findByName(user.getName())).then(answer ->{
			return user;
        });
		authProducts.authenticate(new AuthTestImp(user.getName(), "user1"));
	}
	
}
