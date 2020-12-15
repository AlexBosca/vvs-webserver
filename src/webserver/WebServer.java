package webserver;

import java.net.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Arrays;
//import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import webserver.gui.GUI;

import java.io.*;

public class WebServer extends Thread {
	
	
	static final String[] DEFAULT_PAGES = {"index.html",
											"index.htm",
											"default.html"};
	static final String[] ROOT_DIRECTORIES = {"www",
											"htdocs",
											"public_html"};
	
	public static WebServer webServer;
	
	private Socket clientSocket;
	private String directory;
	private static ServerSocket serverSocket;
	private static int port;
	public static State state;
	
	public static GUI gui;
	
	public enum State{
		Stopped(true, "Run"),
		Running(true, "Maintenance", "Stop"),
		Maintenance(true, "Run", "Stop");
		
		State(boolean expression, String... in){
			inputs = Arrays.asList(in);
			explicit = expression;
		}
		
		public State nextState(String input, State current) {
            if (inputs.contains(input)) {
                return map.getOrDefault(input, current);
            }
            return current;
        }
		
		final List<String> inputs;
		final static Map<String, State> map = new HashMap<String, State>();
		final boolean explicit;
		
		static {
			map.put("Run", State.Running);
			map.put("Maintenance", State.Maintenance);
			map.put("Stop", State.Stopped);
		}
	}

	public static void main(String[] args) {
		setUpGUI();
		startWebServer();
	}
	
	public static void startWebServer() {
		try {
			while(true) {
					switch(state) {
					case Maintenance:
						serverSocket = new ServerSocket(port);
						System.out.println("Connection Socket Created");
						gui.setServerState(GUI.MAINTENANCE);
						gui.setServerInfos(getAddress(), String.valueOf(getPort()));
						try {
							while (true) {
								gui.maintenanceModeState(true);
								gui.maintenanceFieldState(false);
								gui.portFieldState(false);
								gui.webRootFieldState(true);
								System.out.println("Waiting for Connection");
								webServer = new WebServer(serverSocket.accept(), gui.getMaintenanceDirectory().replace('\\', '/'));
							}
						} catch (IOException e) {
							if(state != State.Stopped && state != State.Running) {
								System.err.println("Accept failed.");
								System.exit(1);
							}
						}
						break;
					case Running:
						serverSocket = new ServerSocket(port);
						System.out.println("Connection Socket Created");
						gui.setServerState(GUI.RUNNING);
						gui.setServerInfos(getAddress(), String.valueOf(getPort()));
						try {
							while (true) {
								gui.maintenanceModeState(true);
								gui.maintenanceFieldState(true);
								gui.portFieldState(false);
								gui.webRootFieldState(false);
								System.out.println("Waiting for Connection");
								webServer = new WebServer(serverSocket.accept(), gui.getRootDirectory().replace('\\', '/'));
							}
						} catch (IOException e) {
							if(state != State.Stopped && state != State.Maintenance) {
								System.err.println("Accept failed.");
								System.exit(1);
							}
						}
						break;
					case Stopped:
						gui.maintenanceModeState(false);
						gui.maintenanceFieldState(true);
						gui.portFieldState(true);
						gui.webRootFieldState(true);
						gui.deselectCheckBox();
						break;
					default:
						break;
					}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private WebServer(Socket clientSocket, String directory) {
		this.clientSocket = clientSocket;
		this.directory = directory;
		start();
	}

	public void run() {
		System.out.println("New Communication Thread Started");

		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			BufferedOutputStream data = new BufferedOutputStream(clientSocket.getOutputStream());

			String requestedFile = null;
			
			String inputLine = in.readLine();
			StringTokenizer parse = new StringTokenizer(inputLine);
			String method = parse.nextToken().toUpperCase();
			requestedFile = parse.nextToken().toLowerCase();
			
			
			if(method.equals("GET") || method.equals("HEAD")) {
				
				if(requestedFile.endsWith("/")) {
						
						if(filesFoldersExists(directory + "/" + DEFAULT_PAGES[0])) {
							requestedFile += DEFAULT_PAGES[0];
						}
						else if(filesFoldersExists(directory + "/" + DEFAULT_PAGES[1])) {
							requestedFile += DEFAULT_PAGES[1];
						}
						else if(filesFoldersExists(directory + "/" + DEFAULT_PAGES[2])) {
							requestedFile += DEFAULT_PAGES[2];
						}
						else System.out.println("Nu exista");
				}
				
				
				File root = new File(directory);
				
				File file = new File(root, requestedFile);
				int fileLength = (int) file.length();
				String content = getContentType(requestedFile);
			
				if (method.equals("GET")) { // GET method so we return content
					
					byte[] fileData = readFileData(file, fileLength);
					
					// send HTTP Headers
					out.println("HTTP/1.1 200 OK");
					out.println("Server: Java HTTP Server");
					
					out.println("Content-type: " + content);
					out.println("Content-length: " + fileLength);
					out.println("");
					
					data.write(fileData, 0, fileLength);
					data.flush();
				}
				
			}

			out.close();
			in.close();
			data.close();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Problem with Communication Server");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void setUpGUI() {
		state = State.Stopped;
		gui = new GUI();
	}
	
	public static boolean filesFoldersExists(String path) {
		if(Files.exists(Paths.get(path), LinkOption.NOFOLLOW_LINKS))
			return true;
		else
			return false;
	}
	
	public static String getContentType(String fileRequested) {
		if (fileRequested.endsWith(".htm")  ||  fileRequested.endsWith(".html"))
			return "text/html";
		else
			return "text/plain";
	}
	
	public static byte[] readFileData(File file, int fileLength) throws IOException {
		FileInputStream fileIn = null;
		byte[] fileData = new byte[fileLength];
		
		try {
			fileIn = new FileInputStream(file);
			fileIn.read(fileData);
		} finally {
			if (fileIn != null) 
				fileIn.close();
		}
		
		return fileData;
	}
	
	public static void closeServer() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setPort(int getPort) {
		port = getPort;
	}
	
	public static int getPort() {
		return port;
	}
	
	public static String getAddress() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}
	
}