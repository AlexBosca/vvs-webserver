package webserver.testcase;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import webserver.WebServer;


public class WebDriverTests {
	
	WebDriver driver;
	
	@BeforeClass
	public static void setUp() {
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
	
	@Test
	public void testMySite() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\alex_\\Downloads\\chromedriver_win32\\chromedriver.exe");
		
		driver = new ChromeDriver();
		
		driver.navigate().to("http://localhost:10008/");
		
		driver.findElement(By.name("button1")).click();
	}
	
	@After
	public void after() {
		driver.quit();
		System.out.println("Test finished!!");
	}
}
