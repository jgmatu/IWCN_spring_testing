package es.urjc.javsan.master;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class WebDriverTest {

	private static final String INVALID = "Invalid credentials";
	private static final String EMPTY = "There is no products";
	
	private WebDriver driver;

	@Before
	public void initalize() {
		driver = new FirefoxDriver();
		driver.get("http://localhost:1234/"); 
	}

	@Test
	public void pagesTest() {
		checkRoot();
		checkLogin();
		checkListProducts();
	}
	
	private void checkListProducts() {
		driver.findElement(By.id("list")).click();
		WebElement element = driver.findElement(By.tagName("p"));
		assertEquals(element.getText(), EMPTY);
	}
	
	private void checkRoot() {
		WebElement working = driver.findElement(By.tagName("a"));

		assertEquals(working.getText(),"here");
		working.click();		
	}
	
	private void checkLogin() {
		signIn("user", "password");
		checkBadLogin();
		signIn("user", "user1");
		checkSuccessLogin("user");		
	}
	
	private void signIn(String user, String password) {
		driver.findElement(By.id("user")).sendKeys(user);
		driver.findElement(By.id("password")).sendKeys(password);		
		driver.findElement(By.id("signIn")).click();		
	}
	
	private void checkBadLogin() {
		WebElement invalid = driver.findElement(By.tagName("p"));
		assertEquals(invalid.getText(), INVALID);		
	}

	private void checkSuccessLogin(String user) {
		WebElement valid = driver.findElement(By.id("logged"));
		assertEquals(valid.getText(), "Hello " + user);
	}
}
