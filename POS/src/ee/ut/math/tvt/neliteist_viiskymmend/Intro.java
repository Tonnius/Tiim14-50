package ee.ut.math.tvt.neliteist_viiskymmend;

import java.io.IOException;

public class Intro {
        
        public static void main(String[] args) throws IOException {
        	
        	IntroUI introUI = new IntroUI("application.properties","version.properties");
        	introUI.setVisible(true);
            introUI.setAlwaysOnTop(true);
            
        }

}

