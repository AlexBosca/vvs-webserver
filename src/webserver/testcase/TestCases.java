package webserver.testcase;

import webserver.WebServer;
import static org.junit.Assert.*;

import org.junit.*;

public class TestCases {
	
	private static String path_index_html, path_index_htm, path_style_css;
	
	@BeforeClass
	public static void setup() {
		path_index_html = "www/index.html";
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
}
