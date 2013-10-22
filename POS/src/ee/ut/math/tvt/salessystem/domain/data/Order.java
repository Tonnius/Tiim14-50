package ee.ut.math.tvt.salessystem.domain.data;

import java.util.Date;
import java.util.List;

public class Order implements Cloneable, DisplayableItem{

	private Long id; //unused
	
	private Date date;
	
	private double totalPrice;
	
	private String orderedItems;

	public Order(Date date, List<SoldItem> orderedItems) {
		this.id = null;
		this.date = date;
		this.orderedItems = orderedItems.toString();
		this.totalPrice = 0;
		for (final SoldItem item : orderedItems) {
			totalPrice = totalPrice + item.getPrice()*item.getQuantity();
		}
	}
	
	@Override
	public Long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	
	public String getOrderedItems() {
		return orderedItems;
	}

	
	
}
