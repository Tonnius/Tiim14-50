package ee.ut.math.tvt.neliteist_viiskymmend;

import java.io.IOException;

public class Intro {
        
        public static void main(String[] args) throws IOException {
        	
        	IntroUI introGUI = new IntroUI("application.properties","version.properties");
        	introGUI.setVisible(true);
            introGUI.setAlwaysOnTop(true);
            
        }

}

