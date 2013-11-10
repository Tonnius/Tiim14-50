package ee.ut.math.tvt.salessystem.domain.data;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SALES")
public class Order implements Cloneable, DisplayableItem{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; //unused
	
	@Column(name = "TIME")
	private Date date;
	
	@Column(name = "SALE_SUM")
	private double totalPrice;
	
	private String orderedItems;

	public Order(Date date, List<SoldItem> orderedItems) {
		this.id = null;
		this.date = date;
		final StringBuffer buffer = new StringBuffer();
		buffer.append("ID\t");
		buffer.append("Name\t");
		buffer.append("Price\t");
		buffer.append("Quantity\t");
		buffer.append("Sum\n");
		for (final SoldItem item : orderedItems) {
			buffer.append(item.toString());
		}
		this.orderedItems = buffer.toString();
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
