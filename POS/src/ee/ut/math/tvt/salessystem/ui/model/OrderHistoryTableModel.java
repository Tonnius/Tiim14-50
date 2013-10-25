package ee.ut.math.tvt.salessystem.ui.model;

import ee.ut.math.tvt.salessystem.domain.data.Order;

public class OrderHistoryTableModel extends SalesSystemTableModel<Order> {

	private static final long serialVersionUID = 1L;

	public OrderHistoryTableModel() {
		super(new String[] {"Date", "Total price"});
	}

	@Override
	protected Object getColumnValue(Order item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getDate();
		case 1:
			return item.getTotalPrice();
		case 2:
			return item.getOrderedItems();
		}
		throw new IllegalArgumentException("Column index out of range");
	}
	
	public void addOrder(Order order) {
		rows.add(order);
		fireTableDataChanged();
	}
	
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final Order item : rows) {
			buffer.append(item.getId() + "\t");
			buffer.append(item.getDate() + "\t");
			buffer.append(item.getTotalPrice() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}
	
}
