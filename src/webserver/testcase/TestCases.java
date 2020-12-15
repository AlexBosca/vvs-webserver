package webserver.testcase;

import webserver.WebServer;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.TemporaryFolder;

public class TestCases {
	
	private static String path_index_html, path_index_htm, path_style_css;
	
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	
	@BeforeClass
	public static void setup() {
		path_index_html = "www/mySite/index.html";
		path_index_htm = "www/index.htm";
		path_style_css = "www/style.css";
	}
	
	@Test
	public void filesFoldersExistsTrue() {
		
		boolean path_exists = WebServer.filesFoldersExists(path_index_html);
		
		assertTrue(path_exists);
	}
	
	@Test
	public void filesFoldersExistsFalse() {
		
		boolean path_exists = WebServer.filesFoldersExists(path_index_htm);
		
		assertFalse(path_exists);
	}
	
	@Test
	public void getContentTypeTextPlain() {
		String contentType = WebServer.getContentType(path_style_css);
		
		assertEquals(contentType, "text/plain");
	}
	
	@Test
	public void getContentTypeTextHtmlOrHtm() {
		String contentTypeHtml = WebServer.getContentType(path_index_html);
		String contentTypeHtm = WebServer.getContentType(path_index_htm);
		
		assertEquals(contentTypeHtml, "text/html");
		assertEquals(contentTypeHtm, "text/html");
	}
	
	@Test
	public void setterAndGetterPort() {
		WebServer.setPort(8800);
		assertEquals(8800, WebServer.getPort());
	}
	
	@Test
	public void getAddress() throws UnknownHostException {
		assertEquals(InetAddress.getLocalHost().getHostAddress(), WebServer.getAddress());
	}
	
	@Test
	public void readFileData() throws IOException {
		File tempHTML = tempFolder.newFile("tempHTML.html");
		FileOutputStream writeInFile = new FileOutputStream(tempHTML);
		String htmlContent = "<html>\r\n" + 
				"  <head>\r\n" + 
				"    <title>This is the title of the webpage!</title>\r\n" + 
				"  </head>\r\n" + 
				"  <body>\r\n" + 
				"    <p>This is an example paragraph. Anything in the <strong>body</strong> tag will appear on the page, just like this <strong>p</strong> tag and its contents.</p>\r\n" + 
				"  </body>\r\n" + 
				"</html>";
		
		byte[] htmlBytesToWrite = htmlContent.getBytes();
		int fileLength = (int) tempHTML.length();
		
		writeInFile.write(htmlBytesToWrite);
		writeInFile.flush();
		writeInFile.close();
		
		FileInputStream readFromFile = new FileInputStream(tempHTML);
		byte[] htmlBytes = new byte[fileLength];
		readFromFile.read(htmlBytes);
		readFromFile.close();
		
		Assert.assertArrayEquals(htmlBytes, WebServer.readFileData(tempHTML, fileLength));
	}
	
}
