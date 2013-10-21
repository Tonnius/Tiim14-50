package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

/**
 * Purchase pane + shopping cart table UI.
 */
public class PurchaseItemPanel extends JPanel {

	private static final Logger log = Logger.getLogger(PurchaseItemPanel.class);
	
    private static final long serialVersionUID = 1L;
    
    // Text field on the dialogPane
    private JComboBox nameField;
	private JTextField quantityField;
    private JTextField priceField;
    private JTextField barCodeField;

    private JButton addItemButton;

    // Warehouse model
    private SalesSystemModel model;
    
    private StockTableModel wareHouseModel;
    
    private Map<String, Long> wareHouseItems;
    
    /**
     * Constructs new purchase item panel.
     * 
     * @param model
     *            composite model of the warehouse and the shopping cart.
     */
    public PurchaseItemPanel(SalesSystemModel model) {
        this.model = model;
        this.wareHouseModel = model.getWarehouseTableModel();
        this.wareHouseItems = wareHouseModel.getItems();

        setLayout(new GridBagLayout());

        add(drawDialogPane(), getDialogPaneConstraints());
        add(drawBasketPane(), getBasketPaneConstraints());

        setEnabled(false);
    }

    // shopping cart pane
    private JComponent drawBasketPane() {

        // Create the basketPane
        JPanel basketPane = new JPanel();
        basketPane.setLayout(new GridBagLayout());
        basketPane.setBorder(BorderFactory.createTitledBorder("Shopping cart"));

        // Create the table, put it inside a scollPane,
        // and add the scrollPane to the basketPanel.
        JTable table = new JTable(model.getCurrentPurchaseTableModel());
        JScrollPane scrollPane = new JScrollPane(table);

        basketPane.add(scrollPane, getBacketScrollPaneConstraints());

        return basketPane;
    }

    // purchase dialog
    private JComponent drawDialogPane() {

        // Create the panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Product"));
        
        String[] wareHouseList = (String[]) wareHouseItems.keySet().toArray(new String[0]);
        		
        // Initialize the textfields
        nameField = new JComboBox(wareHouseList);
		quantityField = new JTextField("1");
        priceField = new JTextField();
        barCodeField = new JTextField();

        // Fill the dialog fields if an item is selected from menu
        nameField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	fillDialogFields();
            }
        });

        priceField.setEditable(false);
        barCodeField.setEditable(false);

        // == Add components to the panel

        // - item
        panel.add(new JLabel("Item:"));
        panel.add(nameField);
        
        // - bar code
        panel.add(new JLabel("Bar code:"));
        panel.add(barCodeField);

        // - amount
        panel.add(new JLabel("Amount:"));
        panel.add(quantityField);

        // - price
        panel.add(new JLabel("Price:"));
        panel.add(priceField);

        // Create and add the button
        addItemButton = new JButton("Add to cart");
        addItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addItemEventHandler();
            }
        });

        panel.add(addItemButton);

        return panel;
    }

    // Fill dialog with data from the "database".
    public void fillDialogFields() {
        StockItem stockItem = getStockItemByBarcode();
        if (stockItem != null) {
            String priceString = String.valueOf(stockItem.getPrice());
            priceField.setText(priceString);
            String barCodeString = String.valueOf(stockItem.getId());
            barCodeField.setText(barCodeString);
        } else {
            reset();
        }
    }

    // Search the warehouse for a StockItem matching the one
    // selected from the drop down box
    private StockItem getStockItemByBarcode() {
        try {
            String name = (String) nameField.getSelectedItem();
            Long id = wareHouseItems.get(name);
            return wareHouseModel.getItemById(id);
        } catch (NumberFormatException ex) {
            return null;
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    /**
     * Add new item to the cart.
     */
    public void addItemEventHandler() {
        // add chosen item to the shopping cart.
        StockItem stockItem = getStockItemByBarcode();
    	if (stockItem != null) {
            int quantity;
            try {
                quantity = Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException ex) {
                quantity = 1;
            } 
            // check if we have enough items in stock
            if (quantity > stockItem.getQuantity()) {
            	quantity = stockItem.getQuantity();
            	log.debug("Can't add more items than we have in stock, amount amended.");
            }
            model.getCurrentPurchaseTableModel()
                .addItem(new SoldItem(stockItem, quantity), stockItem.getQuantity());
        }
    }

    /**
     * Sets whether or not this component is enabled.
     */
    @Override
    public void setEnabled(boolean enabled) {
        this.addItemButton.setEnabled(enabled);
        this.nameField.setEnabled(enabled);
        this.quantityField.setEnabled(enabled);
    }

    /**
     * Reset dialog fields and fill in default selected item price.
     */
    public void reset() {
        quantityField.setText("1");
        fillDialogFields();
    }

    /*
     * === Ideally, UI's layout and behavior should be kept as separated as
     * possible. If you work on the behavior of the application, you don't want
     * the layout details to get on your way all the time, and vice versa. This
     * separation leads to cleaner, more readable and better maintainable code.
     * 
     * In a Swing application, the layout is also defined as Java code and this
     * separation is more difficult to make. One thing that can still be done is
     * moving the layout-defining code out into separate methods, leaving the
     * more important methods unburdened of the messy layout code. This is done
     * in the following methods.
     */

    // Formatting constraints for the dialogPane
    private GridBagConstraints getDialogPaneConstraints() {
        GridBagConstraints gc = new GridBagConstraints();

        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.2;
        gc.weighty = 0d;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.fill = GridBagConstraints.NONE;

        return gc;
    }

    // Formatting constraints for the basketPane
    private GridBagConstraints getBasketPaneConstraints() {
        GridBagConstraints gc = new GridBagConstraints();

        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.2;
        gc.weighty = 1.0;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.fill = GridBagConstraints.BOTH;

        return gc;
    }

    private GridBagConstraints getBacketScrollPaneConstraints() {
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0;
        gc.weighty = 1.0;

        return gc;
    }

}
