package webserver.gui;

//import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.BorderFactory;
//import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
		
		this.setSize(600, 300);
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
		
		//info.setBackground(Color.WHITE);
		info.setBorder(BorderFactory.createTitledBorder(null, "WebServer info", TitledBorder.LEFT, TitledBorder.TOP));
		info.setLayout(new GridLayout(3, 2, 100, -15));
		info.add(new Label("Server status: "));
		info.add(new Label("not running"));
		info.add(new Label("Server address: "));
		info.add(new Label("not running"));
		info.add(new Label("Server listening port: "));		
		info.add(new Label("not running"));
		//control.setBackground(Color.WHITE);
		control.setBorder(BorderFactory.createTitledBorder(null, "WebServer control", TitledBorder.LEFT, TitledBorder.TOP));
		
		//configuration.setBackground(Color.WHITE);
		configuration.setBorder(BorderFactory.createTitledBorder(null, "WebServer configuration", TitledBorder.LEFT, TitledBorder.TOP));
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new GUI();
            }
        });

	}

}
