package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.neliteist_viiskymmend.Intro;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;
import ee.ut.math.tvt.salessystem.ui.popups.ErrorPopup;

/**
 * Panel for modifying existing item data in warehouse
 */

public class ModifyItemPanel extends JPanel {
	
	private static final Logger log = Logger.getLogger(Intro.class);
	
	private static final long serialVersionUID = 1L;
	
	private static JComboBox barCodeField;
	private static JTextField nameField;
	private static JTextField priceField;
	private static JTextField quantityField;
	private static JTextField descField;
	
	private StockTableModel wareHouseModel;
    
    private List<String> wareHouseItems;
	
	public ModifyItemPanel(final SalesSystemModel model, final JDialog popupDialog) {
		this.wareHouseModel = model.getWarehouseTableModel();
		this.wareHouseItems = new ArrayList<String>();
		setLayout(new GridLayout(6, 2));
		setBorder(BorderFactory.createTitledBorder("Modify item data: "));
		
		
		for (final StockItem item : wareHouseModel.getRows()) {
			wareHouseItems.add(String.valueOf(item.getId()));
		}
		
		String[] wareHouseList = wareHouseItems.toArray(new String[wareHouseItems.size()]);
		
		// generate text fields
		nameField = new JTextField(10);
		barCodeField = new JComboBox(wareHouseList);
		descField = new JTextField();
		priceField = new JTextField();
		quantityField = new JTextField("1");
      
		// fill in dialog fields when item is selected
		barCodeField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	fillDialogFields();
            }
        });
		
		// button for adding items to table
		JButton addItemButton = new JButton("Submit");
		addItemButton.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					StockItem stockItem = new StockItem(Long.parseLong((String) barCodeField.getSelectedItem()),
							nameField.getText(),
							descField.getText(), 
							Double.parseDouble(priceField.getText()),
							Integer.parseInt(quantityField.getText()));
					model.getWarehouseTableModel().updateItem(stockItem);
					fillDialogFields();
				}
				catch (NumberFormatException ex) {
					log.debug("Invalid item data!");
					ErrorPopup.createPopup("Invalid item data, item not modified!");
					nameField.setText("");
					fillDialogFields();
				}
			}
		});
		
		// button for closing window
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				popupDialog.dispose();
			}
		});
      
		// add fields to panel
		add(new JLabel("Bar code:"));
		add(barCodeField);
		
		add(new JLabel("Name:"));
		add(nameField);
		
		add(new JLabel("Desciption:"));
		add(descField);
      
		add(new JLabel("Price:"));
		add(priceField);
      
		add(new JLabel("Quantity:"));
		add(quantityField);
      
		add(addItemButton);
		add(closeButton);
		
	}

	// fill in dialog fields with current item info
	public void fillDialogFields() {
        StockItem stockItem = getStockItemByBarcode();
        if (stockItem != null) {
        	nameField.setText(stockItem.getName());
        	descField.setText(stockItem.getDescription());
            String priceString = String.valueOf(stockItem.getPrice());
            priceField.setText(priceString);
            String quantityString = String.valueOf(stockItem.getQuantity());
            quantityField.setText(quantityString);
        }
    }

	// Search the warehouse for a StockItem with the selected bar code
	private StockItem getStockItemByBarcode() {
        try {
            Long id = Long.parseLong((String)barCodeField.getSelectedItem());
            return wareHouseModel.getItemById(id);
        } catch (NoSuchElementException ex) {
            return null;
        } catch (NullPointerException ex) {
        	return null;
        }
    }
}
