package webserver.gui;

import java.awt.Dimension;
import java.awt.Font;
//import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel info, control, configuration;
	private JPanel upperSection;
	
	public GUI() {
		super("WebServer GUI");
		
		setupPanels();
		
		this.setSize(550, 300);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		upperSection = new JPanel(new GridLayout(1, 2, 10, 0));
		upperSection.add(info);
		upperSection.add(control);
		//upperSection.setBackground(Color.WHITE);
		
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
		
		//info.setBackground(Color.WHITE);
		info.setBorder(BorderFactory.createTitledBorder(null, "WebServer info", TitledBorder.LEFT, TitledBorder.TOP));
		info.setLayout(new GridLayout(3, 2));
		JLabel notRunning1 = new JLabel("not running");
		notRunning1.setHorizontalAlignment(SwingConstants.RIGHT);
		notRunning1.setFont(notRunning1.getFont().deriveFont(notRunning1.getFont().getStyle() & ~Font.BOLD));
		
		JLabel notRunning2 = new JLabel("not running");
		notRunning2.setHorizontalAlignment(SwingConstants.RIGHT);
		notRunning2.setFont(notRunning2.getFont().deriveFont(notRunning2.getFont().getStyle() & ~Font.BOLD));
		
		JLabel notRunning3 = new JLabel("not running");
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
		
		//configuration.setBackground(Color.WHITE);
		configuration.setBorder(BorderFactory.createTitledBorder(null, "WebServer configuration", TitledBorder.LEFT, TitledBorder.TOP));
		
		
		
		/**
		 * 	Setting control panel
		 */
		
		//control.setBackground(Color.WHITE);
		control.setBorder(BorderFactory.createTitledBorder(null, "WebServer control", TitledBorder.LEFT, TitledBorder.TOP));
		control.setLayout(new GridLayout(2, 1));
		
		JButton button = new JButton("Start server");
		button.setPreferredSize(new Dimension(1, 1));
		
		JCheckBox maintenanceMode = new JCheckBox("Switch to maintenance mode");
		maintenanceMode.setEnabled(false);
		
		control.add(button);
		control.add(maintenanceMode);
		
		
		/**
		 * 	Setting configuration panel
		 */
		
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new GUI();
            }
        });

	}

}
