package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;

import com.jgoodies.looks.windows.WindowsLookAndFeel;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

/**
 * Encapsulates everything that has to do with the order history tab (the tab
 * labeled "History" in the menu).
 */
public class HistoryTab {
    
	private SalesSystemModel model;
	
	private SalesSystemUI ui;

    public HistoryTab(SalesSystemModel model, SalesSystemUI ui) {
    	this.model = model;
    	this.ui = ui;
    } 
    
    public Component draw() {
        JPanel panel = new JPanel();
        // Sales history table
        JTable table = new JTable(model.getOrderHistoryTableModel());
        table.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		JTable target = (JTable)e.getSource();
        	    int row = target.getSelectedRow();
        	    createPopup(row);
        	    }
        	  
        	});

        
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);

        GridBagConstraints gc = new GridBagConstraints();
        GridBagLayout gb = new GridBagLayout();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0;
        gc.weighty = 1.0;

        panel.setLayout(gb);
        panel.add(scrollPane, gc);

        panel.setBorder(BorderFactory.createTitledBorder("Order history"));
        return panel;
    }
    
    public void createPopup(final int param) {
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				final JDialog popupDialog = new JDialog(ui, "Order info");
				popupDialog.setModal(true);
				popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				try 
				{
					UIManager.setLookAndFeel(new WindowsLookAndFeel());
				} catch (Exception e) {
					e.printStackTrace();
				}
				JPanel panel = new JPanel();
				panel.setLayout(new GridBagLayout());

				panel.setBorder(BorderFactory.createTitledBorder("Order info: "));
			
				@SuppressWarnings("unchecked")
				List<SoldItem> orderedItems = (List<SoldItem>) model.getOrderHistoryTableModel().getValueAt(param, 2);
				final StringBuffer buffer = new StringBuffer();
				buffer.append("ID\t");
				buffer.append("Name\t");
				buffer.append("Price\t");
				buffer.append("Quantity\t");
				buffer.append("Sum\n");
				for (final SoldItem item : orderedItems) {
					buffer.append(item.toString());
				}
				
				JTextArea orderInfo = new JTextArea();
				orderInfo.setText(buffer.toString());
				orderInfo.setEditable(false);
				orderInfo.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createLineBorder(Color.BLACK), 
				            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
				panel.add(orderInfo);
				
				popupDialog.add(panel);
				
				popupDialog.pack();
				popupDialog.setLocationByPlatform(true);
				popupDialog.setVisible(true);
				popupDialog.setResizable(false);
			}
		});
	}
}	
