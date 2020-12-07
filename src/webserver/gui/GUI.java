package webserver.gui;

import webserver.WebServer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
//import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String title = "VVS Web Server";
	public static final String MAINTENANCE = "maintenance";
	public static final String NOT_RUNNING = "not running";
	public static final String RUNNING = "running...";
	
	private JPanel info, control, configuration;
	private JPanel upperSection;
	
	private JLabel serverState;
	private JLabel serverAddress;
	private JLabel listeningPort;
	
	private JButton startServer;
	private JCheckBox maintenanceMode;
	
	private JTextField tfPort;
	private JTextField tfRootDir;
	private JTextField tfMaintenanceDir;
	
	//private JFileChooser fileChooser; 
	
	private JButton browseRoot;
	private JButton browseMaintenance;
	private JButton ok;
	private JButton cancel;
	
	public GUI() {
		super(title + " - " + "[" + WebServer.state + "]");
		
		setupPanels();
		
		this.setSize(550, 300);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		upperSection = new JPanel(new GridLayout(1, 2, 10, 0));
		upperSection.add(info);
		upperSection.add(control);
		
		this.setLayout(new GridLayout(2, 1, 0, 10));
		this.add(upperSection);
		this.add(configuration);
	}
	
	public void setupPanels() {
		info = 			new JPanel();
		control = 		new JPanel();
		configuration = new JPanel();
		
		/**
		 * 	Setting informations panel
		 */
		
		info.setBorder(BorderFactory.createTitledBorder(null, "WebServer info", TitledBorder.LEFT, TitledBorder.TOP));
		info.setLayout(new GridLayout(3, 2));
		serverState = new JLabel(NOT_RUNNING);
		serverState.setHorizontalAlignment(SwingConstants.RIGHT);
		serverState.setFont(serverState.getFont().deriveFont(serverState.getFont().getStyle() & ~Font.BOLD));
		
		serverAddress = new JLabel(NOT_RUNNING);
		serverAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		serverAddress.setFont(serverAddress.getFont().deriveFont(serverAddress.getFont().getStyle() & ~Font.BOLD));
		
		listeningPort = new JLabel(NOT_RUNNING);
		listeningPort.setHorizontalAlignment(SwingConstants.RIGHT);
		listeningPort.setFont(listeningPort.getFont().deriveFont(listeningPort.getFont().getStyle() & ~Font.BOLD));
		
		JLabel status = new JLabel("Server status: ");
		status.setFont(status.getFont().deriveFont(status.getFont().getStyle() & ~Font.BOLD));
		
		JLabel address = new JLabel("Server address: ");
		address.setFont(address.getFont().deriveFont(address.getFont().getStyle() & ~Font.BOLD));
		
		JLabel port = new JLabel("Server listening port: ");
		port.setFont(port.getFont().deriveFont(port.getFont().getStyle() & ~Font.BOLD));
		
		
		info.add(status);
		info.add(serverState);
		info.add(address);
		info.add(serverAddress);
		info.add(port);		
		info.add(listeningPort);
		
		
		/**
		 * 	Setting control panel
		 */
		
		control.setBorder(BorderFactory.createTitledBorder(null, "WebServer control", TitledBorder.LEFT, TitledBorder.TOP));
		control.setLayout(new GridLayout(2, 1));
		
		startServer = new JButton("Start server");
		startServer.setPreferredSize(new Dimension(1, 1));
		startServer.setFocusPainted(false);
		startServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(WebServer.state == WebServer.State.Stopped) {
					WebServer.state = WebServer.state.nextState("Run", WebServer.state);
					startServer.setText("Stop Server");
					WebServer.setPort();
					
				}
				else {
					WebServer.state = WebServer.state.nextState("Stop", WebServer.state);
					startServer.setText("Start server");
					WebServer.closeServer();
					setServerState(NOT_RUNNING);
					setServerInfos(NOT_RUNNING, NOT_RUNNING);
				}
				WebServer.gui.setTitle(title + " - " + "[" + WebServer.state + "]");
			}
		});
		
		maintenanceMode = new JCheckBox("Switch to maintenance mode");
		maintenanceMode.setEnabled(false);
		maintenanceMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				if(maintenanceMode.isSelected()) {
					WebServer.state = WebServer.state.nextState("Maintenance", WebServer.state);
				} else {
					WebServer.state = WebServer.state.nextState("Run", WebServer.state);
				}
				WebServer.closeServer();
				WebServer.gui.setTitle(title + " - " + "[" + WebServer.state + "]");
				WebServer.setPort();
			}
			
		});
		
		control.add(startServer);
		control.add(maintenanceMode);
		
		
		/**
		 * 	Setting configuration panel
		 */
		
		configuration.setBorder(BorderFactory.createTitledBorder(null, "WebServer configuration", TitledBorder.LEFT, TitledBorder.TOP));
		configuration.setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel listeningPort = new JLabel("Server listening port");
		listeningPort.setFont(listeningPort.getFont().deriveFont(listeningPort.getFont().getStyle() & ~Font.BOLD));
		
		JLabel rootDir = new JLabel("Web root directory");
		rootDir.setFont(rootDir.getFont().deriveFont(rootDir.getFont().getStyle() & ~Font.BOLD));
		
		JLabel maintenanceDir = new JLabel("Maintanance directory");
		maintenanceDir.setFont(maintenanceDir.getFont().deriveFont(maintenanceDir.getFont().getStyle() & ~Font.BOLD));
	
		tfPort = new JTextField(4);
		tfRootDir = new JTextField(20);
		tfMaintenanceDir = new JTextField(20);
		
		browseRoot = new JButton("...");
		browseRoot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int r = fileChooser.showSaveDialog(null);
				
				if(r == JFileChooser.APPROVE_OPTION) {
					tfRootDir.setText(fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
			
		});
		
		browseMaintenance = new JButton("...");
		browseMaintenance.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int r = fileChooser.showSaveDialog(null);
				
				if(r == JFileChooser.APPROVE_OPTION) {
					tfMaintenanceDir.setText(fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
			
		});
		
		ok = new JButton("Ok");
		cancel = new JButton("Cancel");
		
		JPanel listenPanel = new JPanel(new BorderLayout(18, 0));
		JPanel portPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		portPanel.add(tfPort);
		
		listenPanel.add(listeningPort, BorderLayout.WEST);
		listenPanel.add(portPanel, BorderLayout.CENTER);
		
		
		JPanel webPanel = new JPanel(new BorderLayout(30, 10));
		JPanel webRootPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		webRootPanel.add(tfRootDir);
		webRootPanel.add(browseRoot);
		webRootPanel.add(ok);
		
		webPanel.add(rootDir, BorderLayout.WEST);
		webPanel.add(webRootPanel, BorderLayout.CENTER);
		
		
		JPanel mainPanel = new JPanel(new BorderLayout(10, 0));
		JPanel maintenancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		maintenancePanel.add(tfMaintenanceDir);
		maintenancePanel.add(browseMaintenance);
		maintenancePanel.add(cancel);
		
		mainPanel.add(maintenanceDir, BorderLayout.WEST);
		mainPanel.add(maintenancePanel, BorderLayout.CENTER);
		

		
		configuration.add(listenPanel);
		configuration.add(webPanel);
		configuration.add(mainPanel);
		
	}

	public void maintenanceModeState(boolean state) {
		this.maintenanceMode.setEnabled(state);
	}
	
	public void portFieldState(boolean state) {
		this.tfPort.setEnabled(state);
	}
	
	public void webRootFieldState(boolean state) {
		this.tfRootDir.setEnabled(state);
		this.browseRoot.setEnabled(state);
	}
	
	public void maintenanceFieldState(boolean state) {
		this.tfMaintenanceDir.setEnabled(state);
		this.browseMaintenance.setEnabled(state);
	}
	
	public String getRootDirectory() {
		return tfRootDir.getText().trim();
	}
	
	public String getMaintenanceDirectory() {
		return tfMaintenanceDir.getText().trim();
	}
	
	public int getPort() {
		return Integer.parseInt(tfPort.getText().trim());
	}
	
	public void setServerInfos(String serverAddress, String serverPort) {
		this.serverAddress.setText(serverAddress);
		this.listeningPort.setText(serverPort);
	}
	
	public void setServerState(String state) {
		this.serverState.setText(state);
	}
	
//	public static void main(String[] args) {
//		javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//            	new GUI();
//            }
//        });
//
//	}

}
