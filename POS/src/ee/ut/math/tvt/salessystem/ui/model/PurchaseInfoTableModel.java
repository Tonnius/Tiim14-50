package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;

/**
 * Purchase history details model.
 */
public class PurchaseInfoTableModel extends SalesSystemTableModel<SoldItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(PurchaseInfoTableModel.class);
	
	public PurchaseInfoTableModel() {
		super(new String[] { "Id", "Name", "Price", "Quantity", "Sum"});
	}

	@Override
	protected Object getColumnValue(SoldItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		case 4:
			return item.getPrice()*item.getQuantity();
		}
		throw new IllegalArgumentException("Column index out of range");
	}
	
    public void setValueAt(Object aValue, final int rowIndex, final int columnIndex) {
    	switch (columnIndex) {
		case 0:
			rows.get(rowIndex).setId((Long)aValue);
		case 1:
			rows.get(rowIndex).setName((String)aValue);
		case 2:
			rows.get(rowIndex).setPrice((Long)aValue);
		case 3:
			rows.get(rowIndex).setQuantity((Integer)aValue);
		case 4:
			return;
		}
    }

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final SoldItem item : rows) {
			buffer.append(item.getId() + "\t");
			buffer.append(item.getName() + "\t");
			buffer.append(item.getPrice() + "\t");
			buffer.append(item.getQuantity() + "\t");
			buffer.append(item.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}
	
    /**
     * Add new StockItem to table.
     */
    public void addItem(final SoldItem item, int stockQuantity) {
    	String s = "";
    	boolean exists = false;
    	int location = 0;
    	for (int i=0; i<getRowCount(); i++) 
    	{    
    	    s = getValueAt(i, 1).toString().trim();
    	    if(item.getName().equals(s))
    	    {
    	       exists = true;
    	       location = i;
    	       break;
    	    }
    	}
    	// add item to table
    	if (exists == false)
    		{
    			rows.add(item);
    		}
    	// if item already exists increase quantity
    	else 
    		{
    			Integer newQuantity = (Integer)getValueAt(location, 3) + item.getQuantity();
    			// check if we have enough in stock and lower quantity if needed
    			if (newQuantity > stockQuantity)
    				{
    					newQuantity = stockQuantity;
    					log.debug("Can't add more items than we have in stock, amount amended.");
    				}
    			setValueAt(newQuantity, location, 3);
    		}
    	log.debug("Added " + item.getName() + " quantity of " + item.getQuantity());
    	fireTableDataChanged();

    }
}
