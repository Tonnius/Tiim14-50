package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.neliteist_viiskymmend.Intro;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.popups.ErrorPopup;

/**
 * Panel for adding new item to warehouse or
 * increasing quantity of existing item
 */

public class AddItemPanel extends JPanel{
	
	private static final Logger log = Logger.getLogger(Intro.class);

	private static final long serialVersionUID = 1L;

	private SalesSystemModel model;
	
	private static JTextField nameField;
	private static JTextField barCodeField;
	private static JTextField priceField;
	private static JTextField quantityField;
	private static JTextField descField;
	
	public AddItemPanel(final SalesSystemModel model, final JDialog popupDialog) {
		this.model = model;
		setLayout(new GridLayout(6, 2));
		setBorder(BorderFactory.createTitledBorder("Enter item: "));
		
		// generate text fields
		barCodeField = new JTextField(10);
		nameField = new JTextField();
		descField = new JTextField();
		priceField = new JTextField();
		quantityField = new JTextField("1");
      
		// button for adding items to table
		JButton addItemButton = new JButton("Submit");
		addItemButton.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					StockItem stockItem = new StockItem(Long.parseLong(barCodeField.getText()),
							nameField.getText(), descField.getText(), 
							Double.parseDouble(priceField.getText()),
							Integer.parseInt(quantityField.getText()));
					model.getWarehouseTableModel().addItem(stockItem);
					barCodeField.setText("");
					fillDialogFields();
				}
				catch (NumberFormatException ex) {
					log.debug("Invalid item data!");
					ErrorPopup.createPopup("Invalid item data, item not added!");
					barCodeField.setText("");
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
      
		// fill in dialog fields when barcode field loses focus
		barCodeField.addFocusListener(new FocusListener() 
		{
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				fillDialogFields();
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

	// when entered barcode is of an existing product fill in dialog fields
	// with current values
	public void fillDialogFields() {
		StockItem stockItem = getStockItemByBarcode();
			
		if (stockItem != null) {
			nameField.setText(stockItem.getName());
			String priceString = String.valueOf(stockItem.getPrice());
			descField.setText(stockItem.getDescription());
			priceField.setText(priceString);
			setEnabled(false);
		} else {
			nameField.setText("");
			descField.setText("");
			priceField.setText("");
			setEnabled(true);
		}
		quantityField.setText("1");
	}
	
	public void setEnabled(boolean enabled) {
		nameField.setEnabled(enabled);
		descField.setEnabled(enabled);
		priceField.setEnabled(enabled);
	}

	// Search the warehouse for a StockItem with the bar code entered
	// to the barCode textfield.
	private StockItem getStockItemByBarcode() {
		try {
			int code = Integer.parseInt(barCodeField.getText());
			return model.getWarehouseTableModel().getItemById(code);
		} catch (NumberFormatException ex) {
			return null;
		} catch (NoSuchElementException ex) {
			return null;
		}
	}
}


