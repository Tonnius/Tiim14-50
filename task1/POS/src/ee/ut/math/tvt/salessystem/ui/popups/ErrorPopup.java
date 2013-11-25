package ee.ut.math.tvt.salessystem.ui.popups;

import javax.swing.JOptionPane;

public class ErrorPopup{

public static void createPopup(String errorMessage)
    {
        JOptionPane.showMessageDialog(null, errorMessage, "Error!", JOptionPane.INFORMATION_MESSAGE);
    }
}