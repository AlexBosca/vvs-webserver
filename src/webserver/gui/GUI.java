package webserver.gui;

import webserver.WebServer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
//import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String title = "VVS Web Server";
	
	private JPanel info, control, configuration;
	private JPanel upperSection;
	
	private JLabel notRunning1;
	private JLabel notRunning2;
	private JLabel notRunning3;
	
	private JButton startServer;
	private JCheckBox maintenanceMode;
	
	private JTextField tfPort;
	private JTextField tfRootDir;
	private JTextField tfMaintananceDir;
	
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
		notRunning1 = new JLabel("not running");
		notRunning1.setHorizontalAlignment(SwingConstants.RIGHT);
		notRunning1.setFont(notRunning1.getFont().deriveFont(notRunning1.getFont().getStyle() & ~Font.BOLD));
		
		notRunning2 = new JLabel("not running");
		notRunning2.setHorizontalAlignment(SwingConstants.RIGHT);
		notRunning2.setFont(notRunning2.getFont().deriveFont(notRunning2.getFont().getStyle() & ~Font.BOLD));
		
		notRunning3 = new JLabel("not running");
		notRunning3.setHorizontalAlignment(SwingConstants.RIGHT);
		notRunning3.setFont(notRunning3.getFont().deriveFont(notRunning3.getFont().getStyle() & ~Font.BOLD));
		
		JLabel status = new JLabel("Server status: ");
		status.setFont(status.getFont().deriveFont(status.getFont().getStyle() & ~Font.BOLD));
		
		JLabel address = new JLabel("Server address: ");
		address.setFont(address.getFont().deriveFont(address.getFont().getStyle() & ~Font.BOLD));
		
		JLabel port = new JLabel("Server listening port: ");
		port.setFont(port.getFont().deriveFont(port.getFont().getStyle() & ~Font.BOLD));
		
		
		info.add(status);
		info.add(notRunning1);
		info.add(address);
		info.add(notRunning2);
		info.add(port);		
		info.add(notRunning3);
		
		
		/**
		 * 	Setting control panel
		 */
		
		control.setBorder(BorderFactory.createTitledBorder(null, "WebServer control", TitledBorder.LEFT, TitledBorder.TOP));
		control.setLayout(new GridLayout(2, 1));
		
		startServer = new JButton("Start server");
		startServer.setPreferredSize(new Dimension(1, 1));
		startServer.setFocusPainted(false);
		
		maintenanceMode = new JCheckBox("Switch to maintenance mode");
		maintenanceMode.setEnabled(false);
		
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
		tfMaintananceDir = new JTextField(20);
		
		browseRoot = new JButton("...");
		browseMaintenance = new JButton("...");
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
		JPanel maintenencePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		maintenencePanel.add(tfMaintananceDir);
		maintenencePanel.add(browseMaintenance);
		maintenencePanel.add(cancel);
		
		mainPanel.add(maintenanceDir, BorderLayout.WEST);
		mainPanel.add(maintenencePanel, BorderLayout.CENTER);
		

		
		configuration.add(listenPanel);
		configuration.add(webPanel);
		configuration.add(mainPanel);
		
	}

	/*public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new GUI();
            }
        });

	}*/

}
