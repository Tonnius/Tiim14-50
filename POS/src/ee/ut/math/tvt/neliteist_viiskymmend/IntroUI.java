package ee.ut.math.tvt.neliteist_viiskymmend;

import java.awt.*;
import java.io.*;
import java.util.Properties;

import javax.swing.*;

public class IntroUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public IntroUI(String applicationProperties, String versionProperties) throws IOException {
		
		super("Team information");
		
		Properties properties = new Properties();
		
    	try {

    		properties.load(new FileInputStream(applicationProperties));
    		properties.load(new FileInputStream(versionProperties));

    	} catch (IOException ex) {
    		
    		ex.printStackTrace();
    		
        }
		
    	JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	JLabel information = new JLabel("<html><table border='0' width='100%'><tr><td><b>Team name:</b></td><td>" + properties.getProperty("team_name") + 
    			"</td></tr><tr><td>Team leader:</td><td>" + properties.getProperty("team_leader") + 
    			"</td></tr><tr><td>Team leader e-mail:</td><td>" + properties.getProperty("team_leader_email") + 
    			"</td></tr><tr><td>Team members:</td><td>" + properties.getProperty("team_members") + 
    			"</td></tr><tr><td>Team logo:</td><td><img border='0' src='" + properties.getProperty("team_logo") + "' width='150' height='150'>" +
    			"</td></tr><tr><td>Software version number:</td><td>" + properties.getProperty("build.number") + 
    			"</td></tr></table></html>");
    	
    	panel.add(information);
    	
    	add(panel);
    	setSize(400, 320);
    	setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    	
    	
	}
	
}