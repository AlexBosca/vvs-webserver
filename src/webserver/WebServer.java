package webserver;

//import java.awt.*;
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
	
	static final int PORT = 10008;
	
	//static final File ROOT = new File(".");
	static final String[] DEFAULT_PAGES = {"index.html",
											"index.htm",
											"default.html"};
	static final String[] ROOT_DIRECTORIES = {"www",
											"htdocs",
											"public_html"};
	
	
	protected Socket clientSocket;
	protected static ServerSocket serverSocket;
	public static State state;
	private static String directory;
	private static boolean commandLineDir;
	
	protected enum State{
		Stopped(true, "Run"),
		Running(true, "Maintenance", "Stop"),
		Maintenance(true, "Run");
		
		State(boolean expression, String... in){
			inputs = Arrays.asList(in);
			explicit = expression;
		}
		
		State nextState(String input, State current) {
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
			map.put("Run", State.Running);
		}
	}

	public static void main(String[] args) throws IOException {
		state = State.Stopped;
		
		if(args.length == 1) {
			directory = args[0];
			commandLineDir = true;
		} else {
			commandLineDir = false;
		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new GUI();
            }
        });
		
		/*try {
			serverSocket = new ServerSocket(PORT);
			state = state.nextState("Run", state);
			System.out.println("Connection Socket Created");
			try {
				while (true) {
					System.out.println("Waiting for Connection");
					new WebServer(serverSocket.accept());
				}
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port: 10008.");
			System.exit(1);
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				System.err.println("Could not close port: 10008.");
				System.exit(1);
			} finally {
				state = State.Stopped;
			}
		}*/
	}

	private WebServer(Socket clientSoc) {
		clientSocket = clientSoc;
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
			
//			do {
//				System.out.println("Server: " + inputLine);
//				out.println(inputLine);
//
//				if (inputLine.trim().equals(""))
//					break;
//			} while ((inputLine = in.readLine()) != null);
//			
//			
//			try {
//				in.close();
//			} catch(IOException ex) {
//				ex.printStackTrace();
//			}
			
			if(method.equals("GET") || method.equals("HEAD")) {
				//String root_path = "";
				if(requestedFile.endsWith("/")) {
					if(WebServer.commandLineDir) {
						if(filesFoldersExists(directory + "/" + "a.html")) {
							requestedFile += /*directory + "/" +*/ "a.html";
							//ROOT = new File("./" + directory);
							//root_path += "./" + directory;
						}
					} else {
						if(filesFoldersExists(ROOT_DIRECTORIES[0] + "/" + DEFAULT_PAGES[0])) {
							requestedFile += /*ROOT_DIRECTORIES[0] + "/" +*/ DEFAULT_PAGES[0];
							//ROOT = new File("./" + ROOT_DIRECTORIES[0]);
							//root_path = "./" + ROOT_DIRECTORIES[0];
						}
					}
				}
				
				File root = (commandLineDir)?(new File("./" + directory)):(new File("./" + ROOT_DIRECTORIES[0]));
				
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
}