package webserver.testcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\alex_\\Downloads\\chromedriver_win32\\chromedriver.exe");
		
		driver = new ChromeDriver();
	}
	
	@Ignore@Test
	public void testTitle() {
		driver.navigate().to("http://localhost:10008/");
		
		String actualTitle = driver.getTitle();
		String expectedTitle = "My VVS Web Page";
		
		assertEquals(expectedTitle, actualTitle);
	}
	
	@Ignore@Test
	public void testLoginUsernameField() {
		String expectedUsername = "some_Us3rN4me";
		String actualUsername;
		
		accessLoginPage();
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys(expectedUsername);
		
		actualUsername = username.getAttribute("value");
		
		assertEquals(expectedUsername, actualUsername);
	}
	
	@Ignore@Test
	public void testLoginPasswordField() {
		String expectedPassword = "P4ssw0rdd";
		String actualPassword;
		
		accessLoginPage();
		WebElement password = driver.findElement(By.className("password"));
		password.sendKeys(expectedPassword);
		
		actualPassword = password.getAttribute("value");
		
		assertEquals(expectedPassword, actualPassword);
			
	}
	
	@Ignore@Test
	public void testLoginButton() {
		accessLoginPage();
		driver.findElement(By.id("login")).click();
		assertEquals("http://localhost:10008/index.html", driver.getCurrentUrl());
	}
	
	@Ignore@Test
	public void testPasswordForgot() {
		accessLoginPage();
		driver.findElement(By.name("password")).click();
		assertEquals("http://localhost:10008/Button1/Button1.html#", driver.getCurrentUrl());
	}
	
	@Ignore@Test
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
