package webserver.testcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import webserver.WebServer;


public class WebDriverTests {
	
	WebDriver driver;
	
	@BeforeClass
	public static void setUpClass() {
		new Thread(() -> {
			System.out.println("Started Test!!");
			WebServer.setUpGUI();
			WebServer.gui.setMaintenanceField("D:\\VVS\\webserver\\www\\mantenance");
			WebServer.gui.setRootDirectory("D:\\VVS\\webserver\\www\\mySite");
			WebServer.gui.setPort("10008");
			WebServer.gui.clickStartButton();
			WebServer.startWebServer();
		}).start();
	}
	
	@Before
	public void setUp() {
		
		String path = System.getProperty("user.dir");
		String chromeDriverPath = "\\Chrome Driver\\chromedriver.exe";
		
		System.setProperty("webdriver.chrome.driver", path + chromeDriverPath);
		
		driver = new ChromeDriver();
	}
	
	@Test
	public void testTitle() {
		driver.navigate().to("http://localhost:10008/");
		
		String actualTitle = driver.getTitle();
		String expectedTitle = "My VVS Web Page";
		
		assertEquals(expectedTitle, actualTitle);
	}
	
	@Test
	public void testLoginUsernameField() {
		String expectedUsername = "some_Us3rN4me";
		String actualUsername;
		
		accessLoginPage();
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys(expectedUsername);
		
		actualUsername = username.getAttribute("value");
		
		assertEquals(expectedUsername, actualUsername);
	}
	
	@Test
	public void testLoginPasswordField() {
		String expectedPassword = "P4ssw0rdd";
		String actualPassword;
		
		accessLoginPage();
		WebElement password = driver.findElement(By.className("password"));
		password.sendKeys(expectedPassword);
		
		actualPassword = password.getAttribute("value");
		
		assertEquals(expectedPassword, actualPassword);
			
	}
	
	@Test
	public void testLoginButton() {
		accessLoginPage();
		driver.findElement(By.id("login")).click();
		assertEquals("http://localhost:10008/index.html", driver.getCurrentUrl());
	}
	
	@Test
	public void testPasswordForgot() {
		accessLoginPage();
		driver.findElement(By.name("password")).click();
		assertEquals("http://localhost:10008/Button1/Button1.html#", driver.getCurrentUrl());
	}
	
	@Test
	public void testRememberMeSelected() {
		accessLoginPage();
		assertTrue(Boolean.parseBoolean(driver.findElement(By.id("remember")).getAttribute("checked")));
	}
	
	@Test
	public void testRememberMeUnselected() {
		accessLoginPage();
		WebElement checkBox =  driver.findElement(By.id("remember"));
		checkBox.click();
		assertFalse(Boolean.parseBoolean(checkBox.getAttribute("checked")));
	}
	
	@Test
	public void testRegisterTextFields() {
		accessRegisterPage();
		List<WebElement> elements = driver.findElements(By.className("textfield"));
		String content = "TextField12321Content";
		
		for(WebElement we : elements) {
			we.sendKeys(content);
			assertEquals(content, we.getAttribute("value"));
		}
	}
	
	@Test
	public void testRegisterLink() {
		accessRegisterPage();
		driver.findElement(By.id("register")).click();
		assertEquals("http://localhost:10008/index.html", driver.getCurrentUrl());
	}
	
	@Test
	public void testSearchTextField() {
		accessRegisterPage();
		String content = "Something to search";
		WebElement element = driver.findElement(By.name("search"));
		element.sendKeys(content);
		assertEquals(content, element.getAttribute("value"));
	}
	
	@Test
	public void testSearchButton() {
		driver.navigate().to("http://localhost:10008/");
		driver.findElement(By.tagName("button")).click();
		assertEquals("http://localhost:10008/Button2/Button2.html", driver.getCurrentUrl());
	}
	
	@Test
	public void testHomeLink() {
		driver.navigate().to("http://localhost:10008/");
		driver.findElement(By.id("homelnk")).click();
		assertEquals("http://localhost:10008/#", driver.getCurrentUrl());
	}
	
	public void accessLoginPage() {
		driver.navigate().to("http://localhost:10008/Button1/Button1.html");
	}
	
	public void accessRegisterPage() {
		driver.navigate().to("http://localhost:10008/Button2/Button2.html");
	}
	
	@After
	public void after() {
		driver.quit();
		System.out.println("Test finished!!");
	}
}
